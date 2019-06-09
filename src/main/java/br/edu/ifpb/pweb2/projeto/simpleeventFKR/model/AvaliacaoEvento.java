package br.edu.ifpb.pweb2.projeto.simpleeventFKR.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_avaliacao_evento")
public class AvaliacaoEvento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne	
	private Evento evento;
	private int notaAvaliacaoEvento;
	@OneToOne
	private User participante;

	public AvaliacaoEvento() {
	};

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNotaAvaliacaoEvento() {
		return notaAvaliacaoEvento;
	}

	public void setNotaAvaliacaoEvento(int notaAvaliacaoEvento) {
		this.notaAvaliacaoEvento = notaAvaliacaoEvento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public User getParticipante() {
		return participante;
	}

	public void setParticipante(User participante) {
		this.participante = participante;
	}

	public String toString() {
		return "AvaliaçãoEvento [participante= "+ this.getParticipante().getNome() + ", nota= " + this.getNotaAvaliacaoEvento();
	}

}
