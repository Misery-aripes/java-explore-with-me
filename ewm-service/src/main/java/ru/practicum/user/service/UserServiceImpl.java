package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto addUser(NewUserRequest newUserRequest) {
        User user = UserMapper.newUserRequestToUser(newUserRequest);
        User newUser = userRepository.save(user);
        return UserMapper.userToUserDto(newUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id " + userId + " не найден"));
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUsers(List<Long> ids, PageRequest pageRequest) {
        List<User> users;
        if (ids.isEmpty()) {
            users = userRepository.findAll(pageRequest).stream().collect(Collectors.toList());
        } else {
            users = userRepository.findAllByIdIn(ids, pageRequest);
        }
        return users.stream().map(UserMapper::userToUserDto).collect(Collectors.toList());
    }
}
