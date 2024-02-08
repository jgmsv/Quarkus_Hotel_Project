package org.mindera.controller;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.mindera.dto.hotel.CreateHotelDto;
import org.mindera.dto.hotel.HotelGetDto;
import org.mindera.service.hotel.HotelService;
import org.mindera.util.exceptions.hotel.HotelAdressException;
import org.mindera.util.exceptions.hotel.HotelDuplicationException;
import org.mindera.util.exceptions.hotel.HotelExistsException;
import org.mindera.util.exceptions.room.RoomExistsException;
import org.mindera.util.exceptions.room.RoomPriceException;

import java.util.List;

/**
 * The HotelController class handles HTTP requests related to hotels.
 */
@Path("api/v1/hotel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HotelController {

    @Inject
    HotelService hotelService;

    /**
     * Adds a new hotel.
     *
     * @param hotel the hotel to be added
     * @return the response with the added hotel
     * @throws HotelDuplicationException if the hotel already exists
     */
    @Operation(summary = "Add a new hotel")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Hotel added"),
            @APIResponse(responseCode = "400", description = "Hotel duplicated"),
    })
    @POST
    public Response add(@Valid CreateHotelDto hotel) throws HotelDuplicationException {
        return Response.ok(
                hotelService.addHotel(hotel)).status(Response.Status.CREATED).build();
    }

    /**
     * Updates the price of a room in a hotel.
     *
     * @param hotelN     the hotel name
     * @param roomNumber the room number
     * @param price      the new price
     * @return the response with the updated room price
     * @throws RoomExistsException  if the room does not exist
     * @throws HotelExistsException if the hotel does not exist
     * @throws RoomPriceException   if the price is invalid
     */
    @Operation(summary = "Update hotel price based on room number and hotel name")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Hotel price updated"),
            @APIResponse(responseCode = "400", description = "Hotel not found"),
            @APIResponse(responseCode = "400", description = "Room not found"),
            @APIResponse(responseCode = "400", description = "Price must be greater than price already set"),
    })
    @PUT
    @Path("/updatePrice/{hotelN}/{roomNumber}/{price}")
    public Response updatePrice(@PathParam("hotelN") String hotelN, @PathParam("roomNumber") int roomNumber, @PathParam("price") int price) throws RoomExistsException, HotelExistsException, RoomPriceException {
        return Response.ok(hotelService.updateRoomPrice(hotelN, roomNumber, price)).build();
    }

    /**
     * Retrieves all hotels.
     *
     * @param page the page number
     * @return the response with the list of hotels
     * @throws HotelExistsException if there are no hotels
     */
    @Operation(summary = "Find all hotels")
    @APIResponse(responseCode = "200", description = "Hotels found")
    @GET
    public Response finAll(@QueryParam("page") int page) {
        List<HotelGetDto> findAllHottel = hotelService.findAllHotels(page);
        return Response.ok(findAllHottel).build();
    }

    /**
     * Retrieves hotels by address.
     *
     * @param location the address location
     * @param page     the page number
     * @return the response with the list of hotels
     * @throws HotelAdressException if there are no hotels with the given address
     */
    @Operation(summary = "Find hotels by address")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Hotels found"),
            @APIResponse(responseCode = "400", description = "Address not found"),
    })
    @GET
    @Path("/findByAddress/{location}")
    public Response findHotelsByAddress(@PathParam("location") String location, @QueryParam("page") int page) throws HotelAdressException {
        return Response.ok(hotelService.findHotelsByAddress(location, page)).build();
    }

    /**
     * Retrieves a hotel by hotel name.
     *
     * @param hotelN the hotel name
     * @return the response with the hotel
     * @throws HotelExistsException if the hotel does not exist
     */
    @Operation(summary = "Find hotel by hotel name")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Hotel found"),
            @APIResponse(responseCode = "400", description = "Hotel not found"),
    })
    @GET
    @Path("/HotelName/{hotelN}")
    public Response findHotelByHotelN(@PathParam("hotelN") String hotelN) throws HotelExistsException {
        return Response.ok(hotelService.findHotelByHotelN(hotelN)).build();
    }

}


