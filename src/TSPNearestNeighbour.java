
import java.awt.font.NumericShaper;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;





public class TSPNearestNeighbour implements Runnable

{
	
	
	
	
    private int numberOfNodes;

    private Stack<Integer> stack;
    
    int adjacency_matrix[][] ;
    
    private ArrayList<Integer> path;

    public ArrayList<Integer> getPath(){
    	return path;
    }
    
    private void setPath(ArrayList<Integer> runningMethod) {
    	// TODO Auto-generated method stub
    	path=runningMethod;
    }
    
    public TSPNearestNeighbour()

    {
    	

        stack = new Stack<Integer>();
        
        

    }
    
    public TSPNearestNeighbour(int n,double[][] adj){
    	numberOfNodes=n;
    	adjacency_matrix= new int[numberOfNodes + 1][numberOfNodes + 1];
    	for(int i=0;i<numberOfNodes;i++){
    		for(int j=0;j<numberOfNodes;j++){
    			adjacency_matrix[i][j]=(int) adj[i][j];
    		}
    	}
    	stack = new Stack<Integer>();
    	
    }

    public void print(ArrayList<Integer> v){
    	for(int i=0;i<v.size();i++){
    		System.out.print(v.get(i)+" ");
    	}
    	System.out.println();
    }

    public long calculatetotaldist(int[][] graph,ArrayList<Integer> path){

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
    
ArrayList<Integer> a2optswap(int[][] graph,  ArrayList<Integer> path,int i,int k){

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

long twoOpt1(int[][] graph, ArrayList<Integer> path, long pathLength, int n)
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
//      
//      System.out.println(ite+" PATH");
//      print(path);
//      print(route);
//      System.out.println("SMALLEST TILL NOW :" +dist);
//      System.out.println("JUST CALC :" +new_dist);
    }
  }
	}
  return dist;

}
    
    
    public int tsp(int adjacencyMatrix[][],ArrayList<Integer> path)

    {
    	path.add(1);
    	int distance=0;

        numberOfNodes = adjacencyMatrix[1].length - 1;

        int[] visited = new int[numberOfNodes + 1];

        visited[1] = 1;

        stack.push(1);

        int element, dst = 0, i;

        int min = Integer.MAX_VALUE;

        boolean minFlag = false;

       // System.out.print(1 + "\t");



        while (!stack.isEmpty())

        {

            element = stack.peek();

            i = 1;

            min = Integer.MAX_VALUE;

            while (i <= numberOfNodes)

            {

                if (adjacencyMatrix[element][i] > 1 && visited[i] == 0)

                {

                    if (min > adjacencyMatrix[element][i])

                    {

                        min = adjacencyMatrix[element][i];

                        dst = i;

                        minFlag = true;

                    }

                }

                i++;

            }

            if (minFlag)

            {
            	
            	distance+=min;
                visited[dst] = 1;

                stack.push(dst);
                path.add(dst);
                

                //System.out.print(dst + "\t");

                minFlag = false;

                continue;

            }

            stack.pop();

        }
        path.add(1);
        
        return distance;

    }



public ArrayList<Integer> runningMethod()
    {

    	
        int number_of_nodes=numberOfNodes;

        Scanner scanner = null;

        try

        {

          
        	long startTime = System.currentTimeMillis();

            //System.out.println("the citys are visited as follows");

          //  TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour();

            
            ArrayList<Integer> path = new ArrayList<Integer>();
            
          int dist=  tsp(adjacency_matrix,path);
          //System.out.println();
          
          //print(path);
          
          ArrayList copy = new ArrayList(path);
          
          long pathLength;
          
          
          long dd;
          
          

        	 pathLength=calculatetotaldist(adjacency_matrix, path);
        	 
        	 
        	dd=twoOpt1(adjacency_matrix, path, pathLength, number_of_nodes);
       
        	return path;
        	
      /*    long endTime   = System.currentTimeMillis();
     	float totalTime = endTime - startTime;
      	System.out.println(totalTime);
      */	
        } catch (InputMismatchException inputMismatch)

         {

             System.out.println("Wrong Input format");

         }

        scanner.close();
		return null;

    }

@Override
public void run() {
	// TODO Auto-generated method stub
	setPath( runningMethod());
}



}

