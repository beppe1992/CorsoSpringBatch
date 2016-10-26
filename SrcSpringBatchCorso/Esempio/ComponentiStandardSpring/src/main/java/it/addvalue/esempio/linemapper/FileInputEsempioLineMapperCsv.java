package it.addvalue.esempio.linemapper;

import it.addvalue.esempio.generics.InputObject;

import org.springframework.batch.item.file.LineMapper;

public class FileInputEsempioLineMapperCsv implements LineMapper<InputObject> {

	public InputObject mapLine(String line, int lineNumber) throws Exception {

		String[] recordDivisi = line.split(";");

		InputObject outputObject = new InputObject();

		outputObject.setNome(recordDivisi[0]);
		outputObject.setEta(Integer.parseInt(recordDivisi[1]));

		return outputObject;
	}

}
