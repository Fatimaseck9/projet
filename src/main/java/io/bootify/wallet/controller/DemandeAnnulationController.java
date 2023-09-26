package io.bootify.wallet.controller;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.model.DemandeAnnulationDTO;
import io.bootify.wallet.repos.AccountRepository;
import io.bootify.wallet.service.DemandeAnnulationService;
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
@RequestMapping("/demandeAnnulations")
public class DemandeAnnulationController {

    private final DemandeAnnulationService demandeAnnulationService;
    private final AccountRepository accountRepository;

    public DemandeAnnulationController(final DemandeAnnulationService demandeAnnulationService,
            final AccountRepository accountRepository) {
        this.demandeAnnulationService = demandeAnnulationService;
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
        model.addAttribute("demandeAnnulations", demandeAnnulationService.findAll());
        return "demandeAnnulation/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("demandeAnnulation") final DemandeAnnulationDTO demandeAnnulationDTO) {
        return "demandeAnnulation/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("demandeAnnulation") @Valid final DemandeAnnulationDTO demandeAnnulationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "demandeAnnulation/add";
        }
        demandeAnnulationService.create(demandeAnnulationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("demandeAnnulation.create.success"));
        return "redirect:/demandeAnnulations";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("demandeAnnulation", demandeAnnulationService.get(id));
        return "demandeAnnulation/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("demandeAnnulation") @Valid final DemandeAnnulationDTO demandeAnnulationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "demandeAnnulation/edit";
        }
        demandeAnnulationService.update(id, demandeAnnulationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("demandeAnnulation.update.success"));
        return "redirect:/demandeAnnulations";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        demandeAnnulationService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("demandeAnnulation.delete.success"));
        return "redirect:/demandeAnnulations";
    }

}
