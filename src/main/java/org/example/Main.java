package org.example;

import model.Flight;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static final int HEADER_HEIGHT = 23;
    private static int option = 0;
    private static String input = "";

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Flight.class)
                .buildSessionFactory();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Display all flights");
            System.out.println("2. Display flights by departure city");
            System.out.println("3. Display flights by destination city");
            System.out.println("4. Display flights by airline");
            System.out.println("5. Display flights by status");
            System.out.println("6. Display flights by departure date");
            System.out.println("7. Display flights by arrival date");
            System.out.println("8. Exit");

            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 8) {
                System.out.println("Exiting...");
                sessionFactory.close();
                scanner.close();
                System.exit(0);
            }

            if (option >= 2 && option <= 7) {
                System.out.print("Enter search term: ");
                input = scanner.nextLine();
            }

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Session session = sessionFactory.getCurrentSession();
                    try {
                        session.beginTransaction();

                        String flightNumber = RandomFlightDataGenerator.generateFlightNumber();
                        String departureCity = RandomFlightDataGenerator.generateCity();
                        String destinationCity = RandomFlightDataGenerator.generateCity();
                        String departureDate = RandomFlightDataGenerator.generateDate();
                        String departureTime = RandomFlightDataGenerator.generateTime();
                        String arrivalDate = RandomFlightDataGenerator.generateDate();
                        String arrivalTime = RandomFlightDataGenerator.generateTime();
                        String airline = RandomFlightDataGenerator.generateAirline();
                        String flightStatus = RandomFlightDataGenerator.generateStatus();

                        Flight newFlight = new Flight(airline, flightNumber, departureCity, destinationCity, departureDate, departureTime, flightStatus, arrivalDate, arrivalTime);

                        session.save(newFlight);
                        session.getTransaction().commit();

                        session = sessionFactory.getCurrentSession();
                        session.beginTransaction();
                        List<Flight> flights;

                        switch (option) {
                            case 1:
                                flights = session.createQuery("from Flight").getResultList();
                                break;
                            case 2:
                                flights = session.createQuery("from Flight where departureCity = :param")
                                        .setParameter("param", input)
                                        .getResultList();
                                break;
                            case 3:
                                flights = session.createQuery("from Flight where destinationCity = :param")
                                        .setParameter("param", input)
                                        .getResultList();
                                break;
                            case 4:
                                flights = session.createQuery("from Flight where aviaCompany = :param")
                                        .setParameter("param", input)
                                        .getResultList();
                                break;
                            case 5:
                                flights = session.createQuery("from Flight where flightStatus = :param")
                                        .setParameter("param", input)
                                        .getResultList();
                                break;
                            case 6:
                                flights = session.createQuery("from Flight where departureDate = :param")
                                        .setParameter("param", input)
                                        .getResultList();
                                break;
                            case 7:
                                flights = session.createQuery("from Flight where arrivalDate = :param")
                                        .setParameter("param", input)
                                        .getResultList();
                                break;
                            default:
                                System.out.println("Invalid option.");
                                return;
                        }

                        printFlights(flights);

                        session.getTransaction().commit();
                    } finally {
                        session.close();
                    }
                }
            }, 0, 10000);
        }
    }

    private static void printFlights(List<Flight> flights) {
        for (int i = 0; i < 20; ++i) System.out.println();

        System.out.printf("%-10s | %-20s | %-20s | %-15s | %-15s | %-15s | %-10s | %-10s | %-10s%n",
                "Flight", "Departure city", "Destination city", "Departure date", "Departure time", "Arrival date", "Arrival time", "Airline", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");

        int startIndex = Math.max(0, flights.size() - 10);

        for (int i = flights.size() - 1; i >= startIndex; i--) {
            Flight flight = flights.get(i);
            System.out.printf("%-10s | %-20s | %-20s | %-15s | %-15s | %-15s | %-10s | %-10s | %-10s%n",
                    flight.getFlightNumber(), flight.getDepartureCity(), flight.getDestinationCity(),
                    flight.getDepartureDate(), flight.getDepartureTime(),
                    flight.getArrivalDate(), flight.getArrivalTime(),
                    flight.getAviaCompany(), flight.getFlightStatus());
        }
        System.out.println();
        moveCursorUp(HEADER_HEIGHT + flights.size());
    }

    private static void moveCursorUp(int lines) {
        System.out.print("\033[" + lines + "A");
        System.out.flush();
    }
}
