package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService theEmployeeService;

    @Autowired
    public EmployeeController(EmployeeService theEmployeeService) {
        this.theEmployeeService = theEmployeeService;
    }

    // add mapping for "/list"
    @GetMapping("/list")
    public String getEmployeeList(Model theModel)
    {
        // get employees from db
        List<Employee> employees = theEmployeeService.findAll();

        // add employees to spring model
        theModel.addAttribute("employees" , employees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel)
    {
        // create model attribute to bind form data
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String deleteTheEmployee(@RequestParam("employeeId") int theId , Model theModel)

        {
            // delete the employee from the service
           theEmployeeService.deleteById(theId);


            return "redirect:/employees/list";

        }




    @GetMapping("/showFormForUpdate")
public String showFormForUpdate(@RequestParam("employeeId") int theId , Model theModel)
    {
        // get the employee from the service
        Employee theEmployee = theEmployeeService.findById(theId);

        // set employee in the model
        theModel.addAttribute("employee",theEmployee);

        return "employees/employee-form";

    }
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employees") Employee theEmployee)
    {
        // save the employee
        theEmployeeService.save(theEmployee);

        // redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }
}
