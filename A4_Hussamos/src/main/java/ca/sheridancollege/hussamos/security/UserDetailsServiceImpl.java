package ca.sheridancollege.hussamos.security;
/*
 * By: Mostafa Hussain 
 * User: hussamos
 * Student Id: 991332466
 * Assignment 3
 */
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.hussamos.beans.User;
import ca.sheridancollege.hussamos.database.DatabaseAccessImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	DatabaseAccessImpl da;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Entered useDetails.");
		User user = da.getUserByEmail(email);
		// If the user doesn't exist, throw an exception
		if (user == null) {
			System.out.println("User not found: " + email);
			throw new UsernameNotFoundException("User " + email + " was not found in the database");
		}
		//Get a list of roles for that user
		List<String> roleNameList = da.getRolesByUserId(user.getUser_id());
		// Change the list of the user's roles into a list of GrantedAuthority
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNameList != null) {
			for (String role : roleNameList) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}
		// Create a user based on the information above
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(),user.getEncrypted_password(), grantList);
		System.out.println(userDetails);
		return userDetails;
	}

}
