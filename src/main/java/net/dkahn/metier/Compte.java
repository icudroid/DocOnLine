package net.dkahn.metier;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;

@Entity
public class Compte implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final Integer DROIT_ADMIN = 0;
	public static final Integer DROIT_USER = 1;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String pseudo;

	@Column(unique = true)
	private String email;

	private String motDePasse;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reppri_fk", nullable = false)
	private Repertoire racinePrivate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reppub_fk", nullable = false)
	private Repertoire racinePublic;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "repshare_fk", nullable = false)
	private Repertoire racineShare;

	private Integer droit;

	@Temporal(javax.persistence.TemporalType.DATE)
	private Date updateDate;

	@OneToOne
	@JoinColumn(name="comptesamis_id",referencedColumnName="id")
	private ComptesAmis comptesAmis;

	private String photo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}


	public Repertoire getRacinePrivate() {
		return racinePrivate;
	}

	public void setRacinePrivate(Repertoire racinePrivate) {
		this.racinePrivate = racinePrivate;
	}

	public Repertoire getRacinePublic() {
		return racinePublic;
	}

	public void setRacinePublic(Repertoire racinePublic) {
		this.racinePublic = racinePublic;
	}

	public Repertoire getRacineShare() {
		return racineShare;
	}

	public void setRacineShare(Repertoire racineShare) {
		this.racineShare = racineShare;
	}

	public Integer getDroit() {
		return droit;
	}

	public void setDroit(Integer droit) {
		this.droit = droit;
	}

	public ComptesAmis getComptesAmis() {
		return comptesAmis;
	}

	public void setComptesAmis(ComptesAmis comptesAmis) {
		this.comptesAmis = comptesAmis;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Compte other = (Compte) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		return true;
	}

}
