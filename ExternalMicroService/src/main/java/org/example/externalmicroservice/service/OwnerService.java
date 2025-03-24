package org.example.externalmicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.example.externalmicroservice.producer.OwnerProducer;
import org.example.externalmicroservice.security.OwnerDetails;
import org.example.models.owner.Owner;
import org.example.models.request.OwnerRequest;
import org.example.models.response.OwnerResponse;
import org.example.models.response.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService implements UserDetailsService {
    @Autowired
    private OwnerProducer ownerProducer;
    @Autowired
    private ResponseMapper responseMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OwnerResponse ownerResponse = ownerProducer.loadByName(username);
        Owner owner = responseMapper.mapResponseToOwner(ownerResponse);

        if (owner == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new OwnerDetails(owner);
    }

    public List<OwnerResponse> getAll() throws JsonProcessingException, InterruptedException {
        return ownerProducer.getAll();
    }

    public OwnerResponse getById(int id) throws JsonProcessingException, InterruptedException {
        return ownerProducer.getById(id);
    }

    public void create(OwnerRequest ownerRequest) throws JsonProcessingException {
        ownerProducer.create(ownerRequest);
    }

    public void update(OwnerRequest ownerRequest) throws JsonProcessingException {
        ownerProducer.update(ownerRequest);
    }

    public void delete(int id) throws JsonProcessingException {
        ownerProducer.delete(id);
    }
}
