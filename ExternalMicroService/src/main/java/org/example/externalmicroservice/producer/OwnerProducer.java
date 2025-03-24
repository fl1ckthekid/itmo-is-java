package org.example.externalmicroservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.models.request.OwnerRequest;
import org.example.models.response.OwnerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class OwnerProducer {
    private final String topic = "owner.request";

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CountDownLatch latch = new CountDownLatch(1);
    private OwnerResponse responseOwner;
    private List<OwnerResponse> responseOwners;

    @Autowired
    public OwnerProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public List<OwnerResponse> getAll() throws JsonProcessingException, InterruptedException {
        kafkaTemplate.send(topic, "getAll", null);
        latch.await(1, TimeUnit.SECONDS);
        return responseOwners;
    }

    public OwnerResponse getById(int id) throws JsonProcessingException, InterruptedException {
        String message = objectMapper.writeValueAsString(id);
        kafkaTemplate.send(topic, "getById", message);
        latch.await(1, TimeUnit.SECONDS);
        return responseOwner;
    }

    public void create(OwnerRequest ownerRequest) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(ownerRequest);
        kafkaTemplate.send(topic, "create", message);
    }

    public void update(OwnerRequest ownerRequest) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(ownerRequest);
        kafkaTemplate.send(topic, "update", message);
    }

    public void delete(int id) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(id);
        kafkaTemplate.send(topic, "delete", message);
    }

    public OwnerResponse loadByName(String name) throws InterruptedException {
        kafkaTemplate.send(topic, "loadByName", name);
        latch.await(1, TimeUnit.SECONDS);
        return responseOwner;
    }

    @KafkaListener(topics = "owner.response")
    public void consumeMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        responseOwner = objectMapper.readValue(record.value().toString(), OwnerResponse.class);
    }

    @KafkaListener(topics = "ownerlist.response")
    public void consumeListMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        responseOwners = objectMapper.readValue(record.value().toString(), new TypeReference<List<OwnerResponse>>() {
        });
    }
}
