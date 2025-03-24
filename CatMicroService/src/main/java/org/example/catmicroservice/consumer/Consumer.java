package org.example.catmicroservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.models.cat.CatDto;
import org.example.catmicroservice.service.CatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private final ObjectMapper objectMapper;
    private final CatServiceImpl catService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public Consumer(ObjectMapper objectMapper, CatServiceImpl catService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.catService = catService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "cat.request")
    public void consumeMessage(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        switch (record.key()) {
            case "getAll" ->
                    kafkaTemplate.send("catlist.response", objectMapper.writeValueAsString(catService.getAll()));
            case "getById" -> {
                Integer id = objectMapper.readValue(record.value().toString(), Integer.class);
                kafkaTemplate.send("cat.response", objectMapper.writeValueAsString(catService.getById(id)));
            }
            case "create" -> {
                CatDto catDto = objectMapper.readValue(record.value().toString(), CatDto.class);
                catService.create(catDto);
            }
            case "update" -> {
                CatDto catDto = objectMapper.readValue(record.value().toString(), CatDto.class);
                catService.update(catDto);
            }
            case "delete" -> {
                Integer id = objectMapper.readValue(record.value().toString(), Integer.class);
                catService.delete(id);
            }
        }
    }
}
