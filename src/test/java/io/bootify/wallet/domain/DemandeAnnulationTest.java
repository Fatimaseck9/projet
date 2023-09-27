package io.bootify.wallet.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class DemandeAnnulationTest {
    @Mock
    private DemandeAnnulation demandeAnnulation;

    @BeforeEach
    void setUp() {
        demandeAnnulation = new DemandeAnnulation();
    }

    @Test
    void getMontant() {
        //Définir le montant de la demande d'annulation
        demandeAnnulation.setMontant(50.0);

        // Vérifier que getMontant retourne le montant attendu
        assertEquals(50.0, demandeAnnulation.getMontant());
    }

    @Test
    void setMontant() {
        // Définir le montant de la demande d'annulation
        demandeAnnulation.setMontant(50.0);

        // Vérifier que getMontant retourne le montant attendu
        assertEquals(50.0, demandeAnnulation.getMontant());
    }

    @Test
    void getEtatDemande() {
        // Utilisez la méthode setMontant pour définir un nouveau montant
        demandeAnnulation.setMontant(100.0);

        // Vérifier que la méthode getMontant retourne le nouveau montant
        assertEquals(100.0, demandeAnnulation.getMontant());
    }

    @Test
    void setEtatDemande() {
        // Utilisez la méthode setEtatDemande pour définir un nouvel état
        demandeAnnulation.setEtatDemande("Acceptée");

        // Vérifier que la méthode getEtatDemande retourne le nouvel état
        assertEquals("Acceptée", demandeAnnulation.getEtatDemande());

    }
}