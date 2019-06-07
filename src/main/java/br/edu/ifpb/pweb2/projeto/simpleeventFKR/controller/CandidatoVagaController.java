package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.CandidatoVagaDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.VagaDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.CandidatoVaga;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Status;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Vaga;

@Controller
@RequestMapping("/meustrabalhos")
public class CandidatoVagaController {
	
	@Autowired 
	public EventoDAO eventoDAO;
	
	@Autowired
	public UserDAO userDAO;
	
	@Autowired
	public EspecialidadeDAO especDAO;
	
	@Autowired
	public VagaDAO vagaDAO;
	
	@Autowired
	public CandidatoVagaDAO candidatoVagaDAO;
	
	/**ROUTES
	 * 
	 * **/ 
	
	@GetMapping
	public ModelAndView exibirTrabalhos (Principal user) {
		List<CandidatoVaga> trabalhos = candidatoVagaDAO.findByCandidato(userDAO.findByEmail(user.getName()));
		ModelAndView modelForm = new ModelAndView("evento/meustrabalhos");
		modelForm.addObject("trabalhos", trabalhos);
		return modelForm;
	}
	
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes att) {
		CandidatoVaga trabalho = candidatoVagaDAO.findById(id).get();
		Vaga vaga = vagaDAO.findById(trabalho.getVaga().getId()).get();
		if (trabalho != null) {
			vaga.remove(trabalho);
			candidatoVagaDAO.deleteById(id);
			att.addFlashAttribute("deletado", "Evento deletado com sucesso!");
			att.addFlashAttribute("mensagem", "Evento deletado com sucesso!");			
		}
		return new ModelAndView("redirect:/meustrabalhos");
	}
	
}
