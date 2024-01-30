package org.mindera.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.mindera.util.exceptions.HotelDuplication;
import org.mindera.util.messages.Messages;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "Hotels", database = "my-mongodb")

public class Hotel extends PanacheMongoEntity {
    private String hotelN;
    private String location;
    private int phoneNumber;
    private Set<Rooms> rooms;

    public void saveWithUniqueCheck() throws HotelDuplication {
        if (isHotelNUnique()) {
            persist();
        } else {
            throw new HotelDuplication(Messages.DUPLICATEDHOTEL);
        }
    }

    private boolean isHotelNUnique() {
        return find("hotelN", this.hotelN).count() == 0;
    }

}
