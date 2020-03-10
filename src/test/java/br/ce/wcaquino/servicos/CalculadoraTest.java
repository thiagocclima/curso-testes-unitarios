package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class CalculadoraTest {
	
	@Test
	public void deveSomarDoisInteiros() {
		//cenário
		int a = 3;
		int b = 5;
		Calculadora calc = new Calculadora();
		
		//ação
		int result = calc.somar(a,b);
		
		//verificação
		assertEquals(8, result);
	}
	
	@Test
	public void deveSubtrairDoisInteiros() {
		//cenário
		int a = 3;
		int b = 5;
		Calculadora calc = new Calculadora();
		
		//ação
		int result = calc.subtrair(a,b);
		
		//verificação
		assertEquals(-2, result);
	}
	
	@Test
	public void deveDividirAporB() throws NaoPodeDividirPorZeroException {
		//cenário
		int a = 4;
		int b = 2;
		Calculadora calc = new Calculadora();
		
		//ação
		int result = calc.dividir(a,b);
		
		//verificação
		assertEquals( 2, result);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoDeDivisaoPorZero() throws NaoPodeDividirPorZeroException {
		//cenário
		int a = 10;
		int b = 0;
		Calculadora calc = new Calculadora();
		
		//ação
		calc.dividir(a,b);
	}

}
