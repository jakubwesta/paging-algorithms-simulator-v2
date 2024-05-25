package org.simulator.scenario;

import lombok.Getter;
import org.simulator.system.Process;
import org.simulator.system.Reference;

import java.util.*;

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
    @Getter private final boolean evenProcesses;
    @Getter private final ArrayList<Process> processes;
    private final ArrayList<Reference> savedReferences;

    public Scenario(int pages, int frames, int references, int processes, int localRadius,
                    double localChance, int minSequenceLength, int maxSequenceLength, boolean evenProcesses) {
        this.pageAmount = pages;
        this.frameAmount = frames;
        this.referenceAmount = references;
        this.processAmount = processes;
        this.localRadius = localRadius;
        this.localChance = localChance;
        this.minSequenceLength = minSequenceLength;
        this.maxSequenceLength = maxSequenceLength;
        this.random = new Random();
        this.evenProcesses = evenProcesses;
        if (evenProcesses) {
            this.processes = generateEvenProcesses();
        } else {
            this.processes = generateProcesses();
        }

        this.savedReferences = generateReferences();
    }

    public ArrayList<Reference> getReferences() {
        ArrayList<Reference> copiedList = new ArrayList<>(referenceAmount);
        copiedList.addAll(savedReferences);
        return copiedList;
    }

    private ArrayList<Process> generateEvenProcesses() {
        ArrayList<Process> processes = new ArrayList<>(processAmount);
        int pagesPerProcess = pageAmount / processAmount;
        for (int i = 0; i < processAmount; i++) {
            int minPageId = i * pagesPerProcess;
            int maxPageId = (i + 1) * pagesPerProcess;
            processes.add(new Process(minPageId, maxPageId, localRadius, localChance));
        }
        return processes;
    }

    private ArrayList<Process> generateProcesses() {
        ArrayList<Process> processes = new ArrayList<>();
        int avgPerProcess = pageAmount / processAmount;
        ArrayList<Integer> pageSegments = divideIntoRandomSegments(
                pageAmount,
                processAmount,
                (int) Math.round(0.3 * avgPerProcess),
                (int) Math.round(1.7 * avgPerProcess)
        );

        int lastPageId = 0;
        int i = 0;
        for (int segment : pageSegments) {
            processes.add(new Process(lastPageId + i, lastPageId + segment, localRadius, localChance));
            lastPageId += segment;
            i = 1;
        }

        return processes;
    }

    private ArrayList<Reference> generateReferences() {
        ArrayList<Reference> references = new ArrayList<>(referenceAmount);

        int time = 0;
        int referencesLeft = referenceAmount;
        while (referencesLeft > 0) {
            int length;
            if (referencesLeft < minSequenceLength) {
                length = referencesLeft;
            } else {
                length = random.nextInt(minSequenceLength, maxSequenceLength);
            }

            referencesLeft -= length;

            int processId = random.nextInt(processes.size());
            Process process = processes.get(processId);

            references.addAll(process.generateLocalizedReferences(length, time));
            time += length + 1;
        }

        references.sort(Comparator.comparingInt(Reference::arrivalTime));
        return references;
    }

    private ArrayList<Integer> divideIntoRandomSegments(int total, int segments, int min, int max) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < segments; i++) {
            result.add(min);
        }
        int remaining = total - (segments * min);

        while (remaining > 0) {
            for (int i = 0; i < segments; i++) {
                if (remaining == 0) break;

                int currentValue = result.get(i);

                int maxAddition = Math.min(max - currentValue, remaining);

                if (maxAddition > 0) {
                    int addition = random.nextInt(maxAddition + 1);
                    result.set(i, currentValue + addition);
                    remaining -= addition;
                }
            }
        }
        Collections.shuffle(result, random);

        return result;
    }
}
