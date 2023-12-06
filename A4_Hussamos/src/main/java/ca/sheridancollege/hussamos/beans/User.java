package ca.sheridancollege.hussamos.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class User {
	@NonNull
	private Long user_id;
	@NonNull
	private String email;
	@NonNull
	private String encrypted_password;
	@NonNull
	private Boolean enabled;
}
