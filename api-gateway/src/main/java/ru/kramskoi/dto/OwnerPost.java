package ru.kramskoi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OwnerPost {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("birthday")
    private Date birthday;
}
