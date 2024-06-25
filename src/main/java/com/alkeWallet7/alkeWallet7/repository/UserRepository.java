package com.alkeWallet7.alkeWallet7.repository;


import com.alkeWallet7.alkeWallet7.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByUsername(String username);
    UserEntity findByNameAndEmail(String name,String email);
    UserEntity findByEmail(String email);
}
