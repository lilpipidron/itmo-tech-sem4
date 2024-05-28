package ru.kramskoi.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@Jacksonized
public class FriendMessage {
    private Long catID;
    private Long friendID;
}
