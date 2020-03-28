package service.wagonBuilder;

import domain.Wagon;

public class FirstClassWagonBuilder implements WagonBuilder{
	
	private Wagon wagon;
	
	public FirstClassWagonBuilder() {
		this.wagon = new Wagon();
	}

	@Override
	public void buildType() {
		wagon.setType("first class");
		
	}

	@Override
	public void buildSeats() {
		wagon.setSeats(20);
		
	}

	@Override
	public Wagon getWagon() {
		return wagon;
	}



	

}
