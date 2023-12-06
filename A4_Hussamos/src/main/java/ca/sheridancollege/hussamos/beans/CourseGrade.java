package ca.sheridancollege.hussamos.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseGrade {
    private Long course_id;
    private String course_name;
    private String grade;
    private String term_name;

    // Constructors, getters, and setters
}