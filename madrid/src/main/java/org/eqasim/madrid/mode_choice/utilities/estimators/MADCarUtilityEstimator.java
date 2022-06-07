package org.eqasim.madrid.mode_choice.utilities.estimators;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.utilities.estimators.CarUtilityEstimator;
import org.eqasim.core.simulation.mode_choice.utilities.predictors.CarPredictor;
import org.eqasim.madrid.mode_choice.parameters.MADModeParameters;
import org.eqasim.madrid.mode_choice.utilities.predictors.MADSpatialPredictor;
import org.eqasim.madrid.mode_choice.utilities.variables.MADSpatialVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

import com.google.inject.Inject;

public class MADCarUtilityEstimator extends CarUtilityEstimator {
	private final MADModeParameters parameters;
	private final MADSpatialPredictor spatialPredictor;

	@Inject
	public MADCarUtilityEstimator(MADModeParameters parameters, MADSpatialPredictor spatialPredictor,
			CarPredictor carPredictor) {
		super(parameters, carPredictor);

		this.parameters = parameters;
		this.spatialPredictor = spatialPredictor;
	}


	@Override
	public double estimateUtility(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		MADSpatialVariables variables = spatialPredictor.predictVariables(person, trip, elements);

		double utility = 0.0;

		utility += super.estimateUtility(person, trip, elements);

		return utility;
	}
}
