package ru.kramskoi.service;

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
public class CatGatewayServiceImpl implements CatGatewayService {
    private final RabbitTemplate rabbitTemplate;
    private final CatClient catClient;
    private final PersonRepository personRepository;

    public CatGatewayServiceImpl(RabbitTemplate rabbitTemplate, CatClient catClient, PersonRepository personRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.catClient = catClient;
        this.personRepository = personRepository;
    }

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
    public void deleteCat(long id, Principal principal) {
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
    public CatDTO getCat(long id, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        CatClientDTO cat;
        try {
             cat = catClient.getCatById(id);
        } catch (Exception e) {
            throw new CatNotFound();
        }

        if (cat == null ||
                !Objects.equals(cat.getOwnerId(), person.getOwnerID())
                        && !Objects.equals(person.getRoles(), "ROLE_ADMIN")) {
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
    public List<CatClientDTO> getFriends(long id, Principal principal) {
        getCat(id, principal);
        return catClient.getFriendsById(id);
    }

    @Override
    public List<CatClientDTO> getAllCatsByOwnerId(long id, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        if (!Objects.equals(person.getRoles(), "ROLE_ADMIN") && person.getOwnerID() != id) {
            throw new CatNotFound();
        }

        return catClient.getAllCatsByOwnerId(id);
    }

    @Override
    public List<CatClientDTO> getCatsByColorOrBreed(Color color, Breed breed, Long ownerID, Principal principal) {
        Person person = personRepository.findByUsername(principal.getName());
        if (!Objects.equals(person.getRoles(), "ROLE_ADMIN") && !Objects.equals(person.getOwnerID(), ownerID)) {
            throw new CatNotFound();
        }

        return catClient.getCatsByColorOrBreedAndOwnerId(color, breed, ownerID);
    }
}
