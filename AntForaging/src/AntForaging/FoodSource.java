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
public class FoodSource extends State{

    private int x, y, amount;
    private boolean isActive;
    public static final int defaultAmount=100;
    private int timeOut;//time before the FoodSource is active
    private double phom =0;
    
    
    //the furthest the foodSource could be from the nest (origin)
    static public final int MAXXdist=200;
    static public final int MAXYdist=200;
    public static final double phomDecayK = 2;
    public static final double phomAddK = 4;
    
    FoodSource(int X, int Y, int A) {
        isActive = true;
        x = X;
        y = Y;
        amount = A;
        timeOut=0;
    }
    FoodSource(int X,int Y){
        isActive = true;
        x = X;
        y = Y;
        amount = defaultAmount;
        timeOut=0;
    }

    public void updatePhom(){
        double change = phomAddK*numAnts/getDistance()-phomDecayK*phom;
        if(change>-phom)phom+=change;
    }
    
    public double getPhom(){
        return phom;
    }
    
    public void setIsActive(boolean isOn){
        isActive=isOn;
    }
    
    public boolean isActive(){
        return isActive;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //this method returns the distance from the food source to the nest
    public double getDistance() {
        return Math.pow((double) x * x + y * y, 0.5);
    }

    //returns the amount of food left at this source
    public int returnAmount() {
        return amount;
    }

    public void removeFood(int i) {
        amount -= i;
    }
}