package org.example.ownermicroservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.models.owner.Owner;
import org.example.models.owner.OwnerDto;
import org.example.ownermicroservice.repository.OwnerRepository;
import org.example.ownermicroservice.service.OwnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private final ObjectMapper objectMapper;
    private final OwnerServiceImpl ownerService;
    private final OwnerRepository ownerRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public Consumer(ObjectMapper objectMapper, OwnerServiceImpl ownerService, OwnerRepository ownerRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.ownerService = ownerService;
        this.ownerRepository = ownerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "owner.request")
    public void consumeMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        switch (record.key()) {
            case "getAll" ->
                    kafkaTemplate.send("ownerlist.response", objectMapper.writeValueAsString(ownerService.getAll()));
            case "getById" -> {
                Integer id = objectMapper.readValue(record.value().toString(), Integer.class);
                kafkaTemplate.send("owner.response", objectMapper.writeValueAsString(ownerService.getById(id)));
            }
            case "create" -> {
                OwnerDto ownerDto = objectMapper.readValue(record.value().toString(), OwnerDto.class);
                ownerService.create(ownerDto);
            }
            case "update" -> {
                OwnerDto ownerDto = objectMapper.readValue(record.value().toString(), OwnerDto.class);
                ownerService.update(ownerDto);
            }
            case "delete" -> {
                Integer id = objectMapper.readValue(record.value().toString(), Integer.class);
                ownerService.delete(id);
            }
            case "loadByName" -> {
                String name = record.value().toString();
                Owner owner = ownerRepository.findByName(name);
                kafkaTemplate.send("owner.response", objectMapper.writeValueAsString(owner));
            }
        }
    }
}
