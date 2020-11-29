import java.util.*;
import java.lang.*;
import java.io.*;
import Jama.*;
public class SVD {

	public static void main(String[] args) throws Exception {
		

		// TODO Auto-generated method stub
		String ratingF = "C:\\Users\\swetha.jvn\\Downloads\\training.csv";
		String test = "C:\\Users\\swetha.jvn\\Downloads\\test1.csv";
		String moviesF = "C:\\Users\\swetha.jvn\\Downloads\\movies1.csv";
		
		//int[] movies = new int[9125];
		HashMap<Integer,Integer> hm=new HashMap<Integer,Integer>(); 
		//System.out.println("Hashing:");
		BufferedReader br ;
		int s=-1;
		br = new BufferedReader(new FileReader(moviesF));
		String line;
		for (;(line = br.readLine()) != null;s++) {
			String[] temp = line.split(",");
			if(s==-1)
				continue;
			
			else
			{
				
				hm.put(Integer.parseInt(temp[0]),s);
				//System.out.println(temp[0]+"--"+s);
			}
		}
		br.close();
		double[][] adj = new double[671][807];
		double[][] rev = new double[807][671];
		double[][] res = new double[671][671];
		double[][] test1 = new double[671][807];
		
		//extracting training data
		s = 0;
		br = new BufferedReader(new FileReader(ratingF));
        for (;(line = br.readLine()) != null;s++) {
        	if(s==0)
        		continue;
            // use comma as separator
            String[] temp = line.split(",");
           /* for(int k=0;k<temp.length;k++)
            {
            	System.out.print(temp[k]+" ");
            }*/
            //System.out.println();
            int a = Integer.parseInt(temp[0])-1;
            //System.out.print(" 1st:"+ a);
            int b = Integer.parseInt(temp[1]);
            //System.out.print(" 2nd:"+ b);
            //System.out.println(" temp[2]: "+ temp[2]);
            //System.out.println("b: "+b+"get(b): "+hm.get(b));
            adj[a][hm.get(b)] = Double.parseDouble(temp[2]);
            rev[hm.get(b)][a]=Double.parseDouble(temp[2]);
            s++;
            
        }
        
        
        //Extracting testing data
        s=0;
        BufferedReader br1 = new BufferedReader(new FileReader(test));
        for (;(line = br1.readLine()) != null;s++) {
        	if(s==0)
        		continue;
            // use comma as separator
            String[] temp = line.split(",");
           /* for(int k=0;k<temp.length;k++)
            {
            	System.out.print(temp[k]+" ");
            }*/
            //System.out.println();
            int a = Integer.parseInt(temp[0])-1;
            //System.out.print(" 1st:"+ a);
            int b = Integer.parseInt(temp[1]);
            //System.out.print(" 2nd:"+ b);
            //System.out.println(" temp[2]: "+ temp[2]);
            //System.out.println("b: "+b+"get(b): "+hm.get(b));
            test1[a][hm.get(b)] = Double.parseDouble(temp[2]);
            //rev[hm.get(b)][a]=Double.parseDouble(temp[2]);
            s++;
            
        }
        //Collections.shuffle(Arrays.asList(adj));
        double sum=0;
        long startTime = System.currentTimeMillis();
        Matrix a = new Matrix(adj,671,807);
        Matrix b = new Matrix(rev,807,671);
        Matrix ans = a.times(b);
        /*for(int i=0;i<a.getRowDimension();i++)
        {
        	for(int j=0;j<b.getColumnDimension();j++)
        	{
        		System.out.print(ans.get(i, j)+" ");
        	}
        	System.out.println();
        }*/
        
        //System.out.println("\n\nEIGENVECTOR MATRIX OF U:");  
		EigenvalueDecomposition e = new EigenvalueDecomposition(ans);
		Matrix eigenV = e.getV();  //eigenV = U
		
		/*for(int i=0;i<eigenV.getRowDimension();i++)
        {
        	for(int j=0;j<eigenV.getColumnDimension();j++)
        	{
        		System.out.print(eigenV.get(i, j)+" ");
        	}
        	System.out.println();
        }*/
		
		
		//Arranging in order, hence changing the order of columns in U
		int col = eigenV.getColumnDimension();
		for(int i=0;i<eigenV.getRowDimension();i++)
		{
			for(int j=0;j<eigenV.getColumnDimension()/2;j++)
			{
				double temp = eigenV.get(i,col-j-1);
				eigenV.set(i,col-j-1,eigenV.get(i, j));
				eigenV.set(i, j, temp);
			}
		}
		
		
		//System.out.println();
		
		
		/*double[] mid = e.getRealEigenvalues();
		int len = mid.length;
		double[][] sig = new double[len][len];
		System.out.println("*************Printing sigma matrix*********************");
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<len;j++)
			{
				if(i==j)
				{
					sig[i][j] = mid[i];
				}
				else
				{
					sig[i][j] = 0;
				}
				System.out.print(sig[i][j]+" ");
			}
			System.out.println();
		}*/
		//System.out.println("*************Printing sigma matrix*********************");
		Matrix sigma = e.getD();
		
		/*for(int i=0;i<sigma.getRowDimension();i++)
        {
        	for(int j=0;j<sigma.getColumnDimension();j++)
        	{
        		System.out.print(sigma.get(i, j)+" ");
        	}
        	System.out.println();
        }*/
		int rows = sigma.getRowDimension();
		for (int i = 0; i < rows / 2; i++) {
            double temp = sigma.get(i, i);
            sigma.set(i, i, sigma.get(rows-i-1, rows-i-1)); 
            sigma.set(rows-i-1, rows-i-1, temp);
        }
		//System.out.println("*************Printing final sigma matrix*********************");
		for(int i=0;i<sigma.getRowDimension();i++)
        {
        	for(int j=0;j<sigma.getColumnDimension();j++)
        	{
        		//System.out.print(sigma.get(i, j)+" ");
        	}
        	//System.out.println();
        }
		//Matrix sigma = e.getD();
		double totSing = 0;
		for(int i=0;i<sigma.getRowDimension();i++)
        {
        	totSing = totSing + Math.pow(sigma.get(i,i),2);
        	//System.out.println();
        }
		//System.out.println("total of singualar values totSing:- "+ totSing);
		double[][] sigmaFinalZ = new double[sigma.getRowDimension()][sigma.getColumnDimension()];
		int conceptZ = 0;
		int concept9=0; boolean done = true;
		//For removing "negative" eigen values and storing them in sigmaFinalZ matrix
		double summ=0;
		int count=0;
		for(int i=0;i<sigma.getColumnDimension();i++)
		{
			summ = summ + Math.pow(sigma.get(i, i),2);
			//System.out.print("summ: " + summ + "--summ/totSing: "+ summ/totSing+"******");
			count++;
			if(summ/totSing<=0.95)
			{
				continue;
			}
			else
			{
				break;
			}
		}
		concept9=count;
		
		//To remove negative eigen values
		for(int i=0;i<sigma.getRowDimension();i++)
		{
			for(int j=0;j<sigma.getColumnDimension();j++)
			{
				
				
				if(sigma.get(i, j)>0)
				{
				conceptZ++;
				sigmaFinalZ[i][j] = sigma.get(i, j);
				sigmaFinalZ[i][j] = Math.sqrt(sigmaFinalZ[i][j]);
				}
				else
				{
					
					sigmaFinalZ[i][j] = 0;
				}
			}
		}
		Matrix sigmaF = new Matrix(sigmaFinalZ,sigma.getRowDimension(),sigma.getColumnDimension());
		
		Matrix revA = b.times(a);
		//System.out.println("Printing reverse A\n\n*******\n\n");
		/*for(int i=0;i<revA.getRowDimension();i++)
        {
        	for(int j=0;j<revA.getColumnDimension();j++)
        	{
        		System.out.print(revA.get(i, j)+" ");
        	}
        	System.out.println();
        }*/
		
		//System.out.println("\n\nEIGENVECTOR MATRIX V:");
		EigenvalueDecomposition e1 = new EigenvalueDecomposition(revA);
		Matrix revEigenV = e1.getV();
		Matrix Vt = revEigenV.transpose();
		
		
		//Arranging Vt in order, hence swapping rows of Vt
		int row = Vt.getRowDimension();
		for(int j=0;j<Vt.getColumnDimension();j++)
		{
			for(int i=0;i<Vt.getRowDimension()/2;i++)
			{
				double temp = Vt.get(row-i-1, j);
				Vt.set(row-i-1, j, Vt.get(i, j));
				Vt.set(i, j, temp);
			}
		}
		
		/*double[][] Vtm = Vt.getArray();
		for(int i=0;i<Vt.getRowDimension();i++)
		{
			if(i<conceptZ)
			{
				continue;
			}
			else
			{
				Vtm[i] = null;
			}
		}*/
		
		
		//Resizing U,sigma and Vt matrices after cancelling out negative eigen values
		Matrix Vz = new Matrix(Vt.getArray(),conceptZ,Vt.getColumnDimension());
		
		Matrix Uz = new Matrix(eigenV.getArray(),eigenV.getRowDimension(),conceptZ);
		
		Matrix sigZ = new Matrix(sigmaFinalZ,conceptZ,conceptZ);
		
		Matrix temp = Uz.times(sigZ);
		Matrix temp2 = temp.times(Vz);
		
		double error = 0;
		int total=0;
		
		
		PriorityQueue<Double> pQueue =new PriorityQueue<Double>();
		
		//Calculating error on testing data
		for(int i=0;i<temp2.getRowDimension();i++)
		{
			for(int j=0;j<temp2.getColumnDimension();j++)
			{
				if(test1[i][j]!=0)
				{
					if(Math.pow(temp2.get(i, j)-test1[i][j], 2)!=0)
					{
						pQueue.add(Math.pow(temp2.get(i, j)-test1[i][j], 2));
					}
					error = error + Math.pow(temp2.get(i, j)-test1[i][j], 2);
					total++;
				}
				
			}
			
		}
		double spearman = 0;
        spearman = 1 - (6*(error/(total*((Math.pow(total, 2))-1))));
        System.out.println("spearman:"+spearman);
		double kval = 0;
        for(int i=0;i<100;i++)
        {
        	kval = kval + pQueue.remove();
        }
        kval = Math.sqrt(kval);
        kval = kval/total;
        pQueue.clear();
        System.out.println("kval:"+kval);
        
		//System.out.println("***********************************************************************************************");
		error = Math.sqrt(error);
		error = error/total;
		System.out.println("\n\nError: "+ error + "\n\n\n");
		System.out.println("conceptz: "+ conceptZ + ";;;;;-----;;;;;;concept9: "+ concept9);
		long endTime = System.currentTimeMillis();
		long totalTimeZero = endTime-startTime;
		System.out.println("Time:"+totalTimeZero);
		System.out.println("******************90% Energy**********************************************");
		
		Matrix V9 = new Matrix(Vt.getArray(),concept9,Vt.getColumnDimension());
		Matrix U9 = new Matrix(eigenV.getArray(),eigenV.getRowDimension(),concept9);
		Matrix sig9 = new Matrix(sigmaFinalZ,concept9,concept9);
		
		Matrix temp9 = U9.times(sig9);
		//System.out.println("first");
		Matrix temp2_9 = temp9.times(V9);
		//System.out.println("second");
		
		
		double error9 = 0;
		int total9=0;
		for(int i=0;i<temp2_9.getRowDimension();i++)
		{
			for(int j=0;j<temp2_9.getColumnDimension();j++)
			{
				if(test1[i][j]!=0)
				{
					if(Math.pow(temp2_9.get(i, j)-test1[i][j], 2)!=0)
					{
						pQueue.add(Math.pow(temp2_9.get(i, j)-test1[i][j], 2));
					}
					//System.out.print("error calc......");
					error9 = error9 + Math.pow(temp2_9.get(i, j)-test1[i][j], 2);
					total9++;
				}
				
			}
			
		}
		
        spearman = 1 - (6*(error9/total9*((Math.pow(total9, 2))-1)));
        System.out.println("spearman:"+spearman);
		kval = 0;
        for(int i=0;i<100;i++)
        {
        	kval = kval + pQueue.remove();
        }
        kval = Math.sqrt(kval);
        kval = kval/total;
        pQueue.clear();
        System.out.println("Precision on top k 90% energy:"+kval);
		//System.out.println("***********************************************************************************************");
		error9 = Math.sqrt(error9);
		error9 = error9/total9;
		System.out.println("Error: "+ error9 + "\n\n\n");
		/*for(int i=0;i<revEigenV.getRowDimension();i++)
        {
        	for(int j=0;j<revEigenV.getColumnDimension();j++)
        	{
        		System.out.print(revEigenV.get(i, j)+" ");
        	}
        	System.out.println();
        }*/
		long endTime9 = System.currentTimeMillis();
		long totalTime9 = endTime9- startTime;
		System.out.println("totalTime 90%energy:"+totalTime9);
		
		
		
		
	}

}
