package com.cake.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cake.model.InteractionFact;

public interface IInteractionFactRepository extends MongoRepository<InteractionFact, String>{
	@Query(value="{ 'book.category._id' : ?0 }")
	public List<InteractionFact> findBybookcategory(String categoryid);

}