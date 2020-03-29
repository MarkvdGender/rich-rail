package service.observer;

import java.util.ArrayList;
import java.util.List;

import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;
import persistence.LocomotiveDao;
import persistence.LocomotivePostgresDaoImpl;
import persistence.TrainDao;
import persistence.TrainPostgresDaoImpl;
import persistence.WagonDao;
import persistence.WagonPostgresDaoImpl;
import presentation.WagonDirector;
import presentation.frame.CommandLineFrame;
import service.locomotiveFactory.LocomotiveFactory;
import service.wagonBuilder.CustomWagonBuilder;

public class TrainSubject {

	private List<TrainObserver> observers = new ArrayList<TrainObserver>();
	private List<Train> trains = new ArrayList<Train>();
	private List<Wagon> wagons = new ArrayList<Wagon>();
	private List<Locomotive> locomotives = new ArrayList<Locomotive>();
	private static TrainSubject instance = null;
	private TrainDao tpdi = TrainPostgresDaoImpl.getInstance();
	private WagonDao wpdi = WagonPostgresDaoImpl.getInstance();
	private LocomotiveDao lpdi = LocomotivePostgresDaoImpl.getInstance();
	private LocomotiveFactory locomotiveFactory = new LocomotiveFactory();
	private WagonDirector wagonDirector;

	private TrainSubject() {
		trains = tpdi.findAll();
		wagons = wpdi.findAll();
		locomotives = lpdi.findAll();
	}

	public static TrainSubject getInstance() {
		if (instance == null) {
			instance = new TrainSubject();
		}
		return instance;
	}
	
	public void addObserver(TrainObserver o ) {
		observers.add(o);
		o.update(trains, wagons, locomotives);
		
	}

	public boolean newTrain(String id, String engine) {
		if (findTrain(id) != null) {
			System.out.println("train " + id + " already exists");
			return false;
		}
		Train train = new Train();
		train.setId(id);
		train.setEngine(locomotiveFactory.createLocomotive(engine));
		trains.add(train);
		tpdi.save(train);
		System.out.println("train " + train.getId() + " created");
		notifyObservers();
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

	public void deleteTrain(String id) {
		Train train = findTrain(id);
		trains.remove(train);
		System.out.println("train " + id + " removed");
		notifyObservers();

	}

	public Train findTrain(String id) {
		for (Train t : trains) {
			if (t.getId().equals(id)) {
				return t;
			}
		}
		return null;
	}

	public boolean addWagonToTrain(String trainId, String wagonType) {
		Wagon wagon = findWagon(wagonType);
		Train train = findTrain(trainId);
		train.addRollingStock(wagon);
		tpdi.update(train);
		System.out.println("wagon " + wagonType + " added to train " + trainId);
		notifyObservers();
		return true;

	}

	public boolean newWagon(String type, int seats) {
		if (findWagon(type) == null) {
			System.out.println("wagon " + type + " already exists");
			return false;
		}
		wagonDirector = new WagonDirector(new CustomWagonBuilder(type, seats));
		wagonDirector.makeWagon();
		Wagon wagon = wagonDirector.getWagon();
		wagons.add(wagon);
		wpdi.save(wagon);
		System.out.println("wagon " + type + " created with " + seats + " seats");
		notifyObservers();
		return true;
	}

	public Wagon findWagon(String type) {
		for (Wagon w : wagons) {
			if (w.getType().equals(type)) {
				return w;
			}
		}
		return null;
	}

	public boolean addLocomotiveToTrain(String trainId, String locomotiveType) {
		Locomotive l = findLocomotive(locomotiveType);
		Train t = findTrain(trainId);
		t.addRollingStock(l);
		tpdi.update(t);
		System.out.println("locomotive " + locomotiveType + " added to train " + trainId);
		notifyObservers();
		return true;

	}

	public Locomotive findLocomotive(String type) {
		for (Locomotive l : locomotives) {
			if (l.getType().equals(type)) {
				return l;
			}
		}
		System.out.println("locomotive " + type + " does not exist");
		return null;
	}

	public void notifyObservers() {
		for (TrainObserver o : observers) {
			o.update(trains, wagons, locomotives);
		}
	}

}
