package com.paypal.bfs.test.bookingserv.model.db;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @Column(name = "id")
    @JsonProperty(value = "id")
    private Integer id;

    @Column(name = "first_name")
    @JsonProperty(value = "first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty(value = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    @JsonProperty(value = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "checkin_date_time")
    @JsonProperty(value = "checkin_date_time")
    private String checkinDateTime;

    @Column(name = "checkout_date_time")
    @JsonProperty(value = "checkout_date_time")
    private String checkoutDateTime;

    @Column(name = "total_price")
    @JsonProperty(value = "total_price")
    private Integer totalPrice;

    @Column(name = "deposit")
    @JsonProperty(value = "deposit")
    private Integer deposit;

}
