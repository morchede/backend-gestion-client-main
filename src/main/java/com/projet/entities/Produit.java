package com.projet.entities;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Produit implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProd;
	private String codep;
	private Long qtestock;
	private double prix;
	private String designation;
	private String imagerod;
	private double margebinificitaire;
	private String dateAchat;

	@OneToMany(mappedBy = "produit")
	@JsonIgnore
	private Set<DetailFacture> detailFactures = new HashSet<>();


	public Long getIdProd() {
		return idProd;
	}

	public void setIdProd(Long idProd) {
		this.idProd = idProd;
	}

	public String getCodep() {
		return codep;
	}

	public void setCodep(String codep) {
		this.codep = codep;
	}

	public Long getQtestock() {
		return qtestock;
	}

	public void setQtestock(Long qtestock) {
		this.qtestock = qtestock;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getImagerod() {
		return imagerod;
	}

	public void setImagerod(String imagerod) {
		this.imagerod = imagerod;
	}

	public double getMargebinificitaire() {
		return margebinificitaire;
	}

	public void setMargebinificitaire(double margebinificitaire) {
		this.margebinificitaire = margebinificitaire;
	}

	public String getDateAchat() {
		return dateAchat;
	}

	public void setDateAchat(String dateAchat) {
		this.dateAchat = dateAchat;
	}

	public Set<DetailFacture> getDetailFactures() {
		return detailFactures;
	}

	public void setDetailFactures(Set<DetailFacture> detailFactures) {
		this.detailFactures = detailFactures;
	}
}
