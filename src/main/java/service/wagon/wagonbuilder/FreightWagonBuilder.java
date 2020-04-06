package service.wagon.wagonbuilder;

import domain.Wagon;

public class FreightWagonBuilder implements WagonBuilder{
	
	private Wagon wagon;
	
	public FreightWagonBuilder() {
		this.wagon = new Wagon();
	}

	@Override
	public void buildType() {
		wagon.setType("freight");
		
	}

	@Override
	public void buildSeats() {
		wagon.setSeats(0);
		
	}

	@Override
	public Wagon getWagon() {
		return wagon;
	}
	
	

}
