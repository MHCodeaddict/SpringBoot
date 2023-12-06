package ca.sheridancollege.hussamos.beans;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Course {
	@NonNull
	private Long course_id;
	@NonNull
	private String course_name;
}
