package domain;

public class Wagon implements RollingStock {

	private String type;
	private int seats;
	private int index;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSeats(int seats) {
		this.seats = seats;
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
