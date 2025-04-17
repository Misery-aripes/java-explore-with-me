package ru.practicum.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.base.enums.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatusUpdateCommentInputDto {
    State state;
}
