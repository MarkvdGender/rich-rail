package domain;

import java.util.ArrayList;
import java.util.List;

import domain.locomotive.Locomotive;

public class Train implements Cloneable{

	private List<RollingStock> allRollingStock;
	private String id;
	private String name;
	private Locomotive engine;

	public Train() {
		allRollingStock = new ArrayList<RollingStock>();
	}
	
	public Train clone() {
		Train object = null;
		try {
			object = (Train) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("oh oh");
			e.printStackTrace();
		}
		
		return object;
	}

	public List<RollingStock> getAllRollingStock() {
		return allRollingStock;
	}

	public void setAllRollingStock(List<RollingStock> allRollingStock) {
		this.allRollingStock = allRollingStock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Locomotive getEngine() {
		return engine;
	}

	public void setEngine(Locomotive engine) {
		this.engine = engine;
	}
	
	public int getSeats() {
		int seats = 0;
		for(RollingStock r : allRollingStock) {
			seats+= r.getSeats();
		}
		return seats;
	}
	
	
	
	

	
}
