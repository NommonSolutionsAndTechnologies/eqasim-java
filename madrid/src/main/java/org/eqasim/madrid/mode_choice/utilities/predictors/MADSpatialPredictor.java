package org.eqasim.madrid.mode_choice.utilities.predictors;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.utilities.predictors.CachedVariablePredictor;
import org.eqasim.madrid.mode_choice.utilities.variables.MADSpatialVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

import com.google.inject.Singleton;

@Singleton
public class MADSpatialPredictor extends CachedVariablePredictor<MADSpatialVariables> {
	@Override
	protected MADSpatialVariables predict(Person person, DiscreteModeChoiceTrip trip,
			List<? extends PlanElement> elements) {
		boolean hasUrbanOrigin = MADPredictorUtils.isUrbanArea(trip.getOriginActivity());
		boolean hasUrbanDestination = MADPredictorUtils.isUrbanArea(trip.getDestinationActivity());

		return new MADSpatialVariables(hasUrbanOrigin, hasUrbanDestination);
	}
}
