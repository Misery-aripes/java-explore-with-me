package ru.practicum.events.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountDto {
    private Long eventId;
    private Long count;
}
