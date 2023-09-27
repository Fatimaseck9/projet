package io.bootify.wallet.domain;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class AccountTest {
    private Account account;
    @Test
    void getId() {
        // Définir l'ID de l'Account
        account.setId(1L);

        // Vérifier que getId retourne la valeur attendue
        assertEquals(1L, account.getId());
    }

    @Test
    void setId() {
        // Définir l'ID de l'Account
        account.setId(1L);

        // Vérifier que getId retourne la valeur attendue
        assertEquals(1L, account.getId());
    }

    @Test
    void getSolde() {
        // Définir le solde de l'Account
        account.setSolde("100.0");

        // Vérifier que getSolde retourne la valeur attendue
        assertEquals("100.0", account.getSolde());
    }


    @Test
    void setSolde() {
        // Définir le solde de l'Account en utilisant la méthode setSolde
        account.setSolde("200.0");

        // Vérifier que la valeur a été correctement définie
        assertEquals("200.0", account.getSolde());

    }



    @Test
    void getUser() {
        // Créez un objet User factice
        User user = new User();
        user.setId(1L);

        // Définir l'utilisateur de l'Account
        account.setUser(user);

        // Vérifier que getUser retourne l'utilisateur attendu
        assertEquals(user, account.getUser());
    }

    @Test
    void setUser() {
        // Créez un nouvel utilisateur
        User newUser = new User();
        newUser.setId(2L);

        // Utilisez la méthode setUser pour définir l'utilisateur de l'Account
        account.setUser(newUser);

        // Vérifier que la méthode getUser retourne le nouvel utilisateur
        assertEquals(newUser, account.getUser());
    }

    @Test
    void getTransaction() {
        // Créez une transaction factice
        Transaction transaction = new Transaction();
        transaction.setId(1L);

        // Définir la transaction de l'Account
        account.setTransaction((Set<Transaction>) transaction);

        // Vérifier que getTransaction retourne la transaction attendue
        assertEquals(transaction, account.getTransaction());
    }

    @Test
    void setTransaction() {
        // Créez une nouvelle transaction
        Transaction newTransaction = new Transaction();
        newTransaction.setId(2L);

        // Utilisez la méthode setTransaction pour définir la nouvelle transaction de l'Account
        account.setTransaction((Set<Transaction>) newTransaction);

        // Vérifier que la méthode getTransaction retourne la nouvelle transaction
        assertEquals(newTransaction, account.getTransaction());
    }

    @Test
    void getDemandeAnnulation() {
        // Créez une demande d'annulation factice
        DemandeAnnulation demandeAnnulation = new DemandeAnnulation();
        demandeAnnulation.setId(1L);

        // Définir la demande d'annulation de l'Account
        account.setDemandeAnnulation((Set<DemandeAnnulation>) demandeAnnulation);

        // Vérifier que getDemandeAnnulation retourne la demande d'annulation attendue
        assertEquals(demandeAnnulation, account.getDemandeAnnulation());
    }

    @Test
    void setDemandeAnnulation() {
        // Créez une nouvelle demande d'annulation
        DemandeAnnulation newDemandeAnnulation = new DemandeAnnulation();
        newDemandeAnnulation.setId(2L);

        // Utilisez la méthode setDemandeAnnulation pour définir la nouvelle demande d'annulation de l'Account
        account.setDemandeAnnulation((Set<DemandeAnnulation>) newDemandeAnnulation);

        // Vérifier que la méthode getDemandeAnnulation retourne la nouvelle demande d'annulation
        assertEquals(newDemandeAnnulation, account.getDemandeAnnulation());
    }




}