package com.student.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.dto.StudentDto;
import com.student.entity.Student;
import com.student.exception.StudentAlreadyExistsException;
import com.student.exception.StudentNotFoundException;
import com.student.mapper.StudentMapper;
import com.student.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository repository;

	@Autowired
	public StudentServiceImpl(StudentRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public List<StudentDto> getAllStudents() {
		List<StudentDto> collects = null;
		try {
			List<Student> students = repository.findAll();
			if (students.isEmpty()) {
				throw new StudentNotFoundException("Student details does not presented:");
			} else {
				collects = students.stream().map((student) -> StudentMapper.mapToStudentDto(student))
						.collect(Collectors.toList());
//				  return students.stream() .map(StudentMapper::mapToStudentDto) .collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve students: " + e.getMessage());
		}
		return collects;
	}

	@Override
	public StudentDto createStudent(StudentDto studentDto) {
		Student saveStudent = null;
		try {
			Student student = StudentMapper.mapToStudent(studentDto);
			Student existingStudent = repository.findById(studentDto.getId()).orElse(null);
			if (existingStudent == null) {
				saveStudent = repository.save(student);
			} else {
				throw new StudentAlreadyExistsException("StudentId has Already Exist: " + studentDto.getId());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return StudentMapper.mapToStudentDto(saveStudent);
	}

	@Override
	public StudentDto updateStudent(Long sId, StudentDto studentDto) {
		StudentDto mapToStudentDto = null;
		try {
			Optional<Student> studentId = repository.findById(sId);
			if (studentId.isPresent()) {
				Student updateStudent = studentId.get();
				updateStudent.setName(studentDto.getName());
				updateStudent.setEmail(studentDto.getEmail());
				updateStudent.setAge(studentDto.getAge());
				updateStudent.setDepartment(studentDto.getDepartment());

				Student update = repository.save(updateStudent);
				mapToStudentDto = StudentMapper.mapToStudentDto(update);
			} else {
				throw new StudentNotFoundException("StudentId has not found: " + studentDto.getId());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return mapToStudentDto;
	}

	@Override
	public StudentDto getByStudent(Long sId) {
		StudentDto mapToStudentDto = null;
		try {
			Optional<Student> findById = repository.findById(sId);
			if (findById.isPresent()) {
				mapToStudentDto = StudentMapper.mapToStudentDto(findById.get());
			} else {
				throw new StudentNotFoundException("Student has not found: " + sId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return mapToStudentDto;
	}

	@Override
	public void deleteByStudent(Long sId) {
		try {
			Optional<Student> findById = repository.findById(sId);
			if (findById.isPresent()) {
				repository.deleteById(sId);
			} else {
				throw new StudentNotFoundException("Student has not exist: " + sId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
