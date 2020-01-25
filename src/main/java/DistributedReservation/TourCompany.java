package DistributedReservation;

import DistributedReservation.Backend.BackendSession;
import com.datastax.driver.core.Session;


public class TourCompany {
    private int id;
    private Session session;

    private  int maxGroupSize;
    private int guideCount;

    public TourCompany(int id, BackendSession backend, int maxGroupSize, int guideCount) {
        this.id = id;
        this.session = backend.getSession();
        this.maxGroupSize = maxGroupSize;
        this.guideCount = guideCount;
    }

    public boolean reserveGuide(int groupId, int groupSize, int delay) {
        ReservationRequest reservationRequest = new ReservationRequest(session, this.id, groupId, groupSize, guideCount,maxGroupSize);
        reservationRequest.commit();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reservationRequest.isApproved(groupId, this.id);
    }

    public void cancelReservation(int groupId) {
        ReservationRequest reservationRequest = new ReservationRequest(session, id, groupId, -1, guideCount,maxGroupSize);
        reservationRequest.commit();
    }

    public boolean isReservationDone(int groupId) {
        ReservationRequest reservationRequest = new ReservationRequest(session, id, groupId, 0, guideCount, maxGroupSize);
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return reservationRequest.isApproved(groupId, this.id);
    }
}
