package com.System.VaccineTracking.services;

import com.System.VaccineTracking.dtos.UserDto;
import com.System.VaccineTracking.enums.GenderEnum;
import com.System.VaccineTracking.exceptions.ErrorMessageCode;
import com.System.VaccineTracking.exceptions.VaccineTrackingAPIException;
import com.System.VaccineTracking.models.Users;
import com.System.VaccineTracking.repos.UserRepo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService extends BaseService{

    private final UserRepo userRepo;

    public UserDto getUserInfo(String nationalId) {
        Optional<Users> user = userRepo.findByNationalId(nationalId);
        return modelMapper.map(user, UserDto.class);
    }

    public void deleteUserInfo(String nationalId) {
        Users user = userRepo.findByNationalId(nationalId)
                             .orElseThrow(() -> new VaccineTrackingAPIException(
                                 ErrorMessageCode.RESOURCE_NOT_FOUND_ERROR,
                                 new String[]{"National ", "ID", nationalId}));
        user.setDeleted(true);
        userRepo.save(user);
    }

    public List<UserDto> getUserByNumberOfVaccineDoses(int dose) {
        return Arrays.asList(modelMapper.map(userRepo.findAllByDoses(dose), UserDto[].class));
    }
    public List<UserDto> getUsersByAge(int age) {
        return Arrays.asList(modelMapper.map(userRepo.findAllByAge(age), UserDto[].class));
    }
    public List<UserDto> getWaitingListUsers() {
        return Arrays.asList(
            modelMapper.map(userRepo.findAllByWaitingListTrue(), UserDto[].class));
    }

    public String getWaitingListUsersPercentage() {
        Float waitingListPercentage =
            userRepo.countAllByWaitingListTrue() / userRepo.count();
        return waitingListPercentage.toString() + " % ";
    }

    public String getOneVaccineDoseUsersPercentage() {
        Float waitingListPercentage = userRepo.countAllByDoses(1) / userRepo.count();
        return waitingListPercentage.toString() + " % ";
    }

    public String getTwoVaccineDoseUsersPercentage() {
        Float waitingListPercentage = userRepo.countAllByDoses(2) / userRepo.count();
        return waitingListPercentage.toString() + " % ";
    }
    public String getMalesUsersPercentage() {
        Float waitingListPercentage = userRepo.countAllByGender(GenderEnum.MALE.value) / userRepo.count();
        return waitingListPercentage.toString() + " % ";
    }
    public String getFemalesUsersPercentage() {
        Float waitingListPercentage = userRepo.countAllByGender(GenderEnum.FEMALE.value) / userRepo.count();
        return waitingListPercentage.toString() + " % ";
    }


}
