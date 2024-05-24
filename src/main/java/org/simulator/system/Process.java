package org.simulator.system;

import java.util.ArrayList;
import java.util.Random;

public class Process {
    private final int minPageId;
    private final int maxPageId;
    private final int radius;
    private final double chance;
    private final Random random;

    public Process(int minPageId, int maxPageId, int radius, double chance) {
        this.minPageId = minPageId;
        this.maxPageId = maxPageId;
        this.radius = radius;
        this.chance = chance;
        this.random = new Random();
    }

    public ArrayList<Reference> generateLocalizedReferences(int length, int time) {
        ArrayList<Reference> references = new ArrayList<>(length);

        references.add(genReference(time));
        time++;

        for (int i = 1; i < length; i++) {
            if (random.nextDouble() < chance) {
                int previous = references.get(i - 1).page();

                int pageId = Math.abs(random.nextInt(previous - radius, previous + radius));
                if (pageId > maxPageId) {
                    pageId = maxPageId;
                }
                if (pageId < minPageId) {
                    pageId = minPageId;
                }

                references.add(new Reference(pageId, time, this));
            } else {
                references.add(genReference(time));
            }
            time++;
        }

        return references;
    }

    public int getPageRange() {
        return maxPageId - minPageId;
    }

    private Reference genReference(int time) {
        return new Reference(random.nextInt(minPageId, maxPageId), time, this);
    }
}
