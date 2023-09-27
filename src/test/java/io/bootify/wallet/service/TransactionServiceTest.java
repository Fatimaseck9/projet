package io.bootify.wallet.service;

import io.bootify.wallet.domain.Transaction;
import io.bootify.wallet.model.TransactionDTO;
import io.bootify.wallet.repos.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() {
        // Créez un objet TransactionDTO factice pour le test
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setMontant(50.0);

        // Créez un objet Transaction factice pour le résultat du mock
        Transaction transaction = new Transaction();
        transaction.setId(1L);

        // Définissez le comportement du mock pour save dans le repository
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Appelez la méthode create de TransactionService avec le DTO
        Long createdTransactionId = transactionService.create(transactionDTO);

        // Vérifiez si l'ID retourné est correct
        assertEquals(1L, createdTransactionId);
    }







    @Test
    void update() {
        // Créez un objet TransactionDTO factice pour le test
        TransactionDTO updatedTransactionDTO = new TransactionDTO();
        updatedTransactionDTO.setId(1L);
        updatedTransactionDTO.setMontant(75.0); // Nouveau montant

        // Créez un objet Transaction factice pour le résultat du mock
        Transaction existingTransaction = new Transaction();
        existingTransaction.setId(1L);
        existingTransaction.setMontant(50.0); // Ancien montant

        // Définissez le comportement du mock pour findById dans le repository
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existingTransaction));

        // Appelez la méthode update de TransactionService avec l'ID et le DTO mis à jour
        transactionService.update(1L, updatedTransactionDTO);

        // Vérifiez si la méthode save a été appelée avec la transaction mise à jour
        verify(transactionRepository, times(1)).save(existingTransaction);

        // Vérifiez si le montant de la transaction a été mis à jour
        assertEquals(75.0, existingTransaction.getMontant());
    }
}