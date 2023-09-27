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

        // Créez un compte factice pour les tests
        Account account = new Account();
        account.setId(1L);
        account.setSolde("100.0");


        // Configurez le comportement de mock pour findById
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Créez un objet DTO de mise à jour
        AccountDTO updatedAccountDTO = new AccountDTO();
        updatedAccountDTO.setSolde("200.0");

        // Appelez la méthode update
        accountService.update(1L, updatedAccountDTO);

        // Vérifiez que la méthode save a été appelée avec le compte mis à jour
        verify(accountRepository, times(1)).save(account);

        // Vérifiez que le solde du compte a été mis à jour comme prévu
        assertEquals("200.0", account.getSolde());

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
        // Créer un compte factice pour le test
        Account account = new Account();
        account.setId(1L);
        account.setSolde("100.0");

        // Configurez le comportement de mock pour findById
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Appeler la méthode depositMoney avec un montant positif
        accountService.depositMoney(1L, 50.0);

        // Vérifier que la méthode save a été appelée avec le compte mis à jour
        verify(accountRepository, times(1)).save(account);

        // Vérifier que le solde du compte a été mis à jour correctement
        assertEquals("150.0", account.getSolde());


    }



    @Test
    public void testWithdrawal() {
        // Créer un compte factice pour les tests
        Account account = new Account();
        account.setId(1L);
        account.setSolde("100.0");

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Appeler la méthode withdrawal avec un montant valide
        accountService.withdrawMoney(1L, 50.0);

        // Vérifier que la méthode save a été appelée avec le compte mis à jour
        verify(accountRepository, times(1)).save(account);

        // Vérifier que le solde du compte a été mis à jour correctement
        assertEquals("50.0", account.getSolde());



    }





}
