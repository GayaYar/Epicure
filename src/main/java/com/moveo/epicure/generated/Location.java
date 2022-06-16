package com.moveo.epicure.generated;

import lombok.Data;

@Data
public class Location {
    private String city;

    private String street;

    private Timezone timezone;

    private String postcode;

    private Coordinates coordinates;

    private String state;
}
