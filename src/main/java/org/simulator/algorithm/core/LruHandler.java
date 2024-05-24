package org.simulator.algorithm.core;

import org.simulator.scenario.RunResult;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

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

            if (!frames.contains(page)) {
                processData.addPageFault();

                if (frames.size() == processData.getFrameAmount()) {
                    frames.remove(frameQueue.poll());
                }

                frames.add(page);
                frameQueue.add(page);

            } else {
                frameQueue.remove(page);
                frameQueue.add(page);
            }
        }

        return algorithm.createRunResults(dataMap);
    }

    public String getName() {
        return getClass().getSimpleName();
    }
}
