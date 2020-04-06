package service.train.trainobserver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;
import service.locomotive.LocomotiveService;
import service.train.TrainService;
import service.wagon.WagonService;
import service.Log;

public class TrainSubject {

	private List<TrainObserver> observers = new ArrayList<TrainObserver>();
	private List<Train> trains = new ArrayList<Train>();
	private List<Wagon> wagons = new ArrayList<Wagon>();
	private List<Locomotive> locomotives = new ArrayList<Locomotive>();
	private static TrainSubject instance = null;
	private WagonService wagonService;
	private LocomotiveService locomotiveService;
	private TrainService trainService;
	private Log log = new Log();

	private TrainSubject() {
		trainService = new TrainService();
		wagonService = new WagonService();
		locomotiveService = new LocomotiveService();
		trains = trainService.findAll();
		wagons = wagonService.findAll();
		locomotives = locomotiveService.findAll();
	}

	public static TrainSubject getInstance() {
		if (instance == null) {
			instance = new TrainSubject();
		}
		return instance;
	}

	public void addObserver(TrainObserver o) {
		observers.add(o);
		o.update(trains, wagons, locomotives);

	}

	public void notifyObservers() {
		for (TrainObserver o : observers) {
			o.update(trains, wagons, locomotives);
		}
	}

//	TRAINS

	public boolean trainExists(String id) {
		for (Train t : trains) {
			if (t.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public boolean wagonExists(String type) {
		for (Wagon w : wagons) {
			if (w.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	public boolean locomotiveExists(String type) {
		for (Locomotive l : locomotives) {
			if (l.getType().equals(type)) {
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
		log.warning("train " + id + " does not exist");
		throw new IndexOutOfBoundsException("train " + id + " does not exist");
	}

	public void newTrain(String id, String engine) {
		if (trainExists(id)) {
			log.warning("train " + id + " already exists");
		} else {
			Train train = trainService.newTrain(id, engine);
			trains.add(train);
			log.info("train " + train.getId() + " created");
			notifyObservers();
		}

	}

	public void cloneTrain(String oldId, String newId) {
		if (this.trainExists(oldId)) {
			trains.add(trainService.cloneTrain(findTrain(oldId), newId));
			notifyObservers();
		} else {
			log.warning("train " + oldId + " does not exist");
		}
	}

	public void deleteTrain(String id) {
		if (!trainExists(id)) {
			log.warning("train " + id + " does not exist");
		} else {
			trains.remove(findTrain(id));
			trainService.delete(id);
			log.info("train " + id + " removed");
			notifyObservers();
		}
	}

	public void addWagonToTrain(String trainId, String wagonType) {
		if (trainExists(trainId)) {
			Train train = findTrain(trainId);
			if (wagonExists(wagonType)) {
				Wagon wagon = findWagon(wagonType);
				train.addRollingStock(wagon);
				trainService.update(train);
				log.info("wagon " + wagonType + " added to train " + trainId);
				notifyObservers();
			} else {
				log.warning("wagon " + wagonType + " does not exist");
			}
		} else {
			log.warning("train " + trainId + " does not exist");
		}

	}

	public void addLocomotiveToTrain(String trainId, String locomotiveType) {
		if (trainExists(trainId)) {
			if (locomotiveExists(locomotiveType)) {
				Locomotive l = findLocomotive(locomotiveType);
				Train t = findTrain(trainId);
				t.addRollingStock(l);
				trainService.update(t);
				log.info("locomotive " + locomotiveType + " added to train " + trainId);
				notifyObservers();
			} else {
				log.warning("locomotive " + locomotiveType + " does not exist");
			}
		} else {
			log.warning("train " + trainId + " does not exist");
		}

	}

	public void removeRollingStock(int index, String trainId) {
		if (trainExists(trainId)) {
			Train t = findTrain(trainId);
			if(index>t.getAllRollingStock().size()-1) {
				log.warning("no rollingstock on index "+index);
			}else {
				t.removeRollingStock(index);
				trainService.update(t);
				log.info("rollingstock on index " + index + " removed from train " + trainId);
				notifyObservers();
			}
		} else {
			log.warning("train " + trainId + " does not exist");
		}
	}

	public void updateList() {
		trains = trainService.findAll();
	}

////	WAGONS

	public void deleteWagon(String id) {
		if (wagonExists(id)) {
			Wagon w = findWagon(id);
			wagons.remove(w);
			wagonService.delete(w);
			trains = trainService.findAll();
			log.info("wagon " + id + " removed");
			notifyObservers();
		} else {
			log.warning("wagon " + id + " does not exist");
		}

	}

	public void newWagon(String type, int seats) {
		if (wagonExists(type)) {
			log.info("wagon " + type + " already exists");
		} else {
			wagons.add(wagonService.newWagon(type, seats));
			log.info("wagon " + type + " created with " + seats + " seats");
			notifyObservers();
		}

	}

	public Wagon findWagon(String type) {
		for (Wagon w : wagons) {
			if (w.getType().equals(type)) {
				return w;
			}
		}
		log.warning("wagon " + type + " does not exist");
		throw new IndexOutOfBoundsException("wagon " + type + " does not exist");
	}

//  LOCOMOTIVES

	public Locomotive findLocomotive(String type) {
		for (Locomotive l : locomotives) {
			if (l.getType().equals(type)) {
				return l;
			}
		}
		log.warning("locomotive " + type + " does not exist");
		throw new IndexOutOfBoundsException("locomotive " + type + " does not exist");
	}

}
