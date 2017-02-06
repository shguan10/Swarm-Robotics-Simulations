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
    boolean isActive=true;
    int timeOut;//time before the state is active
    
    public void setTimeOut(int i){
        timeOut=i;
        isActive = i == 0;
    }
    
    public void decreaseTimeOut(){
        setTimeOut(timeOut-1);
    }
    
    public boolean isActive() {
        return isActive;
    }
    public void setIsActive(boolean isOn) {
        isActive = isOn;
    }
    public int getNumAnts(){
        return numAnts;
    }
    
    public String toString() {
        return "numAnts: " + numAnts;
    }

    public double getPhom() {
        return 0;
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
