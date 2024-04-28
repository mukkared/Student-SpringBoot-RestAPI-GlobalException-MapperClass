package com.student.mapper;

import com.student.dto.StudentDto;
import com.student.entity.Student;

public class StudentMapper {

	public static Student mapToStudent(StudentDto studentDto) {
		Student student = new Student(studentDto.getId(), studentDto.getName(), studentDto.getEmail(),
				studentDto.getAge(), studentDto.getDepartment());
		return student;
	}

	public static StudentDto mapToStudentDto(Student student) {
		StudentDto studentDto = new StudentDto(student.getId(), student.getName(), student.getEmail(), student.getAge(),
				student.getDepartment());
		return studentDto;
	}

}
