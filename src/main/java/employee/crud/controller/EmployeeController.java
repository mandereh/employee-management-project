package employee.crud.controller;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import employee.crud.beans.Employee;
import employee.crud.dao.EmployeeDAO;
import employee.crud.dao.EmployeeDAOImpl;

/**
 * Servlet implementation class EmployeeController
 */
@WebServlet("/")
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EmployeeDAO employeeDAO = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		employeeDAO = new EmployeeDAOImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Employee controller, doPost method started.");
		String action = request.getServletPath();
		System.out.println("doPost action ==> " + action);
		switch (action) {
		case "/add": {
			addNewEmployee(request, response);
			break;
		}
		case "/update": {
			updateEmployee(request, response);
			break;
		}
		case "/delete": {
			deleteEmployee(request, response);
			break;
		}
		case "/list": {
			getAllEmployees(request, response);
			break;
		}
		case "/get": {
			getEmployee(request, response);
			break;
		}
		default: {
			getAllEmployees(request, response);
			break;
		}
		}
	}

	private void getEmployee(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("start getEmployee method");

		int id = Integer.parseInt(request.getParameter("employeeId"));
		Employee employee = employeeDAO.getEmployee(id);
		System.out.println("getEmployee result is ==> " + employee);

//		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/employeesView.jsp");
		try {
			ObjectMapper mapper = new ObjectMapper();
			String employeeStr = mapper.writeValueAsString(employee);
			
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(employeeStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getAllEmployees(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("start getAllEmployees method");

		List<Employee> employees = employeeDAO.getAllEmployees();
		System.out.println("getAllEmployees size ==> " + employees.size());

		try {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/employeesView.jsp");
			request.setAttribute("employees", employees);
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("STart deleteEmployee method.");
		String employeeIds = request.getParameter("employeeId");
		System.out.println("deleteEmployee details, employee ID ==> " + employeeIds);
		
		StringTokenizer tokenizer = new StringTokenizer(employeeIds,",");
		while(tokenizer.hasMoreElements()) {
			int employeeId = Integer.parseInt(tokenizer.nextToken());
			boolean result = employeeDAO.deleteEmployee(employeeId);
			System.out.println("deleteEmployee, result is ==>" + result);

		}

		List<Employee> employees = employeeDAO.getAllEmployees();
		System.out.println("getAllEmployees size ==> " + employees.size());
		try {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/employeesView.jsp");
			request.setAttribute("employees", employees);
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("start updateEmployee method.");
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		Employee employee = new Employee(id, name, email, phone, address);
		System.out.println("updateEmployee details " + employee);

		boolean result = employeeDAO.updateEmployee(employee);
		System.out.println("updateEmployee, result is ==>" + result);
		
		List<Employee> employees = employeeDAO.getAllEmployees();
		System.out.println("getAllEmployees size ==> " + employees.size());

		
		try {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/employeesView.jsp");
			request.setAttribute("employees", employees);
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	private void addNewEmployee(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Start addEmployee method");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		Employee employee = new Employee(name, email, phone, address);
		System.out.println("Employee details " + employee);

		boolean result = employeeDAO.addEmployee(employee);
		System.out.println("addEmployee, result is ==>" + result);
		
		List<Employee> employees = employeeDAO.getAllEmployees();
		System.out.println("getAllEmployees size ==> " + employees.size());
		

		
		try {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/employeesView.jsp");
			request.setAttribute("employees", employees);
			requestDispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

}
