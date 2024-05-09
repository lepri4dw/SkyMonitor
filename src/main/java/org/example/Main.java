package org.example;

import model.Flight;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Flight.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Flight flight1 = new Flight("SU101", "Moscow", "New York", "10:00", "16:00");
            Flight flight2 = new Flight("LH202", "Berlin", "London", "12:00", "14:00");
            Flight flight3 = new Flight("BA303", "Paris", "Tokyo", "14:00", "08:00");

            session.save(flight1);
            session.save(flight2);
            session.save(flight3);

            session.getTransaction().commit();

            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<Flight> flights = session.createQuery("from Flight").getResultList();

            System.out.println("Список рейсов:");
            for (Flight flight : flights) {
                System.out.println("Рейс: " + flight.getFlightNumber() +
                        ", Откуда: " + flight.getDepartureCity() +
                        ", Куда: " + flight.getDestinationCity() +
                        ", Время вылета: " + flight.getDepartureTime() +
                        ", Время прибытия: " + flight.getArrivalTime());
            }

        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
