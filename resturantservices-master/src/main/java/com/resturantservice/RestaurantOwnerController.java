/**
 * 
 */
package com.resturantservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.resturantservice.entities.MenuItem;
import com.resturantservice.entities.Orders;
import com.resturantservice.entities.OrderStatus;
import com.resturantservice.entities.Restaurant;
import com.resturantservice.entities.RestaurantOwner;
import com.resturantservice.services.RestaurantOwnerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantowners")
@Tag(name = "Restaurant Owners API", description = "Endpoints for managing restaurant and Restaurant Owners Functionalities")
public class RestaurantOwnerController extends ResponseController{

	@Autowired
    private RestaurantOwnerService ownerService;
	
	   @GetMapping(value="/{restaurantownerid}", produces = MediaType.APPLICATION_JSON_VALUE)
	    @Operation(summary = "Get Restaturant Owner", description = "Get Restaturant Owner.")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Successfully retrieved Restaturant Owner."),
	        @ApiResponse(responseCode = "404", description = "Restaurant Owner Not Found."),
	        @ApiResponse(responseCode = "500", description = "Internal server error.")
	    })
	    public ResponseEntity<Object> getRestaurantOwner(Long restaurantownerid) {
			try {
				RestaurantOwner restaurantOwner = ownerService.getRestaurantOwner(restaurantownerid);
				return handleResponse(restaurantOwner);
			} catch (Exception ex) {
				return handleError(ex);
			}
	    }

    // Register
    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register Restaturant Owner", description = "Register Restaturant Owner.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully registered Restaturant Owner."),
        @ApiResponse(responseCode = "400", description = "Invalid Restaurant Owner Data."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> register(@RequestBody RestaurantOwner owner) {
		try {
			RestaurantOwner restaurantOwner = ownerService.register(owner);
			return handleResponse(restaurantOwner);
		} catch (Exception ex) {
			return handleError(ex);
		}
    }

    // Login
    @PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login Restaturant Owner", description = "Login Restaturant Owner.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully logged in Restaturant Owner."),
        @ApiResponse(responseCode = "403", description = "Forbidden."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String password) {
		try {
			RestaurantOwner restaurantOwner = ownerService.login(email, password);
			return handleResponse(restaurantOwner);
		} catch (Exception ex) {
			return handleError(ex);
		}
    }

    // View Menus
    @GetMapping(value="/menus/{restaurantid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "View Menus by restaurantid", description = "View Menus by restaurantid.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully viewed Menus."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> viewMenus(@PathVariable Long restaurantid) {
		try {
			List<MenuItem> viewMenus = ownerService.manageMenu(restaurantid);
			return handleResponse(viewMenus);
		} catch (Exception ex) {
			return handleError(ex);
		}
    }

    // Add Menu Item
    @PostMapping(value="/menus/{restaurantid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add Menu Item", description = "Add Menu Item.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully added Menu Item."),
        @ApiResponse(responseCode = "400", description = "Invalid Menu Item Data."),
        @ApiResponse(responseCode = "404", description = "Restaurant Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> addMenuItem(@PathVariable Long restaurantid, @RequestBody MenuItem menuItem) {
		try {
			MenuItem addMenuItem = ownerService.addMenuItem(menuItem, restaurantid);
			return handleResponse(addMenuItem);
		} catch (Exception ex) {
			return handleError(ex);
		}
    }

    // Remove Menu Item
    @DeleteMapping(value="/menus/{menuitemid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Remove Menu Item", description = "Remove Menu Item.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully removed Menu Item."),
        @ApiResponse(responseCode = "404", description = "Menu Item Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public void removeMenuItem(@PathVariable Long menuitemid) {
			ownerService.removeMenuItem(menuitemid);
    	}

    // View Orders
    @GetMapping(value="/orders/{restaurantid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "View Orders by restaurantid", description = "View Orders by restaurantid.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully viewed Orders."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> viewOrders(@PathVariable Long restaurantid) {
		try {
			List<Orders> viewOrders = ownerService.viewOrders(restaurantid);
			return handleResponse(viewOrders);
		} catch (Exception ex) {
			return handleError(ex);
		}
    }

    // Update Order Status
    @PatchMapping(value="/orders/{orderid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Order Status", description = "Update Order Status.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated Orders Status."),
        @ApiResponse(responseCode = "400", description = "Invalid Order Data."),
        @ApiResponse(responseCode = "404", description = "Order Not Found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public void updateOrderStatus(@PathVariable Long orderid, @RequestParam OrderStatus status) {
        ownerService.updateOrderStatus(orderid, status);
    }

    // Update Restaurant Details
    @PutMapping(value="/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Restaurant Details", description = "Update Restaurant Details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated Restaurant Details."),
        @ApiResponse(responseCode = "400", description = "Invalid Restaurant Data."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> updateRestaurantDetails(@RequestBody Restaurant updatedRestaurant) {
		try {
			Restaurant restaurant = ownerService.updateRestaurantDetails(updatedRestaurant);
			return handleResponse(restaurant);
		} catch (Exception ex) {
			return handleError(ex);
		}
    }
}
