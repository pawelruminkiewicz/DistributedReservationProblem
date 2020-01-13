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

		Properties properties = new Properties();
		try {
			properties.load(Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME));

			contactPoint = properties.getProperty("contact_point");
			keyspace = properties.getProperty("keyspace");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		BackendSession session = new BackendSession(contactPoint, keyspace);
		int maxTripSize = 20;
		int guideCount = 10;

		TourCompany tourCompany = new TourCompany(1, session, maxTripSize, guideCount);
		Group.createClients(10);

		System.exit(0);
	}
}
