/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AntForaging;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Xinyu
 */
public class Simulation {

    static final int TIMESTEPS = 900;
    static final int NUMANTS = 100;
    static final int NUMFOOD = 10;

    static final int defaultHomeFreeWeight = 1;
    static final int defaultFreeHomeWeight = 1;
    static final double homeFoodWeightK = 2;
    static final double foodHomeWeightK = 2;
    static final double freeFoodWeightK = 2;

    static Ant[] ants = new Ant[NUMANTS];

    public static ArrayList<State> states = new ArrayList<>();
    public static double[][] transitionMatrix = new double[NUMFOOD + 2][NUMFOOD + 2];//[fromIndex][toIndex], 0 is nest, 1 is free space
    public static double[][] runningTotalMatrix = new double[NUMFOOD + 2][NUMFOOD + 2];//[fromIndex][toIndex], 0 is nest, 1 is free space

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rand = new Random();

        System.out.println("Begin Simulation");
        for (int i = 0; i < NUMANTS; i++) {
            ants[i] = new Ant();
        }

        states.add(new Nest());
        states.add(new FreeSpace());
        //initialize the foodSources
        for (int i = 0; i < NUMFOOD; i++) {
            int xpos = rand.nextInt(FoodSource.MAXXdist * 2 + 1) - FoodSource.MAXXdist;
            int ypos = rand.nextInt(FoodSource.MAXYdist * 2 + 1) - FoodSource.MAXYdist;
            states.add(new FoodSource(xpos, ypos, FoodSource.defaultAmount - i));
            System.out.println(states.get(i+2));
        }
        //initialize transitionMatrix
        for (int i = 0; i < transitionMatrix.length; i++) {
            for (int j = 0; j < transitionMatrix[i].length; j++) {
                transitionMatrix[i][j] = 0;
            }
        }
        transitionMatrix[0][0] = 0;//ant must change states every timestep
        transitionMatrix[0][1] = defaultHomeFreeWeight;
        transitionMatrix[1][0] = defaultFreeHomeWeight;
        transitionMatrix[1][1] = 0;

        //***BEGIN SIMULATION***//  
        for (int t = 0; t < TIMESTEPS; t++) {
            //calculate runningTotalMatrix for all ants
            for (int c = 0; c < transitionMatrix.length; c++) {
                runningTotalMatrix[c][0] = transitionMatrix[c][0];
                for (int i = 1; i < runningTotalMatrix[c].length; i++) {
                    runningTotalMatrix[c][i] = transitionMatrix[c][i] + runningTotalMatrix[c][i - 1];
                }
            }
            for (Ant a : ants) {
                a.decideGoNextState();
            }
            updateMatrix();
            //write phom levels to file for rendering
        }
        for(State s:states){
            System.out.println(s);
        }
    }

    private static void updateMatrix() {
        for (State s : states) {
            s.updatePhom();
        }

        //update home column
        for (int i = 2; i < transitionMatrix[0].length; i++) {
            transitionMatrix[0][i] = ((FoodSource) states.get(i)).getPhom() * homeFoodWeightK;
        }

        //update freespace column
        for (int i = 2; i < transitionMatrix[1].length; i++) {//TODO include a/A +R
            transitionMatrix[1][i] = ((FoodSource) states.get(i)).getPhom() * freeFoodWeightK;
        }

        for (int i = 2; i < transitionMatrix.length; i++) {
            transitionMatrix[i][0] = ((FoodSource) states.get(i)).getPhom() * foodHomeWeightK;
        }
    }
}
