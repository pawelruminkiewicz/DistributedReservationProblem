package DistributedReservation;

import java.util.Random;

public class VisitorsGroup {
    private int id;
    private int count;
    private Guide guide;
    private int maxGroupSize;

    public VisitorsGroup(Guide guide, int maxGroupSize) {
        Random generator = new Random();
        this.count = generator.nextInt(maxGroupSize-1)+1;
        this.guide = guide;
        this.maxGroupSize = maxGroupSize;
    }
}
