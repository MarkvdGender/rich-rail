package service.wagon;

import java.util.List;

import domain.Wagon;
import persistence.wagon.WagonDao;
import persistence.wagon.WagonPostgresDaoImpl;
import service.Log;
import service.wagon.wagonbuilder.CustomWagonBuilder;

public class WagonService {
	
	private WagonDao wpdi;
	private WagonDirector wagonDirector;
	private Log log;
	
	public WagonService() {
		wpdi=WagonPostgresDaoImpl.getInstance();
		log = new Log();
	}
	
	public List<Wagon> findAll(){
		return wpdi.findAll();
	}
	
	public Wagon findByType(String type) {
		return wpdi.findByType(type);
	}
	
	public boolean save(Wagon wagon) {
		return wpdi.save(wagon);
	}

	public boolean delete(Wagon wagon) {
		return wpdi.delete(wagon);
	}
	
	public Wagon newWagon(String type, int seats) {
		wagonDirector = new WagonDirector(new CustomWagonBuilder(type, seats));
		wagonDirector.makeWagon();
		Wagon wagon = wagonDirector.getWagon();
		this.save(wagon);
		return wagon;
	}
	
	public void getSeats(String type) {
		log.info("number of seats in wagon "+type+": "+this.findByType(type).getSeats());
	}
}
