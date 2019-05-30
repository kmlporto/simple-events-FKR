package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

@RequestMapping("usuarios")
@Controller
public class UserController {

	@Autowired
	public UserDAO dao;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("usuarios");
		model.addObject("usuarios", dao.findAll());
		return model;
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(@Valid User user, BindingResult  bindingResult) {
		if (bindingResult.hasErrors())
			return form(user);
		else { 
			dao.save(user);
			return list();
		}
	}
	
	@RequestMapping("/form")
	public ModelAndView form(User user) {
		ModelAndView model = new ModelAndView("usuarioForm");
		model.addObject("user", user);
		return model;
	}
}
