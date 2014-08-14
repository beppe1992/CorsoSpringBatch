package it.addvalue.esercitazione.processor;

import it.addvalue.esercitazione.dto.Anagrafica;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;

public class AnagraficaItemProcessor implements
		ItemProcessor<Anagrafica, Anagrafica> {

	public Anagrafica process(Anagrafica item) throws Exception {
		if (StringUtils.isEmpty(item.getCognome())) {
			return null;
		}
		return item;
	}

}
