package service.locomotive;

import java.util.List;

import domain.locomotive.Locomotive;
import persistence.locomotive.LocomotiveDao;
import persistence.locomotive.LocomotivePostgresDaoImpl;
import service.Log;

public class LocomotiveService {
	
	private LocomotiveDao lpdi;
	private LocomotiveFactory locomotiveFactory;
	private Log log;
	
	public LocomotiveService() {
		lpdi = LocomotivePostgresDaoImpl.getInstance();
		locomotiveFactory = new LocomotiveFactory();
		log = new Log();
	}
	
	public List<Locomotive> findAll(){
		return lpdi.findAll();
	}
	
	public Locomotive newLocomotive(String type) {
		return locomotiveFactory.createLocomotive(type);
	}
	
	public void getSeats(String type) {
		log.info("numer of seats in locomotive "+type+": "+locomotiveFactory.createLocomotive(type).getSeats());
	}

}
