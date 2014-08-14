package it.addvalue.esercitazione.dto;

/**
 * L'oggetto che contiene i valori dell'anagrafica.
 * 
 * @author arx50036
 * 
 */
public class Anagrafica {

	private String nome;
	private String cognome;
	private String codiceFiscale;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
}
