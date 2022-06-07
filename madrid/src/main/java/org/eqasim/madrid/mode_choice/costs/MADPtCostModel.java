package org.eqasim.madrid.mode_choice.costs;

import java.util.List;

import org.eqasim.core.simulation.mode_choice.cost.CostModel;
import org.eqasim.madrid.mode_choice.utilities.predictors.MADPersonPredictor;
import org.eqasim.madrid.mode_choice.utilities.predictors.MADSpatialPredictor;
import org.eqasim.madrid.mode_choice.utilities.variables.MADPersonVariables;
import org.eqasim.madrid.mode_choice.utilities.variables.MADSpatialVariables;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;
import org.matsim.core.utils.geometry.CoordUtils;
import org.matsim.pt.routes.TransitPassengerRoute;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

import com.google.inject.Inject;

public class MADPtCostModel implements CostModel {
	private final MADPersonPredictor personPredictor;
	private final MADSpatialPredictor spatialPredictor;

	// TODO: This should be hidden by some custom predictor
	private final TransitSchedule transitSchedule;

	@Inject
	public MADPtCostModel(MADPersonPredictor personPredictor, MADSpatialPredictor spatialPredictor,
			TransitSchedule transitSchedule) {
		this.personPredictor = personPredictor;
		this.spatialPredictor = spatialPredictor;
		this.transitSchedule = transitSchedule;
	}


	@Override
	public double calculateCost_MU(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {

		MADPersonVariables personVariables = personPredictor.predictVariables(person, trip, elements);

		return 2.0;
	}
}
