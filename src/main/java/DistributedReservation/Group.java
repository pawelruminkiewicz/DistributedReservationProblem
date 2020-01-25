package DistributedReservation;

import java.util.ArrayList;
import java.util.Random;

public class Group implements Runnable {
    private int id;
    private TourCompany tourCompany;
    private int delayBeforeStart;
    private int groupStartDelayRange;
    private static int groupCounter = 0;
    private int checkReservationDelay;
    private int groupSize;

    public Group(TourCompany tourCompany, int maxGroupSize, int delayBeforeStart, int groupStartDelayRange, int checkReservationDelay) {
        Random generator = new Random();
        this.id = this.groupCounter;
        this.tourCompany = tourCompany;
        this.groupSize = generator.nextInt(maxGroupSize);
        this.delayBeforeStart = delayBeforeStart;
        this.groupStartDelayRange = groupStartDelayRange;
        this.checkReservationDelay = checkReservationDelay;
        this.groupCounter++;

    }

    public void run() {
        Random generator = new Random();
        try {
            Thread.sleep(this.delayBeforeStart + generator.nextInt(this.groupStartDelayRange));
            if (this.tourCompany.reserveGuide(this.id, this.groupSize, checkReservationDelay)) {
                if (generator.nextInt(100) < 4) {
                    //stats.canceled
                    this.tourCompany.cancelReservation(this.id);
                } else {
                    if (this.tourCompany.isReservationDone(this.id)){
                        //stats.correct
                    }
                    else{
                        //stats.incorrect
                    }
                }
            }
            else{
                this.tourCompany.cancelReservation(this.id);
                //stats.stay

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void createClients(int amountOfGroups, TourCompany tourCompany, int maxGroupSize, int delayBeforeStart, int groupStartDelayRange, int checkReservationDelay) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < amountOfGroups; i++) {
            Thread thread = new Thread(new Group(tourCompany, maxGroupSize, delayBeforeStart, groupStartDelayRange, checkReservationDelay));
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
