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

        response.setContentType("text/html");
        PrintWriter out;

        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Employee> list = EmployeeRepository.getAllEmployees();
        getAllEmployees(list, out);
    }

    private void printEmployees(List<Employee> list, PrintWriter out) {
        for (Employee employee : list) {
            out.print(employee);
        }
    }

    private void getAllEmployees(List<Employee> list, PrintWriter out) {
        try {
            printEmployees(list, out);
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
