package service.wagonBuilder;

import domain.Wagon;

public class PassengerWagonBuilder implements WagonBuilder{
	
	private Wagon wagon;
	
	public PassengerWagonBuilder() {
		this.wagon = new Wagon();
	}


	@Override
	public void buildType() {
		wagon.setType("passenger");
		
	}

	@Override
	public void buildSeats() {
		wagon.setSeats(50);
		
	}

	@Override
	public Wagon getWagon() {
		return wagon;
	}
	
	

}
