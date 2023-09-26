package io.bootify.wallet.controller;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.model.TransactionDTO;
import io.bootify.wallet.repos.AccountRepository;
import io.bootify.wallet.service.TransactionService;
import io.bootify.wallet.util.CustomCollectors;
import io.bootify.wallet.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    public TransactionController(final TransactionService transactionService,
            final AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("accountValues", accountRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Account::getId, Account::getSolde)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        return "transaction/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("transaction") final TransactionDTO transactionDTO) {
        return "transaction/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("transaction") @Valid final TransactionDTO transactionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transaction/add";
        }
        transactionService.create(transactionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transaction.create.success"));
        return "redirect:/transactions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("transaction", transactionService.get(id));
        return "transaction/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("transaction") @Valid final TransactionDTO transactionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transaction/edit";
        }
        transactionService.update(id, transactionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transaction.update.success"));
        return "redirect:/transactions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        transactionService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("transaction.delete.success"));
        return "redirect:/transactions";
    }

}
