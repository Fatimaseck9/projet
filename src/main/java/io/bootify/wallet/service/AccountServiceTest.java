package io.bootify.wallet.service;

import io.bootify.wallet.model.AccountDTO;
import org.junit.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

public class AccountServiceTest {
    private AccountService accountService;

    @Test
    public void testCreateAccount() {
        // Créez un compte fictif pour le test
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setSolde(String.valueOf(100));

        // Appelez la méthode pour créer le compte
        Long accountId = accountService.create(accountDTO);

        // Vérifiez si le compte a été créé avec succès
        assertNotNull(accountId);

        // Vous pouvez ajouter d'autres assertions ici en fonction du comportement attendu
    }

}
