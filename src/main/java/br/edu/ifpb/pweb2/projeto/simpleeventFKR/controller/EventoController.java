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
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;

@Controller
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired 
	public EventoDAO eventoDAO;
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
		return new ModelAndView("evento/form");
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/save")
	public ModelAndView save(@Valid Evento evento, 
			BindingResult result,
			RedirectAttributes att) {
		if (result.hasErrors()) {
            return new ModelAndView("/form");
        }
		eventoDAO.saveAndFlush(evento);
        att.addFlashAttribute("eventos", eventoDAO.findAll());
        return new ModelAndView("redirect:/eventos");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelList = new ModelAndView("evento/list");
		modelList.addObject("eventos", eventoDAO.findAll());
		return modelList;
	}
	
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes att) {
		Optional<Evento> optionalEvento = eventoDAO.findById(id);
		if (optionalEvento != null) {
			att.addFlashAttribute("deletado", "Evento deletado com sucesso!");
			eventoDAO.deleteById(id);
		}
		return new ModelAndView("redirect:/eventos");
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes att) {
		ModelAndView modelForm = new ModelAndView("evento/form-update");
		modelForm.addObject("evento", eventoDAO.findById(id).get());
		return modelForm;
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public ModelAndView update(@PathVariable("id") Long id, 
			@Valid Evento evento, 
			RedirectAttributes att,
			BindingResult result) {
	    if (result.hasErrors()) {
	    	evento.setId(id);
	        return new ModelAndView("evento/form-update");
	    }
	         
	    eventoDAO.save(evento);
	    att.addFlashAttribute("eventos", eventoDAO.findAll());

        return new ModelAndView("redirect:/eventos");
	}
}