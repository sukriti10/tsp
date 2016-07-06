import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TSP implements Runnable {
	int n;
	double adj[][];
	
	   private ArrayList<Integer> path;

	    public ArrayList<Integer> getPath(){
	    	return path;
	    }
	    
	    private void setPath(ArrayList<Integer> runningMethod) {
	    	// TODO Auto-generated method stub
	    	path=runningMethod;
	    }
	    
	    
	public TSP(int n1,double adj1[][]){
		n=n1;
		adj=adj1;
		
		
	}
	
	public static void print(ArrayList<Integer> v){
    	for(int i=0;i<v.size();i++){
    		System.out.print(v.get(i)+" ");
    	}
    	System.out.println();
    }
//COMPUTE TOTAL PATH LENGTH FOR ANY PATH 
    public static long calculatetotaldist(double[][] graph,ArrayList<Integer> path){

    	  long dist=0;

    	  int x=path.get(0);
    	  int n=path.size();
    	  for(int i=1;i<n;i++){
    	    dist+=graph[x][path.get(i)];
    	    //System.out.print(graph[x][path.get(i)]+ " " );
    	    x=path.get(i);
    	  }
    	  //System.out.println();
    	  return dist;
   }
    
static ArrayList<Integer> a2optswap(double[][] graph,  ArrayList<Integer> path,int i,int k){

    	   int a=i;
    	   int b=k;

    	   while(a<b){
    	    int tmp=path.get(a);
    	   // path[a]=path.get(b);
    	    path.set(a,path.get(b));
    	    path.set(b,tmp);
    	    a++;
    	    b--;
    	   }
    	   return path;
    	}

static long twoOpt1(double[][] graph, ArrayList<Integer> path, long pathLength, int n)
{	
	int ite=0;
	ArrayList<Integer> tmp;
	ArrayList<Integer> route;
	
	int flag=1;
	long  new_dist=0;
	  long  dist=pathLength;
	  
	  
	while(ite<10){
		
		ite++;
		flag=0;
  for(int i=1;i<n-1;i++){

    for(int j=i+1;j<n;j++ ){
    	
    	tmp=new ArrayList<Integer>(path);
      route=a2optswap(graph,tmp,i,j);

      new_dist=calculatetotaldist(graph,route);

      if(new_dist < dist){
    	  flag=1;
    	  
       path=route;
        dist=new_dist;
      }
      
    /*  System.out.println(ite+" PATH");
      print(path);
      print(route);
      System.out.println("SMALLEST TILL NOW :" +dist);
      System.out.println("JUST CALC :" +new_dist);*/
    }
  }
	}
  return dist;

}

	//public static void main(String[] args) {
		
	public ArrayList<Integer> doRunning(){
		/*System.out.println("Enter number of cities: ");
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		
		double adj[][]=new double[n+1][n+1];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				int z=sc.nextInt();
				adj[i][j]=z;
			}
		}
		*/

		//Timer timer = new Timer();
		Solver solver = new Solver(adj,n);
		long start=System.currentTimeMillis();
		Integer[] path = solver.calculate();
		long stop=System.currentTimeMillis();		
		ArrayList<Integer> arr=new ArrayList<>();
		for(int i=0;i<path.length;i++){
			arr.add(path[i]);
		}
		arr.add(path[0]);
		twoOpt1(adj,arr,calculatetotaldist(adj,arr),n);
		
		for(int i=0;i<path.length;i++){
			path[i]=arr.get(i);
		}
		
	/*	String message = path[0].toString();
		for(int i = 1; i < path.length; i++) {
			message += " to " + (path[i]);
		}
		message += " to " + (path[0]);
		message += "\nCost: " + solver.getCost();
		message += "\nTime: " + (stop-start) ;
		System.out.println(message);
*/
		
		return arr;
			//return path;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		setPath(doRunning());
		
	}


}
