package ca.sheridancollege.hussamos.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PasswordRecord {
	@NonNull
	private Long id;
	private String title;
	private String username;
	private String password;
	private String url;
	private String email;
	private String notes;
}
