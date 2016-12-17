package net.dkahn.dao.compte;

import net.dkahn.metier.Compte;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CompteDaoHibernate extends HibernateDaoSupport implements CompteDao {

	public Compte getCompte(String id) {
		return (Compte) getHibernateTemplate().get(Compte.class, Long.parseLong(id));
	}

	public void removeCompte(Compte compte) {
		getHibernateTemplate().delete(compte);
	}

	public void saveCompte(Compte compte) {
		getHibernateTemplate().saveOrUpdate(compte);
	}

	public boolean isCompteExists(String email, String pseudo) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		// create a new criteria
		Criteria crit = session.createCriteria(Compte.class);
		crit.add(Expression.eq("pseudo", pseudo));
		crit.add(Expression.eq("email", email));
		return (crit.uniqueResult()!=null);
	}

	public Compte checkLogin(String pseudo, String pwd) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		// create a new criteria
		Criteria crit = session.createCriteria(Compte.class);
		crit.add(Expression.eq("pseudo", pseudo));
		crit.add(Expression.eq("motDePasse", pwd));
		return (Compte) crit.uniqueResult();
	}

	@Override
	public Compte getCompteByPseudo(String pseudo) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria crit = session.createCriteria(Compte.class);
		crit.add(Expression.eq("pseudo", pseudo));
		return (Compte) crit.uniqueResult();
	}
}
