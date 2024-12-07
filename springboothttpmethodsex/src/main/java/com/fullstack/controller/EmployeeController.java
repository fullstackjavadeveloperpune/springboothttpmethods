package com.fullstack.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.exception.RecordNotFoundException;
import com.fullstack.model.Employee;
import com.fullstack.service.IEmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@PostMapping("/save")
	public ResponseEntity<Employee> save(@RequestBody Employee employee) {

		log.info("@@@@@Trying to save data for Employee: " + employee.getEmpName());
		return ResponseEntity.ok(employeeService.save(employee));
	}

	@GetMapping("/findbyid/{empId}")
	public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
		return ResponseEntity.ok(employeeService.findById(empId));
	}

	@GetMapping("/findall")
	public ResponseEntity<List<Employee>> findAll() {
		return ResponseEntity.ok(employeeService.findAll());
	}

	@PutMapping("/update/{empId}")
	public ResponseEntity<Employee> update(@PathVariable int empId, @RequestBody Employee employee) {

		Employee employee1 = employeeService.findById(empId)
				.orElseThrow(() -> new RecordNotFoundException("Employee #ID Does Not Exist"));

		employee1.setEmpName(employee.getEmpName());

		employee1.setEmpAddress(employee.getEmpAddress());

		employee1.setEmpSalary(employee.getEmpSalary());

		return ResponseEntity.ok(employeeService.update(employee1));
	}

	@PatchMapping("/changeaddress/{empId}/{empAddress}")
	public ResponseEntity<Employee> changeAddress(@PathVariable int empId, @PathVariable String empAddress) {
		Employee employee = employeeService.findById(empId)
				.orElseThrow(() -> new RecordNotFoundException("Employee #ID Does Not Exist"));

		employee.setEmpAddress(empAddress);

		return ResponseEntity.ok(employeeService.update(employee));
	}

	@DeleteMapping("/deletebyid/{empId}")
	public ResponseEntity<String> deleteById(@PathVariable int empId) {
		employeeService.deleteById(empId);

		return ResponseEntity.ok("Data Deleted Successfully");
	}
}
