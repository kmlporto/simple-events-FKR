package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		especDAO.saveAndFlush(especialidade);
		return new ModelAndView("redirect:/especialidades");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelList = new ModelAndView("especialidade/list");
		List<Especialidade> especialidade = especDAO.findAll();
		modelList.addObject("especialidades", especialidade);
		return modelList;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes att) {
		Optional<Especialidade> optionalEspecialidade = especDAO.findById(id);
		if (optionalEspecialidade != null) {
			att.addFlashAttribute("deletado", "Especialidade deletada com sucesso!");
			especDAO.deleteById(id);
		}
		return new ModelAndView("redirect:/especialidades");
	}
	
	@RequestMapping(value="/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes att) {
		ModelAndView modelForm = new ModelAndView("especialidade/form");
		modelForm.addObject("especialidade", especDAO.findById(id).get());
		
		return modelForm;
	}
	
}
