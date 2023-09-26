package io.bootify.wallet.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;


public class AccountDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String solde;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dateCreation;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(final String solde) {
        this.solde = solde;
    }

    public OffsetDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(final OffsetDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

}
