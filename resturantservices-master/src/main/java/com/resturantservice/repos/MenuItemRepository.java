/**
 * 
 */
package com.resturantservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resturantservice.entities.MenuItem;

/**
 * 
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
	List<MenuItem> findByRestaurantId(Long restaurantId);
}
