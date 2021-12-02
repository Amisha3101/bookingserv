package com.paypal.bfs.test.bookingserv.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<com.paypal.bfs.test.bookingserv.model.db.Booking, Integer> {
}
