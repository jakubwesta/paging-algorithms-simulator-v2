package org.simulator.algorithm;

import org.simulator.algorithm.core.FrameAllocationAlgorithm;
import org.simulator.algorithm.core.ProcessData;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ZoneModel implements FrameAllocationAlgorithm {
    public ZoneModel() {

    }

    @Override
    public HashMap<Process, ProcessData> initializeDataMap(ArrayList<Reference> references, int frameCount, ArrayList<Process> processes) {
        return null;
    }

    @Override
    public Optional<HashMap<Process, ProcessData>> handlePossibleFault(HashMap<Process, ProcessData> dataMap, Process process, boolean fault) {
        return null;
    }
}
