package in.userproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import in.userproject.entity.CityMaster;

import java.io.Serializable;
import java.util.List;

public interface CityRepository extends JpaRepository<CityMaster, Serializable> {

    List<CityMaster> findByStateId(Integer stateId);

    

}
