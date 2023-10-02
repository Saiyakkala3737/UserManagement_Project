package in.userproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name= "STATE_MASTER")
public class StateMaster {

	@Id
	private Integer stateId;
	private String stateName;
	private Integer countryId;


}
