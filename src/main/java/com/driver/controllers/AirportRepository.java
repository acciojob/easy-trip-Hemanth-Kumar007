package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;

@Repository
public class AirportRepository {

    HashMap<String, Airport> airportDb = new HashMap<>();
    HashMap<Integer, Flight> flightDb = new HashMap<>();
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
}
