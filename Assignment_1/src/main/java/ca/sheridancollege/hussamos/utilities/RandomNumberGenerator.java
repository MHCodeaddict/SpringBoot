package ca.sheridancollege.hussamos.utilities;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator {
	
	public Long generateRandomNumber() {
		
		Random random = new Random();
		
	    Long randomNumber = random.nextLong(1000000000);
	    return randomNumber;
	}
	
}
