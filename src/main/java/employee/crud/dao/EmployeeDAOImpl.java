package employee.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import employee.crud.beans.Employee;
import employee.crud.db.DBConnection;

public class EmployeeDAOImpl implements EmployeeDAO{
	
	private static Connection connection = DBConnection.connection;

	@Override
	public boolean addEmployee(Employee employee) {
		System.out.println(" start AddEmployee");
		try {
			String insertStatement = "INSERT INTO employee_db.employee (name,email,phone,address)VALUES(?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getEmail());
			preparedStatement.setString(3, employee.getPhone());
			preparedStatement.setString(4, employee.getAddress());
			
			int result = preparedStatement.executeUpdate();
			return result == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		System.out.println("start update");
		try {
			String updateStatement = "UPDATE employee_db.employee SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
			PreparedStatement prepareStatement = connection.prepareStatement(updateStatement);
			prepareStatement.setString(1, employee.getName());
			prepareStatement.setString(2, employee.getEmail());
			prepareStatement.setString(3, employee.getPhone());
			prepareStatement.setString(4, employee.getAddress());
			prepareStatement.setInt(5, employee.getId());
			
			int result = prepareStatement.executeUpdate();
			return result == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean deleteEmployee(int employeeId) {
		System.out.println("start delete");
		try {
			String deleteStatement = "DELETE FROM employee_db.employee WHERE id = ?";
			PreparedStatement prepareStatement = connection.prepareStatement(deleteStatement);
			prepareStatement.setInt(1, employeeId);
			
			int result =prepareStatement.executeUpdate();
			return result == 1 ? true : false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}

	@Override
	public List<Employee> getAllEmployees() {
		System.out.println("start select all employee");
		try {
			String selectStatement = "SELECT * FROM employee_db.employee";
			PreparedStatement prepareStatement = connection.prepareStatement(selectStatement);
			
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Employee> employees = new ArrayList<Employee>();
			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setId(resultSet.getInt("id"));;
				employee.setName(resultSet.getString("name"));
				employee.setEmail(resultSet.getString("email"));
				employee.setPhone(resultSet.getString("phone"));
				employee.setAddress(resultSet.getString("address"));
				
				employees.add(employee);
				
			}
			return employees;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Employee getEmployee(int employeeId) {
		System.out.println("start select employee");
		try {
			String selectStatement = "SELECT * FROM employee_db.employee WHERE id = ?";
			PreparedStatement prepareStatement = connection.prepareStatement(selectStatement);
			prepareStatement.setInt(1, employeeId);
			ResultSet resultSet = prepareStatement.executeQuery();
			Employee employee = new Employee();
			while (resultSet.next()) {
				employee.setId(resultSet.getInt("id"));;
				employee.setName(resultSet.getString("name"));
				employee.setEmail(resultSet.getString("email"));
				employee.setPhone(resultSet.getString("phone"));
				employee.setAddress(resultSet.getString("address"));
			}
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		Employee employee = new Employee();
		employee.setId(11);
		employee.setName("Akonte Fubara Braide");
		employee.setEmail("akontebraide@gmail.com");
		employee.setPhone("07033069390");
		employee.setAddress("Stadium Road, Port Harcourt, Rivers State");
		
		EmployeeDAOImpl employeedaoimpl = new EmployeeDAOImpl();
		System.out.println(employeedaoimpl.getEmployee(6));
	}

}
