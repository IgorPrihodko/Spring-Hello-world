package com.bytestree.controller;

import com.bytestree.model.Employee;
import com.bytestree.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	private Logger logger = Logger.getLogger(HelloController.class);

	private final EmployeeService employeeService;

	@Autowired
	public HelloController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/")
	public String hello(Model model) {

		model.addAttribute("name", "Igor Prykhodko");

		Employee newEmployee = new Employee( "Bytes", "Tree",
				"Senior Developer", 2000);

		employeeService.addNewEmployee(newEmployee);

		Employee employee = employeeService.getEmployee(newEmployee.getId());
		logger.debug("Retrieving saved employee " + employee);

		return "welcome";
	}
}
