package ca.sheridancollege.hussamos.beans;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Student {
	@NonNull
	private Long student_id;
	@NonNull
	private String first_name;
	@NonNull
	private String last_name;
	@NonNull
	private Long user_id;
}
