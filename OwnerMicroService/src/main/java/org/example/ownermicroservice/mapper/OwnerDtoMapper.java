package org.example.ownermicroservice.mapper;

import org.example.models.owner.Owner;
import org.example.models.owner.OwnerDto;
import org.springframework.stereotype.Service;

@Service
public class OwnerDtoMapper {
    public OwnerDto mapToOwnerDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setOwnerId(owner.getOwnerId());
        ownerDto.setName(owner.getName());
        ownerDto.setBirthDate(owner.getBirthDate());
        ownerDto.setRole(owner.getRole());
        ownerDto.setCats(owner.getCats());
        return ownerDto;
    }

    public Owner mapDtoToOwner(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setOwnerId(ownerDto.getOwnerId());
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        owner.setRole(ownerDto.getRole());
        owner.setCats(ownerDto.getCats());
        return owner;
    }
}