package ru.practicum.comment.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.model.Comment;
import ru.practicum.base.enums.State;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAuthorId(Long userId, PageRequest pageRequest);

    List<Comment> findAllByEventIdAndState(Long eventId, State state, PageRequest pageRequest);
}

