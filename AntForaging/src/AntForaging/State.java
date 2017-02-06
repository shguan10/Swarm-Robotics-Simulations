/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AntForaging;

/**
 *
 * @author Xinyu
 */
class State {

    int numAnts = 0;

    public String toString() {
        return "numAnts: " + numAnts;
    }

    public void updatePhom() {
    }

    public void addAnt() {
        numAnts++;
    }

    public void removeAnt() {
        numAnts--;
    }
}
