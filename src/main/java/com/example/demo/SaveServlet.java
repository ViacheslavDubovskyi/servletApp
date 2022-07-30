package com.example.demo;

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

        PrintWriter out = getWriter(response);

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        Employee employee = new Employee();
        setEmployeeInformation(employee, name, email, country);

        int status = EmployeeRepository.save(employee);
        printStatus(employee, status, out);
    }

    public void setEmployeeInformation(Employee employee, String name, String email, String country) {
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

    public PrintWriter getWriter(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public void printStatus(Employee employee, int status, PrintWriter out) {
        if (status > 0) {
            out.print("Record saved successfully!" + '\n');
            out.print("Name: " + employee.getName() + '\n');
            out.print("Email: " + employee.getEmail() + '\n');
            out.print("Country: " + employee.getCountry() + '\n');
        } else {
            out.println("Sorry! unable to save record");
        }
        out.close();
    }
}
