package in.userproject.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.userproject.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable>{
	
	//select * from user_master where email=?
	public User findByEmail(String email);
	
	public User findByEmailAndUserpwd(String email, String userpwd);


}
