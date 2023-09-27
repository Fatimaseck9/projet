package io.bootify.wallet.service;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.domain.DemandeAnnulation;
import io.bootify.wallet.domain.Transaction;
import io.bootify.wallet.domain.User;
import io.bootify.wallet.model.AccountDTO;
import io.bootify.wallet.repos.AccountRepository;
import io.bootify.wallet.repos.DemandeAnnulationRepository;
import io.bootify.wallet.repos.TransactionRepository;
import io.bootify.wallet.repos.UserRepository;
import io.bootify.wallet.util.NotFoundException;
import io.bootify.wallet.util.WebUtils;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final DemandeAnnulationRepository demandeAnnulationRepository;

    public AccountService(final AccountRepository accountRepository,
            final UserRepository userRepository, final TransactionRepository transactionRepository,
            final DemandeAnnulationRepository demandeAnnulationRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.demandeAnnulationRepository = demandeAnnulationRepository;
    }



    public List<AccountDTO> findAll() {
        final List<Account> accounts = accountRepository.findAll(Sort.by("id"));
        return accounts.stream()
                .map(account -> mapToDTO(account, new AccountDTO()))
                .toList();
    }

    public AccountDTO get(final Long id) {
        return accountRepository.findById(id)
                .map(account -> mapToDTO(account, new AccountDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AccountDTO accountDTO) {
        final Account account = new Account();
        mapToEntity(accountDTO, account);
        return accountRepository.save(account).getId();
    }

    public void update(final Long id, final AccountDTO accountDTO) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(accountDTO, account);
        accountRepository.save(account);
    }

    public void delete(final Long id) {
        accountRepository.deleteById(id);
    }

    private AccountDTO mapToDTO(final Account account, final AccountDTO accountDTO) {
        accountDTO.setId(account.getId());
        accountDTO.setSolde(account.getSolde());
        accountDTO.setDateCreation(account.getDateCreation());
        return accountDTO;
    }

    private Account mapToEntity(final AccountDTO accountDTO, final Account account) {
        account.setSolde(accountDTO.getSolde());
        account.setDateCreation(accountDTO.getDateCreation());
        return account;
    }

    public String getReferencedWarning(final Long id) {
        final Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final User accountUser = userRepository.findFirstByAccount(account);
        if (accountUser != null) {
            return WebUtils.getMessage("account.user.account.referenced", accountUser.getId());
        }
        final Transaction accountTransaction = transactionRepository.findFirstByAccount(account);
        if (accountTransaction != null) {
            return WebUtils.getMessage("account.transaction.account.referenced", accountTransaction.getId());
        }
        final DemandeAnnulation accountDemandeAnnulation = demandeAnnulationRepository.findFirstByAccount(account);
        if (accountDemandeAnnulation != null) {
            return WebUtils.getMessage("account.demandeAnnulation.account.referenced", accountDemandeAnnulation.getId());
        }
        return null;
    }

    public void depositMoney(Long id, double amount) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        Account account = optionalAccount.get();

        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être positif");
        }
        double currentBalance = Double.parseDouble(account.getSolde());
        String newBalance = String.valueOf(currentBalance + amount);
        account.setSolde(newBalance);

        accountRepository.save(account);
    }


    public void withdrawMoney(Long id, double amount) {

        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        Account account = optionalAccount.get();

        double currentBalance = Double.parseDouble(account.getSolde());

        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être positif");
        }
        if (currentBalance < amount) {
            throw new IllegalArgumentException("Solde insuffisant pour effectuer le retrait");
        }

        String newBalance = String.valueOf(currentBalance - amount);
        account.setSolde(newBalance);

        accountRepository.save(account);
    }







    }
