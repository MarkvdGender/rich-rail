package domain.locomotive;

import domain.RollingStock;

public class ElectricLocomotive implements Locomotive{
	
	String type;
	private int index;
	private int seats;
	
	public ElectricLocomotive() {
		this.type="electric";
		this.seats=1;
	}
	

	@Override
	public String getType() {
		return type;
	}


	@Override
	public void setIndex(int index) {
		this.index=index;
		
	}


	@Override
	public int getIndex() {
		return index;
		
	}
	
	@Override
	public int getSeats() {
		return seats;
	}
	

}
