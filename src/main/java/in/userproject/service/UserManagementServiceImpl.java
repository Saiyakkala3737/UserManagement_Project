package in.userproject.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.userproject.binding.LoginForm;
import in.userproject.binding.UnlockAccountForm;
import in.userproject.binding.UserForm;
import in.userproject.entity.CityMaster;
import in.userproject.entity.CountryMaster;
import in.userproject.entity.StateMaster;
import in.userproject.entity.User;
import in.userproject.repository.CityRepository;
import in.userproject.repository.CountryRepository;
import in.userproject.repository.StateRepository;
import in.userproject.repository.UserRepository;
import in.userproject.utils.EmailUtils;

@Service
public class UserManagementServiceImpl implements UserMgmtService {

	@Autowired
	private UserRepository usrrepo;

	@Autowired
	private CountryRepository countryrepo;

	@Autowired
	private StateRepository staterepo;

	@Autowired
	private CityRepository cityrepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String checkEmail(String email) {
		User usr= usrrepo.findByEmail(email);
		if(usr==null) {
			return "UNIQUE";
		}
		return "Duplicate";
	}

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryMaster>countries=countryrepo.findAll();

		Map<Integer, String> countrymap = new HashMap<>();

		countries.forEach(country -> {
			countrymap.put(country.getCountryId(), country.getCountryName());
		});
		return countrymap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateMaster>states=staterepo.findByCountryId(countryId);

		Map<Integer, String> statemap = new HashMap<>();

		states.forEach(state ->{
			statemap.put(state.getStateId(), state.getStateName());
		});
		return statemap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityMaster> cities=cityrepo.findByStateId(stateId);

		Map<Integer, String> citymap = new HashMap<>();

		cities.forEach(city ->{
			citymap.put(city.getCityId(), city.getCityName());
		});
		return citymap;
	}

	@Override
	public String registerUser(UserForm userform) {

		//copy data from binding obj to entity obj
		User entity = new User();
		BeanUtils.copyProperties(userform, entity);
		usrrepo.save(entity);

		//Generate & set Random pwd
		
		entity.setUserpwd(generateRandomPwd());

		//Set Account status as Locked
		
		entity.setAccStatus("LOCKED");
		
		usrrepo.save(entity);
		
		//send email to unloack account
		String to= userform.getEmail();
		String subject= "REGISTRATION EMAIL - UNLOCK ACCOUNT";
		String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
		emailUtils.sendEmail(to, subject, body);

		return "USER ACCOUNT CREATED";
	}

	@Override
	public String unlockAccount(UnlockAccountForm unlockForm) {

		String email=unlockForm.getEmail();
		User user=usrrepo.findByEmail(email);

		if(user!= null && user.getUserpwd().equals(unlockForm.getTempPwd())) {
			user.setUserpwd(unlockForm.getNewPwd());
			user.setAccStatus("UNLOCKED");
			usrrepo.save(user);
			return "Account unloacked";
		}
		return "INVALID TEMPORARY PASSWORD";
	}

	@Override
	public String login(LoginForm loginForm) {
		User user=usrrepo.findByEmailAndUserpwd(loginForm.getEmail(), loginForm.getPwd());
		if(user==null) {
			return "INVALID CREDENTIALS";
		}

		if(user.getAccStatus().equals("LOCKED")){
			return "Account Locked"; 
		}
		return "SUCCESS" ;
	}

	@Override
	public String forgotPwd(String email) {

		User user= usrrepo.findByEmail(email);
		if(user ==null) {
			return "No account found";
		}

		//TODO: send email to user with pwd
		
		String subject= "RECOVER PASSWORD";
		String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", user);
		emailUtils.sendEmail(email, subject, body);
		return "Password sent to registered email";
		
	}

	private String generateRandomPwd() {
		String text= "ABADIUGEUFGFUO3FSDVJKB2874R2B2982Y4U289";

		StringBuilder sb = new StringBuilder();

		Random random = new Random();

		int pwdLength =6;
		for(int i=1; i<pwdLength; i++) {
			int index=random.nextInt(text.length());
			sb.append(text.charAt(index));		
		}
		return sb.toString();

	}

	private String readEmailBody(String filename, User user) {
		
		StringBuffer sb = new StringBuffer();
		
		try(Stream<String> lines=Files.lines(Paths.get(filename))) {	
			lines.forEach(line ->{
				line=line.replace("${FNAME}",user.getFname());
				line=line.replace("${LNAME}", user.getLname());
				line=line.replace("${TEMP_PWD}", user.getUserpwd());
				line=line.replace("${EMAIL}", user.getEmail());
				line=line.replace("{PWD}", user.getUserpwd());
				
				sb.append(line);

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();

	}

}
