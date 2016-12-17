package net.dkahn.metier;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ComptesAmis {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany (cascade=CascadeType.ALL)
	private Set<Compte> amis =new HashSet<Compte>();

	@OneToOne (mappedBy="comptesAmis")
	private Compte compte;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Compte> getAmis() {
		return amis;
	}

	public void setAmis(Set<Compte> amis) {
		this.amis = amis;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	
	public void addAmi(Compte ami){
		amis.add(ami);
	}
	
	public void removeAmi(Compte ami){
		amis.remove(ami);
	}

}
