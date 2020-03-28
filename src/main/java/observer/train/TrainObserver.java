package observer.train;

import java.util.List;

import domain.Train;
import domain.Wagon;

public interface TrainObserver {
	
	public void update(List<Train> trains, List<Wagon> wagons);

}
