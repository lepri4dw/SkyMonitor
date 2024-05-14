package model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "flight_number")
    private String flightNumber;

    
    @Column(name = "departure_city")
    private String departureCity;


    @Column(name = "airport")
    private String airport;


    @Column(name = "destination_city")
    private String destinationCity;


    @Column(name = "departure_time")
    private String departureTime;


    @Column(name = "arrival_time")
    private String arrivalTime;


    @Column(name = "airline")
    private String airline;


    @Column(name = "aircraft_type")
    private String aircraftType;


    @Column(name = "gate")
    private String gate;

    public Flight() {
    }

    public Flight(String flightNumber, String departureCity, String airport, String destinationCity, String departureTime, String arrivalTime,
                  String airline, String aircraftType, String gate) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.airport = airport;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airline = airline;
        this.aircraftType = aircraftType;
        this.gate = gate;
    }
}
