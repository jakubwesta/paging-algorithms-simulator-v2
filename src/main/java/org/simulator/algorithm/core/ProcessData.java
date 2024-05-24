package org.simulator.algorithm.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessData {
    @Getter private final int frameAmount;
    @Getter private int pageFaults;
    @Getter private final Queue<Integer> frameQueue;
    @Getter private final ArrayList<Integer> frames;

    public ProcessData(int frameAmount) {
        this.frameAmount = frameAmount;
        this.pageFaults = 0;
        this.frameQueue = new LinkedList<>();
        this.frames = new ArrayList<>();
    }

    public int addPageFault() {
        return ++pageFaults;
    }
}
