package persistence.wagon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Wagon;
import persistence.connection.PostgresBaseDao;
import persistence.trainkoppelwagon.TrainKoppelWagonDao;
import persistence.trainkoppelwagon.TrainKoppelWagonPostgresDaoImpl;

public class WagonPostgresDaoImpl implements WagonDao {

	private static Connection conn = PostgresBaseDao.getConnection();
	private static WagonDao instance;
	
	private WagonPostgresDaoImpl() {
		
	}
	
	public static WagonDao getInstance() {
		if(instance==null) {
			instance = new WagonPostgresDaoImpl();
		}
		return instance;
		}

	@Override
	public List<Wagon> findAll() {
		List<Wagon> deWagons = new ArrayList<Wagon>();

		try {
			Statement stmt = conn.createStatement();
			String strQuery = "SELECT * FROM WAGON";
			ResultSet rs = stmt.executeQuery(strQuery);
			

			while (rs.next()) {
				Wagon w = new Wagon();
				w.setType(rs.getString("TYPE"));
				w.setSeats(rs.getInt("SEATS"));
				deWagons.add(w);

			}
		} catch (SQLException sqle) {

		}

		return deWagons;
	}

	@Override
	public boolean save(Wagon wagon) {
		try {

			String strQuery = "INSERT INTO WAGON (TYPE, SEATS) VALUES(?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, wagon.getType());
			pstmt.setInt(2, wagon.getSeats());
			pstmt.executeUpdate();

			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Wagon wagon) {
		delete(wagon);
		return save(wagon);
	}

	@Override
	public boolean delete(Wagon wagon) {
		try {
			TrainKoppelWagonDao tkwpdi = TrainKoppelWagonPostgresDaoImpl.getInstance();
			String type = wagon.getType();
			tkwpdi.deleteOnWagonType(type);
			String strQuery = "DELETE FROM WAGON WHERE TYPE = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, type);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public Wagon findByType(String type) {
		Wagon wagon = new Wagon();

		try {
			String strQuery = "SELECT * FROM WAGON WHERE TYPE = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, type);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				wagon.setType(rs.getString("TYPE"));
				wagon.setSeats(rs.getInt("SEATS"));

			}
		} catch (SQLException sqle) {

		}

		return wagon;
	}

}
