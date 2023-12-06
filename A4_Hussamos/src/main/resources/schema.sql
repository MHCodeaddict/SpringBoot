CREATE TABLE `user` (
	user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(75),
    encrypted_password VARCHAR(128),
    enabled BIT
    );
CREATE TABLE `role` (
	role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(45)
    );
CREATE TABLE user_role (
	user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id,role_id),
    FOREIGN KEY (user_id) REFERENCES `user`(user_id),
    FOREIGN KEY (role_id) REFERENCES `role`(role_id)
    );
CREATE TABLE student (
	student_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES `user`(user_id)
    );
CREATE TABLE course (
	course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(45)
	);	
CREATE TABLE enrollment (
	student_id BIGINT,
    course_id BIGINT,
    term_name VARCHAR(45),
    grade VARCHAR(45),
    enroll_date DATE,
    PRIMARY KEY(student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
    );

