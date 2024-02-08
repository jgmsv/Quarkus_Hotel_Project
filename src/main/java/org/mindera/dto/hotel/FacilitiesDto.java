package org.mindera.dto.hotel;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record FacilitiesDto(
        @Schema(description = "Facilities", example = "BAR")
        String facilities
) {


}
