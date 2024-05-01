package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserFields(Long id, User userUpdates) {
        return userRepository.findById(id).map(existingUser -> {
            if (userUpdates.getFirstName() != null) existingUser.setFirstName(userUpdates.getFirstName());
            if (userUpdates.getLastName() != null) existingUser.setLastName(userUpdates.getLastName());
            if (userUpdates.getEmail() != null) existingUser.setEmail(userUpdates.getEmail());
            if (userUpdates.getBirthDate() != null) existingUser.setBirthDate(userUpdates.getBirthDate());
            if (userUpdates.getAddress() != null) existingUser.setAddress(userUpdates.getAddress());
            if (userUpdates.getPhoneNumber() != null) existingUser.setPhoneNumber(userUpdates.getPhoneNumber());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User newUserDetails) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(newUserDetails.getFirstName());
            existingUser.setLastName(newUserDetails.getLastName());
            existingUser.setEmail(newUserDetails.getEmail());
            existingUser.setBirthDate(newUserDetails.getBirthDate());
            existingUser.setAddress(newUserDetails.getAddress());
            existingUser.setPhoneNumber(newUserDetails.getPhoneNumber());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findUsersByBirthDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        return userRepository.findByBirthDateBetween(startDate, endDate);
    }
}
