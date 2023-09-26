package io.bootify.wallet.repos;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findFirstByAccount(Account account);

}
