package io.bootify.wallet.service;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.model.AccountDTO;
import io.bootify.wallet.repos.AccountRepository;
import io.bootify.wallet.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {
  

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(accountService);
        assertNotNull(accountRepository);
       
    }

    @Test
    void create() {
        // Créez un compte fictif pour le test
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setSolde(String.valueOf(100));

        // Configurez le comportement de mock pour save
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            savedAccount.setId(1L); // Définissez un ID factice pour simuler la sauvegarde
            return savedAccount;
        });

        // Appelez la méthode pour créer le compte
        Long accountId = accountService.create(accountDTO);

        // Vérifiez si le compte a été créé avec succès
        assertNotNull(accountId);

    }

    @Test
    void update() {
        // Créer un compte fictif pour les besoins du test
        Long accountId = 1L;
        AccountDTO updatedAccountDTO = new AccountDTO();


        Account account = new Account();
        account.setId(accountId);

        // Configurer le comportement du mock AccountRepository
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Appeler la méthode à tester
        accountService.update(accountId, updatedAccountDTO);

        // Vérifier que la méthode findById a été appelée avec le bon id
        verify(accountRepository).findById(accountId);

        // Vérifier que la méthode save a été appelée avec le compte modifié
        verify(accountRepository).save(account);

    }


    @Test
    void delete() {

        // Créez un compte factice pour les tests
        Account account = new Account();
        account.setId(1L);

        // Configurez le comportement de mock pour findById
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Appelez la méthode delete
        accountService.delete(1L);

        // Vérifiez que la méthode deleteById a été appelée avec l'ID du compte
        verify(accountRepository, times(1)).deleteById(1L);
    }



    @Test
    void depositMoney() {
        // Créer un compte fictif pour les besoins du test
        Long accountId = 1L;
        double initialBalance = 100.0;
        Account account = new Account();
        account.setId(accountId);
        account.setSolde(String.valueOf(initialBalance));

        // Configurer le comportement du mock AccountRepository
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Appeler la méthode à tester
        double depositAmount = 50.0;
        accountService.depositMoney(accountId, depositAmount);

        // Vérifier que la méthode findById a été appelée avec le bon id
        verify(accountRepository).findById(accountId);

        // Vérifier que la méthode save a été appelée avec le compte modifié
        verify(accountRepository).save(account);

        // Vérifier que le solde du compte a été mis à jour correctement
        double expectedBalance = initialBalance + depositAmount;
        assertEquals(expectedBalance, Double.parseDouble(account.getSolde()), 0.001);




    }

    @Test
    void withdrawMoney() {
        // Créer un compte fictif pour les besoins du test
        Long accountId = 1L;
        double initialBalance = 100.0;
        Account account = new Account();
        account.setId(accountId);
        account.setSolde(String.valueOf(initialBalance));

        // Configurer le comportement du mock AccountRepository
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Appeler la méthode à tester
        double withdrawAmount = 50.0;
        accountService.withdrawMoney(accountId, withdrawAmount);

        // Vérifier que la méthode findById a été appelée avec le bon id
        verify(accountRepository).findById(accountId);

        // Vérifier que la méthode save a été appelée avec le compte modifié
        verify(accountRepository).save(account);

        // Vérifier que le solde du compte a été mis à jour correctement
        double expectedBalance = initialBalance -withdrawAmount;
        assertEquals(expectedBalance, Double.parseDouble(account.getSolde()),0.05);


    }


}
