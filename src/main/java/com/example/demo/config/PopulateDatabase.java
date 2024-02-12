package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import com.example.demo.domain.entity.Customer;
import com.example.demo.domain.entity.CustomerStatus;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.entity.UserRoles;
import com.example.demo.domain.repository.CustomerRepository;
import com.example.demo.domain.repository.UserRepository;

@Profile("prod")
@Configuration
public class PopulateDatabase {

    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private static final Integer MAX_CUSTOMERS = 100;

    @Value("${security.admin.username}")
    private String securityAdminUsername;

    @Value("${security.admin.password}")
    private String securityAdminPassword;

    public PopulateDatabase(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            System.out.println("========== POPULATING DATA ==========");
            populateAdminUser();
            populateCustomers(MAX_CUSTOMERS);
        };
    }

    private void populateAdminUser() {
        if (!StringUtils.hasText(securityAdminUsername) || !StringUtils.hasText(securityAdminPassword))
            return;

        if (userRepository.findOneByUsername(securityAdminUsername).isPresent())
            return;

        var newAdmin = new User();
        newAdmin.setUsername(securityAdminUsername);
        newAdmin.setPassword(new BCryptPasswordEncoder().encode(securityAdminPassword));
        newAdmin.setRole(UserRoles.ADMIN.toString());
        userRepository.save(newAdmin);
        System.out.println("Admin user created: " + securityAdminUsername);
    }

    private void populateCustomers(int quantity) {
        if (customerRepository.findAll().size() == MAX_CUSTOMERS)
            return;

        List<Customer> customersToSave = new ArrayList<>();

        for (int i = 1; i <= quantity; i++) {
            var newCustomer = new Customer();
            newCustomer.setNumber(i);
            newCustomer.setStatus(CustomerStatus.AVAILABLE.toString());
            customersToSave.add(newCustomer);
        }

        customerRepository.saveAll(customersToSave);
        System.out.println(MAX_CUSTOMERS + "x customers created");
    }
}
