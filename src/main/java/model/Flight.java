package model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avia_company")
    private String aviaCompany;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "destination_city")
    private String destinationCity;

    @Column(name = "departure_date")
    private String departureDate;

    @Column(name = "departure_time")
    private String departureTime;

    @Column(name = "flight_status")
    private String flightStatus;

    @Column(name = "arrival_date")
    private String arrivalDate;

    @Column(name = "arrival_time")
    private String arrivalTime;

    // Конструкторы, геттеры и сеттеры

    public Flight() {
    }

    public Flight(String aviaCompany, String flightNumber, String departureCity, String destinationCity, String departureDate, String departureTime, String flightStatus, String arrivalDate, String arrivalTime) {
        this.aviaCompany = aviaCompany;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.flightStatus = flightStatus;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }
}
