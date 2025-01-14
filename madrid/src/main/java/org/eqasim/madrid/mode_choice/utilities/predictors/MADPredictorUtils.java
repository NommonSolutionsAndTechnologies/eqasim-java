package org.eqasim.madrid.mode_choice.utilities.predictors;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;

public class MADPredictorUtils {
	static public boolean airportPassenger(Person person) {
		Boolean isAirportPassenger = (Boolean) person.getAttributes().getAttribute("airportPassenger");
		return isAirportPassenger != null && isAirportPassenger;
	}

	static public boolean isUrbanArea(Activity activity) {
		Boolean isUrban = (Boolean) activity.getAttributes().getAttribute("isUrban");
		return isUrban != null && isUrban;
	}
}
