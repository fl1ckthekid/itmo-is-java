package org.example.catmicroservice.service;

import org.example.catmicroservice.mapper.CatDtoMapper;
import org.example.catmicroservice.repository.CatRepository;
import org.example.models.cat.Cat;
import org.example.models.cat.CatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;
    private final CatDtoMapper catDtoMapper;

    @Autowired
    public CatServiceImpl(CatRepository catRepository, CatDtoMapper catDtoMapper) {
        this.catRepository = catRepository;
        this.catDtoMapper = catDtoMapper;
    }

    @Override
    @Transactional
    public CatDto getById(int id) {
        return catDtoMapper.mapToCatDto(catRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public List<CatDto> getAll() {
        return catRepository.findAll().stream()
                .map(catDtoMapper::mapToCatDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Cat create(CatDto catDto) {
        Cat cat = catDtoMapper.mapDtoToCat(catDto);
        return catRepository.save(cat);
    }

    @Override
    @Transactional
    public Cat update(CatDto catDto) {
        Cat cat = catDtoMapper.mapDtoToCat(catDto);
        return catRepository.save(cat);
    }

    @Override
    @Transactional
    public void delete(int id) {
        catRepository.delete(catRepository.getReferenceById(id));
    }
}
