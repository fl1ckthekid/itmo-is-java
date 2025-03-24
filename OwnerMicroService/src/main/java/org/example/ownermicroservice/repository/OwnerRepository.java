package org.example.ownermicroservice.repository;

import org.example.models.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Owner findByName(String name);
}
