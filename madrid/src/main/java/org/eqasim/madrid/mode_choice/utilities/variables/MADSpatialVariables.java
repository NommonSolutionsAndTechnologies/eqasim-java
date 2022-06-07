package org.eqasim.madrid.mode_choice.utilities.variables;

import org.eqasim.core.simulation.mode_choice.utilities.variables.BaseVariables;

public class MADSpatialVariables implements BaseVariables {
	public final boolean hasUrbanOrigin;
	public final boolean hasUrbanDestination;

	public MADSpatialVariables(boolean hasUrbanOrigin, boolean hasUrbanDestination) {
		this.hasUrbanOrigin = hasUrbanOrigin;
		this.hasUrbanDestination = hasUrbanDestination;
	}
}
