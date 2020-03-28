package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.RollingStock;
import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;
import service.locomotiveFactory.LocomotiveFactory;

public class TrainPostgresDaoImpl implements TrainDao {

	private static Connection conn = PostgresBaseDao.getConnection();
	private TrainKoppelLocomotiveDao tklpdi = TrainKoppelLocomotivePostgresDaoImpl.getInstance();
	private TrainKoppelWagonDao tkwpdi = TrainKoppelWagonPostgresDaoImpl.getInstance();
	private static TrainDao instance;

	private TrainPostgresDaoImpl() {

	}

	public static TrainDao getInstance() {
		if (instance == null) {
			instance = new TrainPostgresDaoImpl();
		}
		return instance;
	}

	@Override
	public List<Train> findAll() {
		List<Train> deTrains = new ArrayList<Train>();

		try {
			Statement stmt = conn.createStatement();
			String strQuery = "SELECT * FROM Train";
			ResultSet rs = stmt.executeQuery(strQuery);

			while (rs.next()) {
				Train train = new Train();
				LocomotiveFactory lC = new LocomotiveFactory();
				Locomotive engine = lC.createLocomotive(rs.getString("LOCOMOTIVE_TYPE"));
				String id = rs.getString("ID");
				List<Locomotive> deLocomotives = tklpdi.findByTrainId(id);
				List<Wagon> deWagons = tkwpdi.findByTrainId(id);
				List<RollingStock> deRollingStock = new ArrayList<RollingStock>();
				for (Wagon w : deWagons) {
					deRollingStock.add(w);
				}
				for (Locomotive l : deLocomotives) {
					deRollingStock.add(l);
				}
				train.setId(id);
				train.setEngine(engine);
				train.setAllRollingStock(deRollingStock);
				deTrains.add(train);

			}
		} catch (SQLException sqle) {

		}

		return deTrains;
	}

	@Override
	public Train findById(String id) {
		Train train = new Train();
		LocomotiveFactory lC = new LocomotiveFactory();
		List<RollingStock> deRollingStock = new ArrayList<RollingStock>();
		List<RollingStock> deRollingStockOrdered = new ArrayList<RollingStock>();

		try {
			String strQuery = "SELECT * FROM TRAIN WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Locomotive engine = lC.createLocomotive(rs.getString("LOCOMOTIVE_TYPE"));
				List<Locomotive> deLocomotives = tklpdi.findByTrainId(id);
				List<Wagon> deWagons = tkwpdi.findByTrainId(id);
				for(Wagon w : deWagons) {
					deRollingStock.add(w);
				}
				for(Locomotive l : deLocomotives) {
					deRollingStock.add(l);
				}
				for(int i=0; i<deRollingStock.size(); i++) {
					for(RollingStock r : deRollingStock) {
						if(r.getIndex()==i) {
							deRollingStockOrdered.add(r);
						}
					}
				}
				train.setId(id);
				train.setEngine(engine);
				train.setAllRollingStock(deRollingStock);
			}
		} catch (SQLException sqle) {

		}

		return train;
	}

	@Override
	public boolean save(Train train) {
		try {

			String strQuery = "INSERT INTO TRAIN (ID, LOCOMOTIVE_TYPE) VALUES(?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, train.getId());
			pstmt.setString(2, train.getEngine().getType());
			pstmt.executeUpdate();
			for (int i=0; i<train.getAllRollingStock().size(); i++) {
				RollingStock r = train.getAllRollingStock().get(i);
				if (r instanceof Wagon) {
					tkwpdi.save(r, train.getId(), i);
				} else if (r instanceof Locomotive) {
					tklpdi.save(r, train.getId(), i);
				}
			}

			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Train train) {

		tkwpdi.deleteOnTrainId(train.getId());
		tklpdi.deleteOnTrainId(train.getId());

		for (int i=0; i< train.getAllRollingStock().size(); i++) {
			RollingStock r = train.getAllRollingStock().get(i);
			if (r instanceof Wagon) {
				tkwpdi.save(r, train.getId(), i);
			} else if (r instanceof Locomotive) {
				tklpdi.save(r, train.getId(), i);
			}
		}
		return true;

	}

	@Override
	public boolean delete(String id) {
		try {

			String strQuery = "DELETE FROM TRAIN WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(strQuery);
			pstmt.setString(1, id);
			tkwpdi.deleteOnTrainId(id);
			tklpdi.deleteOnTrainId(id);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

}
