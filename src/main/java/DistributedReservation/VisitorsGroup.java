package DistributedReservation;

import java.util.Random;

public class VisitorsGroup {
    private int id;
    private int count;
    private TourCompany guide;
    private int maxGroupSize;

    public VisitorsGroup(TourCompany guide, int maxGroupSize) {
        Random generator = new Random();
        this.count = generator.nextInt(maxGroupSize-1)+1;
        this.guide = guide;
        this.maxGroupSize = maxGroupSize;
    }
}
