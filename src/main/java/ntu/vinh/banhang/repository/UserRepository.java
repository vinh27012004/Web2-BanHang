package ntu.vinh.banhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ntu.vinh.banhang.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
} 