package DistributedReservation;

public class Stats {

    private final int singleTicketCost = 30;
    private final int singleRefundCost = 10;

    private int acceptedReservations = 0; // group accepted and picked up a tickets
    private int canceledReservations = 0; // group canceled after time
    private int fakeReservations = 0; // reservation accomplished with error
    private int noReservations = 0; // group wanted a reservation but not enough space under one guide

    private int cummulativeIncome = 0;
    private int cummulativeRefund = 0;

    public Stats(){}


    public void book(Group g){
        this.log("+++++",g.getId(), "I've book a reservation with " + g.getGroupSize() + " participants");
    }

    public void cancel(Group g){
        this.log("XXXXX",g.getId(), "We cannot participate in a trip");
        this.canceledReservations++;
    }

    public void accept(Group g){
        this.log("@@@@@",g.getId(), "That's great! I hope, that it will be great adventure!");
        this.acceptedReservations++;
        this.cummulativeIncome = this.cummulativeIncome + this.singleTicketCost * g.getGroupSize();
    }

    public void no(Group g){
        this.log("-----", g.getId(), "That's a pity that you do not have enough space for us");
        noReservations++;
    }

    public void fake(Group g){
        this.log("!!!!!", g.getId(), "Are you fu@!%#g kidding me?!");
        fakeReservations++;
        cummulativeRefund = cummulativeRefund + cummulativeRefund * singleRefundCost;
    }

    public void showFinalStats(){
        System.out.printf("|%25s|%-5s|\n", "Reservation type", "Count");
        System.out.printf("---------------------------+-----\n");
        System.out.printf("|%25s|%-5d|\n", "Accepted reservations", this.acceptedReservations);
        System.out.printf("|%25s|%-5d|\n", "Canceled reservations", this.canceledReservations);
        System.out.printf("|%25s|%-5d|\n", "Fake reservations", this.fakeReservations);
        System.out.printf("|%25s|%-5d|\n", "No reservations", this.noReservations);
        System.out.printf("---------------------------------\n");
        System.out.printf("\n\n\n");
        System.out.printf("|%25s|%-5s|\n", "Type", "Value");
        System.out.printf("---------------------------------\n");
        System.out.printf("|%25s|%-5d|\n", "Total income", this.cummulativeIncome);
        System.out.printf("|%25s|%-5d|\n", "Total refund", this.cummulativeRefund);
        System.out.printf("|%25s|%-5d|\n", "Stonks", this.cummulativeIncome - this.cummulativeRefund);
        System.out.printf("---------------------------------\n");

    }


    private void log(String indicator, int id, String message){
        System.out.printf("%s {%4d}:%-45s\n", indicator, id, message);
    }

}
