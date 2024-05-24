package org.simulator.algorithm;

import org.simulator.scenario.RunResult;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public abstract class FrameAllocationAlgorithm {
    protected HashMap<Process, ProcessData> dataMap;

    public FrameAllocationAlgorithm() {
        this.dataMap = new HashMap<>();
    }

    public final RunResult simulate(ArrayList<Reference> pageReferences, int frameCount, int processAmount) {
        dataMap = initializeDataMap(frameCount, processAmount);

        for (Reference reference : pageReferences) {
            ProcessData processData = dataMap.get(reference.process());
            ArrayList<Integer> frames = processData.frames;
            Queue<Integer> frameQueue = processData.frameQueue;
            Integer page = reference.page();

            if (!frames.contains(page)) {
                processData.pageFaults++;

                if (frames.size() == processData.frameAmount) {
                    frames.remove(frameQueue.poll());
                }

                frames.add(page);
                frameQueue.add(page);

            } else {
                frameQueue.remove(page);
                frameQueue.add(page);
            }
        }

        return createRunResults(frameCount, processAmount, dataMap);
    }

    protected abstract HashMap<Process, ProcessData> initializeDataMap(int frameCount, int processAmount);

    protected abstract RunResult createRunResults(int frameCount, int processAmount, HashMap<Process, ProcessData> dataMap);

    public void reset() {
        this.dataMap = new HashMap<>();
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    static class ProcessData {
        int frameAmount;
        int pageFaults;
        Queue<Integer> frameQueue;
        ArrayList<Integer> frames;

        ProcessData(int frameAmount) {
            this.frameAmount = frameAmount;
            this.pageFaults = 0;
            this.frameQueue = new LinkedList<>();
            this.frames = new ArrayList<>();
        }
    }
}
