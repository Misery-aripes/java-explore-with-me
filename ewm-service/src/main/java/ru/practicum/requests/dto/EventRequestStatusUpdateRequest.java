package ru.practicum.requests.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.practicum.requests.model.Status;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    private List<Long> requestIds;
    private Status status;
}
