package com.projet.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetailFacture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifDF;
    private  Long qte;
    private  Double prixunitaire;

    @ManyToOne
    @JoinColumn(name = "idFact")
    private Facture facture;

    @ManyToOne
    @JoinColumn(name = "idProd")
    private Produit produit;


    public Long getIfDF() {
        return ifDF;
    }

    public void setIfDF(Long ifDF) {
        this.ifDF = ifDF;
    }

    public Long getQte() {
        return qte;
    }

    public void setQte(Long qte) {
        this.qte = qte;
    }

    public Double getPrixunitaire() {
        return prixunitaire;
    }

    public void setPrixunitaire(Double prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
