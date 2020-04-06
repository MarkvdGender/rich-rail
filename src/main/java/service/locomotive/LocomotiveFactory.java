package service.locomotive;

import domain.locomotive.ElectricLocomotive;
import domain.locomotive.Locomotive;
import domain.locomotive.SteamLocomotive;

public class LocomotiveFactory {

	public Locomotive createLocomotive(String type) {

		if (type.equals("steam")) {
			return new SteamLocomotive();
		} else if (type.equals("electric")) {
			return new ElectricLocomotive();
		} else {
			return null;
		}

	}

}
