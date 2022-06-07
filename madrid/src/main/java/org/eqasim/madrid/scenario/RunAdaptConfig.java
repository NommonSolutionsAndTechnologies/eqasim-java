package org.eqasim.madrid.scenario;

import org.eqasim.core.components.config.ConfigAdapter;
import org.eqasim.core.components.config.EqasimConfigGroup;
import org.eqasim.madrid.MADConfigurator;
import org.eqasim.madrid.mode_choice.MADModeChoiceModule;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.contribs.discrete_mode_choice.modules.config.DiscreteModeChoiceConfigGroup;
import org.matsim.core.config.CommandLine.ConfigurationException;
import org.matsim.core.config.Config;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;

public class RunAdaptConfig {
	static public void main(String[] args) throws ConfigurationException {
		MADConfigurator configurator = new MADConfigurator();
		ConfigAdapter.run(args, configurator.getConfigGroups(), RunAdaptConfig::adaptConfiguration);
	}

	static public void adaptConfiguration(Config config) {
		// Adjust eqasim config
		EqasimConfigGroup eqasimConfig = EqasimConfigGroup.get(config);

		eqasimConfig.setCostModel(TransportMode.car, MADModeChoiceModule.CAR_COST_MODEL_NAME);
		eqasimConfig.setCostModel(TransportMode.pt, MADModeChoiceModule.PT_COST_MODEL_NAME);

		eqasimConfig.setEstimator(TransportMode.car, MADModeChoiceModule.CAR_ESTIMATOR_NAME);

		DiscreteModeChoiceConfigGroup dmcConfig = (DiscreteModeChoiceConfigGroup) config.getModules()
				.get(DiscreteModeChoiceConfigGroup.GROUP_NAME);

		dmcConfig.setModeAvailability(MADModeChoiceModule.MODE_AVAILABILITY_NAME);

		// Potentially should be moved to the general GenerateConfig class. Wait time
		// should matter for routing!
		PlanCalcScoreConfigGroup scoringConfig = config.planCalcScore();
		scoringConfig.setMarginalUtlOfWaitingPt_utils_hr(-1.0);

	}
}
