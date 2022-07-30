package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/viewByIDServlet")
public class ViewByIDServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");
        PrintWriter out;

        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        Employee employee = EmployeeRepository.getEmployeeById(id);

        SaveServlet saveServlet = new SaveServlet();
        Map<Integer, Employee> usersMap = saveServlet.putUserToMap(employee);

        if (usersMap.containsKey(id)) {
            out.print(employee);
        } else {
            out.print("No user with such ID!");
        }
        out.close();

    }
}
