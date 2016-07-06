import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CallingClass extends Thread {
		
		 static double[][] adj;
	 static int n;
	 
	 public static void setN(int x){
		 n=x;
	 }
	 public static int getN(){
		 return n;
		 
	 }
	 
	 public static void setAdj(double adj1[][]){
		 int n1=getN();
		 adj=new double[n1+1][n1+1];
		  for(int i=0;i<n1;i++){
			  for(int j=0;j<n1;j++){
				  adj[i][j]=adj1[i][j];
			  }
		  }

	 }

	  public static long calculatetotaldist_path(double[][] adj2,ArrayList<Integer> path){

    	  long dist=0;

    	  int x=path.get(0);
    	  int n=path.size();
    	  for(int i=1;i<n;i++){
    	    dist+=adj2[x][path.get(i)];
    	    x=path.get(i);
    	  }
    	  return dist;
   }
	  
	  public static void printpath(ArrayList<Integer> v,int a){
	    	for(int i=0;i<v.size();i++){
	    		System.out.print(v.get(i)+a+" ");
	    	}
	    	System.out.println();
	    }

	  
	public static void main(String[] args) throws InterruptedException{
		
		
		getData();
		/*System.out.println("Enter number of cities: ");
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		
		double adj[][]=new double[n+1][n+1];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				double z=sc.nextDouble();
				adj[i][j]=z;
			}
		}*/
		System.out.println("\n");
		
		TSPNearestNeighbour ob1=new TSPNearestNeighbour(n,adj);
		TSP ob2=new TSP(n,adj);
		Thread t1=new Thread(ob1);
		Thread t2=new Thread(ob2);
		t1.start();
		t2.start();
		t1.join();
		
		t1.run();
		t2.run();
		t2.stop();
		t1.stop();
		
		/*ArrayList<Integer> a2=ob1.getPath();
		printpath(a2);
		
		ArrayList<Integer> a1=ob2.getPath();
		printpath(a1);*/
		
		 ArrayList<Integer> a2=ob1.getPath();
		ArrayList<Integer> a1=ob2.getPath();
		
		long d1=calculatetotaldist_path(adj,a2);
		long d2=calculatetotaldist_path(adj, a1);
		
		if(d1>d2){
			printpath(a1,1);
			System.out.println(d2);
			
		}
		else{
			printpath(a2,0);
			System.out.println(d1);
		}
	
	}
	 public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException 
	 {
	    InputStream is = new URL(url).openStream();
	    try 
	    {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    }
	    finally 
	    {
	      is.close();
	    }
	 }
	 private static String readAll(Reader rd) throws IOException 
	 {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) 
	    {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	 }

	private static void getData() {
		  JSONObject json=null;
		  ArrayList<String> city = new ArrayList<String>();
		  System.out.println("Enter number of locations : ");

		  int n1;
		  Scanner sc=new Scanner(System.in);
		  
		  n1=sc.nextInt();
		  setN(n1);
		  System.out.println("Enter n locations : ");

		  for(int in=0;in<n;in++){
			  city.add(sc.next().replace(' ', ','));
		  }
		  
		  double[][] amindist=new double[n+1][n+1];
		  double amindist_dur[][]=new double[n+1][n+1];

		  double amindur_dist[][]=new double[n+1][n+1];

		  double[][] amindur=new double[n+1][n+1];

		  for(int i=0;i<city.size();i++){
			 for(int j=0;j<city.size();j++){
				  //if(i!=j){
				 
		  try 
		  {
			  String url="https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+city.get(i)+"&destinations="+city.get(j)+"&mode=driving&key=AIzaSyBa7jIEMRCHXmHtm85iPQfEEU48erirSgs";
		/*	  https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Malleshwaram,%20Bengaluru,%20Karnataka&destinations=Indiranagar,Bengaluru,Karnataka&key=AIzaSyBa7jIEMRCHXmHtm85iPQfEEU48erirSgs
		*/	  json=readJsonFromUrl(url);
			  json.get("rows");
		   JSONArray arr=null;
		   arr = json.getJSONArray("rows");
		   int t=arr.getJSONObject(0).getJSONArray("elements").length();
		   double distmin=Double.MAX_VALUE;
		   double mindist_dur=0.0;
		   
		   double durmin=Double.MAX_VALUE;
		   double mindur_dist=0.0;
		   
		   for(int i1=0;i1<t;i1++){
			   double tem=(double)arr.getJSONObject(0).getJSONArray("elements").getJSONObject(i1).getJSONObject("distance").getDouble("value");
			   double tem1=(double)arr.getJSONObject(0).getJSONArray("elements").getJSONObject(i1).getJSONObject("duration").getDouble("value");
			   tem1=tem1/60;
			   tem=tem/1000;
			   //tem in km
			   //tem1 in minutes
			   if(distmin>tem){
				   distmin=tem;
				   mindist_dur=tem1;
			   }
			   if(durmin>tem1){
				   durmin=tem1;
				   mindur_dist=tem;
			   }
		   }
		  amindist[i][j]=distmin;
		  amindist_dur[i][j]=mindist_dur;
		  amindur_dist[i][j]=mindur_dist;
		  amindur[i][j]=durmin;
		  
		  }
		  catch (JSONException e) 
		  {
		   e.printStackTrace();
		  } 
		  catch (IOException e)
		  {
		     e.printStackTrace();
		  }
		  	
				 }
	}
		  int ch=0;
		  System.out.println("Choose:\n 1.By time \n 2.By distance:");
		  ch=sc.nextInt();
		  if(ch==1){
			  setAdj(amindur);
			  
		  }
		  else{
			  setAdj(amindist);
		  }
	}

}
