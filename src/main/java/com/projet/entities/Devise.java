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
public class Devise implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long IdDev;
    private String code;
    private String symbole;
    private Double Tauxchange;

    @OneToMany(mappedBy = "devise")
    @JsonIgnore
    private Set<Facture> factures = new HashSet<>();


    public Long getIdDev() {
        return IdDev;
    }

    public void setIdDev(Long idDev) {
        IdDev = idDev;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbole() {
        return symbole;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public Double getTauxchange() {
        return Tauxchange;
    }

    public void setTauxchange(Double tauxchange) {
        Tauxchange = tauxchange;
    }

    public Set<Facture> getFactures() {
        return factures;
    }

    public void setFactures(Set<Facture> factures) {
        this.factures = factures;
    }
}
