package com.cake.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cake.model.Book;
import com.cake.model.User;
import com.cake.service.BookService;
import com.cake.service.UserService;

@RestController
@RequestMapping("api")
public class RestBookController {
	@Autowired
	private BookService bookservice;
	@Autowired
	private UserService userservice;
	
	@GetMapping("/books")
	public List<Book> fecthAllBooks(){
		return bookservice.getAll();
	}
	
	@PreAuthorize("hasAuthority('user:read')")
	@GetMapping("/users")
	public List<User> fecthAllUsers(){
		return userservice.getAll();
	}
	
	@PreAuthorize("hasAuthority('user:delete')")
	@DeleteMapping("/users/{id}")
	public String fecthAllUsers( @PathVariable("id") String id){
		try{
			userservice.delete(id);
			return "deleted "+id;
		}catch (Exception e) {
			return "something is wrong";
		}
	}
	
}
