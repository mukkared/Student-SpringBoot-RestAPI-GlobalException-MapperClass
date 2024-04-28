package com.student.controller;

import com.student.dto.StudentDto;
import com.student.entity.Student;
import com.student.exception.ErrorDetails;
import com.student.exception.StudentAlreadyExistsException;
import com.student.exception.StudentNotFoundException;
import com.student.mapper.StudentMapper;
import com.student.repository.StudentRepository;
import com.student.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	private ErrorDetails errorDetails;

	public String todayDate() {
		LocalDate today = LocalDate.now();
		// Define a date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Format today's date using the defined format
		return today.format(formatter);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/")
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		try {
			List<StudentDto> allStudents = studentService.getAllStudents();
			return new ResponseEntity<>(allStudents, HttpStatus.OK);
		} catch (Exception ex) {
			errorDetails = new ErrorDetails(todayDate(), ex.getMessage(), "Student does not present ");
			return new ResponseEntity<List<StudentDto>>((List<StudentDto>) errorDetails, HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/saveStudent")
	public ResponseEntity<Object> createStudent(@RequestBody StudentDto studentDto) {
		try {
			StudentDto createStudent = studentService.createStudent(studentDto);
			return new ResponseEntity<>(createStudent, HttpStatus.CREATED);
		} catch (StudentAlreadyExistsException ex) {
			errorDetails = new ErrorDetails(todayDate(), ex.getMessage(), "Student creation failed");
			return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/{sId}")
	public ResponseEntity<Object> updateStudent(@PathVariable Long sId, @RequestBody StudentDto studentDto) {
		try {
			StudentDto updateStudent = studentService.updateStudent(sId, studentDto);
			return new ResponseEntity<>(updateStudent, HttpStatus.OK);
		} catch (StudentNotFoundException ex) {
			errorDetails = new ErrorDetails(todayDate(), ex.getMessage(), "Student has not updated ");
			return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{sId}")
	public ResponseEntity<Object> getByStudent(@PathVariable Long sId) {
		try {
			StudentDto student = studentService.getByStudent(sId);
			return new ResponseEntity<>(student, HttpStatus.OK);
		} catch (Exception e) {
			errorDetails = new ErrorDetails(todayDate(), e.getMessage(), "Student has not presented");
			return new ResponseEntity<>(errorDetails, HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/{sId}")
	public ResponseEntity<Object> deleteById(@PathVariable Long sId) {
		try {
			studentService.deleteByStudent(sId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			errorDetails = new ErrorDetails(todayDate(), e.getMessage(), "Student has not presented");
			return new ResponseEntity<>(errorDetails, HttpStatus.NO_CONTENT);
		}
	}

	/*
	 * public ResponseEntity<Object> createStudent(@RequestBody StudentDto
	 * studentDto) { try { StudentDto createStudent =
	 * studentService.createStudent(studentDto); return new
	 * ResponseEntity<>(createStudent, HttpStatus.CREATED); } catch
	 * (StudentAlreadyExistsException ex) { ErrorDetails errorDetails = new
	 * ErrorDetails(todayDate(), ex.getMessage(), "Student creation failed"); return
	 * new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT); } }
	 */
	/*
	 * @GetMapping("/{id}") public ResponseEntity<Student>
	 * getStudentById(@PathVariable(value = "id") Long studentId) throws
	 * ResourceNotFoundException { Student student =
	 * studentRepository.findById(studentId) .orElseThrow(() -> new
	 * ResourceNotFoundException("Student not found for this id :: " + studentId));
	 * return ResponseEntity.ok().body(student); }
	 * 
	 * @PutMapping("/{id}") public ResponseEntity<Student>
	 * updateStudent(@PathVariable(value = "id") Long studentId,
	 * 
	 * @RequestBody Student studentDetails) throws ResourceNotFoundException {
	 * Student student = studentRepository.findById(studentId) .orElseThrow(() ->
	 * new ResourceNotFoundException("Student not found for this id :: " +
	 * studentId));
	 * 
	 * student.setName(studentDetails.getName());
	 * student.setEmail(studentDetails.getEmail());
	 * student.setAge(studentDetails.getAge());
	 * student.setDepartment(studentDetails.getDepartment()); final Student
	 * updatedStudent = studentRepository.save(student); return
	 * ResponseEntity.ok(updatedStudent); }
	 * 
	 * 
	 */
	/*@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Long studentId)
			throws ResourceNotFoundException {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId));

		studentRepository.delete(student);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}*/
}
