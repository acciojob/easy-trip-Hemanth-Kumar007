package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class AirportRepository {

    HashMap<String, Airport> airportDb = new HashMap<>();
    HashMap<Integer, Flight> flightDb = new HashMap<>();
    HashMap<Integer, Passenger> passengerDb = new HashMap<>();
    HashMap<Integer, List<Integer>> bookingDb = new HashMap<>();
    public void addAirport(Airport airport){
        String name = airport.getAirportName();
        airportDb.put(name, airport);
    }

    public String getLargestAirportName(){
        Integer max = 0;
        String largest = "";
        for(String name : airportDb.keySet()){
            if(airportDb.get(name).getNoOfTerminals() > max){
                max = airportDb.get(name).getNoOfTerminals();
                largest = name;
            }
        }
        return largest;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        double shortestDuration = Double.MAX_VALUE;
        boolean flag = false;
        for(Integer id : flightDb.keySet()){
            City from = flightDb.get(id).getFromCity();
            City to = flightDb.get(id).getToCity();
            if(from.equals(fromCity) && to.equals(toCity)){
                if(flightDb.get(id).getDuration() < shortestDuration){
                    shortestDuration = flightDb.get(id).getDuration();
                    flag = true;
                }
            }
        }
        return shortestDuration;
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        Integer totalPersons = 0;
        for(Integer id : flightDb.keySet()){
            City from = flightDb.get(id).getFromCity();
            City to = flightDb.get(id).getToCity();
            Date fDate = flightDb.get(id).getFlightDate();
            if((fDate.equals(date) && from.equals(airportName)) || ((fDate.equals(date)) && to.equals(airportName))){
                totalPersons += flightDb.get(id).getMaxCapacity();
            }
        }
        return totalPersons;
    }

    public String bookATicket(Integer flightId, Integer passengerId){
        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"

        Flight flight = flightDb.get(flightId);
        if(bookingDb.get(flightId).size() > flight.getMaxCapacity()){
            return "FAILURE";
        }
        else if(bookingDb.get(flightId).contains(passengerId)){
            return "FAILURE";
        }
        else{
            bookingDb.get(flightId).add(passengerId);
            return "SUCCESS";
        }
    }

    public String cancelATicket(Integer flightId, Integer passengerId){
        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId

        if(!flightDb.containsKey(flightId)){
            return "FAILURE";
        }
        Flight flight = flightDb.get(flightId);
        if(!bookingDb.get(flightId).contains(passengerId)){
            return "FAILURE";
        }
        else{
            bookingDb.get(flightId).remove(passengerId);
            return "SUCCESS";
        }
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        Integer bookings = 0;
        for(Integer flightId : bookingDb.keySet()){
            if(bookingDb.get(flightId).contains(passengerId)){
                bookings++;
            }
        }
        return bookings;
    }

    public void addFlight(Flight flight){
        Integer id = flight.getFlightId();
        flightDb.put(id, flight);
    }
    public void addPassenger(Passenger passenger){
        Integer id = passenger.getPassengerId();
        passengerDb.put(id, passenger);
    }
}