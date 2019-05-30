package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

@RequestMapping("usuarios")
@Controller
public class UserController {

	@Autowired
	public UserDAO dao;
	
	@RequestMapping("")
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("usuarios");
		List<User> usuarios = dao.findAll();
		model.addObject("usuarios", usuarios);
		return model;
	}
	
	
	@PostMapping("/save")
	public String save(@Valid User user, BindingResult  bindingResult) {
		if (bindingResult.hasErrors())
			return "usuarioForm";
		else { 
			dao.save(user);
			return "redirect:usuarios";
		}
	}
	
	@RequestMapping("/form")
	public ModelAndView form(User user) {
		ModelAndView model = new ModelAndView("usuarioForm");
		model.addObject("user", user);
		return model;
	}
}
