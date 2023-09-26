package io.bootify.wallet.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;


public class TransactionDTO {

    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime heuretrans;

    @NotNull
    private Double montant;

    @NotNull
    private Long cptSource;

    @NotNull
    private Long cptDest;

    @NotNull
    @Size(max = 255)
    private String type;

    private Long account;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public OffsetDateTime getHeuretrans() {
        return heuretrans;
    }

    public void setHeuretrans(final OffsetDateTime heuretrans) {
        this.heuretrans = heuretrans;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(final Double montant) {
        this.montant = montant;
    }

    public Long getCptSource() {
        return cptSource;
    }

    public void setCptSource(final Long cptSource) {
        this.cptSource = cptSource;
    }

    public Long getCptDest() {
        return cptDest;
    }

    public void setCptDest(final Long cptDest) {
        this.cptDest = cptDest;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(final Long account) {
        this.account = account;
    }

}
