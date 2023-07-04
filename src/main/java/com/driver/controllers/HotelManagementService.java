package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HotelManagementService {
    HotelManagementRepository repo= new HotelManagementRepository();
    public String addHotel(Hotel hotel){
        Optional<Hotel> hotelOpt= repo.getByName(hotel.getHotelName());
        if(hotelOpt.isPresent()){
            return "Failure";
        }
        if(hotel.getHotelName().isEmpty()){
            return "Failure";
        }
        repo.addHotel(hotel);
        return "Success";
    }
    public int addUser(User user){
        return repo.addUser(user);
    }
    public String getHotelWithMostFacilities(){
        return repo.getHotelWithMostFacilities();
    }
    public int bookARoom(Booking booking){
        return repo.bookARoom(booking);
    }
    public int getBookings(Integer aadharCard) {
        return repo.getBookings(aadharCard);
    }

    public Hotel update(List<Facility> newFacilities, String hotelName) {
        return repo.update(newFacilities, hotelName);
    }
}
