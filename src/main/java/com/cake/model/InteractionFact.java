package com.cake.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cake.auth.Gender;

import lombok.Data;



@Data
@Document(collection = "InteractionFacts")
public class InteractionFact {
	@Id
	private String id;
	private Interaction type;
	private int weigth;
	private Book book;
	private Gender usergender;
	private String userid;
	private Set<String> userFavoriteTopics;
	private Set<Category> userFavoriteCategories;
	
	
	
	public InteractionFact(String id, Interaction type, int weigth, Book book, Gender usergender, String userid,
			Set<String> userFavoriteTopics, Set<Category> userFavoriteCategories) {
		super();
		this.id = id;
		this.type = type;
		this.weigth = weigth;
		this.book = book;
		this.usergender = usergender;
		this.userid = userid;
		this.userFavoriteTopics = userFavoriteTopics;
		this.userFavoriteCategories = userFavoriteCategories;
	}
	
	
	public InteractionFact() {
		super();
	}


	public InteractionFact(User user, Book book,Interaction type) {
		this.userid=user.getId();
		this.book=book;
		this.weigth=type.getWeight();
		this.type=type;
		this.userFavoriteCategories=user.getFavoriteCategories();
		this.userFavoriteTopics=user.getFavoriteTopics() ;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Interaction getType() {
		return type;
	}
	public void setType(Interaction type) {
		this.type = type;
	}
	public int getWeigth() {
		return weigth;
	}
	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}
	public Book getBookId() {
		return this.book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	public Gender getUsergender() {
		return usergender;
	}
	public void setUsergender(Gender usergender) {
		this.usergender = usergender;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Set<String> getUserFavoriteTopics() {
		return userFavoriteTopics;
	}
	public void setUserFavoriteTopics(Set<String> userFavoriteTopics) {
		this.userFavoriteTopics = userFavoriteTopics;
	}
	public Set<Category> getUserFavoriteCategories() {
		return userFavoriteCategories;
	}
	public void setUserFavoriteCategories(Set<Category> userFavoriteCategories) {
		this.userFavoriteCategories = userFavoriteCategories;
	}
	public Book getBook() {
		return book;
	}


	
	
}

