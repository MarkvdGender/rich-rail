package persistence;

import java.util.List;

import domain.RollingStock;
import domain.Wagon;

public interface TrainKoppelWagonDao {
	

	
	public List<Wagon> findByTrainId(String id);
	
	public boolean save(RollingStock wagon, String id, int index);
	
	public boolean deleteOnTrainId(String id);


}
