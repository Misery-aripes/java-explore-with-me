package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.AdminCommentOutputDto;
import ru.practicum.comment.dto.AdminStatusUpdateCommentInputDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.base.enums.State;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AdminCommentOutputDto adminUpdateCommentStatus(Long commentId,
                                                          AdminStatusUpdateCommentInputDto adminStatusUpdateCommentInputDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден."));
        State state = adminStatusUpdateCommentInputDto.getState();
        if (state != null) {
            switch (state) {
                case PENDING -> comment.setState(State.PENDING);
                case PUBLISHED -> comment.setState(State.PUBLISHED);
                case CANCELED -> comment.setState(State.CANCELED);
                default -> throw new ValidationException("Статус не существует - " + state);
            }
        }
        commentRepository.save(comment);
        return CommentMapper.commentToAdminCommentOutputDto(comment, UserMapper.userToUserShortDto(comment.getAuthor()));
    }

    @Override
    public List<AdminCommentOutputDto> getAuthorComments(Long authorId, PageRequest pageRequest) {
        userRepository.findById(authorId).orElseThrow(()
                -> new NotFoundException("Пользователь с id \"" + authorId + "\" не найден"));
        List<Comment> comments = commentRepository.findByAuthorId(authorId, pageRequest);
        return comments.stream().map((Comment comment) -> CommentMapper.commentToAdminCommentOutputDto(comment,
                UserMapper.userToUserShortDto(comment.getAuthor()))).toList();
    }

    @Override
    @Transactional
    public void adminDeleteComment(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id: " + commentId + " не найден."));
        commentRepository.deleteById(commentId);
    }
}