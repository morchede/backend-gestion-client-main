package com.projet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reglement implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Idreg;
    private Long MontantPayer;
    private String Date;

    @OneToMany(mappedBy = "reglement")
    @JsonIgnore
    private Set<ReglementFacture> reglementFactures = new HashSet<>();

    public Long getIdreg() {
        return Idreg;
    }

    public void setIdreg(Long idreg) {
        Idreg = idreg;
    }

    public Long getMontantPayer() {
        return MontantPayer;
    }

    public void setMontantPayer(Long montantPayer) {
        MontantPayer = montantPayer;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Set<ReglementFacture> getReglementFactures() {
        return reglementFactures;
    }

    public void setReglementFactures(Set<ReglementFacture> reglementFactures) {
        this.reglementFactures = reglementFactures;
    }
}
