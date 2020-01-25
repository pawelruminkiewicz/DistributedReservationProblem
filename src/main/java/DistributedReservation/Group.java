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
    private Stats stats;


    public Group(TourCompany tourCompany, int maxGroupSize, int delayBeforeStart, int groupStartDelayRange, int checkReservationDelay, Stats stats) {
        Random generator = new Random();
        this.id = this.groupCounter;
        this.tourCompany = tourCompany;
        this.groupSize = generator.nextInt(maxGroupSize);
        this.delayBeforeStart = delayBeforeStart;
        this.groupStartDelayRange = groupStartDelayRange;
        this.checkReservationDelay = checkReservationDelay;
        this.groupCounter++;
        this.stats = stats;

    }

    public int getId() {
        return id;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void run() {
        Random generator = new Random();
        try {
            Thread.sleep(this.delayBeforeStart + generator.nextInt(this.groupStartDelayRange));
            if (this.tourCompany.reserveGuide(this.id, this.groupSize, checkReservationDelay)) {
                stats.book(this);
                if (generator.nextInt(100) < 4) {
                    this.stats.cancel(this);
                    this.tourCompany.cancelReservation(this.id);
                } else {
                    if (this.tourCompany.isReservationDone(this.id)){
                        this.stats.accept(this);
                    }
                    else{
                        this.stats.fake(this);
                    }
                }
            }
            else{
                this.tourCompany.cancelReservation(this.id);
                this.stats.no(this);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void createClients(int amountOfGroups, TourCompany tourCompany, int maxGroupSize, int delayBeforeStart, int groupStartDelayRange, int checkReservationDelay, Stats stats) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < amountOfGroups; i++) {
            Thread thread = new Thread(new Group(tourCompany, maxGroupSize, delayBeforeStart, groupStartDelayRange, checkReservationDelay, stats));
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
