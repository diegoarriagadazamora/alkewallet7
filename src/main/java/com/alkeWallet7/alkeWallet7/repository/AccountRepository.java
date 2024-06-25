package com.alkeWallet7.alkeWallet7.repository;

import com.alkeWallet7.alkeWallet7.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {

    Optional<AccountEntity> findByUserEntityId(Long userId);
}
