import java.util.*;
import java.lang.*;
import java.io.*;


public class TSP extends GA
{
 private int TSP_target;
 private int[][] distanceMatrix;

 public TSP(String fileName, String startFile)
    {
       super(fileName,startFile);
       TSP_target = 12;
       GA_numGenes = 9;
        try{
        distanceMatrix = ReadDistanceTable(startFile);
        
        /* prints distance matrix
        int n = 8;
            for(int i=0;i<n;++i){
                for(int j=0;j<n;++j){
                    System.out.print(distanceMatrix[i][j] + " ");
                }
                System.out.print("\n");
            }
        */
        }
        catch(Exception e){
            System.out.println("file not found" + " " + startFile);
        }

        InitPop();
    }


 public void InitPop()
    {
        
        super.InitPop();
        ComputeCost();
        SortPop();
        TidyUp();
        
    }

 public void DisplayParams()
    {
        
        System.out.print("Target: ");
        System.out.println(TSP_target);
        super.DisplayParams();
        
    }

public int[][] ReadDistanceTable(String fileName)
    throws java.io.FileNotFoundException{
        
        Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
            int rows = 8;
            int columns = 8;
            int [][] myArray = new int[rows][columns];
            while(sc.hasNextLine()) {
                for (int i=0; i<myArray.length; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j=0; j<line.length; j++) {
                        myArray[i][j] = Integer.parseInt(line[j]);
                     }
                }
            }

        return myArray;
    }


 protected void ComputeCost()
    {
        char gene1 = 'a';
        char gene2 ='b';
        int val1 = 0;
        int val2 = 0;
        
        for (int i = 0; i < GA_pop.size(); i++)
        {
            int cost = 0;
            Chromosome chrom = GA_pop.remove(i);
            
            
            gene1 = chrom.GetGene(0);
            //chrom.DisplayGenes();
            //System.out.print("\n");
            for (int j = 1; j < GA_numGenes ; j++){
                
                gene2 = chrom.GetGene(j);

                val1 = (int)(gene1) - 97;
                val2 = (int)(gene2) - 97;
                               
                
                //System.out.print( j + " "+gene1 + " " + gene2 +" " + val1 + " " + val2 + ' '+cost+ "\n");
                cost = cost + distanceMatrix[val1][val2];

                gene1 = gene2;
                
            }
            //System.out.print('\n');
            //if(i == 2){System.exit(0);}


            chrom.SetCost(cost);
            GA_pop.add(i,chrom);
        }
        
    }
 //in earlier versions (as on ada) Evolve() from GA is here
 }

