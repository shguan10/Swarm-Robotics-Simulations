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
public class Ant {

    private int state = 0;//default state (nest)
    private int prevstate = 0;

    public int getState() {
        return state;
    }

    private void setState(int i) {
        prevstate = state;
        Simulation.states.get(prevstate).removeAnt();
        state = i;
        Simulation.states.get(state).addAnt();
    }

    void decideGoNextState() {
        double[] col = Simulation.runningTotalMatrix[state];

        //random int in range (0,w-1)
        Random rand = new Random();
        double chance = rand.nextDouble() * col[col.length - 1];

        //searches for the decision
        int i = 0;
        for (; i < col.length; i++) {
            if (col[i] > chance) {
                break;
            }
        }
        setState(i + 2);
    }
}
