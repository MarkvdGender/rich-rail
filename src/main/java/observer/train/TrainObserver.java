package observer.train;

import java.util.List;

import domain.Train;
import domain.Wagon;
import domain.locomotive.Locomotive;

public interface TrainObserver {
	
	public void update(List<Train> trains, List<Wagon> wagons, List<Locomotive> locomotives);

}
