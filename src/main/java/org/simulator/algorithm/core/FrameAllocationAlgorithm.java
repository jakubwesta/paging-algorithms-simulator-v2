package org.simulator.algorithm.core;

import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public interface FrameAllocationAlgorithm {
    HashMap<Process, ProcessData> initializeDataMap(ArrayList<Reference> references, int frameCount, ArrayList<Process> processes);

    Optional<HashMap<Process, ProcessData>> handlePossibleFault(HashMap<Process, ProcessData> dataMap, Process process, boolean fault);
}
