package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
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
}
