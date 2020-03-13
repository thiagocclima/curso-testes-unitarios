package br.ce.wcaquino.matchers;

import java.util.Calendar;

public class DateMatchers {
	
	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher isMonday() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}

}
