package io.bootify.wallet.service;

import io.bootify.wallet.domain.Account;
import io.bootify.wallet.domain.DemandeAnnulation;
import io.bootify.wallet.model.DemandeAnnulationDTO;
import io.bootify.wallet.repos.AccountRepository;
import io.bootify.wallet.repos.DemandeAnnulationRepository;
import io.bootify.wallet.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DemandeAnnulationService {

    private final DemandeAnnulationRepository demandeAnnulationRepository;
    private final AccountRepository accountRepository;

    public DemandeAnnulationService(final DemandeAnnulationRepository demandeAnnulationRepository,
            final AccountRepository accountRepository) {
        this.demandeAnnulationRepository = demandeAnnulationRepository;
        this.accountRepository = accountRepository;
    }

    public List<DemandeAnnulationDTO> findAll() {
        final List<DemandeAnnulation> demandeAnnulations = demandeAnnulationRepository.findAll(Sort.by("id"));
        return demandeAnnulations.stream()
                .map(demandeAnnulation -> mapToDTO(demandeAnnulation, new DemandeAnnulationDTO()))
                .toList();
    }

    public DemandeAnnulationDTO get(final Long id) {
        return demandeAnnulationRepository.findById(id)
                .map(demandeAnnulation -> mapToDTO(demandeAnnulation, new DemandeAnnulationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DemandeAnnulationDTO demandeAnnulationDTO) {
        final DemandeAnnulation demandeAnnulation = new DemandeAnnulation();
        mapToEntity(demandeAnnulationDTO, demandeAnnulation);
        return demandeAnnulationRepository.save(demandeAnnulation).getId();
    }

    public void update(final Long id, final DemandeAnnulationDTO demandeAnnulationDTO) {
        final DemandeAnnulation demandeAnnulation = demandeAnnulationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(demandeAnnulationDTO, demandeAnnulation);
        demandeAnnulationRepository.save(demandeAnnulation);
    }

    public void delete(final Long id) {
        demandeAnnulationRepository.deleteById(id);
    }

    private DemandeAnnulationDTO mapToDTO(final DemandeAnnulation demandeAnnulation,
            final DemandeAnnulationDTO demandeAnnulationDTO) {
        demandeAnnulationDTO.setId(demandeAnnulation.getId());
        demandeAnnulationDTO.setIdEmet(demandeAnnulation.getIdEmet());
        demandeAnnulationDTO.setIdRecept(demandeAnnulation.getIdRecept());
        demandeAnnulationDTO.setMontant(demandeAnnulation.getMontant());
        demandeAnnulationDTO.setEtatDemande(demandeAnnulation.getEtatDemande());
        demandeAnnulationDTO.setHeureDemande(demandeAnnulation.getHeureDemande());
        demandeAnnulationDTO.setAccount(demandeAnnulation.getAccount() == null ? null : demandeAnnulation.getAccount().getId());
        return demandeAnnulationDTO;
    }

    private DemandeAnnulation mapToEntity(final DemandeAnnulationDTO demandeAnnulationDTO,
            final DemandeAnnulation demandeAnnulation) {
        demandeAnnulation.setIdEmet(demandeAnnulationDTO.getIdEmet());
        demandeAnnulation.setIdRecept(demandeAnnulationDTO.getIdRecept());
        demandeAnnulation.setMontant(demandeAnnulationDTO.getMontant());
        demandeAnnulation.setEtatDemande(demandeAnnulationDTO.getEtatDemande());
        demandeAnnulation.setHeureDemande(demandeAnnulationDTO.getHeureDemande());
        final Account account = demandeAnnulationDTO.getAccount() == null ? null : accountRepository.findById(demandeAnnulationDTO.getAccount())
                .orElseThrow(() -> new NotFoundException("account not found"));
        demandeAnnulation.setAccount(account);
        return demandeAnnulation;
    }

}
