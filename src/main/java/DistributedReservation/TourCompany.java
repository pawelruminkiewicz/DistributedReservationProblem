package DistributedReservation;

import DistributedReservation.Backend.BackendSession;
import com.datastax.driver.core.Session;


public class TourCompany {
    private int id;
    private Session session;

    private  int maxTripSize;
    private int guideCount;

    public TourCompany(int id, BackendSession backend, int maxTripSize, int guideCount) {
        this.id = id;
        this.session = backend.getSession();
        this.maxTripSize = maxTripSize;
        this.guideCount = guideCount;
    }

    public void reserveGuide(int clientId, int tripSize, int delay) {
        ReservationRequest reservationRequest = new ReservationRequest(session, id, clientId, ......, tripSize);
        reservationRequest.commit();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(int clientId) {
        ReservationRequest reservationRequest = new ReservationRequest(session, id, clientId, -1, ..., tripSize);
        reservationRequest.commit();
    }

    public boolean isReservationDone(int groupId) {
        ReservationRequest reservationRequest = new ReservationRequest(session, id, groupId, 0, guideCount, maxTripSize);
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return reservationRequest.
    }
}
