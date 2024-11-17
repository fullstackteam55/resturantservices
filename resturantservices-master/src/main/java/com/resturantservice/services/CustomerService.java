/**
 * 
 */
package com.resturantservice.services;

/**
 * 
 */
import org.springframework.stereotype.Service;

import com.resturantservice.entities.Customer;
import com.resturantservice.entities.MenuItem;
import com.resturantservice.entities.Orders;
import com.resturantservice.entities.Restaurant;
import com.resturantservice.entities.User;
import com.resturantservice.repos.CustomerRepository;
import com.resturantservice.repos.MenuItemRepository;
import com.resturantservice.repos.OrderRepository;
import com.resturantservice.repos.RestaurantRepository;
import com.resturantservice.repos.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, RestaurantRepository restaurantRepository,
                           MenuItemRepository menuItemRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // Register/Login
    public Customer register(Customer customer) {
        return customerRepository.save(customer);
    }
    
    public Optional<Customer> getCustomer(Long id) {
    	return customerRepository.findById(id);
    }

    public Customer login(String email, String password) {
    	User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
        	Customer customer = customerRepository.findByEmail(email);
            return customer;
        }
        throw new RuntimeException("Invalid Credentials.");
    }

    // Browse Restaurants
    public List<Restaurant> browseRestaurants() {
        return restaurantRepository.findAll();
    }

    // Search Menus
    public List<MenuItem> searchMenu(String keyword) {
        return menuItemRepository.findAll().stream()
                .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    }

    // Place an Order
    public Orders placeOrder(Orders order) {
        return orderRepository.save(order);
    }

    // Track Orders
    public List<Orders> trackOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // View Order History
    public List<Orders> getOrderHistory(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}

