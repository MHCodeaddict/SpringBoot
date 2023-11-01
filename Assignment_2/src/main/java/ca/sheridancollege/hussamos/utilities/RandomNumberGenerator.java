package ca.sheridancollege.hussamos.utilities;
/*
 * By: Mostafa Hussain
 * Student ID: 991332466
 */
import java.util.Random;

import org.springframework.stereotype.Component;
//This class and method generate a number with maximum 1,000,000,000 for the ID field
@Component
public class RandomNumberGenerator {
	
	public Long generateRandomNumber() {
		
		Random random = new Random();
		
	    Long randomNumber = random.nextLong(1000000000);
	    return randomNumber;
	}
	
}
