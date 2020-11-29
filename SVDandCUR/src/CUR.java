import java.util.*;
import java.lang.*;
import java.io.*;
import Jama.*;
public class CUR {

	public static void main(String[] args) throws Exception{
		String training = "C:\\Users\\swetha.jvn\\Downloads\\training.csv";
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
		double[][] aa = new double[671][807];
		double[][] bb = new double[671][807];
		//double[][] res = new double[671][671];
		
		
		double[][] testing = new double[671][807];
		s = 0;
		
		//Extracting training data and storing in "aa" matrix
		br = new BufferedReader(new FileReader(training));
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
            aa[a][hm.get(b)] = Double.parseDouble(temp[2]);
            bb[a][hm.get(b)]=Double.parseDouble(temp[2]);
            s++;
            
        }
        
        
        //Extracting testing data and storing in "testing" matrix
        s = 0;
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
            //aa[a][hm.get(b)] = Double.parseDouble(temp[2]);
            //bb[a][hm.get(b)]=Double.parseDouble(temp[2]);
            testing[a][hm.get(b)] = Double.parseDouble(temp[2]);
            s++;
            
        }
	    int c = 100;
	    long startTime = System.currentTimeMillis();

        Matrix matA = new Matrix(aa);
        //System.out.println("\n******Rating matrix***********");
        /*for(int i=0;i<matA.getRowDimension();i++)
        {
        	for(int j=0;j<matA.getColumnDimension();j++)
        	{
        		System.out.print(matA.get(i, i)+" ");
        	}
        	System.out.println();
        }*/
        
       // int c = matA.rank()/10;
        double cp[] = new double[807], sum = 0;
        //setting c matrix 
        for (int i = 0; i < 807; i++) {
            for (int j = 0; j < 300; j++) {
                //System.out.println(cp[i]);
                cp[i] = (bb[j][i] * bb[j][i]) + cp[i];
            }
             //System.out.println(cp[i]);
            sum = sum + cp[i];

        }
//        System.out.println(sum);


        for (int i = 0; i < 807; i++) {
            cp[i] = cp[i] / sum;
            //System.out.print(i + " " + cp[i] + " ");
        }

        //System.out.println();
        double rp[] = new double[300];
        //setting r matrix
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 807; j++) {
                rp[i] = bb[i][j] * bb[i][j] + rp[i];
            }
        }

        for (int i = 0; i < 21; i++) {
            rp[i] = rp[i] / sum;
           //System.out.print(i + " " + rp[i] + " ");
        }

        Random ran = new Random();
        ran.setSeed(123456789);
        double column[][] = new double[300][c];
        //double column[][]=new double[21][1000];
        int k = 0;
        int coli[] = new int[c];
        
        for (int i = 0; i < c; i++) {
            int randoms = ran.nextInt(807); //column number 
            if(cp[randoms]!=0){
                coli[i] = randoms;
            }else{
                --i;
            }
        }
        
        for (int i = 0; i < c; i++) {
            
           // System.out.println("The seed is:" + rang + " ");
            for (int j = 0; j < 300; j++) {
                column[j][k] = bb[j][coli[i]] / Math.sqrt(c * cp[coli[i]]);
               //System.out.print(column[j][k] + " ");
            }
            //System.out.println();
            k++;
        }
        System.out.println();
        double row[][] = new double[c][807];
        int rowi[] = new int[c];
        k = 0;

        for (int i = 0; i < c; i++) {
            int randoms = ran.nextInt(20) + 1; //row number 
            if(rp[randoms]!=0){
                rowi[i] = randoms;
            }else{
                --i;
            }
        }

        for (int i = 0; i < c; i++) {

           // System.out.print("The seed is:" + randoms + " ");
            for (int j = 0; j < 807; j++) {
                row[k][j] = bb[rowi[i]][j] / Math.sqrt(c * rp[rowi[i]]);
                //System.out.print(row[k][j] + " ");
            }
          // System.out.println();
            k++;
        }

        //System.out.println("C matrix");
        /*for (int i = 0; i < c; i++) {
            for(int j = 0; j <c ;j++){
            System.out.print(column[j][i] + " ");
            }
            System.out.println();
        }*/
        
        //System.out.println();
        for (int i = 0; i < c; i++) {
            //System.out.println(rowi[i] + " ");
        }
        //System.out.println();
        //w matrix
        double w[][] = new double[c][c];
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < c; j++) {
                w[i][j] = bb[rowi[i]][coli[j]];
            }
        }

      //System.out.println("**********************************************************");
       // System.out.println("W matrix");
        //System.out.println("**********************************************************");
        /*for (int i = 0; i < c; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(w[i][j] + " ");
                //w[i][j]=b[rowi[i]][coli[i]];
            }
           System.out.println();
        }*/
        
        Matrix ww = new Matrix(w);
        Matrix wt = ww.transpose();
        Matrix aAt = ww.times(wt);
        EigenvalueDecomposition e = new EigenvalueDecomposition(aAt);
        Matrix X = e.getV();
        Matrix Z = e.getD();
        int col = X.getColumnDimension();
        for(int i=0;i<X.getRowDimension();i++)
		{
			for(int j=0;j<X.getColumnDimension()/2;j++)
			{
				double temp =X.get(i,col-j-1);
				X.set(i,col-j-1,X.get(i, j));
				X.set(i, j, temp);
			}
		}
        
        for(int i=0;i<Z.getColumnDimension();i++)
        {
        	//System.out.println("Z:"+Z.get(i,i));
        }
        int rows = Z.getRowDimension();
		for (int i = 0; i < rows / 2; i++) {
            double temp = Z.get(i, i);
            Z.set(i, i, Z.get(rows-i-1, rows-i-1)); 
            Z.set(rows-i-1, rows-i-1, temp);
        }
        Matrix Y = (new EigenvalueDecomposition(wt.times(ww))).getV();
        Matrix Yt = Y.transpose();
        int roww = Yt.getRowDimension();
		for(int j=0;j<Yt.getColumnDimension();j++)
		{
			for(int i=0;i<Yt.getRowDimension()/2;i++)
			{
				double temp = Yt.get(roww-i-1, j);
				Yt.set(roww-i-1, j, Yt.get(i, j));
				Yt.set(i, j, temp);
			}
		}
        Matrix Xt = X.transpose();
        Y = Yt.transpose();
        for(int i=0;i<Z.getColumnDimension();i++)
        {
        	if(Z.get(i, i)<0)
        	{
        		Z.set(i, i, 0);
        		//Z.set(i, i, 0);
        	}
        	
        }
        int concept = 0;
        for(int i=0;i<Z.getColumnDimension();i++)
        {
        	if(Z.get(i, i)>0)
        	{
        		//System.out.print(Z.get(i,i)+"--");
        		concept++;
        		Z.set(i,i,(1/Z.get(i, i)));
        		
        		//System.out.println(Z.get(i, i));
        	}
        }
        Z = new Matrix(Z.getArray(),concept,concept);
        Y = new Matrix(Y.getArray(),c,concept);
        Xt = new Matrix(Xt.getArray(),concept,c);
       /* for(int i=0;i<Z.getColumnDimension();i++)
        {
        	System.out.println(Z.get(i, i));
        }*/
        Matrix ans = (Y.times(Z)).times(Xt);
        //System.out.println("rows:"+ ans.getRowDimension()+";columns: "+ ans.getColumnDimension());
        Matrix finalM = ((new Matrix(column)).times(ans)).times(new Matrix(row));
        
        //System.out.println("rows:"+ finalM.getRowDimension()+";columns: "+ finalM.getColumnDimension());
        
        double total = 0;
        double summ =0;
       // System.out.println("*******************FINALLLL**************************");
        double avex=0;
        double avey=0;
        PriorityQueue<Double> pQueue =new PriorityQueue<Double>(); 
        
        
        //Calculating error on testing data
        for(int i=0;i<finalM.getRowDimension();i++)
        {
        	for(int j=0;j<finalM.getColumnDimension();j++)
        	{
        		if(testing[i][j]!=0)
        		{
        			avex+=finalM.get(i, j);
            		avey+=testing[i][j];
            		if(Math.pow(finalM.get(i, j)-testing[i][j], 2)!=0)
            		{pQueue.add(Math.pow(finalM.get(i, j)-testing[i][j], 2));}
            		summ = summ + Math.pow(finalM.get(i, j)-testing[i][j], 2);
            		//System.out.println("sum: " + summ+" "+i+" "+j+" finalM:"+finalM.get(i, j)+"aa[i][j]: "+aa[i][j]);
            		total++;
        		}
        		
        	}
        }
        double kval = 0;
        for(int i=0;i<100;i++)
        {
        	kval = kval + pQueue.remove();
        }
        kval = Math.sqrt(kval);
        kval = kval/total;
        System.out.println("kval:"+kval);
        
        //double cov = (xy/total)-((x/total)*(y/total));
        avex = avex/total;
        avey = avey/total;
        
        //double x=0;
        //double y=0;
        double xy=0;
        //int i;int j;
        for(int i=0;i<finalM.getRowDimension();i++)
        {
        	for(int  j=0;j<finalM.getColumnDimension();j++)
        	{
        		if(testing[i][j]!=0)
        		xy = xy + (finalM.get(i, j)-avex)*(testing[i][j]-avey);
        		
        	}
        }
        
        
        double cov = xy/total;
        
        //System.out.println("total: "+total);
        double spearman = 1 - (6*(summ/(total*((Math.pow(total, 2))-1))));
        System.out.println("spearman:"+spearman);
        summ = Math.sqrt(summ);
        
        summ = summ/total;
        System.out.println("Final error:"+summ);
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime-startTime;
        System.out.println("Time taken:"+totalTime);

	}
}
