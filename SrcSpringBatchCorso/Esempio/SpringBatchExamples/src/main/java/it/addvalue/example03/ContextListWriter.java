package it.addvalue.example03;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class ContextListWriter implements ItemWriter<SimpleTestValueObject> {

	private List<SimpleTestValueObject> listaInContesto;

	public void write(List<? extends SimpleTestValueObject> items)
			throws Exception {

		listaInContesto.addAll(items);

	}

	public void setListaInContesto(List<SimpleTestValueObject> listaInContesto) {
		this.listaInContesto = listaInContesto;
	}

}
