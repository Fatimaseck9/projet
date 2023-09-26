package io.bootify.wallet.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;


public class DemandeAnnulationDTO {

    private Long id;

    @NotNull
    private Long idEmet;

    @NotNull
    private Long idRecept;

    @NotNull
    private Double montant;

    @NotNull
    @Size(max = 255)
    private String etatDemande;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime heureDemande;

    private Long account;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getIdEmet() {
        return idEmet;
    }

    public void setIdEmet(final Long idEmet) {
        this.idEmet = idEmet;
    }

    public Long getIdRecept() {
        return idRecept;
    }

    public void setIdRecept(final Long idRecept) {
        this.idRecept = idRecept;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(final Double montant) {
        this.montant = montant;
    }

    public String getEtatDemande() {
        return etatDemande;
    }

    public void setEtatDemande(final String etatDemande) {
        this.etatDemande = etatDemande;
    }

    public OffsetDateTime getHeureDemande() {
        return heureDemande;
    }

    public void setHeureDemande(final OffsetDateTime heureDemande) {
        this.heureDemande = heureDemande;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(final Long account) {
        this.account = account;
    }

}
