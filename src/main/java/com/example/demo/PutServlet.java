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

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");
        PrintWriter out;

        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setCountry(request.getParameter("country"));

        int status = EmployeeRepository.update(employee);

        try {
            if (status > 0) {
                response.sendRedirect("viewServlet");
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            out.println("Sorry! unable to update record");
        } finally {
            out.close();
        }
    }

    public Map<Integer, Employee> putUserToMap(Employee employee){
        Map<Integer, Employee> usersMap = new HashMap<>();
        int empID = employee.getId();
        String empName = employee.getName();
        String empEmail = employee.getEmail();
        String empCountry = employee.getCountry();
        usersMap.put(empID, new Employee(empName, empEmail, empCountry));
        return usersMap;
    }
}
