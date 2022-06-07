package org.eqasim.madrid.mode_choice.epsilon;

import org.eqasim.core.simulation.mode_choice.AbstractEqasimExtension;
import org.eqasim.core.simulation.mode_choice.epsilon.EpsilonAdapter;
import org.eqasim.core.simulation.mode_choice.epsilon.EpsilonProvider;
import org.eqasim.core.simulation.mode_choice.epsilon.GumbelEpsilonProvider;
import org.eqasim.core.simulation.mode_choice.utilities.estimators.PtUtilityEstimator;
import org.eqasim.core.simulation.mode_choice.utilities.estimators.WalkUtilityEstimator;
import org.eqasim.madrid.mode_choice.utilities.estimators.MADCarUtilityEstimator;
import org.eqasim.madrid.mode_choice.utilities.estimators.MADPtUtilityEstimator;
import org.eqasim.madrid.mode_choice.utilities.estimators.MADWalkUtilityEstimator;
import org.matsim.core.config.groups.GlobalConfigGroup;

import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class EpsilonModule extends AbstractEqasimExtension {
	@Provides
	public GumbelEpsilonProvider provideGumbelEpsilonProvider(GlobalConfigGroup config) {
		return new GumbelEpsilonProvider(config.getRandomSeed(), 1.0);
	}

	@Override
	protected void installEqasimExtension() {
		bind(EpsilonProvider.class).to(GumbelEpsilonProvider.class);

		bind(MADCarUtilityEstimator.class);
		bind(PtUtilityEstimator.class);
		bind(WalkUtilityEstimator.class);

		bindUtilityEstimator("epsilon_car").to(Key.get(EpsilonAdapter.class, Names.named("epsilon_car")));
		bindUtilityEstimator("epsilon_pt").to(Key.get(EpsilonAdapter.class, Names.named("epsilon_pt")));
		bindUtilityEstimator("epsilon_walk").to(Key.get(EpsilonAdapter.class, Names.named("epsilon_walk")));
	}

	@Provides
	@Named("epsilon_car")
	EpsilonAdapter provideEpsilonCarEstimator(MADCarUtilityEstimator delegate, EpsilonProvider epsilonProvider) {
		return new EpsilonAdapter("car", delegate, epsilonProvider);
	}

	@Provides
	@Named("epsilon_pt")
	EpsilonAdapter provideEpsilonPtEstimator(MADPtUtilityEstimator delegate, EpsilonProvider epsilonProvider) {
		return new EpsilonAdapter("pt", delegate, epsilonProvider);
	}

	@Provides
	@Named("epsilon_walk")
	EpsilonAdapter provideEpsilonWalkEstimator(MADWalkUtilityEstimator delegate, EpsilonProvider epsilonProvider) {
		return new EpsilonAdapter("walk", delegate, epsilonProvider);
	}
}
