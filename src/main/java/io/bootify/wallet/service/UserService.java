package io.bootify.wallet.service;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.domain.User;
import io.bootify.wallet.model.UserDTO;
import io.bootify.wallet.repos.AccountRepository;
import io.bootify.wallet.repos.UserRepository;
import io.bootify.wallet.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(final UserRepository userRepository,
            final AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setNom(user.getNom());
        userDTO.setPrenom(user.getPrenom());
        userDTO.setPassword(user.getPassword());
        userDTO.setMail(user.getMail());
        userDTO.setAccount(user.getAccount() == null ? null : user.getAccount().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setPassword(userDTO.getPassword());
        user.setMail(userDTO.getMail());
        final Account account = userDTO.getAccount() == null ? null : accountRepository.findById(userDTO.getAccount())
                .orElseThrow(() -> new NotFoundException("account not found"));
        user.setAccount(account);
        return user;
    }

    public boolean accountExists(final Long id) {
        return userRepository.existsByAccountId(id);
    }

}
