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

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;

@Controller
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired 
	public EventoDAO eventDAO;
	@Autowired
	public UserDAO userDAO;
	@Autowired
	public EspecialidadeDAO especDAO;
	
	/**ROUTES
	 * form (@RequestMapping("/form"))
	 * save (@RequestMapping(method=RequestMethod.POST))
	 * list (@RequestMapping(method=RequestMethod.GET))
	 * delete (@RequestMapping("/delete/{id}"))
	 * edit (@RequestMapping("/edit/{id}"))
	 * 
	 * **/ 
	@RequestMapping("/form")
	public ModelAndView form(Evento evento) {
		ModelAndView modelForm = new ModelAndView("evento/form");
		modelForm.addObject("evento", evento);
		return modelForm;
	}	
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(@Valid Evento evento, BindingResult result) {
		if (result.hasErrors())
			return form(evento);
		else {
			eventDAO.save(evento);
			return list();
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelList = new ModelAndView("evento/list");
		modelList.addObject("eventos", eventDAO.findAll());
		return modelList;
	}
	
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		ModelAndView modelList = new ModelAndView("evento/list");
		Optional<Evento> optionalEvento = eventDAO.findById(id);
		if (optionalEvento != null) {
			modelList.addObject("deletado", "Evento deletado com sucesso!");
			eventDAO.deleteById(id);
		}else
			modelList.addObject("error", "Evento não encontrado!");
		return modelList;
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView modelForm = new ModelAndView("evento/form");
		Optional<Evento> optionalEvento = eventDAO.findById(id);
		if (optionalEvento != null)
			modelForm.addObject("evento", eventDAO.findById(id));
		else
			modelForm.addObject("error", "Evento não encontrado!");
		return modelForm;
	}
}
