package com.cake.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cake.auth.ApplicationUser;
import com.cake.auth.Gender;
import com.cake.auth.Role;
import com.cake.model.Book;
import com.cake.model.Category;
import com.cake.model.Interaction;
import com.cake.model.InteractionFact;
import com.cake.model.Sale;
import com.cake.model.User;
import com.cake.service.BookService;
import com.cake.service.CategoryService;
import com.cake.service.InteractionFactService;
import com.cake.service.SaleService;
import com.cake.service.UserService;

@Controller
public class PrivateController {
	@Autowired
	private UserService userservice;
	@Autowired
	private BookService bookservice;
	@Autowired
	private CategoryService categoryservice;
	@Autowired
	private InteractionFactService interactionservice;
//	@Autowired
//	private List<Category> categories;
	
	@Autowired
	private SaleService saleservice;
	
	
	@GetMapping("/profile")
	public String profilePage(Model model, Authentication authentication) {
		if(authentication!=null && authentication.isAuthenticated()) {
			User currentUser = ((ApplicationUser)  authentication.getPrincipal()).getUser();
			model.addAttribute("user", currentUser);
			model.addAttribute("results",new ArrayList<Book>(currentUser.getHistory()));
			//model.addAttribute("results", bookservice.queryById(currentUser.getHistory(),3));
			return "private/profile";
		}else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/profile/interests")
	public String profileInterests(Model model, Authentication authentication) {	
		if(authentication!=null && authentication.isAuthenticated()) {
			User usuario = ((ApplicationUser)  authentication.getPrincipal()).getUser();
			if(usuario.getFavoriteCategories()==null) {
				usuario.setFavoriteCategories(new HashSet<Category>());	
			}
			model.addAttribute("categories", categoryservice.getAll());
//			model.addAttribute("categories", categories);
			model.addAttribute("usuario",usuario);
			return "private/interests";
			
		}else {
			return "redirect:/login";
		}
	}
	
	@PostMapping("/profile/interests")
	public String profileInterestsSave(Model model,@ModelAttribute User usuario, Authentication authentication) {	
		if(authentication!=null && authentication.isAuthenticated()) {
			User current = ((ApplicationUser)  authentication.getPrincipal()).getUser();
			if(usuario!=null) {
				Set<Category> set=usuario.getFavoriteCategories();
				set.remove(null);
				Iterator<Category> it=set.iterator();
				while(it.hasNext()) {
					Category cat=it.next();
					cat.setDescription(null);
					cat.setImage(null);
				}
				current.setFavoriteCategories(set);
				userservice.updateFavoriteCategories(set, usuario.getId());
			}
		}
		return "redirect:/profile";
	}
	
	
	
	@GetMapping("/book/buysuccess")
	public String buy() {
		return "book/bookbuy";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("book/buy")
	public String buyForm(Model model, Book book, int quanty, Authentication authentication) {
		if(book!=null && quanty>0) {
			if(authentication!=null && authentication.isAuthenticated()) {
				User user = ((ApplicationUser)  authentication.getPrincipal()).getUser();
				Sale sale=new Sale(user,book,quanty);
				System.out.println(book.getTitle());
				Sale result=saleservice.save(sale);
				user.addBought(sale);
				if(result!=null) {
					userservice.updateBought(user.getBought(),user.getId());
					bookservice.updateBought(book.getId(),quanty);
					//////
					InteractionFact i=new InteractionFact(user,book,Interaction.SALE);
					interactionservice.save(i);
					return "redirect:/book/buysuccess";
				}
			}
		}
		if(book!=null) {
			return "redirect:/book/detail/"+book.getId();
		}else {
			return "redirect:/";
		}
	}
	
	@GetMapping("profile/edit")
	public String editUser(Model model, Authentication authentication) {
		if(authentication!=null && authentication.isAuthenticated()) {
			User user = ((ApplicationUser)  authentication.getPrincipal()).getUser();
			model.addAttribute("roles",Role.values());
			model.addAttribute("genders",Gender.values());
			model.addAttribute("usuario", user);
			model.addAttribute("editing",true);
			return "public/signup";
		}
		return "redirect:/login";
	}
	
	@PostMapping("profile/updatephoto")
	public String updatePhoto(Model model, @RequestParam("file") MultipartFile file, User user, Authentication authentication) throws IOException {
		if(authentication!=null && authentication.isAuthenticated()) {
			User currentuser = ((ApplicationUser)  authentication.getPrincipal()).getUser();
			if(!file.isEmpty()) {
				if(file.getBytes()!=null) {
					if(user.getId()!=null) {
						user.setProfilePicture(Base64.getEncoder().encodeToString(file.getBytes())); 
						currentuser.setProfilePicture(user.getProfilePicture());
						userservice.updateStringType("profilePicture",user.getProfilePicture(), user.getId());
					}
				}
			}
		}			
		return "redirect:/profile";
	}
	
	@PostMapping("profile/updatepassword")
	public String updatePassword(Model model, User user) {
		if(user.getId()!=null && user.getPassword()!=null) {
			if(!user.getPassword().isEmpty()) {
				user.setPassword(userservice.encodePassword(user.getPassword()));
				userservice.updateStringType("password",user.getPassword(), user.getId());
				return "redirect:/logout";
			}
		}			
		return "redirect:/profile";
	}
}
