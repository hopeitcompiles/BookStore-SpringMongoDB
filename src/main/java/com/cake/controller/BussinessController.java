package com.cake.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cake.model.InteractionFact;
import com.cake.service.InteractionFactService;

@RequestMapping("bussiness")
@Controller
public class BussinessController {
	@Autowired
	private InteractionFactService interactionService;
	
	@PreAuthorize("hasAuthority('stat:read')")
	@GetMapping("/category/{id}")
	public String fetchMostValuableByCategory(Model model, @PathVariable("id") String categoryid) {
		List<InteractionFact> inter=interactionService.findByCategory(categoryid);
		model.addAttribute("interactions",inter);
		return "/bussiness/valuablecategory";
//		return inter;
	}
	@PreAuthorize("hasAuthority('stat:read')")
	@GetMapping("/")
	public String fetchFacts(Model model ) {
		List<InteractionFact> inter=interactionService.getAll();
		model.addAttribute("interactions",inter);
		return "/bussiness/valuablecategory";
//		return inter;
	}
}
