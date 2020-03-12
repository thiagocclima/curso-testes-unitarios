package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.verificarDiaSemana;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	@Test
	public void testeLocacao() throws Exception {
		Assume.assumeFalse( verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// Cenário
		Usuario usuario = new Usuario("Thiago");
		List<Filme> filmes = Arrays.asList(new Filme("Onde os fracos não tem vez", 2, 8.0));
		
		// Ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verificação
		error.checkThat(locacao.getValor(), is( equalTo(8.0) ) );
		error.checkThat(isMesmaData(new Date(), locacao.getDataLocacao()), is(true));		
		error.checkThat(isMesmaData(adicionarDias(new Date(), 1), locacao.getDataRetorno()), is(true));
	}
	
	/*
	 * Forma elegante
	 * */
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws LocadoraException, FilmeSemEstoqueException {
		// Cenário
		Usuario usuario = new Usuario("Thiago");
		List<Filme> filmes = Arrays.asList(new Filme("Onde os fracos não tem vez", 0, 8.0));
		
		// Ação
		service.alugarFilme(usuario, filmes);
	}
	
	/*
	 * Forma robusta
	 * */
	@Test
	public void naoDeveAlugarFilmeSemEstoque_2() {
		// Cenário
		Usuario usuario = new Usuario("Thiago");
		List<Filme> filmes = Arrays.asList(new Filme("Onde os fracos não tem vez", 0, 8.0));
		
		// Ação
		try {
			service.alugarFilme(usuario, filmes);
			fail("Não deveria ter alugado");
		} catch (FilmeSemEstoqueException e) {
			assertThat( e.getMessage(), is("Filme sem estoque") );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Forma 'nova'
	 * */
	@Test
	public void naoDeveAlugarFilmeSemEstoque_3() throws LocadoraException, FilmeSemEstoqueException {
		// Cenário
		Usuario usuario = new Usuario("Thiago");
		List<Filme> filmes = Arrays.asList(new Filme("Onde os fracos não tem vez", 0, 8.0));
		
		exception.expect(FilmeSemEstoqueException.class);
		exception.expectMessage( is("Filme sem estoque"));
		
		// Ação
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void naoDeveAlugarParaUsuarioVazio() {
		List<Filme> filmes = Arrays.asList(new Filme("Filme 2", 0, 4.0));
		
		try {
			service.alugarFilme(null, filmes);
			fail();
		} catch (LocadoraException e) {
			assertThat( e.getMessage(), is("Usuário vazio") );
		} catch (FilmeSemEstoqueException e) {
			fail();
		}
	}
	
	@Test
	public void nãoDeveAlugarParaFilmeVazio() throws LocadoraException, FilmeSemEstoqueException {
		Usuario usuario = new Usuario("Thiago");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		service.alugarFilme(usuario, null);
	}
	
	@Test
	//@Ignore //pula o teste independente da situação. Exibe o teste como executado
	public void deveDevolverNaSegundaLocacaoFeitaNoSabado() throws LocadoraException, FilmeSemEstoqueException {

		// O Assume faz com que o teste seja executado somente aos sábados, 
		// garantindo que poderá ser executado com sucesso
		Assume.assumeTrue( verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenário
		Usuario usuario = new Usuario("Thiago");
		List<Filme> filmes = new ArrayList<>();
		filmes.add(new Filme("A volta dos que não foram", 3, 5.0));
		
		//ação
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificação
		assertTrue( verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY) );
	}
	
}
