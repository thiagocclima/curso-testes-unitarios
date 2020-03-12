package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculaValorLocacaoTest {
	
	private LocacaoService service;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double valorEsperado;
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	private static Filme filme1 = new Filme("A volta dos que não foram", 3, 5.0);
	private static Filme filme2 = new Filme("O filme do Pelé", 4, 5.0);
	private static Filme filme3 = new Filme("Matrix", 10, 5.0);
	private static Filme filme4 = new Filme("O Rei Leão", 10, 5.0);
	private static Filme filme5 = new Filme("João e Maria", 2, 5.0);
	private static Filme filme6 = new Filme("João e o pé de feijão", 2, 5.0);
	private static Filme filme7 = new Filme("Enrolados", 2, 5.0);
	
	@Parameters
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2), 10.0},
			{Arrays.asList(filme1, filme2, filme3), 13.75},
			{Arrays.asList(filme1, filme2, filme3, filme4), 16.25},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 17.5},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 17.5},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 22.5},
		});
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoRegraDesconto() throws LocadoraException, FilmeSemEstoqueException {
		//cenário
		Usuario usuario = new Usuario("Thiago");
		
		
		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificação
		assertThat( locacao.getValor(), is(valorEsperado));
	}

}
