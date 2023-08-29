package com.kce.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Book")
public class BookList extends HttpServlet {
	private static final String query="SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA";
    @Override
protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	PrintWriter pw=res.getWriter();
	res.setContentType("text/html");
	
	try {
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 
	}catch(ClassNotFoundException cnf) {
		cnf.printStackTrace();
	}
	//generate 
	try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","Chidham@7");
			PreparedStatement ps=con.prepareCall(query);){
		
		 ResultSet rs=ps.executeQuery();
		 pw.println("<table border='1'");
			pw.println("<tr>");
			pw.println("<th>BOOK ID</th>");
			pw.println("<th>BOOK NAME</th>");
			pw.println("<th>BOOK EDITION</th>");
			pw.println("<th>BOOK PRICE</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");
		 while(rs.next()) {
			 pw.println("<tr>");
				pw.println("<td>"+rs.getInt(1)+"</td>");
				pw.println("<td>"+rs.getString(2)+"</td>");
				pw.println("<td>"+rs.getString(3)+"</td>");
				pw.println("<td>"+rs.getFloat(4)+"</td>");
				
				pw.println("<td><a href='editScreen?id="+rs.getInt(1)+"'>Edit </a></td>");
				pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");
				pw.println("</tr>");
			 
		 }
		 pw.println("</table>");
		 pw.println("<a href='NewFile.html'>Home</a>");
		 
		
	}catch(SQLException se) {
		se.printStackTrace();
		pw.println("<h1>"+se.getMessage()+"</h1>");
	}catch(Exception e) {
		e.printStackTrace();
		pw.println("<h1>"+e.getMessage()+"</h1>");
	}
}
  
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	doGet(req,res);
}
}
