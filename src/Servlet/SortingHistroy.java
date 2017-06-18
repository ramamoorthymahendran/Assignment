package Servlet;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Utill.RandomSortingMethods;


public class SortingHistroy extends HttpServlet{
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		 ArrayList historyList = new ArrayList();
		 RandomSortingMethods randomSortingMethods = new RandomSortingMethods();
		 try {
			 historyList = randomSortingMethods.displaySortingData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 String json = new Gson().toJson(historyList);
         response.setContentType("application/json");
         response.getWriter().write(json);
         System.out.println("json List :"+json);

}

// Method to handle POST method request.
public void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
	      doGet(request, response);
}
}
