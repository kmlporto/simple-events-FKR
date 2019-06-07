package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;
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
	

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();


	/**ROUTES
	 * form (@RequestMapping("/form"))
	 * save (@RequestMapping(method=RequestMethod.POST))
	 * list (@RequestMapping(method=RequestMethod.GET))
	 * delete (@RequestMapping("/delete/{id}"))
	 * edit (@RequestMapping("/edit/{id}"))
	 * 
	 * **/ 
	@RequestMapping("/form")
	public ModelAndView form(Evento evento, Authentication auth) {
		ModelAndView modelForm = new ModelAndView("evento/form");
		return modelForm.addObject("especialidades", especDAO.findAll()); 
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/save")
	public ModelAndView save(@Valid Evento evento, 
			Authentication auth,
			BindingResult result,
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
        User currentUser = userDAO.findByEmail(auth.getName());
        evento.setDono(currentUser);
        eventoDAO.save(evento);
        return new ModelAndView("redirect:/eventos");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(Authentication auth) {
		ModelAndView modelList = new ModelAndView("evento/list");
		User usuarioLogado = userDAO.findByEmail(auth.getName());
		modelList.addObject("userLog", usuarioLogado);
		modelList.addObject("eventos", eventoDAO.findAll());
		return modelList;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ModelAndView detail(@PathVariable("id") Long id) {
		Evento evento = eventoDAO.findById(id).get();
		ModelAndView modelDetail = new ModelAndView("evento/detail");
		modelDetail.addObject("evento", evento);
		return modelDetail;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id,
			Authentication auth,
			RedirectAttributes att) {
		Evento evento = eventoDAO.findById(id).get();
		User usuarioLogado = userDAO.findByEmail(auth.getName());
		if (usuarioLogado.getId() != evento.getDono().getId()) {
			att.addAttribute("message", "você não pode deletar este evento");
			return new ModelAndView("redirect:/eventos");
		}
		if (evento != null) {
			att.addFlashAttribute("deletado", "Evento deletado com sucesso!");
			eventoDAO.deleteById(id);
		}
		return new ModelAndView("redirect:/eventos");
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id,
			RedirectAttributes att,
			Authentication auth) {
		ModelAndView modelForm = new ModelAndView("evento/form");
		Evento evento = eventoDAO.findById(id).get();
		User usuarioLogado = userDAO.findByEmail(auth.getName());
		if (usuarioLogado.getId() != evento.getDono().getId()) {
			att.addAttribute("message", "você não pode alterar este evento");
			return new ModelAndView("redirect:/eventos");
		}
		modelForm.addObject("especialidades", especDAO.findAll());
		modelForm.addObject("evento", evento);
		return modelForm;
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public ModelAndView update(
			Authentication auth,
			@PathVariable("id") Long id,
			@Valid Evento evento, 
			RedirectAttributes att,
			BindingResult result) {
	    if (result.hasErrors()) {
	    	evento.setId(id);
	        return new ModelAndView("evento/form");
	    }
	    evento.setDono(userDAO.findByEmail(auth.getName()));
	    eventoDAO.save(evento);
	    return new ModelAndView("redirect:/eventos");
	}
}
