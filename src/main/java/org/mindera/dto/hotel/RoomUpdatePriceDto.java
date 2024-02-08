package org.mindera.dto.hotel;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record RoomUpdatePriceDto(
        @Schema(description = "Room price", example = "100")
        int roomPrice
) {
}
