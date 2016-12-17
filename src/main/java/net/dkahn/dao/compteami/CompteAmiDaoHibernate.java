package net.dkahn.dao.compteami;

import java.util.List;
import java.util.Set;

import net.dkahn.metier.Compte;
import net.dkahn.metier.ComptesAmis;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CompteAmiDaoHibernate extends HibernateDaoSupport implements CompteAmiDao{

	@Override
	public ComptesAmis getCompteAmi(String id) {
		return (ComptesAmis) getHibernateTemplate().get(ComptesAmis.class, Long.parseLong(id));
	}

	@Override
	public void removeCompteAmi(ComptesAmis ami) {
		getHibernateTemplate().delete(ami);
		
	}

	@Override
	public void saveCompteAmi(ComptesAmis ami) {
		getHibernateTemplate().saveOrUpdate(ami);
	}

	@Override
	public List<Compte> getComptesAmiForCompte(String idCompte) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		// create a new criteria
		Query createQuery = session.createQuery("select ca.compte from ComptesAmis ca join ca.amis cs where cs.id=:id");
		createQuery.setLong("id", Long.parseLong(idCompte));
		
		return createQuery.list();
	}




}
