/*
 * Created on 16.08.2004
 *
 */
package utils;

import gui.*;
import formula.*;
import java.util.*;

/**
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 *
 */
public class CalculatorThread extends Thread {

	private AppletPanel aPanel;
	private volatile Stack calcStack = new Stack();
	private volatile boolean running = true;
	private volatile boolean animating = false;
	private volatile Formula lastFormula;
	private volatile int lastPaintStatus;	

	/**
	 * Creates a new calculator-thread.
	 */
	public CalculatorThread(AppletPanel ap) {
		super();
		aPanel = ap;
	}


	public void start() {
		super.start();
		running = true;
		animating = false;
	}
	
	
	/**
	 * Stops the while-loops in run().
	 */
	// NOTE: never used
	public void stopThread() {
		running = false;
		animating = false;
	}


	public void run() {
		System.out.println("DEBUG[CalculatorThread]: start running");
		Formula form;
		while (running) {
			while (animating) {
				calcStep();
				try {
					sleep(aPanel.getControlPanel().getSpeed());
				} catch (InterruptedException ie) {
					ie.printStackTrace(System.err);
				} finally {
					if (calcStack.isEmpty()) {
						stopAnimation();
					}
					if (lastFormula != null) {
						lastFormula.setPaintStatus(lastPaintStatus);
						lastFormula = null;
					}
				}
			}
			try {
				sleep(10);
			} catch (InterruptedException ie) {
				ie.printStackTrace(System.err);
			}
			
		}
		System.out.println("DEBUG[CalculatorThread]: stop running");
	}



	/**
	 * Calculates the top-formula on the stack, but skips all formula
	 * that already have a result.
	 */
	public void calcStep() {
		if (!calcStack.isEmpty()) {
			Formula form = (Formula)calcStack.pop();
			// skip formulas with result != null:
			while (form.isResultCalculated() && !calcStack.isEmpty()) {
				form = (Formula)calcStack.pop();
			}
			try {
				form.calc();
			} catch (FormulaException fe) {
				fe.printStackTrace(System.err);
				aPanel.getControlPanel().updateLblFormula(fe.getMessage());
			}
			if (lastFormula != null) {
				lastFormula.setPaintStatus(lastPaintStatus);
			}
			lastPaintStatus = form.getPaintStatus();
			form.setPaintStatus(Formula.PAINTSTATUS_CALCULATING | form.getPaintStatus());
			lastFormula = form;
		} else {
			animating = false;
		}
	}
	
	
	/**
	 * Calculates all elements on the stack.
	 */
	public void calcAll() {
		while (!calcStack.isEmpty()) {
			calcStep();
		}
		if (lastFormula != null) {
			lastFormula.setPaintStatus(lastPaintStatus);
			lastFormula = null;
		}
	}
	
	
	/**
	 * Starts the calc-animation in run().
	 */
	public void calcAnimated() {
		if (!calcStack.isEmpty()) {
			animating = true;
		}
	}
	
	
	/**
	 * Initialises the thread by creating a stack with all formula-objects in the tree.
	 * @param rootFormula
	 */
	public void initCalculation(Formula rootFormula) {
		animating = false;
		calcStack.clear();
		pushTree(rootFormula);
	}
	
	
	/**
	 * Returns true if there is a non-empty stack (initCalculation was called
	 * and no calc* method was called upto the end of the formula-tree). 
	 * @return true if there are still elements to calculate
	 */
	public boolean isInitialized() {
		return !calcStack.isEmpty();
	}
	
	
	/**
	 * @return true if animation-loop is active
	 */
	public boolean isAnimating() {
		return animating;
	}
	
	
	/**
	 * Stops the animation-loop.
	 */
	// NOTE: stopAnimation bricht die sleep-Anweisung nicht ab, also k�nnte gleich darauf die Animation neu gestartet werden, obwohl sie noch nicht fertig ist.
	public void stopAnimation() {
		animating = false;
		if (lastFormula != null) {
			lastFormula.setPaintStatus(lastPaintStatus);
			lastFormula = null;
		}
		aPanel.getControlPanel().setAnimating(false);
	}
	
	
	/**
	 * Recursive creates a stack of the whole tree. 
	 * @param form current formula-object
	 */
	private void pushTree(Formula form) {
		calcStack.push(form);
		for (int i=0;i<form.getInputCount();i++) {
			pushTree(form.getInput(i));
		}
	}

}
