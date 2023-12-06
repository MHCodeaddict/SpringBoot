package ca.sheridancollege.hussamos.beans;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Role {
	@NonNull
	private Long role_id;
	@NonNull
	private String role_name;
}
