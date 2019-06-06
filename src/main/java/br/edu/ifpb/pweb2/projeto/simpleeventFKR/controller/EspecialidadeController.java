package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	public ModelAndView form() {
		ModelAndView model = new ModelAndView("especialidade/form");
		return model;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/save")
	public ModelAndView save(@Valid Especialidade especialidade, 
			BindingResult result, 
			RedirectAttributes att){
		if (result.hasErrors()) {
            return new ModelAndView("/form");
        }
		especDAO.saveAndFlush(especialidade);
        att.addFlashAttribute("especialidades", especDAO.findAll());
        return new ModelAndView("redirect:/especialidades");
	}


	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelList = new ModelAndView("especialidade/list");
		modelList.addObject("especialidades", especDAO.findAll());
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
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes att) {
		ModelAndView modelForm = new ModelAndView("especialidade/form");
		modelForm.addObject("especialidade", especDAO.findById(id).get());
		return modelForm;
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public ModelAndView update(@PathVariable("id") Long id, 
			@Valid Especialidade especialidade, 
			RedirectAttributes att,
			BindingResult result) {
	    if (result.hasErrors()) {
	        especialidade.setId(id);
	        return new ModelAndView("especialidade/form");
	    }
	         
	    especDAO.save(especialidade);
	    att.addFlashAttribute("especialidades", especDAO.findAll());

        return new ModelAndView("redirect:/especialidades");
	}
	
}
