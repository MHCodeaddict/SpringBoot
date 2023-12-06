package ca.sheridancollege.hussamos.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.hussamos.beans.Course;
import ca.sheridancollege.hussamos.beans.CourseGrade;
import ca.sheridancollege.hussamos.beans.Enrollment;
import ca.sheridancollege.hussamos.beans.Student;
import ca.sheridancollege.hussamos.beans.User;
import ca.sheridancollege.hussamos.database.DatabaseAccessImpl;


@Controller
public class HomeController {
	@Autowired
	private DatabaseAccessImpl da;
	@Lazy
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String index() {
		System.out.println("Entered index.");
		return "index";
	}
	@GetMapping("/login")
	public String login() {
		System.out.println("Entered login().");
		return "login";
	}
	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}
	@GetMapping("/logout-success")
	public String logoutSuccess() {
		return "logout-success";
	}
	@GetMapping("/permission-denied")
	public String permissionDenied(Model model, Authentication auth) {
		User user = da.getUserByEmail(auth.getName());
		model.addAttribute("role", da.getRolesByUserId(user.getUser_id()));
		return "/error/permission-denied";
	}
	@GetMapping("/register")
	public String getRegister () {
		return "register";
	}
	@PostMapping("/register")
	public String postRegister(@RequestParam String email, @RequestParam String password) {
		System.out.println("registered");
		da.addUser(email, password);
		Long userId = da.getUserByEmail(email).getUser_id();
		// this next line is dangerous! For extra credit, try making a DatabaseAccess method to find a roleId associate with a role of “ROLE_USER” and add the “correct” id every time ☺
		da.addUserRole(userId, Long.valueOf(1));
	return "redirect:/";
	}
	@GetMapping("/secure/index")
	public String secureIndex(Model model, Authentication auth) {
		User user = da.getUserByEmail(auth.getName());
		model.addAttribute("role", da.getRolesByUserId(user.getUser_id()));
		model.addAttribute("user", auth.getName());
		return "/secure/index";
	}
	@GetMapping("/secure/courseRegistrationPage")
	public String courseRegistrationPage(Model model, Authentication auth){
		User user = da.getUserByEmail(auth.getName());
		Student student = da.checkStudent(user.getUser_id());
		Student newStudent = new Student();
		Enrollment enrollment = new Enrollment();
		List<Course> courses = da.getCourses();
		model.addAttribute("student", student);
		model.addAttribute("newStudent", newStudent);
		model.addAttribute("user_id", user.getUser_id());
		model.addAttribute("enrollment", enrollment);
		model.addAttribute("courses", courses);
		return "secure/courseRegistrationPage";
	}
	@PostMapping("/addStudent")
	public String addStudent(Model model, @ModelAttribute Student student) {
		da.addStudent(student.getFirst_name(), student.getLast_name(), student.getUser_id());
		model.addAttribute("student", student);
		model.addAttribute("confirmed", true);
		return "redirect:/secure/courseRegistrationPage";
	}
	@GetMapping("/enroll/{course_id}")
	public String enroll(Model model, @PathVariable Long course_id,Authentication auth) {
		LocalDate date = LocalDate.now();
		User user = da.getUserByEmail(auth.getName());
		Student student = da.checkStudent(user.getUser_id());
		//even course_id = FALL term, odd = WINTER term
		String term = (course_id%2==0 ? "FALL" : "WINTER");
		da.addCourse(student.getStudent_id(), course_id, term, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return "redirect:/secure/courseRegistrationPage";
	}
	
	@GetMapping("/gradingPage")
	public String gradingPage() {
		return "secure/gradingPage";
	}
	@PostMapping("/gradingPage")
	public String findCourses(Model model, @RequestParam("student_id") Long student_id) {
		//find the courses enrolled to by this student id
		List<CourseGrade> enrolledCourses = da.getCourseGrades(student_id);
		//Find the student name and ID
		Student student = da.checkStudentByStudentID(student_id);
		//send the list of courses to the page
		model.addAttribute("courses", enrolledCourses);
		//send student name and id to the page
		model.addAttribute("student", student);
		return "secure/gradingPage";
	}
	@GetMapping("/gradingPage/{course_id}/{student_id}")
	public String selectedGrade(Model model, @PathVariable Long course_id,@PathVariable Long student_id) {
		//find the courses enrolled to by this student id
		List<CourseGrade> enrolledCourses = da.getCourseGrades(student_id);
		//Find the student name and ID
		Student student = da.checkStudentByStudentID(student_id);
		//send the list of courses to the page
		model.addAttribute("courses", enrolledCourses);
		//send student name and id to the page
		model.addAttribute("student", student);
		model.addAttribute("editGrade", true);
		model.addAttribute("oldGrade", da.getStudentGrade(course_id, student_id));
		return "secure/gradingPage";
	}
	
	@PostMapping("/updateGrade")
	public String updateGrade(Model model, 
			@RequestParam("student_id") Long student_id,
			@RequestParam("newGrade") String grade,
			@RequestParam("course_id") Long course_id){
		da.addGrade(student_id, course_id, grade);
		//find the courses enrolled to by this student id
		List<CourseGrade> enrolledCourses = da.getCourseGrades(student_id);
		//Find the student name and ID
		Student student = da.checkStudentByStudentID(student_id);
		//send the list of courses to the page
		model.addAttribute("courses", enrolledCourses);
		//send student name and id to the page
		model.addAttribute("student", student);
		return "secure/gradingPage";
	}

	@GetMapping("/transcriptPage")
	public String transcriptPage() {
		return "secure/transcriptPage";
	}
	@PostMapping("/transcriptPage")
	public String transcriptPage(Model model, @RequestParam("student_id") Long student_id) {
		//Search for student
		Student student = da.checkStudentByStudentID(student_id);
		//get ALL valid grades of this specific student
		List<CourseGrade> enrolledCourses = da.getNonNullGrades(student_id);
		model.addAttribute("student", student);
		model.addAttribute("courses", enrolledCourses);
		return "secure/transcriptPage";
	}
}
