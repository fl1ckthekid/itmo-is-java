package org.example.models.request;

import org.example.models.cat.Cat;
import org.example.models.owner.Owner;
import org.springframework.stereotype.Service;

@Service
public class RequestMapper {
//    final OwnerRepository ownerRepository;
//
//    @Autowired
//    public RequestMapper(OwnerRepository ownerRepository) {
//        this.ownerRepository = ownerRepository;
//    }

    public Cat mapToCat(CatRequest catRequest) {
        Cat cat = new Cat();
        cat.setName(catRequest.getName());
        cat.setBirthDate(catRequest.getBirthDate());
        cat.setBreed(catRequest.getBreed());
        cat.setColor(catRequest.getColor());
//        cat.setOwner(ownerRepository.getReferenceById(catRequest.getOwnerId()));
        return cat;
    }

    public Owner mapToOwner(OwnerRequest ownerRequest) {
        Owner owner = new Owner();
        owner.setName(ownerRequest.getName());
        owner.setBirthDate(ownerRequest.getBirthDate());
        owner.setRole(ownerRequest.getRole());
        return owner;
    }
}
