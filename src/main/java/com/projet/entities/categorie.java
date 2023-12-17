package com.projet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class categorie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcat;
    private  String codecat;
    private String libelle ;

    public Long getIdcat() {
        return idcat;
    }

    public void setIdcat(Long idcat) {
        this.idcat = idcat;
    }

    public String getCodecat() {
        return codecat;
    }

    public void setCodecat(String codecat) {
        this.codecat = codecat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
