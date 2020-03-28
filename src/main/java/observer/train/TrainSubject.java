package observer.train;

import java.util.ArrayList;
import java.util.List;

import domain.Train;
import domain.Wagon;
import main.CommandLineFrame;
import persistence.TrainDao;
import persistence.TrainPostgresDaoImpl;
import persistence.WagonDao;
import persistence.WagonPostgresDaoImpl;
import service.locomotiveFactory.LocomotiveFactory;

public class TrainSubject {

	private List<TrainObserver> observers = new ArrayList<TrainObserver>();
	private List<Train> trains = new ArrayList<Train>();
	private List<Wagon> wagons = new ArrayList<Wagon>();
	private static TrainSubject instance = null;
	private TrainDao tpdi = TrainPostgresDaoImpl.getInstance();
	private WagonDao wpdi = WagonPostgresDaoImpl.getInstance();
	private LocomotiveFactory locomotiveFactory = new LocomotiveFactory();

	private TrainSubject() {
		observers.add(new CommandLineFrame());
		trains = tpdi.findAll();
		wagons = wpdi.findAll();
	}

	public static TrainSubject getInstance() {
		if (instance == null) {
			instance = new TrainSubject();
		}
		return instance;
	}

	public boolean newTrain(String id, String engine) {
		for(Train t : trains) {
			String existingId = t.getId();
			if(existingId.contentEquals(id)) {
				System.out.println("train "+id+" already exists");
				return false;
			}
		}
		Train train = new Train();
		train.setId(id);
		train.setEngine(locomotiveFactory.createLocomotive(engine));
		trains.add(train);
		tpdi.save(train);
		notifyObservers();
		System.out.println("train " + train.getId() + " created");
		return true;
	}
	
	public void cloneTrain(Train train, String newId) {
		Train clone = train.clone(newId);
		trains.add(clone);
		notifyObservers();
	}

	public void updateTrain(Train train, String id) {
		for (int i = 0; i < trains.size(); i++) {
			String id1 = trains.get(i).getId();
			if (id1.equals(id)) {
				trains.set(i, train);
			}

		}
		notifyObservers();
	}

	public boolean deleteTrain(String id) {
		for (int i = 0; i < trains.size(); i++) {
			if (trains.get(i).getId().contentEquals(id)) {
				trains.remove(i);
				tpdi.delete(id);
				notifyObservers();
				return true;
			}
		}
		return false;
	}

	public Train findTrain(String id) {
		for (Train t : trains) {
			if (t.getId().equals(id)) {
				return t;
			}
		}
		return null;
	}
	
	public boolean newWagon(Wagon wagon) {
		for(Wagon w : wagons) {
			if(w.equals(wagon)) {
				System.out.println("wagon "+w.getType()+" already exists");
				return false;
			}
		}
		wagons.add(wagon);
		wpdi.save(wagon);
		notifyObservers();
		System.out.println("wagon " + wagon.getType() + " created with "+wagon.getSeats()+" seats");
		return true;
	}

	public void notifyObservers() {
		for (TrainObserver o : observers) {
			o.update(trains, wagons);
		}
	}

}
