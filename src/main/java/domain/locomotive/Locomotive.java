package domain.locomotive;

import domain.RollingStock;

public interface Locomotive extends RollingStock{
	
	public void setIndex(int index);
	
	public int getIndex();

}
