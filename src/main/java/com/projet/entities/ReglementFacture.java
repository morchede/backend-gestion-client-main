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
public class ReglementFacture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRF;
    private double montant;

    @ManyToOne
    @JoinColumn(name = "idFact")
    private Facture facturesr;

    @ManyToOne
    @JoinColumn(name = "Idreg")
    private Reglement reglement;


    public Long getIdRF() {
        return idRF;
    }

    public void setIdRF(Long idRF) {
        this.idRF = idRF;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Facture getFacturesr() {
        return facturesr;
    }

    public void setFacturesr(Facture facturesr) {
        this.facturesr = facturesr;
    }

    public Reglement getReglement() {
        return reglement;
    }

    public void setReglement(Reglement reglement) {
        this.reglement = reglement;
    }
}
