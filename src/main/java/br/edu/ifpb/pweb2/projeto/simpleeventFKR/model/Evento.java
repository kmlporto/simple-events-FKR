package br.edu.ifpb.pweb2.projeto.simpleeventFKR.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tb_evento")
public class Evento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Campo obrigatorio")
	private String descricao;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull(message = "Campo obrigatorio")
	@Future(message = "A data deve estar no futuro")
	private LocalDateTime data;

	private StatusEvento status;
	private String local;
	
	@ManyToOne
	private User dono;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "evento", cascade = CascadeType.REMOVE, orphanRemoval=true)
	@ElementCollection
	private List<Vaga> vagas = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "evento", cascade = CascadeType.ALL)
	private List<AvaliacaoEvento> avaliacaoEventos = new ArrayList<>();


	public Evento() {
	}
	
	public Evento(String d, LocalDateTime dh, String l) {
		super();
		this.descricao = d;
		this.data = dh;
		this.local = l;
	}

	public List<AvaliacaoEvento> getAvaliacaoEventos() {
		return avaliacaoEventos;
	}
	
	public void setAvaliacaoEventos(ArrayList<AvaliacaoEvento> avaliacao) {
		this.avaliacaoEventos = avaliacao;
	}

	public void add(AvaliacaoEvento avaliacao) {
		this.avaliacaoEventos.add(avaliacao);
	}
	
	public List<Vaga> getVagas() {
		return vagas;
	}

	public void setVagas(ArrayList<Vaga> vagas) {
		this.vagas = vagas;
	}

	public void add(Vaga vaga) {
		this.vagas.add(vaga);
	}

	public User getDono() {
		return dono;
	}

	public void setDono(User owner) {
		this.dono = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime datahora) {
		this.data = datahora;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public StatusEvento getStatus() {
		return status;
	}

	public void setStatus(StatusEvento status) {
		this.status = status;
	}

	public void setVagas(List<Vaga> vagas) {
		this.vagas = vagas;
	}

	public void setAvaliacaoEventos(List<AvaliacaoEvento> avaliacaoEventos) {
		this.avaliacaoEventos = avaliacaoEventos;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", descricao=" + descricao + ", data=" + data + ", local=" + local + ", owner="
				+ dono + ", vagas=" + vagas + ", avaliacao eventos=" + avaliacaoEventos + "]";
	}

	public Vaga findVagaByEspecialidade (Long idEspecialidade) {
		Vaga vaga = null;
		for(Vaga v: this.getVagas())
			if (v.getEspecialidade().getId() == idEspecialidade)
				vaga = v;
		return vaga;
	}

	public ArrayList<Vaga> descartarVagas (List<Long> especialidades) {
		System.out.println(especialidades);
		ArrayList<Vaga> vagas = new ArrayList<>(this.getVagas());
		for (Long i: especialidades)
			for (int j = 0; j < vagas.size(); j++) {
				if (vagas.get(j).getEspecialidade().getId() == i) {
					vagas.remove(vagas.get(j));
					j--;
				}
			}
		System.out.println(vagas);
		this.getVagas().removeAll(vagas);
		return vagas;
	}

	public void atualizarEvento (Evento eventoAntigo) {
		this.setVagas(new ArrayList<>(eventoAntigo.getVagas()));
		this.setAvaliacaoEventos(new ArrayList<>(eventoAntigo.getAvaliacaoEventos()));
		this.setDono(eventoAntigo.getDono());
	}

}
