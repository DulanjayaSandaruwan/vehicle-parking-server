package com.project.vehicle_parking.service.user;

import com.project.vehicle_parking.commons.Check;
import com.project.vehicle_parking.commons.Hash;
import com.project.vehicle_parking.commons.exceptions.http.BadRequestException;
import com.project.vehicle_parking.commons.exceptions.http.UnauthorizeException;
import com.project.vehicle_parking.commons.exceptions.http.UserNotFoundException;
import com.project.vehicle_parking.commons.exceptions.user.UserExType;
import com.project.vehicle_parking.dto.user.UserDTO;
import com.project.vehicle_parking.dto.user.UserSearchParamDTO;
import com.project.vehicle_parking.dto.user.UserUpdateDTO;
import com.project.vehicle_parking.model.user.User;
import com.project.vehicle_parking.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(UserDTO dto) {
        log.info("create user email {}", dto.getEmail());
        User user = User.init(dto);
        if (userRepository.findByEmailAndStatusNot(dto.getEmail() , User.UserStatus.DELETED).isPresent()) {
            throw new BadRequestException("email already exist", UserExType.EMAIL_ALREADY_EXIST);
        }
        user.setPassword(Hash.make(dto.getPassword()));
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        } else {
            user.setRole(User.UserRole.CUSTOMER);
        }
        user = userRepository.save(user);
        return user;
    }

    public User login(UserDTO userDto) {
        log.info("login user email {}", userDto.getEmail());
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isEmpty()) {
            throw new UnauthorizeException("Invalid Credentials");
        }
        if (!Hash.match(userDto.getPassword(), user.get().getPassword())) {
            throw new UnauthorizeException("Invalid Credentials");
        }
        return user.get();
    }

    public Optional<User> findUserById(String userId) {
        Optional<User> result = this.userRepository.findById(userId);
        log.debug("get user by id = {}, is available = {}", userId, result.isPresent());
        return result;
    }

    public User getUserById(String userId) {
        log.info("Get user by id = {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        Check.throwIfEmpty(userOptional, new UserNotFoundException("User not found with Id : " + userId));
        User user = userOptional.get();
        return user;
    }

    public User updateUser(UserUpdateDTO updateDTO, String userId) {
        log.info("updated user id {}", userId);
        User user = this.getUserById(userId);
        if (userRepository.findByEmailAndStatusNot(updateDTO.getEmail() , User.UserStatus.DELETED).isPresent()) {
            throw new BadRequestException("email already exist", UserExType.EMAIL_ALREADY_EXIST);
        }
        user.setFullName(updateDTO.getFullName());
        user.setEmail(updateDTO.getEmail());
        user = userRepository.save(user);
        return user;
    }

    public User deleteUser(String userId) {
        log.info("delete. user id={}", userId);
        User user = getUserById(userId);
        if (user.getStatus() == User.UserStatus.DELETED) {
            throw new BadRequestException(userId + " is already deleted");
        } else {
            user.setStatus(User.UserStatus.DELETED);
        }
        return userRepository.save(user);
    }

    public Page<User> getAllCustomers(UserSearchParamDTO searchParams) {
        log.info("get all users");
        Pageable pageable = PageRequest.of(searchParams.getPage(), searchParams.getSize());
        return userRepository.findAllUsers(
                searchParams.getName(),
                searchParams.getEmail(),
                searchParams.getStatus(),
                User.UserRole.CUSTOMER,
                pageable
        );
    }
}
