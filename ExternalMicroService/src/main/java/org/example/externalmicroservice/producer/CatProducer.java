package org.example.externalmicroservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.models.request.CatRequest;
import org.example.models.response.CatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class CatProducer {
    private final String topic = "cat.request";

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CountDownLatch latch = new CountDownLatch(1);
    private CatResponse responseCat;
    private List<CatResponse> responseCats;

    @Autowired
    public CatProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public List<CatResponse> getAll() throws JsonProcessingException, InterruptedException {
        kafkaTemplate.send(topic, "getAll", null);
        latch.await(1, TimeUnit.SECONDS);
        return responseCats;
    }

    public CatResponse getById(int id) throws JsonProcessingException, InterruptedException {
        String message = objectMapper.writeValueAsString(id);
        kafkaTemplate.send(topic, "getById", message);
        latch.await(1, TimeUnit.SECONDS);
        return responseCat;
    }

    public void create(CatRequest catRequest) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(catRequest);
        kafkaTemplate.send(topic, "create", message);
    }

    public void update(CatRequest catRequest) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(catRequest);
        kafkaTemplate.send(topic, "update", message);
    }

    public void delete(int id) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(id);
        kafkaTemplate.send(topic, "delete", message);
    }

    @KafkaListener(topics = "cat.response")
    public void consumeMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        responseCat = objectMapper.readValue(record.value().toString(), CatResponse.class);
    }

    @KafkaListener(topics = "catlist.response")
    public void consumeListMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        responseCats = objectMapper.readValue(record.value().toString(), new TypeReference<List<CatResponse>>() {
        });
    }
}
