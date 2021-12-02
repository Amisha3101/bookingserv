package com.paypal.bfs.test.bookingserv.impl;

import com.paypal.bfs.test.bookingserv.api.model.Address;
import com.paypal.bfs.test.bookingserv.api.model.Booking;
import com.paypal.bfs.test.bookingserv.dao.AddressRepository;
import com.paypal.bfs.test.bookingserv.dao.BookingAddressRepository;
import com.paypal.bfs.test.bookingserv.dao.BookingRepository;
import com.paypal.bfs.test.bookingserv.model.db.BookingAddress;
import com.sun.javaws.exceptions.MissingFieldException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingResourceImplTest {

    @Autowired
    private BookingResourceImpl bookingResourceImpl;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private BookingAddressRepository bookingAddressRepository;

    @Test
    public void testCreateBooking() throws Exception {
        Mockito.doReturn(Optional.empty()).when(bookingRepository).findById(Mockito.any());
        ResponseEntity<Booking> createBookingResponse = bookingResourceImpl.create(getBooking());
        Mockito.verify(bookingRepository).save(Mockito.any());
        Mockito.verify(bookingAddressRepository).save(Mockito.any());
        assertTrue(200 == createBookingResponse.getStatusCodeValue());
        assertTrue(12345 == createBookingResponse.getBody().getId());
    }

    private Booking getBooking() {
        Address address = new Address();
        address.setLine1("Test line 1");
        address.setLine2("Test line 2");
        address.setCity("Test City");
        address.setState("Test State");
        address.setZipCode("226022");
        Booking booking = new Booking();
        booking.setAddress(address);
        booking.setId(12345);
        booking.setFirstName("Customer");
        booking.setLastName("Test");
        booking.setDateOfBirth("01/01/2001");
        booking.setCheckinDateTime("01/01/2005");
        booking.setCheckoutDateTime("05/01/2005");
        booking.setTotalPrice(1000);
        booking.setDeposit(200);
        return booking;
    }

    @Test
    public void testGetAllBookings() throws Exception {
        Mockito.doReturn(getAllBookings()).when(bookingRepository).findAll();
        Mockito.doReturn(getBookingAddress()).when(bookingAddressRepository).findByBookingId(Mockito.any());
        ResponseEntity<List<Booking>> getAllBookingsResponse = bookingResourceImpl.getAllBookings();
        assertTrue(200 == getAllBookingsResponse.getStatusCodeValue());
        assertTrue(2 == getAllBookingsResponse.getBody().size());
        assertTrue(12345 == getAllBookingsResponse.getBody().get(0).getId());
        assertTrue(12378 == getAllBookingsResponse.getBody().get(1).getId());
    }

    private List<com.paypal.bfs.test.bookingserv.model.db.Booking> getAllBookings() {
        List<com.paypal.bfs.test.bookingserv.model.db.Booking> bookingList = new ArrayList<>();
        bookingList.add(com.paypal.bfs.test.bookingserv.model.db.Booking.builder().id(12345).firstName("Customer").lastName("Test").dateOfBirth("01/01/2001")
                .checkinDateTime("01/01/2005").checkoutDateTime("05/01/2005").totalPrice(1000).deposit(500).build());
        bookingList.add(com.paypal.bfs.test.bookingserv.model.db.Booking.builder().id(12378).firstName("Cust").lastName("Test").dateOfBirth("01/01/2010")
                .checkinDateTime("01/01/2015").checkoutDateTime("05/01/2015").totalPrice(5000).deposit(1000).build());
        return bookingList;
    }

    private BookingAddress getBookingAddress() {
        com.paypal.bfs.test.bookingserv.model.db.Address address = com.paypal.bfs.test.bookingserv.model.db.Address.builder().line1("Test line 1").line2("Test line 2")
                .city("Test City").state("Test State").zipCode("226022").build();
        return BookingAddress.builder().address(address).build();
    }

}
