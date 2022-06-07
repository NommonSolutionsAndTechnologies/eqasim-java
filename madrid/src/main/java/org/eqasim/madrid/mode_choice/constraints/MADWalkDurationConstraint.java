package org.eqasim.madrid.mode_choice.constraints;

import java.util.Collection;
import java.util.List;

import org.matsim.api.core.v01.population.Person;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import org.matsim.contribs.discrete_mode_choice.model.constraints.AbstractTripConstraint;
import org.matsim.contribs.discrete_mode_choice.model.trip_based.TripConstraint;
import org.matsim.contribs.discrete_mode_choice.model.trip_based.TripConstraintFactory;
import org.matsim.core.utils.geometry.CoordUtils;
import org.matsim.contribs.discrete_mode_choice.model.mode_availability.ModeAvailability;



public class MADWalkDurationConstraint extends AbstractTripConstraint {
//public class MADWalkDurationConstraint {

	public static final String WALK_MODE = "walk";

	@Override
	public boolean validateBeforeEstimation(DiscreteModeChoiceTrip trip, String mode, List<String> previousModes) {
		if (mode.equals(WALK_MODE)) {

			double distance = CoordUtils.calcEuclideanDistance(trip.getOriginActivity().getCoord(),
					trip.getDestinationActivity().getCoord());

			if (distance > 4500)
				return false;
		}

		return true;
	}

	static public class Factory implements TripConstraintFactory {

		@Override
		public TripConstraint createConstraint(Person person, List<DiscreteModeChoiceTrip> planTrips,
				Collection<String> availableModes) {
			return new MADWalkDurationConstraint();
		}
	}
}