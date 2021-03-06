import java.lang.*;
import java.util.*;

public abstract class GA extends Object
{
 protected int     GA_numChromesInit;
 protected int     GA_numChromes;
 protected int     GA_numGenes;
 protected double  GA_mutFact;
 protected int     GA_numIterations;
 protected ArrayList<Chromosome> GA_pop;
 protected String GA_target;

 public GA(String ParamFile, String target)
    {
        GetParams GP        = new GetParams(ParamFile);
        Parameters P        = GP.GetParameters();
        GA_numChromesInit   = P.GetNumChromesI();
        GA_numChromes       = P.GetNumChromes();
        GA_numGenes         = P.GetNumGenes();
        GA_mutFact          = P.GetMutFact();
        GA_numIterations    = P.GetNumIterations();
        GA_pop              = new ArrayList<Chromosome>();
        GA_target           = target;
        }

 public void DisplayParams()
    {
        System.out.print("Initial Chromosomes:  ");
        System.out.println(GA_numChromesInit);
        System.out.print("Chromosomes: ");
        System.out.println(GA_numChromes);
        System.out.print("Genes: ");
        System.out.println(GA_numGenes);
        System.out.print("Mutation Factor: ");
        System.out.println(GA_mutFact);
        System.out.print("Iterations: ");
        System.out.println(GA_numIterations);
    }

 public void DisplayPop()
    {
        Iterator<Chromosome> itr = GA_pop.iterator();
        System.out.println("Number\tContents\t\tCost");
        
        int chromeNum = 0;
        while (itr.hasNext())
        {
            Chromosome chrome = itr.next();
            System.out.print(chromeNum);
            ++chromeNum;
            System.out.print("\t");
            DisplayChromosome(chrome);
            System.out.println();
        }
    }

 public void DisplayBest(int iterationCt)
    {
        Chromosome chrome = GA_pop.get(0);
        System.out.print("Iteration: ");
        System.out.print(iterationCt);
        System.out.print("\t");
        DisplayChromosome(chrome);
        System.out.println();
    }

 private void DisplayChromosome(Chromosome chrome)
        {
            chrome.DisplayGenes();
            System.out.print("\t\t\t");
            System.out.print(chrome.GetCost());
        }

 protected void SortPop()
    {
       Collections.sort(GA_pop, new CostComparator());
    }

 private class CostComparator implements Comparator <Chromosome>
    {
        int result;
        public int compare(Chromosome obj1, Chromosome obj2)
	{
            result = Integer.valueOf( obj1.GetCost() ).compareTo(
                     Integer.valueOf( obj2.GetCost() ) );
	    return result;
        }
    }
 
 protected void TidyUp()
    {
        int end = GA_numChromesInit - 1;
        while (GA_pop.size() > GA_numChromes)
        {
            GA_pop.remove(end);
            end--;
        }
    }

 protected void Mutate() 
    {
        int totalGenes  = (GA_numGenes * GA_numChromes);
        int numMutate   = (int) (totalGenes * GA_mutFact);
        Random rnum     = new Random();

        for (int i = 0; i < numMutate; i++) 
        {
            //position of chromosome to mutate--but not the first one
            //the number generated is in the range: [1..GA_numChromes)
            
            int chromMut = 1 + (rnum.nextInt(GA_numChromes - 1));
            
            int geneMut = rnum.nextInt(7) +1; //pos of mutated gene

            int geneMut2 = rnum.nextInt(7) +1;
            
            
            //System.out.print(GA_numChromes);
            Chromosome newChromosome = GA_pop.remove(chromMut); //get chromosome
            
            char temp = newChromosome.GetGene(geneMut);
            newChromosome.SetGene(geneMut, newChromosome.GetGene(geneMut2));
            newChromosome.SetGene(geneMut2,temp);
            
            GA_pop.add(newChromosome); //add mutated chromosome at the end
        }
        
    }
 
 protected void InitPop()
    {
    
        Random rnum = new Random();
        char letter;
        for (int index = 0; index < GA_numChromesInit; index++)
        {
            Chromosome Chrom = new Chromosome(GA_numGenes);
            
            for (int j = 0; j < GA_numGenes-1; j++)
                { 
                    letter = (char) (j + 97); //97 is the value 'a'
                    Chrom.SetGene(j,letter);
                }
            Chrom.SetGene(GA_numGenes-1,'w');
            
            for(int i = 0; i < GA_numGenes-2; i++){
                int randIndex = (int)(Math.random() * GA_numGenes-1);
                char temp = Chrom.GetGene(i);
                Chrom.SetGene(i,Chrom.GetGene(randIndex));
                Chrom.SetGene(randIndex,temp);
            }
            

            char temp = Chrom.GetGene(0);
            Chrom.SetGene(GA_numGenes-1,temp);
            

            Chrom.SetCost(0);
            GA_pop.add(Chrom);
        }
    }
 protected abstract void ComputeCost();

 protected void Evolve()
    {
        int iterationCt = 0;
        Pair pairs      = new Pair(GA_pop);
        int numPairs    = pairs.SimplePair();
        boolean found   = false;

        while (iterationCt < GA_numIterations)
            {
                Mate mate = new Mate(GA_pop,GA_numGenes,GA_numChromes);
                GA_pop = mate.Crossover(GA_pop,numPairs);
                Mutate();
                
                ComputeCost();
                
                SortPop();
                
                Chromosome chrome = GA_pop.get(0); //get the best guess
                
                DisplayBest(iterationCt); //print it

                if (chrome.Equals(GA_target)) //if it's equal to the target, stop
                    break;
                ++iterationCt;
            }
    }

}

