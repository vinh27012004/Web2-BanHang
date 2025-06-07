package ntu.vinh.banhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ntu.vinh.banhang.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
} 