package in.userproject.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.userproject.binding.LoginForm;
import in.userproject.binding.UnlockAccountForm;
import in.userproject.binding.UserForm;
import in.userproject.service.UserMgmtService;

@RestController
public class UserRestController {

	@Autowired
	private UserMgmtService service;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginForm){
		String status=service.login(loginForm);
		return new ResponseEntity<>(status, HttpStatus.OK);

	}

	@GetMapping("/countries")
	public Map<Integer, String> loadCountries(){
		return service.getCountries();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> loadStates(@PathVariable Integer countryId){
		return service.getStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer,String> loadCities(@PathVariable Integer stateId){
		return service.getCities(stateId);
	}

	@GetMapping("/email/{email}")
	public String emailCheck(@PathVariable String email) {
		return service.checkEmail(email);
	}

	@PostMapping("/user")
	public ResponseEntity<String> userRegistration(@RequestBody UserForm userForm){
		String register=service.registerUser(userForm);
		return new ResponseEntity<>(register, HttpStatus.CREATED);

	}

	@PostMapping("/unloack")
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccountForm accForm) {
		String status=service.unlockAccount(accForm);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
	
	@GetMapping("/forgotpwd")
	public ResponseEntity<String> forgotPwd(@PathVariable String email) {
		String mail=service.forgotPwd(email);
		return new ResponseEntity<>(mail,HttpStatus.OK);
	}

}
