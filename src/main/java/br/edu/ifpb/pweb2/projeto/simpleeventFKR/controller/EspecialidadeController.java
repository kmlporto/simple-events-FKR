package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;

@Controller
@RequestMapping("/especialidades")
public class EspecialidadeController {
	
	@Autowired
	private EspecialidadeDAO especDAO;
	
	@RequestMapping("/form")
	public ModelAndView form(Especialidade especialidade) {
		ModelAndView modelForm = new ModelAndView("especialidade/form");
		modelForm.addObject("especialidade", especialidade);
		return modelForm;
	}
	

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(Especialidade especialidade) {
		especDAO.save(especialidade);
		return list();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelList = new ModelAndView("especialidade/list");
		List<Especialidade> especialidade = especDAO.findAll();
		modelList.addObject("especialidades", especialidade);
		return modelList;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		ModelAndView modelList = new ModelAndView("especialidade/list");
		Optional<Especialidade> optionalEspecialidade = especDAO.findById(id);
		if (optionalEspecialidade != null) {
			modelList.addObject("deletado", "Evento deletado com sucesso!");
			especDAO.deleteById(id);
		}else
			modelList.addObject("error", "Evento não encontrado!");
		modelList.addObject("especialidades", especDAO.findAll());
		return modelList;
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView modelForm = new ModelAndView("especialidade/form");
		Optional<Especialidade> optionalEspecialidade = especDAO.findById(id);
		if (optionalEspecialidade != null)
			modelForm.addObject("evento", especDAO.findById(id));
		else
			modelForm.addObject("error", "Especialidade não encontrada!");
		return modelForm;
	}
	
}
