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
        int id = EmployeeRepository.idForUser(request);

        Employee employee = new Employee();
        employee.setId(id);
        EmployeeRepository.setEmployeeInformation(request, employee);

        int status = EmployeeRepository.update(employee);
        printStatus(employee, status, id, out);
    }

    private void printStatus(Employee employee, int status, int id, PrintWriter out) {
        try {
            if (status > 0) {
                out.println("Record is successfully update!");
                out.println("New parameters for user with ID " + id + ":");
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