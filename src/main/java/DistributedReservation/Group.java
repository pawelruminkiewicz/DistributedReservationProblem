package DistributedReservation;

import java.util.ArrayList;
import java.util.Random;

public class Group {
    private int id;
    private int count;
    private TourCompany tourCompany;
    private int maxGroupSize;

    public Group(int id, int count, TourCompany tourCompany, int maxGroupSize) implements Runnable {
        Random generator = new Random();
        this.id = id;
        this.count = count;
        this.tourCompany = tourCompany;
        this.maxGroupSize = maxGroupSize;
    }

    public void run(){
        Random generator = new Random();
        try {
            Thread.sleep(delayBeforeStart + generator.nextInt(groupStartDelayRange));
            if (tourCompany.reserveGuide(id, count, checkReservationDelay)) {
                if (generator.nextInt(25) == 5) {
                    tourCompany.cancelReservation(id);
                } else {
                    if (tourCompany.)

                }
            }
        }
    }

    public static void createClients(int count) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(new Group(tourCompany, maxGroupSize));
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
