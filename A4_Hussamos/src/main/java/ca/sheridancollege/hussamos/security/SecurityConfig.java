package ca.sheridancollege.hussamos.security;
/*
 * By: Mostafa Hussain 
 * User: hussamos
 * Student Id: 991332466
 * Assignment 3
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		http.csrf(c -> c.disable())
		.headers(header -> header.frameOptions(frame -> frame.disable()))
		.authorizeHttpRequests(requests -> requests
				.requestMatchers(antMatcher("/h2-console/**")).permitAll()
				//EVERYONE privileges
				.requestMatchers(
					mvc.pattern("/js/**"),
					mvc.pattern("/css/**"),
					mvc.pattern("/img/**"),
					mvc.pattern("/"), 
					mvc.pattern("/login"), 
					mvc.pattern("/register"),
					mvc.pattern("/error"),
					mvc.pattern("/permission-denied")
				).permitAll()
				//ADMIN and USER privileges
				.requestMatchers(
					mvc.pattern("/secure/index"),
					mvc.pattern("/courseRegistrationPage"),
					mvc.pattern("/secure/courseRegistrationPage"),
					mvc.pattern("/enroll/**"),
					mvc.pattern("/addStudent")
				).hasAnyRole("USER", "ADMIN")
				//ADMIN privileges
				.requestMatchers(
					mvc.pattern("/searchStudent"),
					mvc.pattern("/gradingPage"),
					mvc.pattern("/transcriptPage"),
					mvc.pattern("/findStudent"),
					mvc.pattern("/secure/gradingPage"),
					mvc.pattern("/editGrade/**"),
					mvc.pattern("/gradingPage/**"),
					mvc.pattern("/updateGrade/**"),
					mvc.pattern("/updateGrade")
				).hasRole("ADMIN")
				.requestMatchers(mvc.pattern("/")).permitAll()
				.requestMatchers(mvc.pattern("/**")).denyAll())
		
		.formLogin(form -> form.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/secure/index", true).permitAll())
		
		.exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler))
		
		.logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true).logoutUrl("/logout")
				.logoutSuccessUrl("/logout-success").permitAll());
		System.out.println("Entered Security chain.");
		return http.build();
	}
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
