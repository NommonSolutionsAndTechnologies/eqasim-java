package org.eqasim.madrid.mode_choice.utilities.variables;

import org.eqasim.core.simulation.mode_choice.utilities.variables.BaseVariables;

public class MADPersonVariables implements BaseVariables {
	public final boolean airportPassenger;

	public MADPersonVariables(boolean airportPassenger) {
		this.airportPassenger = airportPassenger;
	}
	
}