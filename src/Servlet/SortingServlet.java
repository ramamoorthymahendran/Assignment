package Servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONException;
import org.json.JSONObject;
import com.model.SortingModel;
import Utill.RandomSortingMethods;
import com.google.*;
import com.google.gson.Gson;
public class SortingServlet extends HttpServlet {

		
	   // Method to handle GET method request.
	   public void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
		   RandomSortingMethods randomSortingMethods = new RandomSortingMethods();
		   StringBuffer sortedValues = new StringBuffer();
		   StringBuilder sb = new StringBuilder();
	        BufferedReader br = request.getReader();
	        String str = null;
	        String sortingValues = null;
	        while ((str = br.readLine()) != null) {
	            sb.append(str);
	        }
	       // String json = new Gson().toJson(sb);
	       // System.out.println(json);
	        JSONObject jObj;
			try {
				jObj = new JSONObject(sb.toString());
				 sortingValues = jObj.getString("sorting_values");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] sortingValuesArray = sortingValues.split(",");
			   int[] results = new int[sortingValuesArray.length];
			   for (int i = 0; i < sortingValuesArray.length; i++) {
				    try {
				        results[i] = Integer.parseInt(sortingValuesArray[i]);
				    } catch (NumberFormatException nfe) {
				        //NOTE: write something here if you need to recover from formatting errors
				    };
				}
			   int positionChangeCount = randomSortingMethods.getPositionChanges(results);
			   long start = System.nanoTime();
			      Arrays.sort(results);
			      long end = System.nanoTime();
			      long timeInMillis = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
			      
			      for (int i = 0; i < results.length; i++) {
			          if(i != 0) {
			        	  sortedValues.append(",");
			          }
			          sortedValues.append(results[i]);                     
			       }
			      
			     
					//displaySortingData();
				
			      randomSortingMethods.setSortingProp(sortedValues,timeInMillis,positionChangeCount);
			     
			      SortingModel sortingModel = new SortingModel();
			      sortingModel.setTiming(timeInMillis);
			      sortingModel.setSortingList(Arrays.toString(results));
			      sortingModel.setPositionChangeCount(2);
			     
			        String json = new Gson().toJson(sortingModel);
			        randomSortingMethods.savesortingData(json);
	                response.setContentType("application/json");
	                response.getWriter().write(json);

	   }

	   // Method to handle POST method request.
	   public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
		  	      doGet(request, response);
	   }
	}


