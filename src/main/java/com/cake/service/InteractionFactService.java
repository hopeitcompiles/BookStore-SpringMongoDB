package com.cake.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.cake.model.InteractionFact;
import com.cake.repository.IInteractionFactRepository;


@Service
public class InteractionFactService extends GenericService<InteractionFact, String> {

	@Autowired
	private IInteractionFactRepository repository;

	
	@Override
	public CrudRepository<InteractionFact, String> getDao() {
		// TODO Auto-generated method stub
		return repository;
	}
	@Override
	public Optional<InteractionFact> selectId(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}
	
	public List<InteractionFact> findByCategory(String categoryid) {
//		Pageable pageable = PageRequest.of( 0, 10);
		return repository.findBybookcategory(categoryid);
	}

}
