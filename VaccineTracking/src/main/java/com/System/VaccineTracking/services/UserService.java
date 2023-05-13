package com.System.VaccineTracking.services;

import com.System.VaccineTracking.dtos.UserDto;
import com.System.VaccineTracking.models.Users;
import com.System.VaccineTracking.repos.UserRepo;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {this.userRepo = userRepo;}

    public UserDto getUserInfo(String nationalId) {
        Users user = userRepo.findByNationalId(nationalId)
                             .orElseThrow(() -> new RuntimeException("not found" + nationalId));
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto addUserInfo(UserDto userDto, String nationalId) {
        Users user = userRepo.findByNationalId(nationalId)
                             .orElseThrow(() -> new RuntimeException("not found" + nationalId));
        user.setFullName(userDto.getFullName());
        user.setGender(userDto.getGender());
        user.setAge(userDto.getAge());
        user.setDoses(userDto.getDoses());
        user.setGovernorate(userDto.getGovernorate());
        if (user.getDoses() == 0) {
            user.setWaitingList(true);
        }
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    public void deleteUserInfo(String nationalId) {
        Users user = userRepo.findByNationalId(nationalId)
                             .orElseThrow(() -> new RuntimeException("not found" + nationalId));
        user.setDeleted(true);
        userRepo.save(user);
    }

    public UserDto updateUserInfo(UserDto userDto, String nationalId) {
        Users user = userRepo.findByNationalId(nationalId)
                             .orElseThrow(() -> new RuntimeException("not found" + nationalId));
        Optional.ofNullable(userDto.getFullName()).ifPresent(user::setFullName);
        Optional.ofNullable(userDto.getAge()).ifPresent(user::setAge);
        Optional.ofNullable(userDto.getGovernorate()).ifPresent(user::setGovernorate);
        Optional.ofNullable(userDto.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(userDto.getDoses()).ifPresent(user::setDoses);
        Optional.ofNullable(userDto.getNationalId()).ifPresent(user::setNationalId);
        if (userDto.getDoses() == 0) {
            user.setWaitingList(true);
        }
        user.setWaitingList(false);
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }
}
