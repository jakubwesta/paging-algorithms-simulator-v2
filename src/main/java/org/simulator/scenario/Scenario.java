package org.simulator.scenario;

import lombok.Getter;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Scenario {
    @Getter private final int pageAmount;
    @Getter private final int frameAmount;
    @Getter private final int referenceAmount;
    @Getter private final int processAmount;
    @Getter private final int localRadius;
    @Getter private final double localChance;
    private final int minSequenceLength;
    private final int maxSequenceLength;
    private final Random random;
    @Getter private final ArrayList<Process> processes;
    private final ArrayList<Reference> savedReferences;

    public Scenario(int pages, int frames, int references, int processes,
                    int localRadius, double localChance, int minSequenceLength, int maxSequenceLength) {
        this.pageAmount = pages;
        this.frameAmount = frames;
        this.referenceAmount = references;
        this.processAmount = processes;
        this.localRadius = localRadius;
        this.localChance = localChance;
        this.minSequenceLength = minSequenceLength;
        this.maxSequenceLength = maxSequenceLength;
        this.random = new Random();
        this.processes = generateProcesses();
        this.savedReferences = generateReferences();
    }

    public ArrayList<Reference> getReferences() {
        ArrayList<Reference> copiedList = new ArrayList<>(referenceAmount);
        copiedList.addAll(savedReferences);
        return copiedList;
    }

    // todo
    private ArrayList<Process> generateProcesses() {
        ArrayList<Process> processes = new ArrayList<>(processAmount);
        int pagesPerProcess = pageAmount / processAmount;
        for (int i = 0; i < processAmount; i++) {
            int minPageId = i * pagesPerProcess;
            int maxPageId = (i + 1) * pagesPerProcess;
            processes.add(new Process(minPageId, maxPageId, localRadius, localChance));
        }
        return processes;
    }

    private ArrayList<Reference> generateReferences() {
        ArrayList<Reference> references = new ArrayList<>(referenceAmount);

        int time = 0;
        int referencesLeft = referenceAmount;
        while (referencesLeft > 0) {
            int length = random.nextInt(minSequenceLength, maxSequenceLength);
            referencesLeft -= length;

            int processId = random.nextInt(processes.size());
            Process process = processes.get(processId);

            references.addAll(process.generateLocalizedReferences(length, time));
            time += length + 1;
        }

        references.sort(Comparator.comparingInt(Reference::arrivalTime));
        return references;
    }
}
