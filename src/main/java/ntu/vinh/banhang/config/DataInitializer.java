package ntu.vinh.banhang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ntu.vinh.banhang.entity.Role;
import ntu.vinh.banhang.entity.User;
import ntu.vinh.banhang.repository.RoleRepository;
import ntu.vinh.banhang.repository.UserRepository;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create roles if they don't exist
        Role adminRole = roleRepository.findByName("ADMIN")
            .orElseGet(() -> {
                Role role = new Role();
                role.setName("ADMIN");
                return roleRepository.save(role);
            });

        Role staffRole = roleRepository.findByName("STAFF")
            .orElseGet(() -> {
                Role role = new Role();
                role.setName("STAFF");
                return roleRepository.save(role);
            });

        // Create admin user if not exists
        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Administrator");
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }

        // Create staff user if not exists
        if (!userRepository.findByUsername("staff").isPresent()) {
            User staff = new User();
            staff.setUsername("staff");
            staff.setPassword(passwordEncoder.encode("staff123"));
            staff.setFullName("Staff User");
            staff.setRoles(Set.of(staffRole));
            userRepository.save(staff);
        }
    }
}