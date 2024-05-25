package org.example;
import java.util.Random;

public class RandomFlightDataGenerator {
    private static final String[] flightNumbers = {"SU101", "LH202", "BA303", "AF404", "EK505", "QR606", "TK707", "AA808", "DL909", "UA1010"};
    private static final String[] cities = {"Moscow", "Berlin", "Paris", "London", "New York", "Tokyo", "Dubai", "Los Angeles", "Sydney", "Rome"};
    private static final String[] airports = {"SVO", "TXL", "CDG", "LHR", "JFK", "NRT", "DXB", "LAX", "SYD", "FCO"};
    private static final String[] airlines = {"Aeroflot", "Lufthansa", "British Airways", "Air France", "Emirates", "Qatar Airways", "Turkish Airlines", "American Airlines", "Delta Air Lines", "United Airlines"};
    private static final String[] aircraftTypes = {"Boeing 777", "Airbus A320", "Boeing 787", "Airbus A380", "Boeing 737", "Airbus A350", "Boeing 747", "Embraer E190", "Bombardier CRJ900", "Airbus A330"};
    private static final String[] gates = {"A1", "B2", "C3", "D4", "E5", "F6", "G7", "H8", "I9", "J10"};
    private static final String[] statuses = {"On Time", "Delayed", "Cancelled"};

    private static final Random random = new Random();

    public static String generateFlightNumber() {
        return flightNumbers[random.nextInt(flightNumbers.length)];
    }

    public static String generateCity() {
        return cities[random.nextInt(cities.length)];
    }

    public static String generateAirport() {
        return airports[random.nextInt(airports.length)];
    }

    public static String generateAirline() {
        return airlines[random.nextInt(airlines.length)];
    }

    public static String generateAircraftType() {
        return aircraftTypes[random.nextInt(aircraftTypes.length)];
    }

    public static String generateGate() {
        return gates[random.nextInt(gates.length)];
    }

    public static String generateTime() {
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        return String.format("%02d:%02d", hour, minute);
    }

    public static String generateStatus() {
        return statuses[random.nextInt(statuses.length)];
    }

    public static String generateDate() {
        int year = 2024;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1; // To simplify, assuming all months have 28 days
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
