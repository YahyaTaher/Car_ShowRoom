package com.carshowroom.mycar_showroom.service;

// Stub service - extend with @Service, repositories
import com.carshowroom.mycar_showroom.entity.Customer;
import com.carshowroom.mycar_showroom.entity.User;
import com.carshowroom.mycar_showroom.entity.Role;
import com.carshowroom.mycar_showroom.repository.CustomerRepository;
import com.carshowroom.mycar_showroom.repository.UserRepository;
import com.carshowroom.mycar_showroom.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Role getRoleByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        return role.orElse(null);
    }

    // Add more methods for business logic
}
