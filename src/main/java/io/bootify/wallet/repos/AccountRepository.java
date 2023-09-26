package io.bootify.wallet.repos;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.domain.DemandeAnnulation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findById(long id);


}
