package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.RollingStock;
import domain.locomotive.Locomotive;
import service.locomotiveFactory.LocomotiveFactory;

public class TrainKoppelLocomotivePostgresDaoImpl implements TrainKoppelLocomotiveDao{
	
	private static Connection conn = PostgresBaseDao.getConnection();
	private static TrainKoppelLocomotiveDao instance;
	
	private TrainKoppelLocomotivePostgresDaoImpl() {
		
	}
	
	public static TrainKoppelLocomotiveDao getInstance() {
		if(instance==null) {
			instance = new TrainKoppelLocomotivePostgresDaoImpl();
		}
		return instance;
		}

	@Override
	public List<Locomotive> findByTrainId(String id) {
		List<Locomotive> deLocomotives = new ArrayList<Locomotive>();
		LocomotiveFactory l = new LocomotiveFactory();

		try {
			String strQuery = "SELECT tl.locomotive_TYPE, tl.index FROM train\r\n"
					+ "	INNER JOIN train_locomotive as tl ON ID = tl.train_ID WHERE ID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Locomotive loco = l.createLocomotive(rs.getString("LOCOMOTIVE_TYPE"));
				loco.setIndex(rs.getInt("INDEX"));
				deLocomotives.add(loco);
			}

		} catch (SQLException sqle) {

		}

		return deLocomotives;
	}

	@Override
	public boolean save(RollingStock locomotive, String id, int index) {
		try {

			String strQuery = "INSERT INTO TRAIN_LOCOMOTIVE (TRAIN_ID, LOCOMOTIVE_TYPE, INDEX) VALUES(?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, id);
			pstmt.setString(2, locomotive.getType());
			pstmt.setInt(3, index);
			pstmt.executeUpdate();

			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteOnTrainId(String id) {
		try {

			String strQuery = "DELETE FROM TRAIN_LOCOMOTIVE WHERE TRAIN_ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}
	
	
	
	

}
