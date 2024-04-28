package com.student.service;

import java.util.List;

import com.student.dto.StudentDto;

public interface StudentService {

	public StudentDto createStudent(StudentDto studentDto);
	public StudentDto updateStudent(Long sId, StudentDto studentDto);
	public List<StudentDto> getAllStudents();
	public StudentDto getByStudent(Long sId);
	public void deleteByStudent(Long sId);
}
