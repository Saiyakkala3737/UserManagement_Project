package in.userproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "COUNTRY_MASTER")
public class CountryMaster {

	@Id
	private Integer countryId;
	private String countryName;

}
