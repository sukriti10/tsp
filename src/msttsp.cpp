#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <bits/stdc++.h>
#include <time.h>
#define ll long long int
using namespace std;

//Custom comparator for pairs

bool comp1(const pair<int,int> &a,const pair<int,int> &b){
	return a.second<b.second;

}

void print(vector<int> &p){
  for(int i=0;i<p.size();i++){
    cout<<p[i]<<" ";
  }
  cout<<"\n";
}

ll calculatetotaldist(ll** graph,vector<int> &path){

  ll dist=0;

  int x=path[0];
  int n=path.size();
  for(int i=1;i<n;i++){
    dist+=graph[x][path[i]];
    x=path[i];
  }
  return dist;
}

vector<int> a2optswap(ll **graph,vector<int> path,int i,int k){

   int a=i;
   int b=k;

   while(a<b){
    int tmp=path[a];
    path[a]=path[b];
    path[b]=tmp;
    a++;
    b--;
   }
   return path;
}

long long int twoOpt1(ll **graph, vector<int> &path, ll &pathLength, int n)
{
  vector<int> route;
  long long int new_dist;
  long long int dist=pathLength;
  //int n=path.size();
  //vector<int> temp;
  int flag=1;

  while(flag==1){
    flag=0;
  for(int i=1;i<n-1;i++){

    for(int j=i+1;j<n;j++ ){
vector<int> tmp(path);
      route=a2optswap(graph,tmp,i,j);

      new_dist=calculatetotaldist(graph,route);

      if(new_dist < dist){
        path=route;
        flag=1;
        dist=new_dist;
      }
    }
  }
}
//  return 1;

  return dist;

}



int main() {
    
    clock_t t1,t2;

   int n,m;
    //cin>>n>>m;
  
  	cin>>n;

    int nit=n;

    vector<int> tour;

     
    vector<pair<int,ll> > tourr[n+1];

	vector<pair<int,ll> > G[n+1];
  /*  for(int i=0;i<m;i++){
        int x,y,wt;
        cin>>x>>y>>wt;
        G[x-1].push_back(make_pair(y-1,wt));
        G[y-1].push_back(make_pair(x-1,wt));
        

    }
    */
    ll** graph=new ll*[n];
    for (int i = 0; i < n; i++) {
    graph[i] = new ll[n];
    for (int j = 0; j < n; j++) graph[i][j] = 0;
  }

    for(int i=0;i<n;i++){

    	for(int j=0;j<n;j++){
    		ll x;
    		cin>>x;
        graph[i][j]=x;

    		if(j!=i){

    			G[i].push_back(make_pair(j,x));
    		}
    	}
    }
/*
MST

*/  

  t1=clock();

  bool in[n];
  for(int i=0;i<n;i++){
    in[i]=false;
  } 
    long long int d;
    cin>>d;
    int s;
    cin>>s;
    s++;
    tour.push_back(s-1);
    in[s-1]=true;
   nit--;
    int vert=-1;
    ll wt;
    ll sum=0;


    while(nit--){

        wt=LONG_MAX;

        int kiska=0;

    for(int i=0;i<n;i++){

            if(in[i]==true){
                vector<pair<int,ll> >::iterator it;
                for(it=G[i].begin();it!=G[i].end();it++){
                    if(in[it->first]==false){
                        if(wt>it->second){
                            wt=it->second;
                            vert=it->first;
                            kiska=i;
                        }
                    }
                }

            }



    }
    tourr[kiska].push_back(make_pair(vert,wt));
    tourr[vert].push_back(make_pair(kiska,wt));
    cout<<kiska<<" "<<vert<<" "<< wt<<"\n";
        
    sum+=wt;
    tour.push_back(vert);
    in[vert]=true;
    in[kiska]=true; 
    }
   /* cout<<sum<<"\n";

    for (int i = 0; i < n; ++i)
    {
    	cout<<tour[i]+1<<" ";
    }

    cout<<"\n";
*/

/*

  PERFECT MATCHING

*/
  vector<int> odds;

    cout<<"\nNEW\n";

    

   	for(int i=0;i<n;i++){
   		if(tourr[i].size()%2!=0){
   			  odds.push_back(i);
   		}
   	}


  cout<<"\n";

	//vector<pair<int,int> >::iterator tmp;
  ll closest, length; //int d;
  std::vector<int>::iterator tmp, first;

  // Find nodes with odd degrees in T to get subgraph O
 // findOdds();

  // for each odd node
  while (!odds.empty()) {
    //cout<<"HERE I AM\n";
    first = odds.begin();
    vector<int>::iterator it = odds.begin() + 1;
    vector<int>::iterator end = odds.end();
    length = std::numeric_limits<int>::max();
    for (; it != end; ++it) {
      // if this node is closer than the current closest, update closest and length
      if (graph[*first][*it] < length) {
        length = graph[*first][*it];
        closest = *it;
        tmp = it;
      }
    } 
    cout<<*first<<" "<<closest<<"\n";
    // two nodes are matched, end of list reached
    tourr[*first].push_back(make_pair(closest,length));
    tourr[closest].push_back(make_pair(*first,length));
    odds.erase(tmp);
    odds.erase(first);
  }






   	//NOW CONSTRUCTING EULER TOUR FOR TSP 

/*
   1)	Start with an empty stack and an empty circuit (eulerian path).
- If all vertices have even degree - choose any of them.
- If there are exactly 2 vertices having an odd degree - choose one of them.
- Otherwise no euler circuit or path exists.
2)If current vertex has no neighbors - add it to circuit, remove the last vertex from the stack and set it as the current one. Otherwise (in case it has neighbors) - add the vertex to the stack, take any of its neighbors, remove the edge between selected neighbor and that vertex, and set that neighbor as the current vertex.
3)Repeat step 2 until the current vertex has no more neighbors and the stack is empty.


*/

   	stack<int> eulerr;
   int pos=s-1;

   vector<int> path;

  vector<pair<int,ll> > temp[n+1];
  for(int i=0;i<=n;i++){
  	temp[i].resize(tourr[i].size());
  	temp[i]=tourr[i];

  	//sort(tourr[i].begin(),tourr[i].end(),comp1);
  }

  path.clear();

  stack<int> stk;
  while (!stk.empty() || temp[pos].size() > 0 ) {
    // If current vertex has no neighbors -
    if (temp[pos].size() == 0) {
      // add it to circuit,
      path.push_back(pos);
      // remove the last vertex from the stack and set it as the current one.
      int last = stk.top();
      stk.pop();
      pos = last;
    }
    // Otherwise (in case it has neighbors)
    else {
      // add the vertex to the stack,
      stk.push(pos);
      // take any of its neighbors,
      int neighbor = temp[pos].back().first;
      // remove the edge between selected neighbor and that vertex,
      temp[pos].pop_back();
          for (unsigned int i = 0; i < temp[neighbor].size(); i++)
              if (temp[neighbor][i].first == pos) { // find position of neighbor in list
                temp[neighbor].erase (temp[neighbor].begin() + i); // remove it
                  break;
              }
      // and set that neighbor as the current vertex.
          pos = neighbor;
    }
  }
  path.push_back(pos);

   cout<<"\nEuler\n";
   for(int i=0;i<path.size();i++){
   	cout<<path[i]<<" ";
   }
   //cout<<s<<" ";
   cout<<"\n";
   

/*  ---------------------------------
  HAMILTONIAN
  ---------------------------------
*/	ll p=0;

    bool visited[n];
    for(int i=0;i<n;i++){
      visited[i]=false;

    }
    int root=path.front();
    cout<<root<<" ";
  vector<int>::iterator curr = path.begin();
  vector<int>::iterator next = path.begin()+1;
  visited[root] = true;

  std::vector<int> ans;
  ans.push_back(root);

  // loop until the end of the circuit list is reached
  while ( next != path.end() ) {
    // if we haven't been to the next city yet, go there
    if (!visited[*next]) {
      p += graph[*curr][*next];
      curr = next;
      ans.push_back(*curr);
      cout<<*curr<<" ";
      visited[*curr] = true;
      next = curr + 1;
    }else {
      next = path.erase(next); // remove it
    }
  }

  ans.push_back(*next);
  cout<<*next<<"\n";
  // add the distance back to the root
  p += graph[*curr][*next];




//cout<<p<<"\n";
  //vector<int> v(ans);
  
long long int aa=twoOpt1(graph,ans,p,n);

  /* aa=twoOpt1(graph,ans,aa,n);
   aa=twoOpt1(graph,ans,aa,n);
   aa=twoOpt1(graph,ans,aa,n);
  */

  t2=clock();

cout<<"\n"<<aa-((n-1)*d)<<"\n";

  print(ans);

  //cout<<p-((n-1)*d)<<"\n";
    float diff=(float)t2-(float)t1;
  diff=diff/CLOCKS_PER_SEC;
  cout<<"TIME: "<<diff<<"\n";
	
    return 0;
}




/*1. 2 opt post bb , nn , christo 
2. on some permutation ( 5) */