package com.toab.app.signup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.toab.app.signup.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findByEmailIdIgnoreCaseContaining(String emailId);
	
	public User findByEmailId(String emailId);
	
	@Query(nativeQuery = true, value = "SELECT IF(COUNT(*) = 0, 0, 1) Result FROM User WHERE email_id = ?1")
	public Boolean doesUserWithEmailExist(String email);
	
}
