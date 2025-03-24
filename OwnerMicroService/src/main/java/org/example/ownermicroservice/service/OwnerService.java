package org.example.ownermicroservice.service;

import org.example.models.owner.Owner;
import org.example.models.owner.OwnerDto;

import java.util.List;

public interface OwnerService {
    OwnerDto getById(int id);

    List<OwnerDto> getAll();

    Owner create(OwnerDto ownerDto);

    Owner update(OwnerDto ownerDto);

    void delete(int id);
}
