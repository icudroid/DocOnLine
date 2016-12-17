package net.dkahn.dao.fichier;

import net.dkahn.metier.Fichier;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class FichierDaoHibernate extends HibernateDaoSupport implements FichierDao{

	public Fichier getFichier(String id) {
		return (Fichier) getHibernateTemplate().get(Fichier.class, Long.parseLong(id));
	}

	public void removeFichier(Fichier fichier) {
		getHibernateTemplate().delete(fichier);
	}

	public void saveFichier(Fichier fichier) {
		getHibernateTemplate().saveOrUpdate(fichier);
	}

}
