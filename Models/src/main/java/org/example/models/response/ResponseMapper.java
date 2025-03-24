package org.example.models.response;

import org.example.models.cat.CatDto;
import org.example.models.owner.Owner;
import org.example.models.owner.OwnerDto;
import org.springframework.stereotype.Service;

@Service
public class ResponseMapper {
    public CatResponse mapToCatResponse(CatDto catDto) {
        CatResponse catResponse = new CatResponse();
        catResponse.setCatId(catDto.getCatId());
        catResponse.setName(catDto.getName());
        catResponse.setBirthDate(catDto.getBirthDate());
        catResponse.setBreed(catDto.getBreed());
        catResponse.setColor(catDto.getColor());
        catResponse.setOwnerId(catDto.getOwnerId());
        return catResponse;
    }

    public OwnerResponse mapToOwnerResponse(OwnerDto ownerDto) {
        OwnerResponse ownerResponse = new OwnerResponse();
        ownerResponse.setOwnerId(ownerDto.getOwnerId());
        ownerResponse.setName(ownerDto.getName());
        ownerResponse.setBirthDate(ownerDto.getBirthDate());
        ownerResponse.setRole(ownerDto.getRole());
        return ownerResponse;
    }

    public Owner mapResponseToOwner(OwnerResponse ownerResponse) {
        Owner owner = new Owner();
        owner.setOwnerId(ownerResponse.getOwnerId());
        owner.setName(ownerResponse.getName());
        owner.setBirthDate(ownerResponse.getBirthDate());
        owner.setRole(ownerResponse.getRole());
        owner.setPassword(ownerResponse.getPassword());
        return owner;
    }
}
