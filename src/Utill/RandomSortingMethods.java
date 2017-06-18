package Utill;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;

import com.google.gson.Gson;
import com.model.SortingModel;

public class RandomSortingMethods {
	public static int getPositionChanges(int[] arr)
    {
        int n = arr.length;
 
        // Create two arrays and use as pairs where first
        // array is element and second array
        // is position of first element
        ArrayList <Pair <Integer, Integer> > arrpos =
                  new ArrayList <Pair <Integer, Integer> > ();
        for (int i = 0; i < n; i++)
             arrpos.add(new Pair <Integer, Integer> (arr[i], i));
 
        // Sort the array by array element values to
        // get right position of every element as the
        // elements of second array.
        arrpos.sort(new Comparator<Pair<Integer, Integer>>()
        {
            @Override
            public int compare(Pair<Integer, Integer> o1,
                               Pair<Integer, Integer> o2)
            {
                if (o1.getValue() > o2.getValue())
                    return -1;
 
                // We can change this to make it then look at the
                // words alphabetical order
                else if (o1.getValue().equals(o2.getValue()))
                    return 0;
 
                else
                    return 1;
            }
        });
 
        // To keep track of visited elements. Initialize
        // all elements as not visited or false.
        Boolean[] vis = new Boolean[n];
        Arrays.fill(vis, false);
 
        // Initialize result
        int ans = 0;
 
        // Traverse array elements
        for (int i = 0; i < n; i++)
        {
            // already swapped and corrected or
            // already present at correct pos
            if (vis[i] || arrpos.get(i).getValue() == i)
                continue;
 
            // find out the number of  node in
            // this cycle and add in ans
            int cycle_size = 0;
            int j = i;
            while (!vis[j])
            {
                vis[j] = true;
 
                // move to next node
                j = arrpos.get(j).getValue();
                cycle_size++;
            }
 
            // Update answer by adding current cycle.
            ans += (cycle_size - 1);
        }
 
        // Return result
        return ans;
    }
    
	
	public static String savesortingData(String sortingModel){   
		Writer output;
        try(PrintWriter output1 = new PrintWriter(new FileWriter("C:\\ec\\testout.txt",true))) 
        {
            output1.printf("%s\r\n", sortingModel);
        } 
        catch (Exception e) {}
         System.out.println("Success...");
		return null;    
    }    
	
	public ArrayList displaySortingData() throws Exception{    
        FileReader fr=new FileReader("C:\\ec\\testout.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\ec\\testout.txt"), "Cp1252"));         
	      SortingModel sortingModel = new SortingModel();
	      ArrayList historyArray = new ArrayList();
        String line;    
        while ((line = br.readLine()) != null) {
        	sortingModel.setSortingList(line);
        	historyArray.add(line);
        
        }
        System.out.print(historyArray); 
        fr.close();
		return historyArray;    
  }    
	public static String setSortingProp(StringBuffer sortedValues,long timeInMillis, int PositionChange) {

	    Properties prop = new Properties();
	    OutputStream output = null;
	    File f = new File("C:\\ec\\config.properties");
	    try {
	    	if (f.exists()) {
	        FileInputStream input = new FileInputStream("C:\\ec\\config.properties");    
	        prop.load(input);
	        input.close();
	    	}
	        output = new FileOutputStream("C:\\ec\\config.properties");
	        prop.setProperty("app.sorting", sortedValues.toString());
	        prop.setProperty("app.timing", String.valueOf(timeInMillis));
	        prop.setProperty("app.position", String.valueOf(PositionChange));
	        prop.store(output, null);


	    } catch (IOException io) {
	        io.printStackTrace();
	    } finally {
	        if (output != null) {
	            try {
	                output.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	    }
		return null;
	}


}
