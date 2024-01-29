package employee.crud.dao;

import java.util.List;

import employee.crud.beans.Employee;

public interface EmployeeDAO {

	//Insert Employee
	public boolean addEmployee(Employee employee);
	//Update Employee
	public boolean updateEmployee(Employee employee);
	//Delete Employee
	public boolean deleteEmployee(int employeeId);
	//Get All Employees
	public List<Employee> getAllEmployees();
	//Get Employee
	public Employee getEmployee(int employeeId);
}
