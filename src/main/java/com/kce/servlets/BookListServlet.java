package com.kce.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookListServlet extends HttpServlet {
	private static final String query="SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA";
    @Override
protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	PrintWriter pw=res.getWriter();
	res.setContentType("text/html");
	String bookName=req.getParameter("bookName");
	String bookEdition=req.getParameter("bookEdition");
	float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
	
	try {
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 
	}catch(ClassNotFoundException cnf) {
		cnf.printStackTrace();
	}
	//generate 
	try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","Chidham@7");
			PreparedStatement ps=con.prepareCall(query);){
		  ps.setString(1, bookName);
		  ps.setString(2, bookEdition);
		  ps.setFloat(3, bookPrice);
		  int count=ps.executeUpdate();
		  if(count==1) {
			  pw.println("<h2>Record is Registered</h2>");
		  }else {
			  pw.println("<h2>Record Not Registerd</h2>");
		  }
		
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
	doGet(res,req);
}

}
