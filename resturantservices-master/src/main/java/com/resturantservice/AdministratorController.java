/**
 * 
 */
package com.resturantservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.resturantservice.entities.Orders;
import com.resturantservice.entities.User;
import com.resturantservice.services.AdministratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Administrator API", description = "Endpoints for managing artifacts and Administrator Functionalities")
public class AdministratorController extends ResponseController{
	
	@Autowired
    private AdministratorService adminService;
	
    @GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Users", description = "Retrieve User and Details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the users details."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> getUsers() {
    	try {
          List<User> users = adminService.getUsers();
          return ResponseEntity.ok(users);
    	}
    	catch(IllegalArgumentException ex) {
    	     return handleError(ex);
    	}
    	
    }

    // Manage Users
    @PostMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create User", description = "Create a new User.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created the user."),
        @ApiResponse(responseCode = "400", description = "Invalid User Data."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> createUser(@RequestBody User userAccount) {
     	try {
           User user = adminService.createUser(userAccount);
           return ResponseEntity.ok(user);
     	}
     	catch(IllegalArgumentException ex) {
     		return handleError(ex);
     	}
    }

    @PutMapping(value="/users/{userid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update User", description = "Update existing User Profile.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the user."),
        @ApiResponse(responseCode = "400", description = "Invalid User Data."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> updateUser(@PathVariable Long userid, @RequestBody User updatedUser) {
     	try {
     		User user = adminService.updateUser(userid, updatedUser);
         	return ResponseEntity.ok(user);
     	}
     	catch(IllegalArgumentException ex) {
     		return handleError(ex);
     	}

    }
    
    @GetMapping(value="/users/{userid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get User by userid", description = "Get User by userid.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user."),
        @ApiResponse(responseCode = "404", description = "User Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> getUser(@PathVariable Long userid) {
    	try {
          User user = adminService.getUser(userid);
          return ResponseEntity.ok(user);
    	}
    	catch(IllegalArgumentException ex) {
    	     return handleError(ex);
    	}
    	
    }

    @PatchMapping(value="/users/{userId}/deactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deactivate User Profile", description = "Deactivate User Profile by userid")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deactivated the user."),
        @ApiResponse(responseCode = "404", description = "User Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> deactivateUser(@PathVariable Long userId) {
    	try {
           User user = adminService.deactivateUser(userId);
           return ResponseEntity.ok(user);
     	}
     	catch(IllegalArgumentException ex) {
     		return handleError(ex);
     	}
    }

    // View and Manage Orders
    @GetMapping(value="/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "View and Manage Orders", description = "View and Manage Orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the orders."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> viewAllOrders() {
        try {
        	List<Orders> orders = adminService.viewAllOrders();
        	return ResponseEntity.ok(orders);
      	}
      	catch(IllegalArgumentException ex) {
      		return handleError(ex);
      	}
      	
    }

    @PatchMapping(value="/orders/{orderId}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cancel Order by orderId", description = "Cancel Order by orderId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deactivated the user."),
        @ApiResponse(responseCode = "404", description = "Order Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> cancelOrder(@PathVariable Long orderId) {
        try {
        	Orders order = adminService.cancelOrder(orderId);
          	return ResponseEntity.ok(order);
        }
      	catch(IllegalArgumentException ex) {
      		return handleError(ex);
      	}

    }

    // Generate Reports
    @GetMapping(value="/reports/popularRestaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate Reports of Popular Restaurants", description = "Generate Reports of Popular Restaurants")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated reports of popular restaurants."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> getMostPopularRestaurants() {
    	try {
    		List<Map<String,Object>> popularResturants = adminService.getMostPopularRestaurants();
	        return ResponseEntity.ok(popularResturants);
    	}
      	catch(IllegalArgumentException ex) {
      		return handleError(ex);
      	}
    }

//    @GetMapping("/reports/averageDeliveryTime")
//    public Double getAverageDeliveryTime() {
//        return adminService.getAverageDeliveryTime();
//    }

    @GetMapping(value="/reports/orderStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate Reports of Order status count", description = "Generate Reports of Order status count")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated reports of Order status count."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> getOrderStatusCounts() {
    	try {
    		List<Map<String,Object>> orderStatusCounts = adminService.getOrderStatusCounts();
	        return ResponseEntity.ok(orderStatusCounts);
    	}
      	catch(IllegalArgumentException ex) {
      		return handleError(ex);
      	}
    }
}

