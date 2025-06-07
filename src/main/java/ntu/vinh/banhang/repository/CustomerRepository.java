package ntu.vinh.banhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ntu.vinh.banhang.entity.Customer;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhone(String phone);
} 