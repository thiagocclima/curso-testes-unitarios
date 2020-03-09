package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

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
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testeLocacao() throws Exception {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Thiago");
		Filme filme = new Filme("Onde os fracos não tem vez", 2, 8.0);
		
		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);

		// Verificação
		error.checkThat(locacao.getValor(), is( equalTo(8.0) ) );
		error.checkThat(isMesmaData(new Date(), locacao.getDataLocacao()), is(true));		
		error.checkThat(isMesmaData(adicionarDias(new Date(), 1), locacao.getDataRetorno()), is(true));
	}
	
	/*
	 * Forma elegante
	 * */
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws LocadoraException, FilmeSemEstoqueException {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Thiago");
		Filme filme = new Filme("Onde os fracos não tem vez", 0, 8.0);
		
		// Ação
		service.alugarFilme(usuario, filme);
	}
	
	/*
	 * Forma robusta
	 * */
	@Test
	public void testeLocacao_filmeSemEstoque_2() {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Thiago");
		Filme filme = new Filme("Onde os fracos não tem vez", 0, 8.0);
		
		// Ação
		try {
			service.alugarFilme(usuario, filme);
			fail("Não deveria ter alugado");
		} catch (FilmeSemEstoqueException e) {
			assertThat( e.getMessage(), is("Filme sem estoque") );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Forma elegante
	 * */
	@Test
	public void testeLocacao_filmeSemEstoque_3() throws LocadoraException, FilmeSemEstoqueException {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Thiago");
		Filme filme = new Filme("Onde os fracos não tem vez", 0, 8.0);
		
		exception.expect(FilmeSemEstoqueException.class);
		exception.expectMessage( is("Filme sem estoque"));
		
		// Ação
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testeLocacao_usuarioVazio() {
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		try {
			service.alugarFilme(null, filme);
			fail();
		} catch (LocadoraException e) {
			assertThat( e.getMessage(), is("Usuário vazio") );
		} catch (FilmeSemEstoqueException e) {
			fail();
		}
	}
	
	@Test
	public void testeLocacao_filmeVazio() throws LocadoraException, FilmeSemEstoqueException {
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Thiago");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		service.alugarFilme(usuario, null);
	}

}
