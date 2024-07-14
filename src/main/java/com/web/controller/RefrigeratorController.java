package com.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.web.domain.Ingredient;
import com.web.domain.Refrigerator;
import com.web.domain.SearchRecipe;
import com.web.domain.User;
import com.web.service.RefrigeratorService;
import com.web.util.SessionConst;

@Controller
@SessionAttributes("user")
@RequestMapping("refrigerator")
public class RefrigeratorController {

	@Autowired
	RefrigeratorService refService;

	@Autowired
	private HttpSession session;

	// 냉장고 메인 페이지
	@GetMapping("")
	public String refrigerator(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		User loginMember = (User) session.getAttribute("user");
		if (loginMember != null) {
			Long userNumber = loginMember.getUserNumber();

			Pageable pageable = PageRequest.of(page - 1, size);
			Page<Refrigerator> refriPage = refService.getRefriList(pageable, userNumber);
			model.addAttribute("refriList", refriPage.getContent());
			model.addAttribute("totalPages", refriPage.getTotalPages());
			model.addAttribute("currentPage", page);
			return "refrigerator/refrigerator_main";
		} else {
			return "redirect:/login/login";
		}
	}

	// 재료 삭제
	@PostMapping("ingredientDelete")
	public String ingredientDelete(@RequestBody List<Long> refrigeratorNumbers) {
		refService.ingredientDelete(refrigeratorNumbers);
		return "redirect:/refrigerator";
	}

	// 재료 추가 페이지
	@GetMapping("addIngredient")
	public String addIngredient(Model model) {
		model.addAttribute("getAll", refService.getAllCategory());
		model.addAttribute("getMeat", refService.getCategory("육류"));
		model.addAttribute("getVegetable", refService.getCategory("채소"));
		model.addAttribute("getFruit", refService.getCategory("과일"));
		model.addAttribute("getSauce", refService.getCategory("양념"));
		model.addAttribute("getGrains", refService.getCategory("곡류"));
		model.addAttribute("getProcessed", refService.getCategory("가공식품"));
		model.addAttribute("getSeafood", refService.getCategory("해산물"));
		model.addAttribute("getDairy", refService.getCategory("유제품"));
		return "refrigerator/refrigerator_add";
	}

	// 재료 추가
	@PostMapping("ingredientAddConfirm")
	public String ingredientAddConfirm(@RequestBody List<Refrigerator> refrigerators) {
		refService.ingredientAddConfirm(refrigerators);
		return "redirect:/refrigerator";
	}

	// 레시피 검색
	@GetMapping("searchRecipe")
	public String searchRecipe(Model model) {
		User loginMember = (User) session.getAttribute("user");
		Long userNumber = loginMember.getUserNumber();
		List<String> ingre = refService.getIngreName(userNumber);
		List<SearchRecipe> recipe = refService.getRecipe(ingre);
		model.addAttribute("ingre", ingre);
		model.addAttribute("recipe", recipe);
		return "refrigerator/refrigerator_search";
	}

}
