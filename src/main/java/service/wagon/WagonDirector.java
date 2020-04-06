package service.wagon;

import domain.Wagon;
import service.wagon.wagonbuilder.WagonBuilder;

public class WagonDirector {
	
	private WagonBuilder wagonBuilder;
	
	public WagonDirector(WagonBuilder wagonBuilder) {
		this.wagonBuilder = wagonBuilder;
	}

	public Wagon getWagon() {
		return this.wagonBuilder.getWagon();
	}

	public void makeWagon() {
		this.wagonBuilder.buildType();
		this.wagonBuilder.buildSeats();

	}
	
	

}
