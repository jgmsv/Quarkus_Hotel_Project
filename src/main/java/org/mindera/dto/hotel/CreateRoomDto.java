package org.mindera.dto.hotel;


import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.mindera.model.hotel.RoomType;


public record CreateRoomDto(
        @NotNull
        @Schema(description = "Room number", example = "101")
        int roomNumber,
        @NotNull
        @Schema(description = "Number of beds", example = "1")
        int numberOfBeds,
        @NotNull
        @Schema(description = "Room type", example = "SINGLEROOM")
        RoomType roomType,
        @NotNull
        @Schema(description = "Room price", example = "100")
        int roomPrice

) {


}
