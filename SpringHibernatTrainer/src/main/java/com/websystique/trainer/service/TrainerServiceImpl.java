package com.websystique.trainer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.trainer.dao.TrainerDao;
import com.websystique.trainer.model.Trainer;

@Service("trainerService")
@Transactional
public class TrainerServiceImpl implements TrainerService {

	@Autowired
	private TrainerDao dao;
	
	public Trainer findById(int id) {
		return dao.findById(id);
	}

	public void saveTrainer(Trainer trainer) {
		dao.saveTrainer(trainer);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateTrainer(Trainer trainer) {
		Trainer entity = dao.findById(trainer.getId());
		if(entity!=null){
			entity.setName(trainer.getName());
			entity.setJoiningDate(trainer.getJoiningDate());
			entity.setSalary(trainer.getSalary());
			entity.setSsn(trainer.getSsn());
		}
	}

	public void deleteTrainerBySsn(String ssn) {
		dao.deleteTrainerBySsn(ssn);
	}
	
	public List<Trainer> findAllTrainers() {
		return dao.findAllTrainers();
	}

	public Trainer findTrainerBySsn(String ssn) {
		return dao.findTrainerBySsn(ssn);
	}

	public boolean isTrainerSsnUnique(Integer id, String ssn) {
		Trainer trainer = findTrainerBySsn(ssn);
		return ( trainer == null || ((id != null) && (trainer.getId() == id)));
	}
	
}
