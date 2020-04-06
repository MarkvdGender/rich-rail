package service.train;

import java.util.List;

import domain.Train;
import persistence.train.TrainDao;
import persistence.train.TrainPostgresDaoImpl;
import service.Log;
import service.locomotive.LocomotiveService;

public class TrainService {
	
	private TrainDao tpdi;
	private LocomotiveService locomotiveService;
	private Log log;
	
	public TrainService() {
		tpdi = TrainPostgresDaoImpl.getInstance();
		locomotiveService = new LocomotiveService();
		log = new Log();
	}
	
	public List<Train> findAll(){
		return tpdi.findAll();
	}
	
	public Train findById(String id) {
		return tpdi.findById(id);
	}
	
	public boolean save(Train train) {
		return tpdi.save(train);
	}
	
	public boolean update(Train train) {
		return tpdi.update(train);
	}
	
	public boolean delete(String id) {
		return tpdi.delete(id);
	}
	
	public Train newTrain(String id, String engine) {
		Train train = new Train();
		train.setId(id);
		
		train.setEngine(locomotiveService.newLocomotive(engine));
		
		
		this.save(train);
		return train;
	}
	
	public Train cloneTrain(Train train, String newId) {
		
		return train.clone(newId);
		
	}
	
	public void getSeats(String id) {
		log.info("number of seats in train "+id+": "+this.findById(id).getSeats());
	}



}
