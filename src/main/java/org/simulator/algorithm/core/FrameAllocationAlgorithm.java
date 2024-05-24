package org.simulator.algorithm.core;

import org.simulator.scenario.RunResult;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;

public interface FrameAllocationAlgorithm {
    HashMap<Process, ProcessData> initializeDataMap(ArrayList<Reference> references, int frameCount, ArrayList<Process> processes);

    RunResult createRunResults(HashMap<Process, ProcessData> dataMap);
}
