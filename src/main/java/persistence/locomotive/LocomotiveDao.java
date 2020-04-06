package persistence.locomotive;

import java.util.List;

import domain.locomotive.Locomotive;

public interface LocomotiveDao {
	
	public List<Locomotive> findAll();

	
}
