package it.addvalue.esempio.linemapper;

import it.addvalue.esempio.generics.InputObject;

import org.springframework.batch.item.file.LineMapper;

public class FileInputEsempioLineMapperFixedFile implements
		LineMapper<InputObject> {

	public InputObject mapLine(String line, int lineNumber) throws Exception {

		InputObject outputObject = new InputObject();

		outputObject.setNome(line.substring(0, 10));
		outputObject.setEta(Integer.parseInt(line.substring(10, 13)));

		return outputObject;
	}

}
