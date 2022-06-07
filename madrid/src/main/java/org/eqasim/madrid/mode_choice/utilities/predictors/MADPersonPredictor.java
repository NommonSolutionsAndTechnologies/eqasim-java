package org.eqasim.madrid.mode_choice.utilities.predictors;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.utilities.predictors.CachedVariablePredictor;
import org.eqasim.madrid.mode_choice.utilities.variables.MADPersonVariables;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

public class MADPersonPredictor extends CachedVariablePredictor<MADPersonVariables> {
	@Override
	protected MADPersonVariables predict(Person person, DiscreteModeChoiceTrip trip,
			List<? extends PlanElement> elements) {
		boolean hasSubscription = MADPredictorUtils.hasSubscription(person);
		return new MADPersonVariables(hasSubscription);
	}
}
