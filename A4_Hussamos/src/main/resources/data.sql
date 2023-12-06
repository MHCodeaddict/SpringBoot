INSERT INTO `user` (email, encrypted_password, enabled)
VALUES ('user@h.com','$2a$12$w/BhdXbgWGAUsDZo52X2W.2EcR2lTrVxEdZFV1FIyjvnBMraXsZGa', 1);
--password "user"
INSERT INTO `user` (email, encrypted_password, enabled)
VALUES ('admin@h.com', '$2a$12$Lse4/IMJe1jHlH2W/fStQeKSLDv.H94oM/UfWzVH3/.i3CbXDBNPu', 1);
--password "admin"
INSERT INTO `user` (email, encrypted_password, enabled)
VALUES ('guest@h.com', '$2a$12$OAOUoe84C3aW3IROb6DiyeLFUt9ol5CKMdTia/52oZovIhGE8rtGu', 1);
--password "guest"
INSERT INTO `user` (email, encrypted_password, enabled)
VALUES ('mostafa.h95@hotmail.com', '$2a$12$iSTty9SnNoEaoX6RMNLwuu6u59v0.a7KK0vBZMdx13yNg3mT5XfzO', 1);
--password "1234"

INSERT INTO `role` (role_name)
VALUES ('ROLE_USER');
INSERT INTO `role` (role_name)
VALUES ('ROLE_ADMIN');
INSERT INTO `role` (role_name)
VALUES ('ROLE_GUEST');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);
INSERT INTO user_role (user_id, role_id)
VALUES (2, 2);
INSERT INTO user_role (user_id, role_id)
VALUES (3, 3);
INSERT INTO user_role (user_id, role_id)
VALUES (4, 1);

INSERT INTO student (first_name, last_name, user_id)
VALUES ('user','USER',1);
INSERT INTO student (first_name, last_name, user_id)
VALUES ('admin','ADMIN',2);
INSERT INTO student (first_name, last_name, user_id)
VALUES ('guest','GUEST',3);
INSERT INTO student (first_name, last_name, user_id)
VALUES ('Mostafa','Hussain',4);

INSERT INTO course (course_name)
VALUES ('Math');
INSERT INTO course (course_name)
VALUES ('Science');
INSERT INTO course (course_name)
VALUES ('English');
INSERT INTO course (course_name)
VALUES ('French');

INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (1,1,'FALL', 'A', '2023-09-08');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (1,2,'FALL', 'A', '2023-09-08');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (1,3,'FALL', 'A', '2023-09-08');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (2,1,'WINTER', 'B', '2023-01-05');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (2,2,'WINTER', 'B', '2023-01-05');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (2,3,'WINTER', 'B', '2023-01-05');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (3,1,'FALL', 'C', '2023-06-11');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (3,2,'FALL', 'C', '2023-06-11');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (3,3,'FALL', 'C', '2023-06-11');
INSERT INTO enrollment (student_id, course_id, term_name, grade, enroll_date)
VALUES (4,4,'WINTER', 'F', '2022-09-08');



