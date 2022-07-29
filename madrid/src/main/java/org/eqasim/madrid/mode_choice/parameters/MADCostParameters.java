package org.eqasim.madrid.mode_choice.parameters;

import org.eqasim.core.simulation.mode_choice.ParameterDefinition;

public class MADCostParameters implements ParameterDefinition {
	public double carCost_EUR_km = 0.0;
	public double carCost_beta_time = 0.0;
	public double carCost_taxi = 0.0;


	public static MADCostParameters buildDefault() {
		MADCostParameters parameters = new MADCostParameters();

		parameters.carCost_EUR_km = 0.21;
		parameters.carCost_beta_time = 0.0221;
		parameters.carCost_taxi = 30.0;
		
		return parameters;
	}
}
