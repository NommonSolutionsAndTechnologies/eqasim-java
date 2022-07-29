package org.eqasim.madrid.mode_choice;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Person;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import org.matsim.contribs.discrete_mode_choice.model.mode_availability.ModeAvailability;
import org.matsim.core.population.PersonUtils;
import org.eqasim.madrid.mode_choice.utilities.variables.MADPersonVariables;
import org.eqasim.madrid.mode_choice.utilities.predictors.MADPersonPredictor;


public class MADModeAvailability implements ModeAvailability {
	@Override
	public Collection<String> getAvailableModes(Person person, List<DiscreteModeChoiceTrip> trips) {
		Collection<String> modes = new HashSet<>();

		// Modes that are always available
		// modes.add(TransportMode.walk);
		modes.add(TransportMode.pt);
		
		// Check car availability
		boolean carAvailability = true;

		if ("none".equals((String) person.getAttributes().getAttribute("carAvailability"))) {
			carAvailability = false;
		}
		if (carAvailability) {
			modes.add(TransportMode.car);
		}

		// Check bike availability
		boolean bikeAvailability = false;

		if (bikeAvailability) {
			modes.add(TransportMode.bike);
		}
	

		// Check airport passangers
		boolean notairportPassenger = true;
		Boolean isairportPassenger = (Boolean) person.getAttributes().getAttribute("airportPassenger");
		if (isairportPassenger) {
			notairportPassenger = false;
		}
		if (notairportPassenger) {
			modes.add(TransportMode.walk);
		}

		// Add special mode "outside" if applicable
		Boolean isOutside = (Boolean) person.getAttributes().getAttribute("outside");

		if (isOutside != null && isOutside) {
			modes.add("outside");
		}
		if (isOutside != null && isOutside) {
			modes.add("outside_train_17000");
		}
		if (isOutside != null && isOutside) {
			modes.add("outside_train_60000");	
		}
		if (isOutside != null && isOutside) {
			modes.add("outside_airport_MAD");
		}
		return modes;
	}
}
