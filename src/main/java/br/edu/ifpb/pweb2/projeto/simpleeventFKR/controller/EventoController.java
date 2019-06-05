package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.VagaDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Vaga;

@Controller
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired 
	public EventoDAO eventoDAO;
	
	@Autowired
	public UserDAO userDAO;
	
	@Autowired
	public EspecialidadeDAO especDAO;
	
	@Autowired
	public VagaDAO vagaDAO;
	
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
		return modelForm.addObject("especialidades", especDAO.findAll()); 
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/save")
	public ModelAndView save(@Valid Evento evento, 
			BindingResult result,
			RedirectAttributes att,
			@RequestParam("especialidades") List<Long> especialidades,
			@RequestParam("quantidades") List<Integer> quantidades
			) {
		if (result.hasErrors()) {
            return new ModelAndView("/form");
        }
		eventoDAO.save(evento);
		Optional<Especialidade> esp;
        int i = 0;
        for (Long id : especialidades) {
            esp = especDAO.findById(id);
            Vaga vaga = new Vaga();
            vaga.setEspecialidade(esp.get());
            vaga.setQtdVagas(quantidades.get(i));
            vaga.setEvento(evento);
            vagaDAO.save(vaga);
            evento.add(vaga);
            i++;
        }
        System.out.println(evento.toString());
		eventoDAO.save(evento);
        att.addFlashAttribute(eventoDAO.findAll());
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
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView modelForm = new ModelAndView("evento/form-update");
		modelForm.addObject("especialidades", especDAO.findAll());
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
