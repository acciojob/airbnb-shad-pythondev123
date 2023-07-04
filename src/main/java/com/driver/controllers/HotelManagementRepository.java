package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementRepository {
    private Map<String, Hotel> hotelDb= new HashMap<>();
    private Map<Integer, User> userDb= new HashMap<>();
    private Map<String, Booking> bookingDb= new HashMap<>();
    HashMap<Integer, List<Booking>> userBookingDb = new HashMap<>();
    private int maxFacility=0;
    private String hotelNameWithMostFacilities= "";


    public String addHotel(Hotel hotel){

        if(hotel == null || hotel.getHotelName() == null) {
            return "FAILURE";
        }else if(hotelDb.containsKey(hotel.getHotelName())) {
            return "FAILURE";
        }

        String name = hotel.getHotelName();

        hotelDb.put(name, hotel);

        return "SUCCESS";

    }
//    public Optional<Hotel> getByName(String hotelname){
//        if(hotelDb.containsKey(hotelname)){
//            return Optional.of(hotelDb.get(hotelname));
//        }
//        return Optional.empty();
//    }
    public int addUser(User user){
        userDb.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }
    public String getHotelWithMostFacilities(){
        String res = "";
        int cnt = 1;

        for(String hotels : hotelDb.keySet()) {
            Hotel hotel = hotelDb.get(hotels);
            char ch = hotels.charAt(0);
            List<Facility> facilities = hotel.getFacilities();
            int size = facilities.size();

            if(cnt <= size) {
                if(cnt == size) {
                    if(res.equals("")) {
                        res = hotels;
                    } else {
                        res = res.charAt(0) > ch ? hotels : res;
                    }
                } else {
                    res = hotels;
                    cnt = size;
                }
            }
        }
        return res;
    }
 public int bookARoom(Booking booking){
     UUID uuid = UUID.randomUUID();
     booking.setBookingId(String.valueOf(uuid));

     int pricePerRoom = hotelDb.get(booking.getHotelName()).getPricePerNight();
     int noOfRooms = booking.getNoOfRooms();

     if(noOfRooms > hotelDb.get(booking.getHotelName()).getAvailableRooms()) {
         return -1;
     }

     booking.setAmountToBePaid(pricePerRoom * noOfRooms);
     List<Booking> list = userBookingDb.getOrDefault(booking.getBookingAadharCard(), new ArrayList<>());
     list.add(booking);
     userBookingDb.put(booking.getBookingAadharCard(), list);
     bookingDb.put(String.valueOf(uuid), booking);

     return booking.getAmountToBePaid();
 }
    public int getBookings(Integer aadharCard) {
        List<Booking> bookings = userBookingDb.get(aadharCard);

        return bookings.size();
    }
    public Hotel update(List<Facility> newFacilities, String hotelName) {

        Hotel hotel = hotelDb.get(hotelName);

        List<Facility> facilityList = hotel.getFacilities();

        HashSet<Facility> facilities = new HashSet<>(facilityList);

        facilities.addAll(newFacilities);

        List<Facility> finalList = new ArrayList<>(facilities);

        hotel.setFacilities(finalList);

        hotelDb.put(hotelName, hotel);

        return hotel;

    }
}
