package service.wagon.wagonbuilder;

import domain.Wagon;

public interface WagonBuilder {
	

	public void buildType();
	
	public void buildSeats();
	
	public Wagon getWagon();

}
