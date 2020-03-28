package persistence;

import java.util.List;

import domain.RollingStock;
import domain.locomotive.Locomotive;

public interface TrainKoppelLocomotiveDao {
	
	
	public boolean save(RollingStock locomotive, String id, int index);
	
	public boolean deleteOnTrainId(String id);

	public List<Locomotive> findByTrainId(String id);

}
