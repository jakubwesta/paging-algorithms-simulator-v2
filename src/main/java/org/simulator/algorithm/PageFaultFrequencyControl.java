package org.simulator.algorithm;

import org.simulator.algorithm.core.FrameAllocationAlgorithm;
import org.simulator.algorithm.core.ProcessData;
import org.simulator.scenario.RunResult;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PageFaultFrequencyControl implements FrameAllocationAlgorithm {
    public PageFaultFrequencyControl() {

    }

    @Override
    public HashMap<Process, ProcessData> initializeDataMap(ArrayList<Reference> references, int frameCount, ArrayList<Process> processes) {
        return null;
    }

    @Override
    public RunResult createRunResults(HashMap<Process, ProcessData> dataMap) {
        int total = 0;
        for (Map.Entry<Process, ProcessData> entry : dataMap.entrySet()) {
            total += entry.getValue().addPageFault();
        }
        int average = total / dataMap.size();
        return new RunResult(total, average);
    }
}
