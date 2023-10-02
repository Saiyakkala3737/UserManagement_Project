package in.userproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "CITY_MASTER")
public class CityMaster {

	@Id
	private Integer cityId;
	private String cityName;
	private Integer stateId;

}
