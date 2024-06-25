package com.alkeWallet7.alkeWallet7.repository;

import com.alkeWallet7.alkeWallet7.model.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity,Long> {

    List<ContactEntity> findByUserEntityId(Long userId);
    List<ContactEntity> findByNameAndEmail(String name,String email);
}
