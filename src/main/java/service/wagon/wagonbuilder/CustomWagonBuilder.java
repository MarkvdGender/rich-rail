package service.wagon.wagonbuilder;

import domain.Wagon;
import domain.Wagon;

public class CustomWagonBuilder implements WagonBuilder{
	
	private Wagon wagon;
	private String type;
	private int seats;
	
	public CustomWagonBuilder(String type, int seats) {
		this.wagon = new Wagon();
		this.type = type;
		this.seats = seats;
	}

	@Override
	public void buildType() {
		wagon.setType(type);
		
	}

	@Override
	public void buildSeats() {
		wagon.setSeats(seats);
		
	}

	@Override
	public Wagon getWagon() {
		return wagon;
	}
	

}
