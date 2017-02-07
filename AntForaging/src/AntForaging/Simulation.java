package AntForaging;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Xinyu
 */
public class Simulation {

	static final int timesToRun = 50000;
	static final int TIMESTEPS = 135000;
	static final int NUMANTS = 100;
	static final int NUMFOOD = 10;

	static final int defaultHomeFreeWeight = 1;
	static final int defaultFreeHomeWeight = 1;
	static final double homeFoodWeightK = 10;
	static final double foodHomeWeightK = 10;
	static final double freeFoodWeightK = 10;
	static final double defaultFreeFood = 2;
	public static ArrayList<State> states = new ArrayList<>();
	public static double[][] transitionMatrix = new double[NUMFOOD + 2][NUMFOOD + 2];//[fromIndex][toIndex], 0 is nest, 1 is free space
	public static double[][] runningTotalMatrix = new double[NUMFOOD + 2][NUMFOOD + 2];//[fromIndex][toIndex], 0 is nest, 1 is free space
	public static Renderer r;
	static Ant[] ants = new Ant[NUMANTS];

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		r = new Renderer();

		System.out.println("Begin Simulation");
		int expected = 0;
		for (int iterate = 0; iterate < timesToRun; iterate++) {
			if (iterate % 100 == 0) {
				System.out.println(iterate);
				System.out.println("Expected results so far: " + expected);
			}
			for (int i = 0; i < NUMANTS; i++) {
				ants[i] = new Ant();
			}

			states.add(new Nest());
			states.add(new FreeSpace());
			//initialize the foodSources
			for (int i = 0; i < NUMFOOD; i++) {
				int xpos = rand.nextInt(FoodSource.MAXXdist * 2 + 1) - FoodSource.MAXXdist;
				int ypos = rand.nextInt(FoodSource.MAXYdist * 2 + 1) - FoodSource.MAXYdist;
				double dist = Math.pow(xpos * xpos + ypos * ypos + 0.0, .5);
				int timeOut = rand.nextInt((int) (TIMESTEPS / 2 / dist + 1.0));
				states.add(new FoodSource(xpos, ypos, timeOut, FoodSource.defaultAmount - i));
				//System.out.println(states.get(i + 2));
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
			//printMatrix();

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
				for (State s : states) {
					if (!s.isActive()) {
						s.decreaseTimeOut();
					}
				}
				r.render(states);
				//printMatrix();
			}
			//write phom levels to output
			double highestPhomState[] = {0, 0};//{index, phom}
			double closestState[] = {2, ((FoodSource) states.get(2)).getDistance()};
			for (int i = 0; i < states.size(); i++) {
				State s = states.get(i);
				//System.out.println(s);
				if (s.getPhom() > highestPhomState[1]) {
					highestPhomState[0] = i;
					highestPhomState[1] = s.getPhom();
				}
				if (i > 2 && ((FoodSource) s).getDistance() < closestState[1]) {
					closestState[0] = i;
					closestState[1] = ((FoodSource) s).getDistance();
				}
			}
			//System.out.println("\n\nClosest State: "+((FoodSource)states.get((int)closestState[0]))+"\nState with highest phom: "+((FoodSource)states.get((int)highestPhomState[0])));
			if (closestState[0] == highestPhomState[0]) {
				expected++;
			}
			states.removeAll(states);
		}
		System.out.println("Expected results: " + expected);
	}

	private static void updateMatrix() {
		for (State s : states) {
			s.updatePhom();
		}

		//update home column
		for (int i = 2; i < transitionMatrix[0].length; i++) {
			transitionMatrix[0][i] = states.get(i).getPhom() * homeFoodWeightK;
		}

		//update freespace column
		for (int i = 2; i < transitionMatrix[1].length; i++) {//TODO include a/A +R
			if (states.get(i).isActive()) {
				transitionMatrix[1][i] = states.get(i).getPhom() * freeFoodWeightK + defaultFreeFood;
			}
		}

		for (int i = 2; i < transitionMatrix.length; i++) {
			transitionMatrix[i][0] = states.get(i).getPhom() * foodHomeWeightK;
		}
		//printMatrix();
	}

	public static void printMatrix() {
		System.out.println("\nhome col:");
		for (int i = 0; i < transitionMatrix[0].length; i++) {
			System.out.print(transitionMatrix[0][i] + " ");
		}
		System.out.println("\nfree col:");
		for (int i = 0; i < transitionMatrix[1].length; i++) {
			System.out.print(transitionMatrix[1][i] + " ");
		}
		for (int i = 2; i < transitionMatrix.length; i++) {
			System.out.println("\nFood Source " + (i - 2));
			for (int j = 0; j < transitionMatrix[i].length; j++) {
				System.out.print(transitionMatrix[i][j] + " ");
			}
		}
	}
}
