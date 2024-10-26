package com.electronics.store.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.electronics.store.enties.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

	// custom method

	// get user By User Email

	Optional<User> findByEmail(String userEmail);

	// search user by name with some keyword
	
	List<User> findByNameContaining(String keyword);

	// JPQL Query with named parameter

	@Query("select e from User e where e.email= :email")
	Optional<User> findUserByEmail(@Param("email") String userEmail);

	// JPQL Query with Index Parameter //sequence of parameter

	@Query("select e from User e where  e.name= ?1")  
	List<User> findUserByName(String keyword);
	
	//JPQL Query with some Sorting
	
	@Query("select u from User u where u.gender=:gender")
	List<User> getUserByGenderSortByName(@Param("gender") String userId,Sort sort);
	
	// Native SQL Query with named parameter
	
	@Query(value="SELECT * FROM User WHERE user_city =:c",nativeQuery = true)
	List<User> findUserByCity(@Param("c") String city);
	
	//native SQL Query with index parameter with sory by name
	
	@Query(value = "SELECT * FROM User WHERE gender=?1",nativeQuery = true)
	List<User> findUserByGnder(String gender);;
}
