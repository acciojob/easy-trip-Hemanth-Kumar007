package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class AirportService {

   // @Autowired
    AirportRepository repositoryObj = new AirportRepository();

    public void addAirport(Airport airport){
        repositoryObj.addAirport(airport);
    }

    public String getLargestAirportName(){
        return repositoryObj.getLargestAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        return repositoryObj.getShortestDurationOfPossibleBetweenTwoCities(fromCity, toCity);
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        return repositoryObj.getNumberOfPeopleOn(date, airportName);
    }

    public int calculateFlightFare(Integer flightId){
        return repositoryObj.calculateFlightFare(flightId);
    }

    public String bookATicket(Integer flightId, Integer passengerId){
        return repositoryObj.bookATicket(flightId, passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId){
        return repositoryObj.cancelATicket(flightId, passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        return repositoryObj.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public void addFlight(Flight flight){
        repositoryObj.addFlight(flight);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        return repositoryObj.getAirportNameFromFlightId(flightId);
    }

    public void addPassenger(Passenger passenger){
        repositoryObj.addPassenger(passenger);
    }
}
