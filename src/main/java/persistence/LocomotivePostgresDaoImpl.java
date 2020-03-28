package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.locomotive.Locomotive;
import service.locomotiveFactory.LocomotiveFactory;

public class LocomotivePostgresDaoImpl implements LocomotiveDao {

	private static Connection conn = PostgresBaseDao.getConnection();
	private static LocomotiveDao instance;
	
	private LocomotivePostgresDaoImpl() {
		
	}
	
	public static LocomotiveDao getInstance() {
		if(instance==null) {
			instance = new LocomotivePostgresDaoImpl();
		}
		return instance;
		}

	@Override
	public List<Locomotive> findAll() {
		List<Locomotive> deLocomotives = new ArrayList<Locomotive>();

		try {
			Statement stmt = conn.createStatement();
			String strQuery = "SELECT * FROM LOCOMOTIVE";
			ResultSet rs = stmt.executeQuery(strQuery);

			while (rs.next()) {
				LocomotiveFactory l = new LocomotiveFactory();
				Locomotive loco = l.createLocomotive(rs.getString("TYPE"));
				deLocomotives.add(loco);

			}
		} catch (SQLException sqle) {

		}

		return deLocomotives;
	}

}
