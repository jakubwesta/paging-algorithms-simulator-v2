package org.simulator;

import org.simulator.algorithm.EqualAllocation;
import org.simulator.algorithm.ProportionalAllocation;
import org.simulator.algorithm.core.FrameAllocationAlgorithm;
import org.simulator.algorithm.core.LruHandler;
import org.simulator.scenario.RunResult;
import org.simulator.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;

public class SimulationHandler {
    private final ArrayList<Scenario> scenarios = new ArrayList<>(List.of(
            new Scenario(10000, 100, 100000, 10, 3, 0.9d, 15, 100, false),
            new Scenario(10000, 1000, 100000, 10, 3, 0.9d, 15, 100, false),
            new Scenario(10000, 10000, 100000, 10, 3, 0.9d, 15, 100, false),
            new Scenario(10000, 10000, 100000, 10, 3, 0.9d, 15, 100, true),
            new Scenario(10000, 100000, 100000, 10, 3, 0.9d, 15, 100, false)
    ));

    private final ArrayList<FrameAllocationAlgorithm> algorithms = new ArrayList<>(List.of(
            new EqualAllocation(),
            new ProportionalAllocation()
    ));

    public SimulationHandler() {

    }

    public void runTests() {
        for (Scenario scenario : scenarios) {
            for (FrameAllocationAlgorithm algorithm : algorithms) {
                LruHandler handler = new LruHandler(algorithm);
                RunResult result = handler.simulate(
                        scenario.getReferences(),
                        scenario.getFrameAmount(),
                        scenario.getProcesses()
                );
                printLogs(scenario, algorithm, result);
                log("");
            }
        }
    }

    private void printLogs(Scenario scenario, FrameAllocationAlgorithm algorithm, RunResult result) {
        log("Algorithm:              %s".formatted(algorithm.getClass().getSimpleName()));
        log("Page amount:            %s".formatted(scenario.getPageAmount()));
        log("Frame amount:           %s".formatted(scenario.getFrameAmount()));
        log("Reference amount:       %s".formatted(scenario.getReferenceAmount()));
        log("Process amount:         %s".formatted(scenario.getProcessAmount()));
        log("Even processes:         %s".formatted(scenario.isEvenProcesses()));
        log("Total page faults:      %s".formatted(result.totalPageFaults()));
        log("Average page faults:    %s".formatted(result.avgPageFaults()));
    }

    private void log(String string) {
        System.out.println(string);
    }
}
