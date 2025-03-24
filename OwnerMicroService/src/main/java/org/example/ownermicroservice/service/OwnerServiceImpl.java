package org.example.ownermicroservice.service;

import org.example.models.owner.Owner;
import org.example.models.owner.OwnerDto;
import org.example.ownermicroservice.mapper.OwnerDtoMapper;
import org.example.ownermicroservice.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private OwnerDtoMapper ownerDtoMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public OwnerDto getById(int id) {
        return ownerDtoMapper.mapToOwnerDto(ownerRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public List<OwnerDto> getAll() {
        return ownerRepository.findAll().stream()
                .map(ownerDtoMapper::mapToOwnerDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Owner create(OwnerDto ownerDto) {
        Owner owner = ownerDtoMapper.mapDtoToOwner(ownerDto);
        owner.setRole("USER");
        owner.setPassword(bCryptPasswordEncoder.encode(owner.getPassword()));
        return ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public Owner update(OwnerDto ownerDto) {
        Owner owner = ownerDtoMapper.mapDtoToOwner(ownerDto);
        return ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void delete(int id) {
        ownerRepository.deleteById(id);
    }
}
