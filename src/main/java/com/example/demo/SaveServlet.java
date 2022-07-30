package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/saveServlet")
public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out;

        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        Employee employee = new Employee();
        setEmployeeInformation(employee, name, email, country);

        //out.println(employee.toString());
        //out.println(EmployeeRepository.getConnection());

        int status = EmployeeRepository.save(employee);
        //out.println(status);

        if (status > 0) {
            out.print("Record saved successfully!" + '\n');
            out.print("Name: " + name + '\n');
            out.print("Email: " + email + '\n');
            out.print("Country: " + country + '\n');
        } else {
            out.println("Sorry! unable to save record");
        }
        out.close();
    }

    private void setEmployeeInformation(Employee employee, String name, String email, String country) {
        employee.setName(name);
        employee.setEmail(email);
        employee.setCountry(country);
    }

    public Map<Integer, Employee> putUserToMap(Employee employee) {
        Map<Integer, Employee> usersMap = new HashMap<>();
        int empID = employee.getId();
        String empName = employee.getName();
        String empEmail = employee.getEmail();
        String empCountry = employee.getCountry();
        usersMap.put(empID, new Employee(empName, empEmail, empCountry));
        return usersMap;
    }
}
