package org.simulator.algorithm;

import org.simulator.algorithm.core.FrameAllocationAlgorithm;
import org.simulator.algorithm.core.ProcessData;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ProportionalAllocation implements FrameAllocationAlgorithm {
    public ProportionalAllocation() {

    }

    @Override
    public HashMap<Process, ProcessData> initializeDataMap(ArrayList<Reference> references, int frameCount, ArrayList<Process> processes) {
        HashMap<Process, ProcessData> dataMap = new HashMap<>();

        ArrayList<Integer> uniquePagesList = new ArrayList<>();
        for (Reference reference : references) {
            if (!uniquePagesList.contains(reference.page())) {
                uniquePagesList.add(reference.page());
            }
        }
        int pageAmount = uniquePagesList.size();

        for (Process process : processes) {
            dataMap.put(process, new ProcessData((int) Math.round(((double) process.getPageRange() / pageAmount * frameCount))));
        }

        return dataMap;
    }

    @Override
    public Optional<HashMap<Process, ProcessData>> handlePossibleFault(HashMap<Process, ProcessData> dataMap, Process process, boolean fault) {
        return Optional.empty();
    }
}
