package ca.sheridancollege.hussamos.beans;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UserRole {
	@NonNull
	private Long user_id;
	@NonNull
	private Long role_id;
}
