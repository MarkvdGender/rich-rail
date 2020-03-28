package domain.locomotive;

import domain.RollingStock;

public class SteamLocomotive implements Locomotive {

	private String type;
	private int index;
	private int seats;
	
	public SteamLocomotive() {
		this.type = "steam";
		this.seats=1;
	}
	

	@Override
	public String getType() {
		return type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


	@Override
	public int getSeats() {
		return seats;
	}
	
	
	
	



}
