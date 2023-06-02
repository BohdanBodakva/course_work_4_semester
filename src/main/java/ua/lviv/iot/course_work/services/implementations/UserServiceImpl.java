package ua.lviv.iot.course_work.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.entities.enums.UserRole;
import ua.lviv.iot.course_work.entities.enums.UserStatus;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;
import ua.lviv.iot.course_work.exceptions.UsernameAlreadyExistsException;
import ua.lviv.iot.course_work.repositories.DeviceRepository;
import ua.lviv.iot.course_work.repositories.UserRepository;
import ua.lviv.iot.course_work.services.UserService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username = " + username + " doesn't exist"));
    }

    @Override
    public UserEntity getUserByUsernameIfActive(String username) throws UserNotFoundException {
        return userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public UserEntity updateUserByUsernameIfActive(String username, UserEntity user) throws UserNotFoundException, UsernameAlreadyExistsException {
        UserEntity userToUpdate = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with email = " + username + " doesn't exist"));

        if(!Objects.equals(user.getUsername(), "")){
            if(!userRepository.findAll().stream()
                    .filter(u -> u.getUsername().equals(user.getUsername()))
                    .toList().isEmpty()){
                throw new UsernameAlreadyExistsException("Username = " + user.getUsername() + " already exists");
            } else {
                userToUpdate.setUsername(user.getUsername());
            }
        }

        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());

        if(!Objects.equals(user.getPassword(), "")){
            userToUpdate.setPassword(
                    new BCryptPasswordEncoder(12).encode(user.getPassword())
            );
        }

        return userRepository.save(userToUpdate);
    }

    @Override
    public void banUserByUsername(String username) throws UserNotFoundException {
        UserEntity user =  userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        user.setStatus(UserStatus.BANNED);
        userRepository.save(user);
    }

    @Override
    public void makeUserActiveByUsername(String username) throws UserNotFoundException {
        UserEntity user =  userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public void deleteUserByUsername(String username) throws UserNotFoundException {
        UserEntity user =  userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        userRepository.deleteById(user.getUsername());
    }

    @Override
    public List<DeviceEntity> getAllUserDevicesByUsername(String username) throws UserNotFoundException {
        UserEntity user =  userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        return deviceRepository.findDeviceEntitiesByUserUsername(username);
    }
}
