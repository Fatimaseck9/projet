package io.bootify.wallet.controller;

import io.bootify.wallet.model.AccountDTO;
import io.bootify.wallet.service.AccountService;
import io.bootify.wallet.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "account/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("account") final AccountDTO accountDTO) {
        return "account/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("account") @Valid final AccountDTO accountDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "account/add";
        }
        accountService.create(accountDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("account.create.success"));
        return "redirect:/accounts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("account", accountService.get(id));
        return "account/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("account") @Valid final AccountDTO accountDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "account/edit";
        }
        accountService.update(id, accountDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("account.update.success"));
        return "redirect:/accounts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = accountService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            accountService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("account.delete.success"));
        }
        return "redirect:/accounts";
    }
    @PostMapping("/deposit/{id}")
    public String deposit(@PathVariable Long id, @RequestParam("amount") double amount,
                          final RedirectAttributes redirectAttributes) {
        accountService.depositMoney(id, amount);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("deposit.success"));
        return "redirect:/accounts";
    }

    @PostMapping("/withdraw/{id}")
    public String withdraw(@PathVariable final Long id,
                           @RequestParam("amount") double amount,
                           final RedirectAttributes redirectAttributes) {
        accountService.withdrawMoney(id, amount);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("withdraw.success"));
        return "redirect:/accounts";
    }

}
