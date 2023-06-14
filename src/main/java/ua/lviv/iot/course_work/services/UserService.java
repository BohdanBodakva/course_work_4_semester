package ua.lviv.iot.course_work.services;

import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;
import ua.lviv.iot.course_work.exceptions.UsernameAlreadyExistsException;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();
    UserEntity getUserByUsername(String username) throws UserNotFoundException;
    UserEntity getUserByUsernameIfActive(String username) throws UserNotFoundException;
    UserEntity saveUser(UserEntity user);
    UserEntity updateUserByUsernameIfActive(String username, UserEntity user) throws UserNotFoundException, UsernameAlreadyExistsException;
    void banUserByUsername(String username) throws UserNotFoundException;
    void makeUserActiveByUsername(String username) throws UserNotFoundException;
    void deleteUserByUsername(String username) throws UserNotFoundException;

    List<DeviceEntity> getAllUserDevicesByUsername(String username) throws UserNotFoundException;
}
