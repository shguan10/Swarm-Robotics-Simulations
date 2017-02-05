/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AntForaging;

import java.util.Random;

/**
 *
 * @author Xinyu
 */
public class Simulation {

    static final int TIMESTEPS = 900;
    static final int NUMANTS = 100;
    
    static final int defaultHomeFreeWeight = 1;
    static final int defaultFreeHomeWeight=1;
    
    static Ant[] ants = new Ant[NUMANTS];
    static final int NUMFOOD = 10;
    static FoodSource foodSources[] = new FoodSource[NUMFOOD];
    static double[][] transitionMatrix=new double[NUMFOOD+2][NUMFOOD+2];//[fromIndex][toIndex], 0 is nest, 1 is free space

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rand = new Random();
        
        //initialize the foodSources
        for (int i=0;i<foodSources.length;i++) {
            int xpos = rand.nextInt(FoodSource.MAXXdist*2+1) - FoodSource.MAXXdist;
            int ypos = rand.nextInt(FoodSource.MAXYdist*2+1) - FoodSource.MAXYdist;
            foodSources[i] = new FoodSource(xpos,ypos,FoodSource.defaultAmount-i);
        }
        //initialize transitionMatrix
        for(int i=0;i<transitionMatrix.length;i++){
            for(int j=0;j<transitionMatrix[i].length;j++)
            transitionMatrix[i][j]=0;
        }
        transitionMatrix[0][0]=0;//ant must change states every timestep
        transitionMatrix[0][1]=defaultHomeFreeWeight;
        transitionMatrix[1][0]=defaultFreeHomeWeight;
        transitionMatrix[1][1]=0;
        /*
        Variables:
        NUMANTS
        food sources
        amount of food at each source
        amount of phom at each food source
        transition matrix
        ant array
        
        psuedo-code
        for each time step
            for each ant
                decide next state to go to based on transition matrix
                update phom and weight for each entry in the transition matrix
                update where ant is
        write phom levels to file for pic producing
         */
        
        for (int t = 0; t < TIMESTEPS; t++) {
            for (Ant a : ants) {
                a.decideNextState();
                a.updateMatrix();
                a.updateState();
            }
            //write phom levels to file for rendering
        }
    }

}
