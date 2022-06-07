package org.eqasim.madrid.mode_choice;

import java.io.File;
import java.io.IOException;

import org.eqasim.core.components.config.EqasimConfigGroup;
import org.eqasim.core.simulation.mode_choice.AbstractEqasimExtension;
import org.eqasim.core.simulation.mode_choice.ParameterDefinition;
import org.eqasim.core.simulation.mode_choice.parameters.ModeParameters;
import org.eqasim.madrid.mode_choice.costs.MADCarCostModel;
import org.eqasim.madrid.mode_choice.costs.MADPtCostModel;
import org.eqasim.madrid.mode_choice.parameters.MADCostParameters;
import org.eqasim.madrid.mode_choice.parameters.MADModeParameters;
import org.eqasim.madrid.mode_choice.utilities.estimators.MADCarUtilityEstimator;
import org.eqasim.madrid.mode_choice.utilities.estimators.MADPtUtilityEstimator;
import org.eqasim.madrid.mode_choice.utilities.estimators.MADWalkUtilityEstimator;
import org.eqasim.madrid.mode_choice.constraints.MADWalkDurationConstraint;

import org.eqasim.madrid.mode_choice.utilities.predictors.MADPersonPredictor;
import org.eqasim.madrid.mode_choice.utilities.predictors.MADSpatialPredictor;
import org.matsim.core.config.CommandLine;
import org.matsim.core.config.CommandLine.ConfigurationException;

import com.google.inject.Provides;
import com.google.inject.Singleton;

public class MADModeChoiceModule extends AbstractEqasimExtension {
	private final CommandLine commandLine;

	public static final String MODE_AVAILABILITY_NAME = "MADModeAvailability";

	public static final String CAR_COST_MODEL_NAME = "MADCarCostModel";
	public static final String PT_COST_MODEL_NAME = "MADPtCostModel";

	public static final String CAR_ESTIMATOR_NAME = "MADCarUtilityEstimator";
	public static final String PT_ESTIMATOR_NAME = "MADPtUtilityEstimator";
	public static final String WALK_ESTIMATOR_NAME = "MADWalkUtilityEstimator";
	
	public static final String WALK_CONSTRAINT_NAME = "MADWalkDurationConstraint";



	public MADModeChoiceModule(CommandLine commandLine) {
		this.commandLine = commandLine;
	}

	@Override
	protected void installEqasimExtension() {
		bindModeAvailability(MODE_AVAILABILITY_NAME).to(MADModeAvailability.class);
		bindTripConstraintFactory(WALK_CONSTRAINT_NAME).to(MADWalkDurationConstraint.Factory.class);

		bind(MADPersonPredictor.class);

		bindCostModel(CAR_COST_MODEL_NAME).to(MADCarCostModel.class);
		bindCostModel(PT_COST_MODEL_NAME).to(MADPtCostModel.class);

		bindUtilityEstimator(CAR_ESTIMATOR_NAME).to(MADCarUtilityEstimator.class);
		bindUtilityEstimator(PT_ESTIMATOR_NAME).to(MADPtUtilityEstimator.class);
		bindUtilityEstimator(WALK_ESTIMATOR_NAME).to(MADWalkUtilityEstimator.class);

		bind(MADSpatialPredictor.class);

		bind(ModeParameters.class).to(MADModeParameters.class);
	}

	@Provides
	@Singleton
	public MADModeParameters provideModeChoiceParameters(EqasimConfigGroup config)
			throws IOException, ConfigurationException {
		MADModeParameters parameters = MADModeParameters.buildDefault();

		if (config.getModeParametersPath() != null) {
			ParameterDefinition.applyFile(new File(config.getModeParametersPath()), parameters);
		}

		ParameterDefinition.applyCommandLine("mode-choice-parameter", commandLine, parameters);
		return parameters;
	}

	@Provides
	@Singleton
	public MADCostParameters provideCostParameters(EqasimConfigGroup config) {
		MADCostParameters parameters = MADCostParameters.buildDefault();

		if (config.getCostParametersPath() != null) {
			ParameterDefinition.applyFile(new File(config.getCostParametersPath()), parameters);
		}

		ParameterDefinition.applyCommandLine("cost-parameter", commandLine, parameters);
		return parameters;
	}
}
