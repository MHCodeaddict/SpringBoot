package ca.sheridancollege.hussamos.utilities;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator {
	
	public static Long generateRandomNumber() {
		
		Random random = new Random();
		
		//Generate number from 0 to 1000000000 (1bil | 1x10^9)
	    int randomNumber = random.nextInt(1000000000);
	    //this line formats its to a 9 digit number (ex: 89221= 000089221)
	    String formattedNumber = String.format("%09d", randomNumber);
	 
	    return Long.valueOf(formattedNumber);
	}
	
}
