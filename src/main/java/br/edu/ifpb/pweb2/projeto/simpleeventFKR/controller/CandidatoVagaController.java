package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Status;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.StatusEvento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Vaga;

@Controller
@RequestMapping("/candidaturas")
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
	
	@Autowired
	public AvaliacaoEventoDAO avaliacaoEventoDAO;
	
	/**ROUTES
	 * 
	 * **/ 
	
	@GetMapping("/meustrabalhos")
	public ModelAndView exibirTrabalhos (Principal user) {
		List<CandidatoVaga> trabalhos = candidatoVagaDAO.findByCandidato(userDAO.findByEmail(user.getName()));
		User usuario = userDAO.findByEmail(user.getName());
		List<AvaliacaoEvento> avaliacoes = avaliacaoEventoDAO.findByParticipante(usuario); 
		Map<Long,String> mapAvaliacoes = fazerDicionarioAvaliacoeUsuario(avaliacoes);
		ModelAndView modelForm = new ModelAndView("evento/meustrabalhos");
		modelForm.addObject("trabalhos", trabalhos);
		modelForm.addObject("mapAvaliacoes", mapAvaliacoes);
		return modelForm;
	}
	
	private Map<Long,String> fazerDicionarioAvaliacoeUsuario(List<AvaliacaoEvento> avaliacoesDoUsuario) {
		Map<Long,String> mapAvaliacoes = new HashMap<Long, String>();
		for (AvaliacaoEvento avaliacaoEvento : avaliacoesDoUsuario) {
			mapAvaliacoes.put(avaliacaoEvento.getEvento().getId(), Integer.toString(avaliacaoEvento.getNotaAvaliacaoEvento()));
		}
		return mapAvaliacoes;
	}
	
	@RequestMapping("/meustrabalhos/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes att) {
		CandidatoVaga trabalho = candidatoVagaDAO.findById(id).get();
		Vaga vaga = vagaDAO.findById(trabalho.getVaga().getId()).get();
		if (trabalho != null) {
			vaga.remove(trabalho);
			candidatoVagaDAO.deleteById(id);
			att.addFlashAttribute("mensagem", "Evento deletado com sucesso!");			
		}
		return new ModelAndView("redirect:/candidaturas/meustrabalhos");
	}
	
	@RequestMapping("/{id}/aprovar")
	public ModelAndView aprovarCandidato (@PathVariable("id") Long id,
			RedirectAttributes att) {
		CandidatoVaga candidatura = candidatoVagaDAO.findById(id).get();
		Boolean validacao = validarCandidatura(candidatura.getVaga(), candidatura.getCandidato());
		if(validacao) {
			candidatura.setStatus(Status.APROVADO);
			candidatoVagaDAO.save(candidatura);
			att.addFlashAttribute("mensagem", "Aprovado com sucesso!");
			if(verificaVagasCompletas(candidatura.getVaga().getEvento())) {
				fechaEventoPorVagasPreenchidas(candidatura.getVaga().getEvento());
			}
		}else {
			candidatura.setStatus(Status.NAO_APROVADO);
			candidatoVagaDAO.save(candidatura);
			att.addFlashAttribute("mensagemerro", "Não foi possível aprovar, candidato já aprovado no evento ou Vaga lotada!");	
		}
		return new ModelAndView(String.format("redirect:/eventos/%d/candidatos",candidatura.getVaga().getEvento().getId()));
	}
	
	@RequestMapping("/{id}/reprovar")
	public ModelAndView reprovarCandidato (@PathVariable("id") Long id,
			RedirectAttributes att) {
		CandidatoVaga candidatura = candidatoVagaDAO.findById(id).get();
		candidatura.setStatus(Status.NAO_APROVADO);
		candidatoVagaDAO.save(candidatura);
		att.addFlashAttribute("mensagemsecundaria", "Candidato reprovado com sucesso!");	
		return new ModelAndView(String.format("redirect:/eventos/%d/candidatos",candidatura.getVaga().getEvento().getId()));
	}
	
	private boolean validarCandidatura (Vaga vaga, User candidato) {
		
		int qntAprovados = 0;
		
		for (CandidatoVaga candidatura : vaga.getCandidatoVaga()) {
			if(candidatura.getStatus() == Status.APROVADO) {
				qntAprovados++;
			}
		}
		
		if (qntAprovados < vaga.getQtdVagas()) {
			List<Vaga> vagas = vaga.getEvento().getVagas();
			
			for (Vaga vagaanalise : vagas) {
				List<CandidatoVaga> candidaturas = vagaanalise.getCandidatoVaga();
				for (CandidatoVaga candidatura : candidaturas) {
					if(candidatura.getCandidato().equals(candidato) && candidatura.getStatus() == Status.APROVADO) {
						return false;
					}
				}
			}
			return true;	
		}
		return false;
	}
	
	private boolean verificaVagasCompletas(Evento e) {
		List<Vaga> vagas = e.getVagas();
		int qntpreenchida;
		boolean teste = false;
		for (Vaga vaga : vagas) {
			qntpreenchida = 0; 
			for (CandidatoVaga candidatura : vaga.getCandidatoVaga()) {
				if(candidatura.getStatus() == Status.APROVADO) {
					qntpreenchida++;
				}
			}
			if(qntpreenchida == vaga.getQtdVagas()) {
				teste = true;
			}else {
				return false;
			}
		}
		if(teste) {
			return true;
		}
		return false;
	}
	
	private void fechaEventoPorVagasPreenchidas(Evento e) {
		e.setStatus(StatusEvento.FECHADO);
		eventoDAO.save(e);
		List<Vaga> vagas = e.getVagas();
		for (Vaga vaga : vagas) {
			for (CandidatoVaga candidatura : vaga.getCandidatoVaga()) {
				if(candidatura.getStatus()==Status.AGUARDANDO_APROVACAO) {
					candidatura.setStatus(Status.NAO_APROVADO);
					candidatoVagaDAO.save(candidatura);
				}
			}
			
		}
	}
	
}
