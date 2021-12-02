package com.paypal.bfs.test.bookingserv.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.bookingserv.api.BookingResource;
import com.paypal.bfs.test.bookingserv.api.model.Address;
import com.paypal.bfs.test.bookingserv.api.model.Booking;
import com.paypal.bfs.test.bookingserv.dao.AddressRepository;
import com.paypal.bfs.test.bookingserv.dao.BookingAddressRepository;
import com.paypal.bfs.test.bookingserv.dao.BookingRepository;
import com.paypal.bfs.test.bookingserv.model.db.BookingAddress;
import com.sun.javaws.exceptions.MissingFieldException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class BookingResourceImpl implements BookingResource {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BookingAddressRepository bookingAddressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResponseEntity<Booking> create(Booking booking) throws Exception {
        validation(booking);
        try{
            if(bookingRepository.findById(booking.getId()).isPresent())
                return new ResponseEntity<>(booking, HttpStatus.OK);
            bookingRepository.save(objectMapper.convertValue(booking, com.paypal.bfs.test.bookingserv.model.db.Booking.class));
            com.paypal.bfs.test.bookingserv.model.db.BookingAddress bookingAddress = new BookingAddress();
            bookingAddress.setBooking(objectMapper.convertValue(booking, com.paypal.bfs.test.bookingserv.model.db.Booking.class));
            bookingAddress.setAddress(objectMapper.convertValue(booking.getAddress(), com.paypal.bfs.test.bookingserv.model.db.Address.class));
            bookingAddressRepository.save(bookingAddress);
        } catch (Exception ex)
        {
            log.error("Exception occurred while creating booking entry in DB. exception : {}", ex.getMessage());
            throw new Exception(ex.getMessage(), ex);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Booking>> getAllBookings() throws Exception {
        List<com.paypal.bfs.test.bookingserv.model.db.Booking> bookings = null;
        try{
            bookings = bookingRepository.findAll();
        }catch(Exception ex)
        {
            log.error("Exception occurred while fetching bookings from DB. exception : {}", ex.getMessage());
            throw new Exception(ex.getMessage(), ex);
        }
        List<Booking> bookingList = new ArrayList<>();
        for(com.paypal.bfs.test.bookingserv.model.db.Booking bookingDO : bookings)
        {
            BookingAddress bookingAddress = null;
            try{
                bookingAddress = bookingAddressRepository.findByBookingId(bookingDO.getId());
            }catch(Exception ex)
            {
                log.error("Exception occurred while fetching booking address from DB for booking id : {} , exception : {}", bookingDO.getId(), ex.getMessage());
                throw new Exception(ex.getMessage(), ex);
            }
            Booking booking = new Booking();
            booking.setAddress(objectMapper.convertValue(bookingAddress.getAddress(), Address.class));
            booking.setId(bookingDO.getId());
            booking.setFirstName(bookingDO.getFirstName());
            booking.setLastName(bookingDO.getLastName());
            booking.setDateOfBirth(bookingDO.getDateOfBirth());
            booking.setCheckinDateTime(bookingDO.getCheckinDateTime());
            booking.setCheckoutDateTime(bookingDO.getCheckoutDateTime());
            booking.setTotalPrice(bookingDO.getTotalPrice());
            booking.setDeposit(bookingDO.getDeposit());
            bookingList.add(booking);
        }
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    private void validation(Booking booking) {
        required(booking.getId(), "Booking Id");
        required(booking.getFirstName(), "First Name");
        required(booking.getLastName(), "Last Name");
    }

    public void required(Object entity, final String entityName) {
        if (entity == null) {
            throw new IllegalArgumentException(StringUtils.join("Missing " , entityName));
        }
    }
}
