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
public class FoodSource extends State {

    public static final int defaultAmount = 100;
    //the furthest the foodSource could be from the nest (origin)
    static public final int MAXXdist = Renderer.width / 2;
    static public final int MAXYdist = Renderer.height / 2;
    public static final double phomDecayK = .1;
    public static final double phomAddK = 4;
    private int x, y, amount;
    private double phom = 0;

    FoodSource(int X, int Y, int A) {
        isActive = true;
        x = X;
        y = Y;
        amount = A;
        timeOut = 0;
    }

    FoodSource(int X, int Y) {
        isActive = true;
        x = X;
        y = Y;
        amount = defaultAmount;
        timeOut = 0;
    }

    FoodSource(int xpos, int ypos, int timeOut, int i) {
        setTimeOut(timeOut);
        //isActive = true;
        x = xpos;
        y = ypos;
        amount = i;
    }

    public void updatePhom() {
        double change = phomAddK * numAnts / getDistance() - phomDecayK * phom;
        if (change > -phom) {
            phom += change;
        }
    }

    public double getPhom() {
        return phom;
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

    public String toString() {
        return "dist:" + getDistance() + "| X:" + x + "| Y:" + y + "| phom:" + phom +"| timeOut:"+timeOut+ "| numAnts:" + numAnts;
    }
}
