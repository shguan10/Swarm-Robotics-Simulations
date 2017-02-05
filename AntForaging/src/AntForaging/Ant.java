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
public class Ant {

    private int state=0;//default state (nest)
    
    public int getState(){
        return state;
    }
    private void setState(int i){
        state=i;
    }
    void decideNextState() {
        
    }

    void updatePhom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void updateState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void updateMatrix() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
