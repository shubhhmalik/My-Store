package com.project.my_store.repository;

import com.project.my_store.model.Order;
import com.project.my_store.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RoleRepositiory extends JpaRepository<Role, Long> {
    Collection<Object> findByName(String role);

}
