package org.eqasim.core.simulation.mode_choice.constraints;

import java.util.Collection;
import java.util.List;

import org.matsim.api.core.v01.BasicLocation;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Person;
import org.matsim.contribs.discrete_mode_choice.components.utils.LocationUtils;
import org.matsim.contribs.discrete_mode_choice.components.utils.home_finder.HomeFinder;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import org.matsim.contribs.discrete_mode_choice.model.tour_based.TourCandidate;
import org.matsim.contribs.discrete_mode_choice.model.tour_based.TourConstraint;
import org.matsim.contribs.discrete_mode_choice.model.tour_based.TourConstraintFactory;

/**
 * Attention. This constraint generalizes the existing vehicle tour constraint
 * from the discrete_mode_choice contrib of MATSim. See below for the
 * documentation. Eventually, this extended version should be moved to the
 * MATSim contrib.
 * 
 * This constraint makes sure that trips are continuous in the sense that
 * vehicles get not dumped somewhere in the network:
 * 
 * <ul>
 * <li>Vehicles can only be used where they have been moved to before.</li>
 * <li>Within one tour, vehicles must depart first from the home location.</li>
 * <li>Within one tour, vehicles must be brought back to the home location.</li>
 * </ul>
 *
 * For that, it needs to be decided where "home" is. Currently, there are two
 * options: Either the location of the first activity is used (as it is for
 * SubtourModeChoice), or the location of first activity with a certain type
 * (default is "home") is used.
 * 
 * If a home location cannot be found in the tour, a mode must start and end at
 * the first and last location in the tour.
 * 
 * @author sebhoerl
 */
public class EqasimVehicleTourConstraint implements TourConstraint {
	private final Collection<String> restrictedModes;
	private final Id<? extends BasicLocation> vehicleLocationId;

	public EqasimVehicleTourConstraint(Collection<String> restrictedModes,
			Id<? extends BasicLocation> vehicleLocationId) {
		this.restrictedModes = restrictedModes;
		this.vehicleLocationId = vehicleLocationId;
	}

	private int getFirstIndex(String mode, List<String> modes) {
		for (int i = 0; i < modes.size(); i++) {
			if (modes.get(i).equals(mode)) {
				return i;
			}
		}

		return -1;
	}

	private int getLastIndex(String mode, List<String> modes) {
		for (int i = modes.size() - 1; i >= 0; i--) {
			if (modes.get(i).equals(mode)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean validateBeforeEstimation(List<DiscreteModeChoiceTrip> tour, List<String> modes,
			List<List<String>> previousModes) {
		for (String restrictedMode : restrictedModes) {
			if (modes.contains(restrictedMode)) {
				// I) Make sure vehicle is picked up and dropped off at its predetermined home
				// base. If the chain does not start at the vehicle base, the vehicle may also
				// be picked up at the first activity. If the chain does not end at the vehicle
				// base, the vehicle may still be dropped off at the last activity.

				int firstIndex = getFirstIndex(restrictedMode, modes);
				int lastIndex = getLastIndex(restrictedMode, modes);

				Id<? extends BasicLocation> startLocationId = LocationUtils
						.getLocationId(tour.get(firstIndex).getOriginActivity());
				Id<? extends BasicLocation> endLocationId = LocationUtils
						.getLocationId(tour.get(lastIndex).getDestinationActivity());

				if (!startLocationId.equals(vehicleLocationId)) {
					// Vehicle does not depart at the depot

					if (firstIndex > 0) {
						// If vehicle starts at very first activity, we still allow this tour!
						return false;
					}
				}

				if (!endLocationId.equals(vehicleLocationId)) {
					// Vehicle does not end at the depot

					if (lastIndex < modes.size() - 1) {
						// If vehicle ends at the very last activity, we still allow this tour!
						return false;
					}
				}

				// II) Make sure that in between the vehicle is only picked up at the location
				// where it has been moved previously

				Id<? extends BasicLocation> currentLocationId = LocationUtils
						.getLocationId(tour.get(firstIndex).getDestinationActivity());

				for (int index = firstIndex + 1; index <= lastIndex; index++) {
					if (modes.get(index).equals(restrictedMode)) {
						DiscreteModeChoiceTrip trip = tour.get(index);

						if (!currentLocationId.equals(LocationUtils.getLocationId(trip.getOriginActivity()))) {
							return false;
						}

						currentLocationId = LocationUtils.getLocationId(trip.getDestinationActivity());
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean validateAfterEstimation(List<DiscreteModeChoiceTrip> tour, TourCandidate candidate,
			List<TourCandidate> previousCandidates) {
		return true;
	}

	public static class Factory implements TourConstraintFactory {
		private final Collection<String> restrictedModes;
		private final HomeFinder homeFinder;

		public Factory(Collection<String> restrictedModes, HomeFinder homeFinder) {
			this.restrictedModes = restrictedModes;
			this.homeFinder = homeFinder;
		}

		@Override
		public TourConstraint createConstraint(Person person, List<DiscreteModeChoiceTrip> planTrips,
				Collection<String> availableModes) {
			return new EqasimVehicleTourConstraint(restrictedModes, homeFinder.getHomeLocationId(planTrips));
		}
	}
}
