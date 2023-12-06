package ca.sheridancollege.hussamos.beans;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Enrollment {
	@NonNull
	private Long student_id;
	@NonNull
	private Long course_id;
	@NonNull
	private String term_name;
	@NonNull
	private String grade;
	@NonNull
	private String enrollDate;
}
