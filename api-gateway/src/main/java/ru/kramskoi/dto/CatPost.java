package ru.kramskoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.sql.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CatPost {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("birthday")
    private Date birthday;

    @JsonProperty("breed")
    private Breed breed;

    @JsonProperty("color")
    private Color color;

    @JsonProperty("ownerID")
    private Long ownerID;
}
