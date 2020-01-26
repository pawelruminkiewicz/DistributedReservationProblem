package DistributedReservation;

import java.io.IOException;
import java.util.Properties;

import DistributedReservation.Backend.BackendException;
import DistributedReservation.Backend.BackendSession;

public class Main {

	private static final String PROPERTIES_FILENAME = "config.properties";

	public static void main(String[] args) throws IOException, BackendException {
		String contactPoint = null;
		String keyspace = null;

		int tourCompanyId = 1;
		int maxGroupSize = 20;
		int guideCount = 100;
		int amountOfGroups = 250;
		int delayBeforeStart = 1000;
		int groupStartDelayRange = 100;
		int checkReservationDelay = 100;


		Stats stats = new Stats();

		Properties properties = new Properties();
		try {
			properties.load(Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME));

			contactPoint = properties.getProperty("contact_point");
			keyspace = properties.getProperty("keyspace");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		BackendSession session = new BackendSession(contactPoint, keyspace);


		TourCompany tourCompany = new TourCompany(tourCompanyId, session, maxGroupSize, guideCount);
		Group.createClients(amountOfGroups, tourCompany, maxGroupSize, delayBeforeStart, groupStartDelayRange, checkReservationDelay, stats);
		stats.showFinalStats();

		System.exit(0);

	}
}
