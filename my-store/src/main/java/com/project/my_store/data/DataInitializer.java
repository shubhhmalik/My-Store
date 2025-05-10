package com.project.my_store.data;


import com.project.my_store.model.Role;
import com.project.my_store.model.User;
import com.project.my_store.repository.RoleRepository;
import com.project.my_store.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepositiory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRolesIfNotExist(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepositiory.findByName("ROLE_USER").get();
        for (int i=1; i<=5; i++){
            String defaultEmail = "user" +i+ "@email.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" +i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default user is " +i+ " successfully created.");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepositiory.findByName("ROLE_ADMIN").get();
        for (int i=1; i<=2; i++){
            String defaultEmail = "admin" +i+ "@email.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The Admin");
            user.setLastName("Admin" +i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin is " +i+ " successfully created.");
        }
    }


//    @Override
//    public boolean supportsAsyncExecution() {
//        return ApplicationListener.super.supportsAsyncExecution();
//    }

    private void createDefaultRolesIfNotExist(Set<String> roles) {
        for (String i : roles) {
            if (roleRepositiory.findByName(i).isEmpty()) {
                roleRepositiory.save(new Role(i));
            }
        }
    }

}
