package org.simulator.algorithm.core;

import org.simulator.scenario.RunResult;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.*;

public class LruHandler {
    private final FrameAllocationAlgorithm algorithm;

    public LruHandler(FrameAllocationAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public final RunResult simulate(ArrayList<Reference> pageReferences, int frameCount, ArrayList<Process> processes) {
        HashMap<Process, ProcessData> dataMap = algorithm.initializeDataMap(pageReferences, frameCount, processes);

        for (Reference reference : pageReferences) {
            ProcessData processData = dataMap.get(reference.process());
            ArrayList<Integer> frames = processData.getFrames();
            Queue<Integer> frameQueue = processData.getFrameQueue();
            Integer page = reference.page();

            boolean faultOccurred = false;
            if (!frames.contains(page)) {
                processData.addPageFault();
                faultOccurred = true;

                if (frames.size() == processData.getFrameAmount()) {
                    frames.remove(frameQueue.poll());
                }

                frames.add(page);
                frameQueue.add(page);

            } else {
                frameQueue.remove(page);
                frameQueue.add(page);
            }

            Optional<HashMap<Process, ProcessData>> newDataMap = algorithm.handlePossibleFault(dataMap, reference.process(), faultOccurred);
            if (newDataMap.isPresent()) {
                dataMap = newDataMap.get();
            }
        }

        int total = 0;
        for (Map.Entry<Process, ProcessData> entry : dataMap.entrySet()) {
            total += entry.getValue().getPageFaults();
        }
        int average = total / dataMap.size();
        return new RunResult(total, average);
    }

    public String getName() {
        return getClass().getSimpleName();
    }
}
