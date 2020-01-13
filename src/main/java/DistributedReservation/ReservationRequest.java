package DistributedReservation;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import java.util.*;

public class ReservationRequest extends DbModel implements Comparable<ReservationRequest> {
    private int tourCompanyId;
    private int groupId;
    private int groupSize;
    private int guideCount;
    private int maxTripSize;
    private long timestamp;

    @Override
    public int compareTo(ReservationRequest m2) {
        if (this.timestamp == m2.timestamp) {
            return 0;
        }
        return this.timestamp < m2.timestamp ? -1 : 1;
    }

    public ReservationRequest(Session session, int tourCompanyId, int groupId, int groupSize, int guideCount, int maxTripSize) {
        super(session);
        this.tourCompanyId = tourCompanyId;
        this.groupId = groupId;
        this.groupSize = groupSize;
        this.guideCount = guideCount;
        this.maxTripSize = maxTripSize;
        this.timestamp = timestamp;
    }

    public ReservationRequest(Session session, int tourCompanyId, int groupId, int groupSize, int guideCount, int maxTripSize, long timestamp) {
        this(session, tourCompanyId, groupId, groupSize, guideCount, maxTripSize);
        this.timestamp = timestamp;
    }

    public void commit() {
        createTable("ReservationRequests");
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append("ReservationRequests").append("(id, tourCompanyId, groupId, groupSize, timestamp) ")
                .append("VALUES (").append(UUID.randomUUID())
                .append(", ").append(tourCompanyId)
                .append(", ").append(groupId)
                .append(", ").append(groupSize)
                .append(", ").append(System.currentTimeMillis())
                .append(");");

        String query = sb.toString();
        execute(query);
    }

    public List<List<Integer>>  giveReservation() {
        List<ReservationRequest> reservationRequests = getUpdatedReservations();
        List<List<Integer>> reservations = new ArrayList<>();
        ArrayList<Integer> guides = new ArrayList<>();

        for (int i = 0; i < guideCount; i++) {
            guides.add(maxTripSize);
            List<Integer> guide = new ArrayList<>();
            reservations.add(guide);
        }

        for (ReservationRequest reservationRequest : reservationRequests) {
            for (int i = 0; i < guideCount; i++) {
                int availablePlacesInTrip = guides.get(i);
                if (availablePlacesInTrip >= reservationRequest.groupSize) {
                    guides.set(i, availablePlacesInTrip - reservationRequest.groupSize);
                    reservations.get(i).add(reservationRequest.groupSize);
                    break;
                }
            }
        }
        return reservations;
    }

    public boolean isApproved(int clientId, int tourCompanyId) {
        List<ReservationRequest> reservationRequests = getUpdatedReservations(tourCompanyId);
        ArrayList<Integer> guides = new ArrayList<>();
        for (int i = 0; i < groupSize; i++) {
            guides.add(groupSize);
        }
        for (ReservationRequest reservationRequest : reservationRequests) {
            boolean decision = false;
            for (int i = 0; i < groupSize; i++) {
                int availablePlacesInTrip = guides.get(i);
                if (availablePlacesInTrip >= reservationRequest.groupSize) {
                    guides.set(i, availablePlacesInTrip - reservationRequest.groupSize);
                    decision = true;
                    break;
                }
            }
            if (reservationRequest.groupId == groupId) {
                return decision;
            }
        }
        return false;
    }

    public List<ReservationRequest> getUpdatedReservations(int tourCompanyId) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ReservationRequests WHERE tourCompanyId =").append(tourCompanyId);
        String query = sb.toString();
        ResultSet rs = session.execute(query);
        List<ReservationRequest> reservationRequestList = new ArrayList<>();

        rs.forEach(r -> {
            if (r.getInt("groupSize") > 0) {
                reservationRequestList.add(new ReservationRequest(
                        session,
                        r.getInt("tourCompanyId"),
                        r.getInt("groupId"),
                        r.getInt("groupSize"),
                        guideCount,
                        maxTripSize,
                        r.getLong("timestamp")));
            } else {
                for (int i = 0; i < reservationRequestList.size(); i++) {
                    if (reservationRequestList.get(i).groupId == r.getInt("groupId")) {
                        reservationRequestList.remove(i);
                        break;
                    }
                }
            }
        });

        Collections.sort(reservationRequestList);

        return reservationRequestList;
    }

    public List<ReservationRequest> getAllReservations(int tourCompanyId) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ReservationRequests WHERE tourCompanyId =").append(tourCompanyId);
        String query = sb.toString();
        ResultSet rs = session.execute(query);
        List<ReservationRequest> reservationRequestList = new ArrayList<>();

        rs.forEach(r -> {
            reservationRequestList.add(new ReservationRequest(
                    session,
                    r.getInt("tourCompanyId"),
                    r.getInt("groupId"),
                    r.getInt("groupSize"),
                    guideCount,
                    maxTripSize,
                    r.getLong("timestamp")));
        });
        Collections.sort(reservationRequestList);
        return reservationRequestList;
    }


}
