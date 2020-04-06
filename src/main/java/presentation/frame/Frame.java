package presentation.frame;

import service.train.trainobserver.TrainObserver;

public interface Frame extends TrainObserver{
	
	public void showFrame();
	
	public void hideFrame();

}
