package org.eqasim.madrid.mode_choice.parameters;

import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;

public class MADModeParameters extends ModeParameters {
	public class MADParameters {
		public double betaInsideUrbanArea;
		public double betaCrossingUrbanArea;
	}


//	public final MADCarParameters madCar = new MADCarParameters();

	public static MADModeParameters buildDefault() {
		MADModeParameters parameters = new MADModeParameters();

		// Cost
		parameters.betaCost_u_MU = -0.206;
		parameters.lambdaCostEuclideanDistance = -0.4;
		parameters.referenceEuclideanDistance_km = 5.0;

		// Car
		parameters.car.alpha_u = 0.00;
		parameters.car.betaTravelTime_u_min = -0.0221;

		parameters.car.constantAccessEgressWalkTime_min = 4.0;
		parameters.car.constantParkingSearchPenalty_min = 4.0;

//		parameters.madCar.betaInsideUrbanArea = -0.5;
//		parameters.madCar.betaCrossingUrbanArea = -1.0;

		// PT
		parameters.pt.alpha_u = 1.47;
		parameters.pt.betaLineSwitch_u = -0.00;
		parameters.pt.betaInVehicleTime_u_min = -0.00137;
		parameters.pt.betaWaitingTime_u_min = -0.0484;
		parameters.pt.betaAccessEgressTime_u_min = -0.03174;
		//parameters.pt.betaMonetaryCost = -0.8097;


		// Walk
		parameters.walk.alpha_u = 1.11;
		parameters.walk.betaTravelTime_u_min = -0.03174;
		parameters.walk.betaExpTime_u_min = -0.44984;
		//parameters.walk.betaTravelTime2_u_min = -0.00;

		return parameters;

	}
}


//public class MADModeParameters extends ModeParameters {
//	public class CarParameters {
//		public double alpha_u = 0.0;
//		public double betaTravelTime_u_min = 0.0;
//
//		public double constantAccessEgressWalkTime_min = 0.0;
//		public double constantParkingSearchPenalty_min = 0.0;
//	}
//
//	public class PtParameters {
//		public double alpha_u = 0.0;
//		public double betaLineSwitch_u = 0.0;
//		public double betaInVehicleTime_u_min = 0.0;
//		public double betaWaitingTime_u_min = 0.0;
//		public double betaAccessEgressTime_u_min = 0.0;
//	}
//
//	public class WalkParameters {
//		public double alpha_u = 0.0;
//		public double betaTravelTime_u_min = 0.0;
//	}
//
//	public double lambdaCostEuclideanDistance = 0.0;
//	public double referenceEuclideanDistance_km = 0.0;
//
//	public double betaCost_u_MU = 0.0;
//
//	public final CarParameters car = new CarParameters();
//	public final PtParameters pt = new PtParameters();
//	public final WalkParameters walk = new WalkParameters();
//}
