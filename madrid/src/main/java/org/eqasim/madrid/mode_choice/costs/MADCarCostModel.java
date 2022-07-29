package org.eqasim.madrid.mode_choice.costs;

import java.util.List;

//import org.eqasim.core.simulation.mode_choice.cost.AbstractCostModel;
import org.eqasim.madrid.mode_choice.parameters.MADCostParameters;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.contribs.discrete_mode_choice.model.DiscreteModeChoiceTrip;

import com.google.inject.Inject;

// new cost model
import org.eqasim.core.simulation.mode_choice.cost.CostModel;
import org.eqasim.madrid.mode_choice.utilities.predictors.MADPersonPredictor;
import org.eqasim.madrid.mode_choice.utilities.predictors.MADSpatialPredictor;
import org.eqasim.madrid.mode_choice.utilities.variables.MADPersonVariables;
import org.eqasim.madrid.mode_choice.utilities.variables.MADSpatialVariables;

//public class MADCarCostModel extends AbstractCostModel {
//	private final MADCostParameters costParameters;
//
//	@Inject
//	public MADCarCostModel(MADCostParameters costParameters) {
//		super("car");
//		this.costParameters = costParameters;
//	}
//
//	@Override
//	public double calculateCost_MU(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
//		return costParameters.carCost_EUR_km * getInVehicleDistance_km(elements)+ costParameters.carCost_taxi;
//	}
//}

public class MADCarCostModel implements CostModel {
	private final MADPersonPredictor personPredictor;
	private final MADSpatialPredictor spatialPredictor;
	private final MADCostParameters costParameters;
	

	// TODO: This should be hidden by some custom predictor
//	private final TransitSchedule transitSchedule;

	@Inject
	public MADCarCostModel(MADPersonPredictor personPredictor, MADSpatialPredictor spatialPredictor, 
			MADCostParameters costParameters) {
		this.personPredictor = personPredictor;
		this.spatialPredictor = spatialPredictor;
		this.costParameters = costParameters;
		
//		this.transitSchedule = transitSchedule;
	}
	
	private double getInVehicleDistance_km(List<? extends PlanElement> elements) {
		double distance_km = 0.0;

		for (PlanElement element : elements) {
			if (element instanceof Leg) {
				Leg leg = (Leg) element;

				if (leg.getMode().contentEquals("car")) {
					distance_km += leg.getRoute().getDistance() * 1e-3;
				}
			}
		}
		return distance_km;
	}

	@Override
	public double calculateCost_MU(Person person, DiscreteModeChoiceTrip trip, List<? extends PlanElement> elements) {
		// I) If the person is an airport passenger
		MADPersonVariables personVariables = personPredictor.predictVariables(person, trip, elements);

		if (personVariables.airportPassenger) {
			return costParameters.carCost_taxi;
		}

		// II) If the person is not an airport passenger
		return costParameters.carCost_EUR_km * getInVehicleDistance_km(elements);
	}
}
