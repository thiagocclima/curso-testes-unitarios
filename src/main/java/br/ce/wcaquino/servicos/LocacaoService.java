package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) 
			throws LocadoraException, FilmeSemEstoqueException  {
		validar(usuario, filmes);
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		double desconto = 0d;
		double valorTotal = 0d;
		for (int i = 0; i < filmes.size(); i++) {
			if (i > 1 && i < 6) {
				desconto += 0.25;
			}
			
			valorTotal += filmes.get(i).getPrecoLocacao() - (filmes.get(i).getPrecoLocacao() * desconto);
		}
		locacao.setValor( valorTotal );

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	private void validar(Usuario usuario, List<Filme> filmes) 
			throws LocadoraException, FilmeSemEstoqueException {
		if (usuario == null) {
			throw new LocadoraException("Usuário vazio");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		};
	}
	
}