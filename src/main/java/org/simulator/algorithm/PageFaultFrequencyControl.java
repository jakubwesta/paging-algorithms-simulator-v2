package org.simulator.algorithm;

import org.simulator.algorithm.core.FrameAllocationAlgorithm;
import org.simulator.algorithm.core.ProcessData;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class PageFaultFrequencyControl implements FrameAllocationAlgorithm {
    private final double maxThreshold;
    private final double minThreshold;
    private int freeFrames;
    private final HashMap<Process, Integer> referencesMap;

    public PageFaultFrequencyControl(double max, double min) {
        this.maxThreshold = max;
        this.minThreshold = min;
        this.freeFrames = 0;
        this.referencesMap = new HashMap<>();
    }

    @Override
    public HashMap<Process, ProcessData> initializeDataMap(ArrayList<Reference> references, int frameCount, ArrayList<Process> processes) {
        HashMap<Process, ProcessData> dataMap = new HashMap<>();
        int processAmount = processes.size();
        int framesPerProcess = frameCount / processAmount;

        for (Process process : processes) {
            dataMap.put(process, new ProcessData(framesPerProcess));
            referencesMap.put(process, 0);
        }

        return dataMap;
    }

    @Override
    public Optional<HashMap<Process, ProcessData>> handlePossibleFault(HashMap<Process, ProcessData> dataMap, Process process, boolean fault) {
        return null;
    }
}
