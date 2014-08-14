package it.addvalue.esercitazione.dao;

/**
 * Contratto per chi manipola la tabella ANAGRAFICA
 * 
 * @author arx50036
 * 
 */
public interface IAnagraficaDao {

	public static final String INSERIMENTO_TABELLA = "INSERT INTO ANAGRAFICA (NOME, COGNOME, CODICE_FISCALE) VALUE (?,?,?)";

	public void printCountRecordInTabella();

}
