/**
 * 
 */
package com.resturantservice.services;

import org.springframework.stereotype.Service;

import com.resturantservice.ErrorCode;
import com.resturantservice.entities.MenuItem;
import com.resturantservice.entities.Orders;
import com.resturantservice.entities.OrderStatus;
import com.resturantservice.entities.Restaurant;
import com.resturantservice.entities.RestaurantOwner;
import com.resturantservice.entities.User;
import com.resturantservice.repos.MenuItemRepository;
import com.resturantservice.repos.OrderRepository;
import com.resturantservice.repos.RestaurantOwnerRepository;
import com.resturantservice.repos.RestaurantRepository;
import com.resturantservice.repos.UserRepository;

import java.util.List;

@Service
public class RestaurantOwnerService {
    private final RestaurantOwnerRepository ownerRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantOwnerService(RestaurantOwnerRepository ownerRepository,
                                  MenuItemRepository menuItemRepository,
                                  OrderRepository orderRepository,RestaurantRepository restaurantRepository,UserRepository userRepository) {
        this.ownerRepository = ownerRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public RestaurantOwner register(RestaurantOwner owner) {
        return ownerRepository.save(owner);
    }

    public RestaurantOwner getRestaurantOwner(Long id) {
    	return ownerRepository.findById(id).get();
    }
    public RestaurantOwner login(String email, String password) {
    	User user = userRepository.findByEmail(email);
         if (user != null && user.getPassword().equals(password)) {
        	RestaurantOwner owner = ownerRepository.findByEmail(email);
            return owner;
        }
        throw new IllegalArgumentException(ErrorCode.FORBIDDEN);
    }

    public List<MenuItem> manageMenu(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public MenuItem addMenuItem(MenuItem menuItem, Long restaurantId) {
        Restaurant restaurant = ownerRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"))
                .getRestaurant();
        menuItem.setRestaurant(restaurant);
        return menuItemRepository.save(menuItem);
    }

    public void removeMenuItem(Long menuItemId) {
        menuItemRepository.deleteById(menuItemId);
    }

    public List<Orders> viewOrders(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Restaurant updateRestaurantDetails(Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(updatedRestaurant.getId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setAddress(updatedRestaurant.getAddress());
        //existingRestaurant.setHoursOfOperation(updatedRestaurant.getHoursOfOperation());

        return restaurantRepository.save(existingRestaurant);
    }
}

