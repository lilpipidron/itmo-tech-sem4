package ru.kramskoi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.kramskoi.config.CatClient;
import ru.kramskoi.dto.*;
import ru.kramskoi.entity.Person;
import ru.kramskoi.exception.CatNotFound;
import ru.kramskoi.repository.PersonRepository;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CatServiceImpl implements CatGatewayService {
    private final RabbitTemplate rabbitTemplate;
    private final CatClient catClient;
    private final PersonRepository personRepository;

    @Override
    public void addCat(CatDTO catDTO, Principal principal) {
        CatMessage catMessage = new CatMessage(
                catDTO.getId(),
                catDTO.getName(),
                catDTO.getBirthday(),
                catDTO.getBreed(),
                catDTO.getColor(),
                catDTO.getOwnerId()
        );

        rabbitTemplate.convertAndSend("QueueCatAdd", catMessage);
    }

    @Override
    public void deleteCat(Long id, Principal principal) {
        getCat(id, principal);
        rabbitTemplate.convertAndSend("QueueCatDelete", id);
    }

    @Override
    public void updateCat(CatDTO catDTO, Principal principal) {
        getCat(catDTO.getId(), principal);
        CatMessage message = new CatMessage(
                catDTO.getId(),
                catDTO.getName(),
                catDTO.getBirthday(),
                catDTO.getBreed(),
                catDTO.getColor(),
                catDTO.getOwnerId()
        );
        rabbitTemplate.convertAndSend("QueueCatUpdate", message);

    }

    @Override
    public CatDTO getCat(Long id, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        CatClientDTO cat = catClient.getCatById(id);
        if (cat == null ||
                !Objects.equals(cat.getOwnerId(), person.getOwnerID())
                        && !Objects.equals(person.getRoles(), "ADMIN")) {
            throw new CatNotFound();
        }

        return new CatDTO(
                cat.getId(),
                cat.getName(),
                cat.getBirthday(),
                cat.getBreed(),
                cat.getColor(),
                cat.getOwnerId()
        );
    }

    @Override
    public void addFriend(FriendMessage friendMessage, Principal principal) {
        getCat(friendMessage.getCatID(), principal);
        rabbitTemplate.convertAndSend("QueueCatAddFriend", friendMessage);
    }

    @Override
    public List<CatClientDTO> getFriends(Long id, Principal principal) {
        getCat(id, principal);
        List<CatClientDTO> friends = catClient.getFriendsById(id);
        if (friends == null) {
            throw new CatNotFound();
        }

        return friends;
    }

    @Override
    public List<CatClientDTO> getAllCatsByOwnerId(Long id, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        if (!Objects.equals(person.getRoles(), "ADMIN") && !Objects.equals(person.getOwnerID(), id)) {
            throw new CatNotFound();
        }

        List<CatClientDTO> cats = catClient.getAllCatsByOwnerId(id);
        if (cats == null) {
            throw new CatNotFound();
        }

        return cats;
    }

    @Override
    public List<CatClientDTO> getCatsByColorOrBreed(Color color, Breed breed, Long ownerID, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        if (!Objects.equals(person.getRoles(), "ADMIN") && !Objects.equals(person.getOwnerID(), ownerID)) {
            throw new CatNotFound();
        }

        List<CatClientDTO> cats =  catClient.getCatsByColorOrBreedAndOwnerId(color, breed, ownerID);
        if (cats == null) {
            throw new CatNotFound();
        }

        return cats;
    }
}
