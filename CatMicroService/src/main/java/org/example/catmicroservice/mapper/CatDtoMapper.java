package org.example.catmicroservice.mapper;

import org.example.models.cat.Cat;
import org.example.models.cat.CatDto;
import org.springframework.stereotype.Service;

@Service
public class CatDtoMapper {
    public CatDto mapToCatDto(Cat cat) {
        CatDto catDto = new CatDto();
        catDto.setCatId(cat.getCatId());
        catDto.setName(cat.getName());
        catDto.setBirthDate(cat.getBirthDate());
        catDto.setBreed(cat.getBreed());
        catDto.setColor(cat.getColor());
        catDto.setOwnerId(cat.getOwner().getOwnerId());
        catDto.setFriends(cat.getFriends());
        return catDto;
    }

    public Cat mapDtoToCat(CatDto catDto) {
        Cat cat = new Cat();
        cat.setCatId(catDto.getCatId());
        cat.setName(catDto.getName());
        cat.setBirthDate(catDto.getBirthDate());
        cat.setBreed(catDto.getBreed());
        cat.setColor(catDto.getColor());
//        cat.setOwner(catDto.getOwnerId());
        cat.setFriends(catDto.getFriends());
        return cat;
    }
}
