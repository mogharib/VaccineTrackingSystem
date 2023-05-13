package com.System.VaccineTracking.repos;

import com.System.VaccineTracking.models.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Optional<Users> findByNationalId(String nationalId);

    boolean existsByNationalId(String nationalId);

    void deleteByNationalId(String nationalId);

    List<Users> findAllByDoses(int dose);

    Float countAllByWaitingListTrue();

    Float countAllByDoses(int i);

    List<Users> findAllByWaitingListTrue();

    Float countAllByGender(String gender);

    List<Users> findAllByAge(int age);
}
