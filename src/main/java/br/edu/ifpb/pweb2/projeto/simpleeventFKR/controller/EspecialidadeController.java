package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;

@Controller
@RequestMapping("/especialidades")
public class EspecialidadeController {
	
	@Autowired
	private EspecialidadeDAO dao;
	
	@RequestMapping("")
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("especialidade/list");
		List<Especialidade> espec = dao.findAll();
		model.addObject("especialidades", espec);
		return model;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String save(Especialidade especialidade) {
		dao.save(especialidade);
		return "redirect:especialidades";
	}
	
	@RequestMapping("/form")
	public String form() {
		return "especialidade/form";
	}
}
