package ua.lviv.iot.course_work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    @Query(nativeQuery = true, value = "select u.* from user_entity u where u.username=:username and u.status='ACTIVE';")
    Optional<UserEntity> findUserEntityByUsernameAndStatusIsActive(@Param("username") String username);



}
