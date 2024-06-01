package ru.kramskoi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.kramskoi.config.OwnerClient;
import ru.kramskoi.dto.OwnerClientDTO;
import ru.kramskoi.dto.OwnerDTO;
import ru.kramskoi.dto.OwnerMessage;
import ru.kramskoi.exception.OwnerNotFound;
import ru.kramskoi.repository.PersonRepository;

import java.security.Principal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerGatewayService {
    private final RabbitTemplate rabbitTemplate;
    private final OwnerClient ownerClient;
    private final PersonRepository personRepository;

    @Override
    public Long addOwner(OwnerDTO owner, Principal principal) {
        owner.setPersonID(personRepository.findByUsername(principal.getName()).getId());
        rabbitTemplate.convertAndSend("QueueOwnerAdd", owner);
        return owner.getId();
    }

    @Override
    public OwnerClientDTO getOwnerByID(Long id, Principal principal) {
        OwnerClientDTO owner = ownerClient.getOwner(id);
        if (owner == null ||
                !Objects.equals(owner.getPersonID(), personRepository.findByUsername(principal.getName()).getOwnerID())) {
            throw new OwnerNotFound();
        }
        return owner;
    }

    @Override
    public void updateOwner(OwnerDTO owner, Principal principal) {
        getOwnerByID(owner.getId(), principal);
        rabbitTemplate.convertAndSend("QueueOwnerUpdate", new OwnerMessage(
                owner.getId(),
                owner.getName(),
                owner.getBirthday(),
                personRepository.findByUsername(principal.getName()).getOwnerID()
        ));
    }

    @Override
    public void deleteOwner(Long id, Principal principal) {
        getOwnerByID(id, principal);
        rabbitTemplate.convertAndSend("QueueOwnerDelete", id);
    }
}
