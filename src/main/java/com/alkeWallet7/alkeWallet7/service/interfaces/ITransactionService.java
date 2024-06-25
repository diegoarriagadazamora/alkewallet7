package com.alkeWallet7.alkeWallet7.service.interfaces;

import com.alkeWallet7.alkeWallet7.model.entity.TransactionEntity;

import java.util.List;

public interface ITransactionService {

    List<TransactionEntity> getAllTransaction();
    List<TransactionEntity> getByUserEntityId(Long userId);
    void saveTransaction(TransactionEntity transaction);
    TransactionEntity getById(Long id);
}
