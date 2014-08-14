package it.addvalue.esercitazione.processor;

import it.addvalue.esercitazione.dto.Anagrafica;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnagraficaItemProcessorUnitTest {

	private AnagraficaItemProcessor processor;

	private Anagrafica anagrafica;

	@Before
	public void init() {
		processor = new AnagraficaItemProcessor();
		anagrafica = new Anagrafica();
		anagrafica.setCognome("cognome");
	}

	@Test
	public void processCognome() throws Exception {
		// il cognome contiene dei caratteri validi e mi aspetto ritorni lo
		// stesso oggetto passato
		Assert.assertEquals(anagrafica, processor.process(anagrafica));
	}

	@Test
	public void processSpaceCognome() throws Exception {
		// il cognome contiene spazi e mi aspetto ritorni lo stesso oggetto
		// passato
		anagrafica.setCognome(" ");
		Assert.assertEquals(anagrafica, processor.process(anagrafica));
	}

	@Test
	public void nullCognome() throws Exception {
		// il cognome e' null mi aspetto ritorni null
		anagrafica.setCognome(null);
		Assert.assertNull(processor.process(anagrafica));
	}

	@Test(expected = NullPointerException.class)
	public void nullAnagrafica() throws Exception {
		// se viene passato un oggetto null verra' lanciata un'eccezzione
		processor.process(null);
	}
}
