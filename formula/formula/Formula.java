/*
 * Created on 05.04.2004
 *
 */
package formula;

import java.awt.*;
import java.util.*;

/**
 * This is the top-level class for all formula-elements. It's not usable in itself as it is abstract.
 * It only provides a general set of methods that apply to all other formula-classes that extend this class.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class Formula extends Container implements Cloneable {

	public static final int BOXHEIGHT = 30;
	public static final int RESULTHEIGHT = 20;
	public static final int CONNECTHEIGHT = 5; 
	public static final int FORMULAHEIGHT = BOXHEIGHT + RESULTHEIGHT +  2*CONNECTHEIGHT;
	public static final int FORMULAWIDTH = 120;

	protected Dimension dimension = new Dimension(FORMULAWIDTH+1,FORMULAHEIGHT+1);

	protected Formula[] input;
	protected Formula output;

	protected String formulaName;

	public static LinkedList treeList;

	/**
	 * Creates a new formula object.
	 */
	public Formula() {
		super();
		setSize(dimension);
	}

	/**
	 * Calculates the result of this formula-object and stores it in result (result is defined in subclasses).
	 * @throws Throws a FormulaException when there aren't all inputs connected with a formula-object.
	 */
	public abstract void calc() throws FormulaException;

	/**
	 * Will return a boolean-value as a result, if possible. If not, a FormulaException will be thrown.
	 * @return The result of all calc-operations up to this formula-element.
	 * @throws FormulaException Shouldn't happen.
	 */
	public abstract boolean getBooleanResult() throws FormulaException;


	/**
	 * Will return a double-value as a result, if possible. If not, a FormulaException
	 * will be thrown.
	 * @return The result of all calc-operations up to this formula-element.
	 * @throws FormulaException Normally this shouldn't happen. ;)
	 */
	public abstract double getDoubleResult() throws FormulaException;

	/**
	 * Will return a string-value as a result, if possible. If not, a FormulaException
	 * will be thrown.
	 * @return The result of all calc-operations up to this formula-element, casted into a string.
	 * @throws FormulaException Normally this shouldn't happen. ;)
	 */
	public abstract String getStringResult() throws FormulaException;
	
//	/**
//	 * Will return a long-value as a result, if possible. If not, a FormulaException will be thrown.
//	 * @return The result of all calc-operations up to this formula-element.
//	 * @throws FormulaException Shouldn't happen. ;)
//	 */
//	public abstract long getLongResult() throws FormulaException;


	/**
	 * @return Name of the formula-element.
	 */
	public String getFormulaName() {
		return formulaName;
	}

	/**
	 * Paints the formula element. For a different visible style
	 * some formula elements may overwrite this method.
	 *
	 * @param g Graphics object for painting.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		//TODO Grafik verbessern
		//((Graphics2D)g).scale(scaleX, scaleY);
		g.setColor(Color.BLACK);
		g.drawRect(0, CONNECTHEIGHT, FORMULAWIDTH, FORMULAHEIGHT-2*CONNECTHEIGHT);
		g.drawLine(0, CONNECTHEIGHT+RESULTHEIGHT, FORMULAWIDTH, CONNECTHEIGHT+RESULTHEIGHT);
		for (int i=0; i<getInputCount(); i++){
			g.drawLine((i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT-CONNECTHEIGHT, (i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT);
		}
		g.drawLine(FORMULAWIDTH/2 +1, CONNECTHEIGHT, FORMULAWIDTH/2 +1, 0);
		g.setFont(new Font("Arial", Font.PLAIN, 10));		
		g.drawString(formulaName, (FORMULAWIDTH-g.getFontMetrics().stringWidth(formulaName))/2, RESULTHEIGHT+CONNECTHEIGHT+BOXHEIGHT/2+g.getFontMetrics().getHeight()/2); // Align: center
		
	}

	/**
	 * @return Returns the number of inputs a formula element has.
	 */
	public int getInputCount() {
		return input.length;
	}


	/**
	 * @param index number of input (0=left...max-1=right).
	 * @return Returns an array of all possible classes for input.
	 * @throws FormulaException Shouldn't happen. ;)
	 * If no Input is possible, Returns an array with content "Nothing".
	 */
	public abstract Class[] getInputTypes(int index) throws FormulaException;
	
	
	/**
	 * @return Returns an array of all possible classes for output.
	 * @throws FormulaException Shouldn't happen. ;)
	 * If no Output is possible, Returns an array with content "Nothing".
	 */
	public abstract Class[] getOutputTypes() throws FormulaException;


	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public abstract void clearResult();


	/**
	 * @param index Number of the input (left=0, right=inputCount-1)
	 * @return The formula, that is connected to this input, or null.
	 */
	public Formula getInput(int index) {
		return input[index];
	}

	/**
	 * @return Returns the string-equvalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */
	public abstract String toString();

	/**
	 * @return Returns the maximal number of formula-objects in this subtree which are side by side.
	 */
	public int getWidthOfTree() {
		int widthOfTree = 0;
		if (getInputCount() == 0)
			widthOfTree = 1;
		else {
			for (int i = 0; i < getInputCount(); i++) {
				if (input[i] != null)
					widthOfTree += input[i].getWidthOfTree();		
		}}
		return widthOfTree;
	}

	/**
	 * Connects another formula to this output.
	 * @param out The other formula-object.
	 */
	public void setOutput(Formula out) {
		this.output = out;
	}

	/**
	 * @return Returns the formula-object that is connected to this output.
	 */
	public Formula getOutput() {
		return output;
	}

	/**
	 * Connects another formula-object to this input.
	 * @param in The other formula-object.
	 * @param index Number of the input (left=0).
	 */
	public void setInput(Formula in, int index) {
		this.input[index] = in;
	}
	/**
	 * @return Validates, that tree is complete and isn't missing some inputs.
	 * MAURICE Darf ich dieses Abbruchbedingung benutzen (for-Schleife)?
	 */
	public boolean completeTree() {
		boolean complete = true;
		for (int i = 0; i < getInputCount() && complete; i++) {
			complete = complete && (input[i] == null);
		}
		return complete;
	}


	/**
	 * Checks, on which input a specific Formula-Object is.
	 * @param toFind Formula-Object, which is searched.
	 * @param whereToFind Formula-Object, whose inputs have to be checked.
	 * @return Returns value of input index, -1 if it isn't found.
	 */
	protected int indexOfInput(Formula toFind, Formula whereToFind) {
		int index = -1;
		for (int i = 0; i < whereToFind.getInputCount() && index != -1; i++) {
			if (toFind == whereToFind.input[i])
				index = i; 
		}
		return index;
	}

	public static LinkedList getTreeList() {
		return treeList;
	}

	//"Quick-fix" :)
	public Dimension getSize() {
		return dimension;
	}

	public Dimension preferredSize() {
		return dimension;
	}
	
	public Dimension getPreferredSize() {
		return dimension;
	}
	
	public Dimension getMinimumSize() {
		return dimension;
	}
	
	public void setSize(int x,int y) {
		dimension = new Dimension(x,y);
	}
	
	public void setSize(Dimension dim) {
		dimension = dim;
	}
}