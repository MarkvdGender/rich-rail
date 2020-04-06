package persistence.wagon;

import java.util.List;

import domain.Wagon;

public interface WagonDao {
	
	public List<Wagon> findAll();
	
	public Wagon findByType(String type);
	
	public boolean save(Wagon wagon);
	
	public boolean update(Wagon wagon);
	
	public boolean delete(Wagon wagon);

}
