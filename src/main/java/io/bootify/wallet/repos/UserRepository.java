package io.bootify.wallet.repos;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByAccountId(Long id);

    User findFirstByAccount(Account account);

}
