package org.simulator.algorithm;

import org.simulator.algorithm.core.FrameAllocationAlgorithm;
import org.simulator.algorithm.core.ProcessData;
import org.simulator.scenario.RunResult;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EqualAllocation implements FrameAllocationAlgorithm {
    public EqualAllocation() {

    }

    @Override
    public HashMap<Process, ProcessData> initializeDataMap(ArrayList<Reference> references, int frameCount, ArrayList<Process> processes) {
        HashMap<Process, ProcessData> dataMap = new HashMap<>();
        int processAmount = processes.size();
        int framesPerProcess = frameCount / processAmount;

        for (Process process : processes) {
            dataMap.put(process, new ProcessData(framesPerProcess));
        }

        return dataMap;
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
