///*
// * Created on 09.08.2004
// */
//package gui;
//
//import formula.*;
//import java.util.*;
//
///**
// * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
// */
//public class CalculateFormula {
//
//	//Starting Formula for calculating. Can be anywhere in a tree, but should be the root.
//	private static Formula whereToBegin;
//	private static Timer interval;
//
//	public static final Formula getWhereToBegin() {
//		return whereToBegin;
//	}
//
//	public static final void setWhereToBegin(Formula toSet) {
//		whereToBegin = toSet;
//	}
//
//	/**
//	 * Resets all calculations by setting result of all Formulas to null.
//	 */
//	public static final void ResetFormula() {
//		Formula[] allRoots = new Formula[Formula.getTreeListSize()];
//		allRoots = Formula.getTreeList();
//		for (int i=0; i < allRoots.length; i++) {
//			ResetFormula(allRoots[i]);
//		}
//	}
//
//	/**
//	 * Resets one tree.
//	 * @param root Which tree to reset.
//	 */
//	private static final void ResetFormula(Formula root) {
//		root.clearResult();
//		for (int i=0; i < root.getInputCount(); i++) {
//			ResetFormula(root.getInput(i));
//		}
//	}
//
//	/**
//	 * Calculated each Formula in intervales. Called from outside. 
//	 * @param speed Intervale in milliseconds.
//	 */
//	public final void AnimateFormula(int speed) {
//		interval = new Timer(true);
//		interval.schedule(new Animate(), 0, speed);
//	}
//
//	public static final void CancelAnimteFormula() {
//		interval.cancel();
//	}
//
////	/**
////	 * Calculated each Formula in intervales.
////	 * @param root Where to begin calculating.
////	 * @param speed Intervale in milliseconds.
////	 */
////	private static final void AnimateFormula(Formula root, int speed) {
////		
////	}
//
//	class Animate extends TimerTask {
//		public void run() {
//			if (whereToBegin == null) {
//				interval.cancel();
//			} else {
//				whereToBegin = StepFormula(whereToBegin);
//			}
//		}
//	}
//
//	/**
//	 * Calculates all Formulas at once. Called from outside.
//	 */
//	public static final void CalculateAllFormula() {
//		CalculateAllFormula(whereToBegin);
//	}
//
//	/**
//	 * Calculates all Formulas at once.
//	 * @param root Where to begin calculating.
//	 */
//	private static final void CalculateAllFormula(Formula root) {
//		if (root != null) {
//			CalculateAllFormula(StepFormula(root));
//		}
//	}
//
//	/**
//	 * Calculates Formula-Tree step by step. Called from outside.
//	 */
//	public static final void StepFormula() {
//		whereToBegin = StepFormula(whereToBegin);
//	}
//
//	/**
//	 * 
//	 * @param root Where to start calculatin.
//	 * @return Returns next Formula to be calculated.
//	 */
//	private static final Formula StepFormula(Formula root) {
//		//No tree found ^_^
//		if (root != null) {
//			//Checks, if all inputs are already calculated.
//			for (int i=0; i < root.getInputCount(); i++) {
//				if (! root.isResultCalculated()) {
//					return StepFormula(root.getInput(i));
//				}
//			}
//			//Calculates this Formula
//			try {
//				root.calc();
//			} catch (FormulaException e) {
//				System.err.println(e.getMessage());
//			} finally {
//				return root.getOutput();
//			}
//		} else {
//			return null;
//		}
//	}
//
//}
