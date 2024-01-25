package org.mindera.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.mindera.repository.HotelRepository;
import org.mindera.service.HotelService;


public class HotelController {

    @Inject
    HotelService hotelService;


}
