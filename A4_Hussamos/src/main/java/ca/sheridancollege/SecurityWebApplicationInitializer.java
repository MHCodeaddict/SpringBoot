package ca.sheridancollege;
/*
 * By: Mostafa Hussain 
 * User: hussamos
 * Student Id: 991332466
 * Assignment 3
 */
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import ca.sheridancollege.hussamos.security.SecurityConfig;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer{
	public SecurityWebApplicationInitializer() {
		super(SecurityConfig.class);
	}
}
