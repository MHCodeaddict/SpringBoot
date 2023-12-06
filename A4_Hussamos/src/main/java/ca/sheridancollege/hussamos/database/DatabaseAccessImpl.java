package ca.sheridancollege.hussamos.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.hussamos.beans.Course;
import ca.sheridancollege.hussamos.beans.CourseGrade;
import ca.sheridancollege.hussamos.beans.Enrollment;
import ca.sheridancollege.hussamos.beans.Role;
import ca.sheridancollege.hussamos.beans.Student;
import ca.sheridancollege.hussamos.beans.User;

@Repository
public class DatabaseAccessImpl {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	public User getUserByEmail(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM `user` WHERE (email = :email);";
		namedParameters.addValue("email", email);
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException erdae) {
			System.out.println("Query did not work or could not find user");
			return null;
		}
	}
	
	public List<String> getRolesByUserId(Long user_id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT role.role_name "
		+ "FROM user_role, role "
		+ "WHERE user_role.role_id = role.role_id "
		+ "AND user_id = :user_id";
		namedParameters.addValue("user_id", user_id);
		return jdbc.queryForList(query, namedParameters, String.class);
	}
	
	public void addUserRole(Long user_id, Long role_id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO user_role(user_id, role_id) "
				+ "VALUES (:user_id, :role_id);";
		namedParameters.addValue("user_id", user_id);
		namedParameters.addValue("role_id", role_id);
		jdbc.update(query, namedParameters);
	}
	
	public void addUser(String email, String password) {
	    if (email == null || password == null) {
	        throw new IllegalArgumentException("Email and password cannot be null.");
	    }
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO `user` (email, encrypted_password, enabled) "
				+ "VALUES (:email, :encrypted_password, 1); ";
		namedParameters.addValue("email", email);
		namedParameters.addValue("encrypted_password", passwordEncoder.encode(password));
		jdbc.update(query, namedParameters);
	}
	
	public void addStudent(String first_name, String last_name, Long user_id) {
	    if (first_name == null || last_name == null || user_id <=0) {
	        throw new IllegalArgumentException("First and Last name cannot be null");
	    }
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO student (first_name, last_name, user_id) "
				+ "VALUES(:first_name, :last_name, :user_id);";
		namedParameters.addValue("first_name", first_name);
		namedParameters.addValue("last_name", last_name);
		namedParameters.addValue("user_id", user_id);
		jdbc.update(query, namedParameters);
	}
	
	public Student checkStudent(Long user_id) {
		if(user_id <=0) {
			throw new IllegalArgumentException("user ID doesnt exist");
		}
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM student WHERE user_id = :user_id;";
		namedParameters.addValue("user_id", user_id);
		//if there's a student, return an object of the field , otherwise return a null object
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<Student>(Student.class));
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	public Student checkStudentByStudentID(Long student_id) {
		if(student_id <=0) {
			throw new IllegalArgumentException("user ID doesnt exist");
		}
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM student WHERE student_id = :student_id;";
		namedParameters.addValue("student_id", student_id);
		//if there's a student, return an object of the field , otherwise return a null object
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<Student>(Student.class));
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void addCourse(Long student_id, Long course_id, String term_name, String enroll_date) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO enrollment(student_id, course_id, term_name, grade, enroll_date) "
				+ "VALUES (:student_id, :course_id, :term_name, :grade, :enroll_date)";
		namedParameters.addValue("student_id", student_id);
		namedParameters.addValue("course_id", course_id);
		namedParameters.addValue("term_name", term_name);
		namedParameters.addValue("grade", "");
		namedParameters.addValue("enroll_date", enroll_date);
		jdbc.update(query, namedParameters);
	}
	
	public void addGrade(Long student_id, Long course_id, String grade) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "UPDATE enrollment "
				+ "SET grade = :grade "
				+ "WHERE student_id = :student_id "
				+ "AND course_id = :course_id;";
		namedParameters.addValue("grade", grade);
		namedParameters.addValue("student_id", student_id);
		namedParameters.addValue("course_id", course_id);
		try {
			jdbc.update(query, namedParameters);
		} catch(DuplicateKeyException e) {
			System.out.println("Course already on the schedule");
		}
	}
	
	public List<Course> getCourses() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM course;";
		try {
			return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Course>(Course.class));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	public List<Enrollment> getStudentCourses(Long student_id) {
		if(student_id == null || student_id <= 0)
				System.out.println("Student Id empty");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM enrollment WHERE student_id = :student_id;";
		namedParameters.addValue("student_id",student_id);
		try {
			return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Enrollment>(Enrollment.class));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	public String getStudentGrade(Long course_id, Long student_id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT grade FROM enrollment WHERE course_id = :course_id AND student_id = :student_id;";
		namedParameters.addValue("course_id", course_id);
		namedParameters.addValue("student_id", student_id);
		try {
			return jdbc.queryForObject(query, namedParameters, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	public List<CourseGrade> getCourseGrades(Long student_id){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		String query = "SELECT c.course_id, c.course_name, e.grade "
//				+ "FROM course c "
//				+ "JOIN enrollment e ON c.course_id = e.course_id "
//				+ "WHERE c.course_id = :course_id";
		String query = "SELECT c.course_id, c.course_name, e.grade "
                + "FROM course c "
                + "JOIN enrollment e ON c.course_id = e.course_id "
                + "WHERE e.student_id = :student_id";
		namedParameters.addValue("student_id", student_id);
		try {
			return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<CourseGrade>(CourseGrade.class));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
	public List<CourseGrade> getNonNullGrades(Long student_id){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT c.course_id, c.course_name, e.grade, e.term_name "
				+ "FROM enrollment e "
				+ "JOIN course c ON e.course_id = c.course_id "
				+ "WHERE e.grade IS NOT NULL "
				+ "AND e.student_id = :student_id;";
		namedParameters.addValue("student_id", student_id);
		try {
			return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<CourseGrade>(CourseGrade.class));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}
	
//	public void updateGrade(String grade) {
//		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		String query = "UPDATE";
//	}
	
	
	
}
