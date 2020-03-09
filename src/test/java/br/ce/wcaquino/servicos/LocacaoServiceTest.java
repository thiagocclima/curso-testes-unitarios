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
	@Test(expected = Exception.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
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
		} catch (Exception e) {
			assertThat( e.getMessage(), is("Filme sem estoque") );
		}
	}
	
	/*
	 * Forma elegante
	 * */
	@Test
	public void testeLocacao_filmeSemEstoque_3() throws Exception {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Thiago");
		Filme filme = new Filme("Onde os fracos não tem vez", 0, 8.0);
		
		exception.expect(Exception.class);
		exception.expectMessage( is("Filme sem estoque"));
		
		// Ação
		service.alugarFilme(usuario, filme);
	}

}
