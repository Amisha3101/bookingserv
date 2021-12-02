package com.paypal.bfs.test.bookingserv.dao;

import com.paypal.bfs.test.bookingserv.model.db.Booking;
import com.paypal.bfs.test.bookingserv.model.db.BookingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookingAddressRepository extends JpaRepository<BookingAddress, Long> {

    BookingAddress findByBookingId(Integer bookingId);
}
