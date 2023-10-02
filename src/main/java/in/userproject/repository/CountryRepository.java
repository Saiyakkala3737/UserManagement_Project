package in.userproject.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.userproject.entity.CountryMaster;

public interface CountryRepository extends JpaRepository<CountryMaster, Serializable>{
	

}
