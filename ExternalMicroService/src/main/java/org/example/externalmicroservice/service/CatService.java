package org.example.externalmicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.externalmicroservice.producer.CatProducer;
import org.example.models.request.CatRequest;
import org.example.models.response.CatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatService {
    private final CatProducer catProducer;

    @Autowired
    public CatService(CatProducer catProducer) {
        this.catProducer = catProducer;
    }

    public List<CatResponse> getAll() throws JsonProcessingException, InterruptedException {
        return catProducer.getAll();
    }

    public CatResponse getById(int id) throws JsonProcessingException, InterruptedException {
        return catProducer.getById(id);
    }

    public void create(CatRequest catRequest) throws JsonProcessingException {
        catProducer.create(catRequest);
    }

    public void update(CatRequest catRequest) throws JsonProcessingException {
        catProducer.update(catRequest);
    }

    public void delete(int id) throws JsonProcessingException {
        catProducer.delete(id);
    }
}
