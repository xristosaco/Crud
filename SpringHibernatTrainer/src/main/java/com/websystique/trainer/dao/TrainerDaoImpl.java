package com.websystique.trainer.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.trainer.model.Trainer;

@Repository("trainerDao")
public class TrainerDaoImpl extends AbstractDao<Integer, Trainer> implements TrainerDao {

	public Trainer findById(int id) {
		return getByKey(id);
	}

	public void saveTrainer(Trainer trainer) {
		persist(trainer);
	}

	public void deleteTrainerBySsn(String ssn) {
		Query query = getSession().createSQLQuery("delete from Trainer where ssn = :ssn");
		query.setString("ssn", ssn);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Trainer> findAllTrainers() {
		Criteria criteria = createEntityCriteria();
		return (List<Trainer>) criteria.list();
	}

	public Trainer findTrainerBySsn(String ssn) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssn", ssn));
		return (Trainer) criteria.uniqueResult();
	}
}
