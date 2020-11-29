      import java.util.*;
      import java.lang.*;
      import java.io.*;
      import java.io.BufferedReader;
      import java.io.FileNotFoundException;
      import java.io.FileReader;
      import java.io.IOException;
      
       class colBase
      {
          
      	public static void main (String[] args) throws IOException
      	{
      	    long stime=System.currentTimeMillis();
      	    int i,j,n;
      	    double sum,max,rate,raten,rated;
      	    ///working mat
      	    double rat[][]=new double[674][808];
            ///original mat 
      	    double rato[][]=new double[674][808];
      	    ///testcase
      	    int teusmo[][]= new int[210][2];
      	    
      	    for(i=0;i<674;i++)
      	      for(j=0;j<808;j++)
      	        {rat[i][j]=0;rato[i][j]=0;}
      	    
      	     ///column=avg+vector+length+sim
      	    ///modified
      	    ///****input test data ***************
      	    String csvFile0 = "C:/Users/Barkha/Desktop/4-1/IR/project/Collaborative Filtering/testU.csv";
              BufferedReader br0 = null;
                      String linet = "";
              int k=0,u,m;
              String cvsSplitBy0 = ",";
                  br0 = new BufferedReader(new FileReader(csvFile0));
                  while ((linet = br0.readLine()) != null) {
      
                      /// use comma as separator
                      String[] test = linet.split(cvsSplitBy0);
                      ///System.out.println(test);
                     if(k<210)
                       {
                            u=Integer.parseInt(test[0]);
                      m=Integer.parseInt(test[1]);
                     teusmo[k][0]= u;
                     teusmo[k][1]=m ;
                     k++;
                    }
                    else
                    continue;
                    }
            
      	    ///*********************movie file input..............................................................................................................
      	    
      	    String csvFile1 = "C:/Users/Barkha/Desktop/4-1/IR/project/Collaborative Filtering/movies1.csv" ;	    
              BufferedReader br1 = null;
              String line1 = "";
              String cvsSplitBy1 = ",";
                  br1 = new BufferedReader(new FileReader(csvFile1));
                  ///Hashmap to index movies
                  HashMap<Integer,Integer> M=new HashMap<Integer,Integer>();
                  int xu=1;
                  while ((line1 = br1.readLine()) != null) {
      
                      /// use comma as separator
                      String[] li1 = line1.split(cvsSplitBy1);
                      int in=Integer.parseInt(li1[0]);
                      M.put(in,xu);xu++;
                      ///System.out.println( "yp "+M.get(in));
                     
                  }  
                      
      	    ///rating file input .............................................................................................................
      	    String csvFile = "C:/Users/Barkha/Desktop/4-1/IR/project/Collaborative Filtering/ratings1.csv";
              BufferedReader br = null;
              String line = "";
              String cvsSplitBy = ",";
                  br = new BufferedReader(new FileReader(csvFile));
                  while ((line = br.readLine()) != null) {
      
                      /// use comma as separator
                      String[] li = line.split(cvsSplitBy);
                     /// System.out.println("user= "+li[0]+"  movie= "+li[1]+" rating= "+li[2]);
                   
                      int user= Integer.parseInt(li[0]);
                      int movie= Integer.parseInt(li[1]);
                      double rating=Double.parseDouble(li[2]);
                      rato[user][M.get(movie)]=rating;
                      rat[user][M.get(movie)]=rating;
                    }
                  
                  int T=0;///total non-zero ratings 
                 double mu=0;
                 Scanner sc=new Scanner(System.in);
                 
                 ///finding col average which is stored in 1st cell of each col
                 for (j=1;j<808;j++)
                 {n=0;sum=0;
                     for(i=1;i<672;i++)
                     {
                         if(rat[i][j]!=0)
                         {sum+=rat[i][j];
                             n++;T++;mu+=rat[i][j];
                          }
                      }
                      rat[0][j]=sum/n;
                  }
                 mu=mu/T;
                  ///finding row average which is stored in 1st cell of each row
                 for (i=1;i<672;i++)
                 {n=0;sum=0;
                     for(j=1;j<808;j++)
                     {
                         if(rat[i][j]!=0)
                         {sum+=rat[i][j];
                             n++;
                          }
                      }
                      rat[i][0]=sum/n;
                  }
                 
                 
                 ///centralising to zero 
                 for (j=1;j<808;j++)
                 {
                     for(i=1;i<672;i++)
                     {
                         if(rat[i][j]!=0)
                         rat[i][j]-=rat[0][j];
                      }
                  }
                  
                  ///finding length of col vector which is stored in second last cell of each col
                 for (j=1;j<807;j++)
                 {   sum=0;
                     
                     for(i=1;i<672;i++)
                     {
                         if(rat[i][j]!=0)
                         sum+=(rat[i][j]*rat[i][j]);
                        }
                      rat[672][j]=Math.sqrt(sum);
                  } 
                  ///************************************************item-item similarity model****************************************************************************
                 ///System.out.println("You chose 1.Please enter user no. and the movie for which you want to know rating for");
                  ///finding item(mo2)-item similarity and storing in last cell of each row 
                 int q,test=150;
                 double bline,bx,bi,ero=0.0,resu=0.0,sp=0 ;
                  int itr;
                Comparator<obj> comparator2 =new myComp2();
                      PriorityQueue<obj> pre = new PriorityQueue<obj>(10,comparator2);
                  
                  
                  for(q=0;q<test;q++)
                {
                    
                    bline=0;bx=0;bi=0;                  
                    bx=rat[teusmo[q][0]][0]-mu;
                    bi=rat[0][M.get(teusmo[q][1])]-mu;
                    bline=mu+bx+bi;
                    
                    for (j=1;j<808;j++)
                    {   sum=0;
                     
                     for(i=1;i<672;i++)
                         sum+=(rat[i][M.get(teusmo[q][1])]*rat[i][j]);
                      ///sim
                      rat[673][j]=sum/(rat[672][j]*rat[672][M.get(teusmo[q][1])]);
                      //System.out.println("sim"+rat[673][j]);
                    }
                       ///******top 7 similar*****
                          Comparator<obj> comparator =new myComp();
                          PriorityQueue<obj> pq =
                          new PriorityQueue<obj>(7,comparator);
                          obj[] arr= new obj[807];
                          for(i=0;i<807;i++)
                          {
                              arr[i]= new obj(i,rat[673][i+1]);
                             if(i==M.get(teusmo[q][1]) || rato[teusmo[q][0]][i]==0||Double.isInfinite(rat[673][i+1]) ||Double.isNaN(rat[673][i+1]))continue;
                              pq.add(arr[i]);
                            }
                            int res=7;
                            double denom=0,num=0;
                            while(res--!=0)
                            {
                            obj temp= pq.remove();
                            //System.out.println(temp.ind+"-->"+temp.val);
                            denom+=temp.val;
                            num+=(rato[teusmo[q][0]][temp.ind]-bline)*temp.val;
                        }
                        pq.clear();
                        ///finding rating 
                        double frate=bline+(num/denom);
                        ero=frate-rato[teusmo[q][0]][M.get(teusmo[q][1])];
                        double pe= (ero/rato[teusmo[q][0]][M.get(teusmo[q][1])]);
                        
                       System.out.println();
                        System.out.print(q+"."+teusmo[q][0]+"->"+teusmo[q][1]+" In : "+rato[teusmo[q][0]][M.get(teusmo[q][1])]);
                         System.out.print(" Fin : "+frate);
                          System.out.print(" Error % : "+(pe*100));
                  
                        
                        ///precision
                          
                             if(pe<50){
                            obj aro= new obj(i,pe);
                            pre.add(aro);
                            }
                        
                        
                        
                        resu+=(ero*ero);
                    }   
                    System.out.println("Collaborative filtering with baseline");
                    System.out.println("rmse= "+(Math.sqrt(resu/test)));
                   System.out.println("total ratings :"+T+" mu ="+mu);
                     long etime=System.currentTimeMillis();
                 long ttime=etime-stime;
                
              
                    ///top10 precision
                    int rs=10;
                    double pr=0;
                            while(rs--!=0)
                            {
                            obj temp= pre.remove();
                            //System.out.println(" "+temp.val);
                            pr+=temp.val*temp.val;
                        }
                        pre.clear();
                        System.out.println("precision of top 10 : "+Math.sqrt(pr/10));
                        
                        ///pearson
                        sp=1-(6*resu)/(T*(T*T-1));
                        System.out.println("Spearman :"+sp);
                         System.out.println("Time Taken :"+ttime+" milliseconds");
                        System.out.println();
                }}
            