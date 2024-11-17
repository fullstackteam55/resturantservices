/**
 * 
 */
package com.resturantservice.services;

import org.springframework.stereotype.Service;

import com.resturantservice.entities.DeliveryPersonnel;
import com.resturantservice.entities.Orders;
import com.resturantservice.entities.OrderStatus;
import com.resturantservice.repos.DeliveryPersonnelRepository;
import com.resturantservice.repos.OrderRepository;

import java.util.List;

@Service
public class DeliveryPersonnelService {
    private final DeliveryPersonnelRepository personnelRepository;
    private final OrderRepository orderRepository;

    public DeliveryPersonnelService(DeliveryPersonnelRepository personnelRepository,
                                    OrderRepository orderRepository) {
        this.personnelRepository = personnelRepository;
        this.orderRepository = orderRepository;
    }

    // Register
    public DeliveryPersonnel register(DeliveryPersonnel personnel) {
        return personnelRepository.save(personnel);
    }

    // Login
    public DeliveryPersonnel login(String email,String password) {
        DeliveryPersonnel personnel = personnelRepository.findByEmail(email);
        if (personnel == null) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return personnel;
    }

    // View Available Deliveries
    public List<Orders> viewAvailableDeliveries() {
        return orderRepository.findByStatus("available");
    }

    // Accept Order
    public Orders acceptOrder(Long orderId, Long personnelId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!OrderStatus.AVAILABLE.equals(order.getStatus())) {
            throw new IllegalStateException("Order is no longer available for delivery");
        }

        DeliveryPersonnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery personnel not found"));

        order.setDeliverypersonnel(personnel);
        order.setStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }

    // Track Delivery Status
    public Orders updateDeliveryStatus(Long orderId, OrderStatus status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }

    // Manage Availability
    public DeliveryPersonnel updateAvailability(Long personnelId, boolean available) {
        DeliveryPersonnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery personnel not found"));

        personnel.setAvailable(available);
        return personnelRepository.save(personnel);
    }
}

