package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = EmployeeRepository.getWriter(response);

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        Employee employee = new Employee();
        employee.setId(id);
        EmployeeRepository.setEmployeeInformation(employee, name, email, country);

        int status = EmployeeRepository.update(employee);
        printStatus(employee, status, id, out);
    }

    private void printStatus(Employee employee, int status, int id, PrintWriter out) {
        try {
            if (status > 0) {
                out.println("New parameters for user with ID : " + id);
                out.print("Name: " + employee.getName() + '\n');
                out.print("Email: " + employee.getEmail() + '\n');
                out.print("Country: " + employee.getCountry() + '\n');
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            out.println("Sorry! unable to update record");
        } finally {
            out.close();
        }
    }
}