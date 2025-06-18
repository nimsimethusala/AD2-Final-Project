package lk.ijse.userservice.repo;

import lk.ijse.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);
}
