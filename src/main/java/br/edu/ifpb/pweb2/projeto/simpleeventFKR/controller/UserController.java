package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelList = new ModelAndView("user/list");
		modelList.addObject("usuarios", dao.findAll());
		return modelList;
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(@Valid User user, BindingResult  bindingResult) {
		if (bindingResult.hasErrors())
			return form(user);
		else {
			user.setSenha(passwordEncoder.encode(user.getSenha()));
			dao.save(user);
			return new ModelAndView("redirect:/");
		}
	}
	
	@RequestMapping("/form")
	public ModelAndView form(User user) {
		ModelAndView modelForm = new ModelAndView("user/form");
		modelForm.addObject("user", user);
		return modelForm;
	}
}
