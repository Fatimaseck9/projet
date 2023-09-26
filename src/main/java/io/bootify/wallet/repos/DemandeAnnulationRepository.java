package io.bootify.wallet.repos;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.domain.DemandeAnnulation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DemandeAnnulationRepository extends JpaRepository<DemandeAnnulation, Long> {

    DemandeAnnulation findFirstByAccount(Account account);

}
