package in.userproject.service;

import java.util.Map;

import in.userproject.binding.LoginForm;
import in.userproject.binding.UnlockAccountForm;
import in.userproject.binding.UserForm;

public interface UserMgmtService {

	public String checkEmail(String email);
	public Map<Integer, String> getCountries();
	public Map<Integer, String> getStates(Integer countryId);
	public Map<Integer, String> getCities(Integer stateId);
	public String registerUser(UserForm user);
	public String unlockAccount(UnlockAccountForm accForm);
	public String login(LoginForm loginForm);
	public String forgotPwd(String email);


}
