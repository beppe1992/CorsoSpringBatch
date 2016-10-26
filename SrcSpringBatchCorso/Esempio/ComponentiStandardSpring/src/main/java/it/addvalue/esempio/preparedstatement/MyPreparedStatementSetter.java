package it.addvalue.esempio.preparedstatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import it.addvalue.esempio.generics.InputObject;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class MyPreparedStatementSetter implements
		ItemPreparedStatementSetter<InputObject> {

	public void setValues(InputObject item, PreparedStatement ps)
			throws SQLException {

		ps.setString(0, item.getNome());
		ps.setInt(1, item.getEta());
	}

}
