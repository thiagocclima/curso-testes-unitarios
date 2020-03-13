package br.ce.wcaquino.matchers;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;

public class DateMatchers {
	
	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher isMonday() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static MesmoDiaMatcher ehHoje() {
		return new MesmoDiaMatcher(new Date());
	}
	
	public static MesmoDiaMatcher ehHojeComDiferencaDeDias(Integer quantidadeDias) {
		return new MesmoDiaMatcher( adicionarDias(new Date(), quantidadeDias) );
	}

}
