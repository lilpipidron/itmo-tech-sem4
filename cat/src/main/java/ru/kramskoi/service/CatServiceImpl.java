package ru.kramskoi.service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kramskoi.dto.*;
import ru.kramskoi.entity.Cat;
import ru.kramskoi.exception.CatNotFound;
import ru.kramskoi.mapper.CatMapper;
import ru.kramskoi.repository.CatRepository;

import java.util.List;

@Component
@EnableRabbit
public class CatServiceImpl implements CatService {
    final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    @Transactional
    @RabbitListener(queues = "QueueCatAdd")
    public void addCat(CatMessage catMessage) {
        catRepository.save(
                new Cat(catMessage.getName(),
                        catMessage.getBirthday(),
                        catMessage.getBreed(),
                        catMessage.getColor(),
                        catMessage.getOwnerId())
        );
    }

    @Override
    public CatClientDTO findCatByID(Long id) {
        Cat cat = catRepository.findById(id).orElseThrow(CatNotFound::new);

        return new CatClientDTO(
                cat.getId(),
                cat.getName(),
                cat.getBirthday(),
                cat.getBreed(),
                cat.getColor(),
                cat.getOwnerID()
        );
    }

    @Override
    public List<CatClientDTO> getFriendsById(Long id) {
        Cat cat = catRepository.findById(id).orElseThrow(CatNotFound::new);
        return cat.getFriends()
                .stream()
                .map(CatMapper::fromCatToDTOClient)
                .toList();
    }

    @Override
    @Transactional
    @RabbitListener(queues = "QueueCatAddFriend")
    public void addFriend(FriendMessage friend) {
        Cat cat = catRepository.findById(friend.getCatID()).orElseThrow(CatNotFound::new);
        Cat catFriend = catRepository.findById(friend.getFriendID()).orElseThrow(CatNotFound::new);

        cat.addFriend(catFriend);
        catFriend.addFriend(cat);

        catRepository.save(cat);
        catRepository.save(catFriend);
    }

    @Override
    public List<CatClientDTO> getAllCatsByOwnerId(Long id) {
        return catRepository.getCatsByOwnerId(id)
                .stream()
                .map(CatMapper::fromCatToDTOClient)
                .toList();
    }

    @Override
    public List<CatClientDTO> getCatsByColorOrBreed(Color color, Breed breed) {
        if (color == null && breed == null) {
            return null;
        }

        if (breed == null) {
            return catRepository.getCatsByColor(color)
                    .stream()
                    .map(CatMapper::fromCatToDTOClient)
                    .toList();
        }

        if (color == null) {
            return catRepository.getCatsByBreed(breed)
                    .stream()
                    .map(CatMapper::fromCatToDTOClient)
                    .toList();
        }

        return catRepository.getCatsByColorOrBreed(color, breed)
                .stream()
                .map(CatMapper::fromCatToDTOClient)
                .toList();
    }

    @Override
    @Transactional
    @RabbitListener(queues = "QueueCatUpdate")
    public void updateCat(CatMessage catMessage) {
        Cat cat = catRepository.findById(catMessage.getId()).orElseThrow(CatNotFound::new);

        cat.setColor(catMessage.getColor());
        cat.setBreed(catMessage.getBreed());
        cat.setName(catMessage.getName());
        cat.setBirthday(catMessage.getBirthday());
        catRepository.save(cat);
    }

    @Override
    @Transactional
    @RabbitListener(queues = "QueueCatDelete")
    public void deleteCat(CatMessage catMessage) {
        Cat cat = catRepository.findById(catMessage.getId()).orElseThrow(CatNotFound::new);
        catRepository.delete(cat);
    }
}
