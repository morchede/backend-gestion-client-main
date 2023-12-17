package com.projet.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Entity
@Builder
@Getter
@Setter
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCl;
	private String CodeCl;
	private String nomCl;
	private String prenomCl;
	private String adresseCl;
	private String emailCl;
	private String numeroTelCl;
	private Boolean actif;



	@OneToMany(mappedBy = "client")
	@JsonIgnore
	private Set<Facture> factures = new HashSet<>();

	public void setIdCl(Long customerId) {
	}

	public Long getIdCl() {
		return idCl;
	}

	public String getCodeCl() {
		return CodeCl;
	}

	public void setCodeCl(String codeCl) {
		CodeCl = codeCl;
	}

	public String getNomCl() {
		return nomCl;
	}

	public void setNomCl(String nomCl) {
		this.nomCl = nomCl;
	}

	public String getPrenomCl() {
		return prenomCl;
	}

	public void setPrenomCl(String prenomCl) {
		this.prenomCl = prenomCl;
	}

	public String getAdresseCl() {
		return adresseCl;
	}

	public void setAdresseCl(String adresseCl) {
		this.adresseCl = adresseCl;
	}

	public String getEmailCl() {
		return emailCl;
	}

	public void setEmailCl(String emailCl) {
		this.emailCl = emailCl;
	}

	public String getNumeroTelCl() {
		return numeroTelCl;
	}

	public void setNumeroTelCl(String numeroTelCl) {
		this.numeroTelCl = numeroTelCl;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public Set<Facture> getFactures() {
		return factures;
	}

	public void setFactures(Set<Facture> factures) {
		this.factures = factures;
	}
public Client(){

}
	public Client(Long idCl, String codeCl, String nomCl, String prenomCl, String adresseCl, String emailCl, String numeroTelCl, Boolean actif, Set<Facture> factures) {
		this.idCl = idCl;
		CodeCl = codeCl;
		this.nomCl = nomCl;
		this.prenomCl = prenomCl;
		this.adresseCl = adresseCl;
		this.emailCl = emailCl;
		this.numeroTelCl = numeroTelCl;
		this.actif = actif;
		this.factures = factures;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long idCl;
		private String codeCl;
		private String nomCl;
		private String prenomCl;
		private String adresseCl;
		private String emailCl;
		private String numeroTelCl;
		private Boolean actif;
		private Set<Facture> factures;

		private Builder() {
			// private constructor to enforce the use of the builder pattern
		}

		public Builder idCl(Long idCl) {
			this.idCl = idCl;
			return this;
		}

		public Builder codeCl(String codeCl) {
			this.codeCl = codeCl;
			return this;
		}

		public Builder nomCl(String nomCl) {
			this.nomCl = nomCl;
			return this;
		}

		public Builder prenomCl(String prenomCl) {
			this.prenomCl = prenomCl;
			return this;
		}

		public Builder adresseCl(String adresseCl) {
			this.adresseCl = adresseCl;
			return this;
		}

		public Builder emailCl(String emailCl) {
			this.emailCl = emailCl;
			return this;
		}

		public Builder numeroTelCl(String numeroTelCl) {
			this.numeroTelCl = numeroTelCl;
			return this;
		}

		public Builder actif(Boolean actif) {
			this.actif = actif;
			return this;
		}

		public Builder factures(Set<Facture> factures) {
			this.factures = factures;
			return this;
		}

		public Client build() {
			Client client = new Client();
			client.setIdCl(this.idCl);
			client.setCodeCl(this.codeCl);
			client.setNomCl(this.nomCl);
			client.setPrenomCl(this.prenomCl);
			client.setAdresseCl(this.adresseCl);
			client.setEmailCl(this.emailCl);
			client.setNumeroTelCl(this.numeroTelCl);
			client.setActif(this.actif);
			client.setFactures(this.factures);
			return client;
		}
	}

}
