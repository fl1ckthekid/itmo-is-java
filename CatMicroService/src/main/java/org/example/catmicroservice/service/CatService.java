package org.example.catmicroservice.service;

import org.example.models.cat.Cat;
import org.example.models.cat.CatDto;

import java.util.List;

public interface CatService {
    CatDto getById(int id);

    List<CatDto> getAll();

    Cat create(CatDto catDto);

    Cat update(CatDto catDto);

    void delete(int id);
}
