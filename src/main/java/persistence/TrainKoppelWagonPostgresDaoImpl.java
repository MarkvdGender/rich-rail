package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.RollingStock;
import domain.Wagon;

public class TrainKoppelWagonPostgresDaoImpl implements TrainKoppelWagonDao{
	
	private static Connection conn = PostgresBaseDao.getConnection();
	private static TrainKoppelWagonDao instance;
	private WagonDao wpdi = WagonPostgresDaoImpl.getInstance();
	
	private TrainKoppelWagonPostgresDaoImpl() {
		
	}
	
	public static TrainKoppelWagonDao getInstance() {
		if(instance==null) {
			instance = new TrainKoppelWagonPostgresDaoImpl();
		}
		return instance;
		}

	@Override
	public List<Wagon> findByTrainId(String id) {
		List<Wagon> deWagons = new ArrayList<Wagon>();

		try {
			String strQuery = "SELECT tw.wagon_TYPE, tw.index FROM train\r\n"
					+ "	INNER JOIN train_wagon as tw ON ID = tw.train_ID WHERE ID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Wagon w = wpdi.findByType(rs.getString("WAGON_TYPE"));
				w.setIndex(rs.getInt("INDEX"));
				deWagons.add(w);

			}

		} catch (SQLException sqle) {

		}

		return deWagons;
	}

	@Override
	public boolean save(RollingStock wagon, String id, int index) {
		try {

			String strQuery = "INSERT INTO TRAIN_WAGON (TRAIN_ID, WAGON_TYPE, INDEX) VALUES(?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, id);
			pstmt.setString(2,wagon.getType());
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

			String strQuery = "DELETE FROM TRAIN_WAGON WHERE TRAIN_ID = ?";
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
