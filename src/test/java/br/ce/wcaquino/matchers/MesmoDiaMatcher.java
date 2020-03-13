package br.ce.wcaquino.matchers;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class MesmoDiaMatcher extends TypeSafeMatcher<Date> {
	
	private Date dataEsperada;

	public MesmoDiaMatcher(Date dataEsperada) {
		this.dataEsperada = dataEsperada;
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean matchesSafely(Date data) {
		return isMesmaData(dataEsperada, data);
	}

}
