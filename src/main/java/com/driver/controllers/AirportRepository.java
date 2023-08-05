package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class AirportRepository {

    HashMap<String, Airport> airportDb = new HashMap<>();
    HashMap<Integer, Flight> flightDb = new HashMap<>();
    HashMap<Integer, Passenger> passengerDb = new HashMap<>();
    HashMap<Integer, List<Integer>> bookingDb = new HashMap<>();
    HashMap<Integer,Integer>canceltikets=new HashMap<>();
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
            else if(airportDb.get(name).getNoOfTerminals() == max){
                int comp = name.compareTo(largest);
                if(comp < 0){
                    largest = name;
                }
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
        if(flag == false) return -1;

        else return shortestDuration;

    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        Integer totalPersons = 0;
        for(Integer id : flightDb.keySet()){
            City from = flightDb.get(id).getFromCity();
            City to = flightDb.get(id).getToCity();
            Date fDate = flightDb.get(id).getFlightDate();
            if(fDate.equals(date) && ((from.equals(airportName)) || (to.equals(airportName)))){
                //totalPersons += flightDb.get(id).getMaxCapacity();
                totalPersons += bookingDb.getOrDefault(id, new ArrayList<>()).size();
            }
        }
        return totalPersons;
    }

    public int calculateFlightFare(Integer flightId){

        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight for the third person will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be just checking price

        int fare = 0;
        if(bookingDb.containsKey(flightId)) {
            int alreadyBooked = bookingDb.get(flightId).size();
            fare = 3000 + alreadyBooked * 50;
        }
        return fare;
    }

    public String bookATicket(Integer flightId, Integer passengerId){
        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"

        Flight flight = flightDb.get(flightId);
        List<Integer> passengerList = bookingDb.getOrDefault(flightId, new ArrayList<>());
        if(passengerList.size() < flight.getMaxCapacity()) {
            if (passengerList.contains(passengerId)) {
                return "FAILURE";
            } else {
                passengerList.add(passengerId);
                bookingDb.put(flightId, passengerList);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }

    public String cancelATicket(Integer flightId, Integer passengerId){
        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId

        if(flightDb.containsKey(flightId)) {
            //Flight flight = flightDb.get(flightId);
            List<Integer> passengerList = bookingDb.get(flightId);
            if (!passengerList.contains(passengerId)) {
                return "FAILURE";
            } else {
                passengerList.remove(passengerId);
                bookingDb.put(flightId, passengerList);
                canceltikets.put(flightId, canceltikets.getOrDefault(flightId, 0) + 1);
                return "SUCCESS";
            }
        }
        return "FAILURE";
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

    public String getAirportNameFromFlightId(Integer flightId){
        if(!flightDb.containsKey(flightId)){
            return null;
        }
        else{
            City city = flightDb.get(flightId).getFromCity();
            for(Airport airport : airportDb.values()){
                if(airport.getCity().equals(city)){
                    return airport.getAirportName();
                }
            }
            return null;
        }
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        int cancelfare=canceltikets.getOrDefault(flightId,1)*50;

        return calculateFlightFare(flightId)-cancelfare;
    }

    public void addPassenger(Passenger passenger){
        Integer id = passenger.getPassengerId();
        passengerDb.put(id, passenger);
    }
}
