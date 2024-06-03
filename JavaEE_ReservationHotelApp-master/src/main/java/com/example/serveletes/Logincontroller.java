package com.example.serveletes;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.example.controllers.DBconnection;

@WebServlet("/Login")
public class Logincontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Logincontroller() {
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    DBconnection con = new DBconnection();
	    con.getConnection();
	    int isauthentication= con.authenticateUser(username ,password );
	    if(isauthentication>0) {
	    	response.sendRedirect("dashboard.jsp");
	    }
	    else {
	    	request.setAttribute("messagelogin", "login failed");
	    	request.getRequestDispatcher("/login.jsp").forward(request, response);
	    }
	}

}
