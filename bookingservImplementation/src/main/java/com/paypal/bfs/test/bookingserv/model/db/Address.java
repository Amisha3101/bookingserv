package com.paypal.bfs.test.bookingserv.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Address")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "line_1")
    @JsonProperty(value = "line_1")
    private String line1;

    @Column(name = "line_2")
    @JsonProperty(value = "line_2")
    private String line2;

    @Column(name = "city")
    @JsonProperty(value = "city")
    private String city;

    @Column(name = "state")
    @JsonProperty(value = "state")
    private String state;

    @Column(name = "zip_code")
    @JsonProperty(value = "zip_code")
    private String zipCode;
}
