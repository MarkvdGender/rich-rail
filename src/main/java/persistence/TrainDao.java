package persistence;

import java.util.List;

import domain.Train;

public interface TrainDao {
	
	public List<Train> findAll();
	
	public Train findById(String id);
	
	public boolean save(Train train);
	
	public boolean update(Train train);
	
	public boolean delete(String id);

}
