package service.wagonBuilder;

import domain.Wagon;

public interface WagonBuilder {
	

	public void buildType();
	
	public void buildSeats();
	
	public Wagon getWagon();

}
