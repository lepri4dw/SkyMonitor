package org.example;

import model.Flight;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static final int HEADER_HEIGHT = 23;
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Flight.class)
                .buildSessionFactory();

//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        Flight flight1 = new Flight("SU101", "Moscow", "Sheremetyevo", "New York", "10:00", "16:00", "Aeroflot", "Boeing 777", "D23");
//        Flight flight2 = new Flight("LH202", "Berlin", "Berlin Tegel", "London", "12:00", "14:00", "Lufthansa", "Airbus A320", "B12");
//        Flight flight3 = new Flight("BA303", "Paris", "Charles de Gaulle", "Tokyo", "14:00", "08:00", "British Airways", "Boeing 787", "C5");
//
//        session.save(flight1);
//        session.save(flight2);
//        session.save(flight3);
//
//        session.getTransaction().commit();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Session session = sessionFactory.getCurrentSession();
                try {
                    session.beginTransaction();

                    String flightNumber = RandomFlightDataGenerator.generateFlightNumber();
                    String departureCity = RandomFlightDataGenerator.generateCity();
                    String departureAirport = RandomFlightDataGenerator.generateAirport();
                    String destinationCity = RandomFlightDataGenerator.generateCity();
                    String departureTime = RandomFlightDataGenerator.generateTime();
                    String arrivalTime = RandomFlightDataGenerator.generateTime();
                    String airline = RandomFlightDataGenerator.generateAirline();
                    String aircraftType = RandomFlightDataGenerator.generateAircraftType();
                    String gate = RandomFlightDataGenerator.generateGate();

                    Flight newFlight = new Flight(flightNumber, departureCity, departureAirport,
                            destinationCity, departureTime, arrivalTime, airline, aircraftType, gate);

                    session.save(newFlight);
                    session.getTransaction().commit();

                    session = sessionFactory.getCurrentSession();
                    session.beginTransaction();
                    List<Flight> flights = session.createQuery("from Flight").getResultList();

                    printFlights(flights);

                    session.getTransaction().commit();
                } finally {
                    session.close();
                }
            }
        }, 0, 3000);
    }

    private static void printFlights(List<Flight> flights) {
        for (int i = 0; i < 20; ++i) System.out.println();

        System.out.printf("%-10s | %-25s | %-25s | %-20s | %-20s | %-20s | %-15s | %-5s%n",
                "Flight", "Departure city (Airport)", "Destination city", "Departure time", "Arrival time",
                "Airline", "Aircraft Type", "Gate");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        int startIndex = Math.max(0, flights.size() - 10);

        for (int i = flights.size() - 1; i >= startIndex; i--) {
            Flight flight = flights.get(i);
            System.out.printf("%-10s | %-25s | %-25s | %-20s | %-20s | %-20s | %-15s | %-5s%n",
                    flight.getFlightNumber(), flight.getDepartureCity() + " (" + flight.getAirport() + ")",
                    flight.getDestinationCity(), flight.getDepartureTime(),
                    flight.getArrivalTime(), flight.getAirline(),
                    flight.getAircraftType(), flight.getGate());
        }
        System.out.println();
        moveCursorUp(HEADER_HEIGHT + flights.size());
    }

    private static void moveCursorUp(int lines) {
        System.out.print("\033[" + lines + "A");
        System.out.flush();
    }
}
