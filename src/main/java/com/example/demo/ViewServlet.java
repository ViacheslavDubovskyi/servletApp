package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/viewServlet")
public class ViewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = EmployeeRepository.getWriter(response);
        List<Employee> list = EmployeeRepository.getAllEmployees();
        printAllEmployees(list, out);
    }

    private void getEmployees(List<Employee> list, PrintWriter out) {
        for (Employee employee : list) {
            out.print(employee);
        }
    }

    private void printAllEmployees(List<Employee> list, PrintWriter out) {
        try {
            getEmployees(list, out);
            if (list.isEmpty()) {
                throw new IOException();
            }
        } catch (IOException e) {
            out.println("The table is Empty!");
        } finally {
            out.close();
        }
    }
}