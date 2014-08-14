package it.addvalue.esercitazione.dao;

import com.arca.danni.datalayer.BasicDao;

/**
 * L'implementazione dell'interfaccia {@link IAnagraficaDao}.
 * 
 * @author arx50036
 * 
 */
public class AnagraficaDao extends BasicDao implements IAnagraficaDao {

	public void printCountRecordInTabella() {
		System.out.println("Il numero di record presenti in tabella e' "
				+ getJdbcTemplate().queryForInt(
						"SELECT COUNT(*) FROM ANAGRAFICA"));
	}

}
