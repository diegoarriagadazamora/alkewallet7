package com.alkeWallet7.alkeWallet7.service;

import com.alkeWallet7.alkeWallet7.model.DTO.UserDTO;
import com.alkeWallet7.alkeWallet7.model.entity.AccountEntity;
import com.alkeWallet7.alkeWallet7.model.entity.ContactEntity;
import com.alkeWallet7.alkeWallet7.model.entity.UserEntity;
import com.alkeWallet7.alkeWallet7.repository.AccountRepository;
import com.alkeWallet7.alkeWallet7.repository.ContactRepository;
import com.alkeWallet7.alkeWallet7.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private ContactRepository contactRepository;

    private static final double usdValue = 908.79;
    private static final double recharge = 300.00;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Correcta creación de usuario ")
    void createUserSuccess() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("juan");
        userDTO.setUsername("jc");
        userDTO.setEmail("jc@gmail.com");
        userDTO.setPassword("123456");
        userDTO.setPasswordConfirm("123456");
        when(userRepository.findByNameAndEmail(userDTO.getEmail(),userDTO.getUsername())).thenReturn(null);
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(userDTO.getPassword())).thenReturn("encodePassword");

        UserEntity user = userService.createUser(userDTO);
        assertNotNull(user);
        assertEquals("juan", user.getName());
        assertEquals("jc", user.getUsername());
        assertEquals("jc@gmail.com", user.getEmail());
        assertEquals("encodePassword", user.getPassword());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Buscar usuario por ID existente")
    void getUserByIdExisting() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Test User");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity foundUser = userService.getById(1L);

        assertNotNull(foundUser);
        assertEquals("Test User", foundUser.getName());
    }

    @Test
    @DisplayName("Buscar usuario por ID inexistente")
    void getUserByIdNonExisting() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.getById(1L)
        );

        assertEquals("Usuario no es encontrado: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Creación de una cuenta nueva de usuario")
    void createAccount() {
        AccountEntity account = new AccountEntity();
        account.setId(1L);
        account.setBalance(5000.0);

        when(accountRepository.save(account)).thenReturn(account);

        accountService.createAccount(account);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    @DisplayName("Conversión de moneda exitosa")
    void currencyConversionSuccess() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1L);
        accountEntity.setBalance(500.00);

        accountService.currencyConversion(accountEntity);

        assertEquals(0.55, accountEntity.getForeignBalance(), 0.001);
        verify(accountRepository).save(accountEntity);
    }

    @Test
    @DisplayName("Depósito exitoso")
    void depositSuccess() {
        Long accountId = 1L;
        double initialBalance = 1000.00;
        double depositAmount = 200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));

        accountService.deposit(accountId, depositAmount);

        assertEquals(initialBalance + depositAmount, accountEntity.getBalance());
        verify(accountRepository).save(accountEntity);
    }

    @Test
    @DisplayName("Depósito con monto negativo debe lanzar IllegalArgumentException")
    void depositNegativeAmount() {
        Long accountId = 1L;
        double depositAmount = -200.00;

        assertThrows(IllegalArgumentException.class, () -> accountService.deposit(accountId, depositAmount));
    }

    @Test
    @DisplayName("Retiro exitoso")
    void withdrawSuccess() {
        Long accountId = 1L;
        double initialBalance = 1000.00;
        double withdrawalAmount = 200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));

        accountService.withdraw(accountId, withdrawalAmount);

        assertEquals(initialBalance - withdrawalAmount, accountEntity.getBalance());
        verify(accountRepository).save(accountEntity);
    }

    @Test
    @DisplayName("Retiro con monto mayor al saldo debe lanzar IllegalArgumentException")
    void withdrawAmountGreaterThanBalance() {
        Long accountId = 1L;
        double initialBalance = 1000.00;
        double withdrawalAmount = 1200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));

        assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(accountId, withdrawalAmount));
    }

    @Test
    @DisplayName("Transferencia exitosa a usuario")
    void executeTransferToUserSuccess() {
        Long accountId = 1L;
        Long contactId = 2L;
        double initialBalance = 1000.00;
        double transferAmount = 200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(initialBalance);

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(contactId);
        contactEntity.setUser(true);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));
        when(contactRepository.findById(contactId)).thenReturn(java.util.Optional.of(contactEntity));

        accountService.executeTransfer(accountId, contactId, transferAmount);

        assertEquals(initialBalance - transferAmount, accountEntity.getBalance());
        verify(accountRepository).save(accountEntity);
    }

    @Test
    @DisplayName("Transferencia exitosa a contacto no usuario")
    void executeTransferToNonUserContactSuccess() {
        Long accountId = 1L;
        Long contactId = 2L;
        double initialBalance = 1000.00;
        double transferAmount = 200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(initialBalance);

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(contactId);
        contactEntity.setUser(false);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));
        when(contactRepository.findById(contactId)).thenReturn(java.util.Optional.of(contactEntity));

        accountService.executeTransfer(accountId, contactId, transferAmount);

        assertEquals(initialBalance - (transferAmount + recharge), accountEntity.getBalance());
        verify(accountRepository).save(accountEntity);
    }

    @Test
    @DisplayName("Transferencia con monto mayor al saldo debe lanzar IllegalArgumentException")
    void executeTransferAmountGreaterThanBalance() {
        Long accountId = 1L;
        Long contactId = 2L;
        double initialBalance = 1000.00;
        double transferAmount = 1200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setBalance(initialBalance);

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(contactId);
        contactEntity.setUser(true);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));
        when(contactRepository.findById(contactId)).thenReturn(java.util.Optional.of(contactEntity));

        assertThrows(IllegalArgumentException.class, () -> accountService.executeTransfer(accountId, contactId, transferAmount));
    }

    @Test
    @DisplayName("Añadir saldo entrante exitoso")
    void addIncomingBalanceSuccess() {
        Long accountId = 1L;
        double initialBalance = 1000.00;
        double incomingAmount = 200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setTotalIncomingBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));

        accountService.addIncomingBalance(accountId, incomingAmount);

        assertEquals(initialBalance + incomingAmount, accountEntity.getTotalIncomingBalance());
        verify(accountRepository).save(accountEntity);
    }

    @Test
    @DisplayName("Restar saldo saliente exitoso")
    void subtractOutgoingBalanceSuccess() {
        Long accountId = 1L;
        double initialBalance = 1000.00;
        double outgoingAmount = 200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setTotalOutgoingBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));

        accountService.subtractOutgoingBalance(accountId, outgoingAmount);

        assertEquals(initialBalance + outgoingAmount, accountEntity.getTotalOutgoingBalance());
        verify(accountRepository).save(accountEntity);
    }

    @Test
    @DisplayName("Restar saldo saliente con recarga exitoso")
    void subtractOutgoingBalanceRechargeSuccess() {
        Long accountId = 1L;
        double initialBalance = 1000.00;
        double outgoingAmount = 200.00;

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(accountId);
        accountEntity.setTotalOutgoingBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(java.util.Optional.of(accountEntity));

        accountService.subtractOutgoingBalanceRecharge(accountId, outgoingAmount);

        assertEquals(initialBalance + outgoingAmount + recharge, accountEntity.getTotalOutgoingBalance());
        verify(accountRepository).save(accountEntity);
    }
}