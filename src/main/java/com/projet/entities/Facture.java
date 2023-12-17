package com.projet.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Facture  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idFact;
	private String dateFact;
	private int num;
	@ManyToOne
	@JoinColumn(name = "idClient")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "IdDev")
	private Devise devise;

	@OneToMany(mappedBy = "facture")
	@JsonIgnore
	private Set<DetailFacture> detailFactures = new HashSet<>();

	@OneToMany(mappedBy = "facturesr")
	@JsonIgnore
	private Set<ReglementFacture> reglementFactures = new HashSet<>();


	public Long getIdFact() {
		return idFact;
	}

	public void setIdFact(Long idFact) {
		this.idFact = idFact;
	}

	public String getDateFact() {
		return dateFact;
	}

	public void setDateFact(String dateFact) {
		this.dateFact = dateFact;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Devise getDevise() {
		return devise;
	}

	public void setDevise(Devise devise) {
		this.devise = devise;
	}

	public Set<DetailFacture> getDetailFactures() {
		return detailFactures;
	}

	public void setDetailFactures(Set<DetailFacture> detailFactures) {
		this.detailFactures = detailFactures;
	}

	public Set<ReglementFacture> getReglementFactures() {
		return reglementFactures;
	}

	public void setReglementFactures(Set<ReglementFacture> reglementFactures) {
		this.reglementFactures = reglementFactures;
	}
}
