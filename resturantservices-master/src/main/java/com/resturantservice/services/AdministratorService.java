/**
 * 
 */
package com.resturantservice.services;

import org.springframework.stereotype.Service;

import com.resturantservice.ErrorCode;
import com.resturantservice.entities.Orders;
import com.resturantservice.entities.OrderStatus;
import com.resturantservice.entities.User;
import com.resturantservice.repos.OrderRepository;
import com.resturantservice.repos.UserRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdministratorService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AdministratorService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    // Manage Users
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userid, User updatedUser) {
    	User user = userRepository.findById(userid)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND));
        user.setPassword(updatedUser.getPassword());
        user.setPhonenumber(updatedUser.getPhonenumber());

        user.setActive(updatedUser.isActive());

        return userRepository.save(user);
    }
    
    public User getUser(Long userid) {
    	User user = userRepository.findById(userid)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND));
    	return user;

    }
    
    public List<User> getUsers() {
    	List<User> users = userRepository.findAll();
    	if(users.isEmpty()) {
    		throw new IllegalArgumentException(ErrorCode.NOT_FOUND);
    	}
    	return users;

    }

    public User deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND));
        user.setActive(false);
        userRepository.save(user);
        return user;
    }

    // View Orders
    public List<Orders> viewAllOrders() {
        return orderRepository.findAll();
    }

    // Manage Orders
    public Orders cancelOrder(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND));
        OrderStatus currentOrderStatus = order.getStatus();
        if(OrderStatus.PENDING.equals(currentOrderStatus)) {
        	order.setStatus(OrderStatus.CANCELLED);
        }
        return orderRepository.save(order);
    }

    // Generate Reports
    public List<Map<String,Object>> getMostPopularRestaurants() {
    	List<Map<String,Object>> popularMapList = new ArrayList<>();
    	List<Object[]> popularResturants =  orderRepository.findMostPopularRestaurants();
    	if(null!=popularResturants && !popularResturants.isEmpty()) {
    		for(Object[] popularResturant:popularResturants) {
    			Map<String,Object> popularMap = new LinkedHashMap<>();
    			popularMap.put("name",String.valueOf(popularResturant[0]));
    			popularMap.put("rank",String.valueOf(popularResturant[1]));
    			popularMapList.add(popularMap);
    		}
    	}
    	return popularMapList;
    }

//    public Double getAverageDeliveryTime() {
//        return orderRepository.findAverageDeliveryTime();
//    }

    public List<Map<String,Object>> getOrderStatusCounts() {
    	List<Map<String,Object>> statusCountMapList = new ArrayList<>();
    	List<Object[]> statusCounts =  orderRepository.findOrderStatusCounts();
    	if(null!=statusCounts && !statusCounts.isEmpty()) {
    		for(Object[] popularResturant:statusCounts) {
    			Map<String,Object> statusCountMap = new LinkedHashMap<>();
    			statusCountMap.put("status",String.valueOf(popularResturant[0]));
    			statusCountMap.put("count",String.valueOf(popularResturant[1]));
    			statusCountMapList.add(statusCountMap);
    		}
    	}
    	return statusCountMapList;
    }
}

