package net.dkahn.dao.repertoire;

import net.dkahn.metier.Compte;
import net.dkahn.metier.Repertoire;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class RepertoireDaoHibernate extends HibernateDaoSupport implements
		RepertoireDao {

	public Repertoire getRepertoire(String id) {
		return (Repertoire) getHibernateTemplate().get(Repertoire.class,
				Long.parseLong(id));
	}

	public void removeRepertoire(Repertoire repertoire) {
		getHibernateTemplate().delete(repertoire);
	}

	public void saveRepertoire(Repertoire repertoire) {
		getHibernateTemplate().saveOrUpdate(repertoire);
	}

	public Compte getCompteRacine(String idFolder) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		// create a new criteria
		Criteria crit = session.createCriteria(Compte.class);

		Repertoire repertoire = getRepertoire(idFolder);

		switch (repertoire.getType()) {
		case Repertoire.REPERTOIRE_PUBLIC:
			crit.add(Expression.eq("racinePublic.id", Long.parseLong(idFolder)));
			break;
		case Repertoire.REPERTOIRE_PARTAGE:
			crit.add(Expression.eq("racineShare.id", Long.parseLong(idFolder)));
			break;
		case Repertoire.REPERTOIRE_PRIVE:
			crit.add(Expression.eq("racinePrivate.id", Long.parseLong(idFolder)));
			break;
		}
		
		return (Compte) crit.uniqueResult();
	}

}
