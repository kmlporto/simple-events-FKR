package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.AvaliacaoEventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.CandidatoVagaDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.VagaDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.AvaliacaoEvento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.CandidatoVaga;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;

@Controller
@RequestMapping("/avaliar")
public class AvaliarController {
	
	@Autowired 
	public EventoDAO eventoDAO;
	
	@Autowired
	public UserDAO userDAO;
	
	@Autowired
	public EspecialidadeDAO especDAO;
	
	@Autowired
	public VagaDAO vagaDAO;
	
	@Autowired
	public AvaliacaoEventoDAO avaliacaoEventoDAO;

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();


	@Autowired
	public CandidatoVagaDAO candidatoVagaDAO;
	
	/**ROUTES
	 * 
	 * **/ 
	
	@PostMapping("/{id}")
	public ModelAndView detail(@PathVariable("id") Long id,
			Principal userlogado,
			@RequestParam("notaAvaliacaoEvento") int notaAvaliacaoEvento,
			RedirectAttributes att
			) {
		CandidatoVaga candidatura = candidatoVagaDAO.findById(id).get();
		AvaliacaoEvento avaliacao = new AvaliacaoEvento();
		Evento evento = eventoDAO.findById(candidatura.getVaga().getEvento().getId()).get();
		avaliacao.setEvento(evento);
		avaliacao.setNotaAvaliacaoEvento(notaAvaliacaoEvento);
		avaliacao.setParticipante(userDAO.findByEmail(userlogado.getName()));
		avaliacao = avaliacaoEventoDAO.save(avaliacao);
		evento.add(avaliacao);
		eventoDAO.save(evento);   
		ModelAndView model = new ModelAndView("redirect:/candidaturas/meustrabalhos");
		att.addFlashAttribute("mensagem", "Avaliado com sucesso!");
		return model;
	}
}
