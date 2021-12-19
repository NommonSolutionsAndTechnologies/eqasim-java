package org.eqasim.examples.corsica_convergence;

import java.net.URL;

import org.eqasim.core.simulation.EqasimConfigurator;
import org.eqasim.core.simulation.analysis.EqasimAnalysisModule;
import org.eqasim.core.simulation.convergence.ConvergenceTerminationCriterion;
import org.eqasim.core.simulation.mode_choice.EqasimModeChoiceModule;
import org.eqasim.ile_de_france.IDFConfigurator;
import org.eqasim.ile_de_france.mode_choice.IDFModeChoiceModule;
import org.eqasim.vdf.VDFConfigGroup;
import org.eqasim.vdf.VDFModule;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.CommandLine;
import org.matsim.core.config.CommandLine.ConfigurationException;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.TerminationCriterion;
import org.matsim.core.scenario.ScenarioUtils;

import com.google.common.io.Resources;

/**
 * This is an example run script that runs the Corsica test scenario with
 * convergence information.
 */
public class RunCorsicaConvergenceSimulation {
	static public void main(String[] args) throws ConfigurationException {
		CommandLine cmd = new CommandLine.Builder(args) //
				.allowPrefixes("mode-parameter", "cost-parameter") //
				.build();

		URL configUrl = Resources.getResource("corsica/corsica_config.xml");
		IDFConfigurator configurator = new IDFConfigurator();
		Config config = ConfigUtils.loadConfig(configUrl, configurator.getConfigGroups());

		config.controler().setLastIteration(1000);
		config.qsim().setFlowCapFactor(1e6);
		config.addModule(new VDFConfigGroup());

		Scenario scenario = ScenarioUtils.createScenario(config);
		configurator.configureScenario(scenario);
		ScenarioUtils.loadScenario(scenario);

		Controler controller = new Controler(scenario);
		configurator.configureController(controller);
		controller.addOverridingModule(new EqasimAnalysisModule());
		controller.addOverridingModule(new EqasimModeChoiceModule());
		controller.addOverridingModule(new IDFModeChoiceModule(cmd));

		controller.addOverridingModule(new VDFModule());

		controller.addOverridingModule(new AbstractModule() {
			@Override
			public void install() {
				addEventHandlerBinding().to(ModeShareListener.class);
				addControlerListenerBinding().to(ModeShareListener.class);

				bind(ConvergenceTerminationCriterion.class).asEagerSingleton();
				bind(TerminationCriterion.class).to(ConvergenceTerminationCriterion.class);
			}
		});

		controller.run();
	}
}
