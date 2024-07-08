package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.web.service.RefrigeratorService;

@Controller
@SessionAttributes("user")
@RequestMapping("refrigerator")
public class RefrigeratorController {
	
	@Autowired
	RefrigeratorService refService;

	@GetMapping("")
	public String refrigerator() {
		return "refrigerator/refrigerator_main";
	}
	
//	@GetMapping("addIngredient")
//	public String addIngredient(Model model) {
//		model.addAttribute("meatCategory" , refService.getMeat());
//		return "refrigerator/refrigerator_add";
//	}
	
	
	@GetMapping("addIngredient")
	public String addIngredient(Model model ) {
		model.addAttribute("getAll" , refService.getAllCategory());
		model.addAttribute("getMeat" , refService.getCategory("육류"));
		model.addAttribute("getVegetable" , refService.getCategory("채소"));
		model.addAttribute("getFruit" , refService.getCategory("과일"));
		model.addAttribute("getSauce" , refService.getCategory("양념"));
		model.addAttribute("getGrains" , refService.getCategory("곡류"));
		model.addAttribute("getProcessed" , refService.getCategory("가공식품"));
		model.addAttribute("getSeafood" , refService.getCategory("해산물"));
		model.addAttribute("getDairy" , refService.getCategory("유제품"));
		
		return "refrigerator/refrigerator_add";
	}
	
	
	
}
