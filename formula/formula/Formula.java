/*
 * Created on 05.04.2004
 *
 */
package formula;

import java.awt.*;

/**
 * This is the top-level class for all formula-elements. It's not usable in itself as it is abstract.
 * It only provides a general set of methods that apply to all other formula-classes that extend this class.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public abstract class Formula extends Container implements Cloneable {

	public static final int BOXHEIGHT = 50;
	public static final int RESULTHEIGHT = 25;
	public static final int BOXWIDTH = 120;
	public static final int FORMULAHEIGHT = BOXHEIGHT + RESULTHEIGHT;

	protected Formula[] input;
	protected Formula output;

	protected String formulaName;

	public static Formula[] treeList;

	/**
	 * Creates a new formula object.
	 */
	public Formula() {
		super();
	}


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
	 * Will return a long-value as a result, if possible. If not, a FormulaException will be thrown.
	 * @return The result of all calc-operations up to this formula-element.
	 * @throws FormulaException Shouldn't happen. ;)
	 */
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
		//		((Graphics2D)g).scale(scaleX,scaleY);
		//		setSize((int)(sizeX*scaleX+1),(int)(sizeY*scaleY+1));
		//		g.setColor(Color.BLACK);
		//		g.drawRect(0,5,sizeX,30);
		//		g.setFont(new Font("Arial",Font.PLAIN,10));
		//		g.drawString(formulaName,(sizeX-g.getFontMetrics().stringWidth(formulaName))/2,25); // Align: center
		//		for(int i=0;i<getInputCount();i++){
		//			g.drawLine((i+1)*sizeX/(getInputCount()+1),35,(i+1)*sizeX/(getInputCount()+1),40);
		//		}
		//		for(int i=0;i<getOutputCount();i++){
		//			g.drawLine((i+1)*sizeX/(getOutputCount()+1),5,(i+1)*sizeX/(getOutputCount()+1),0);
		//		}
	}

	/**
	 * @return Returns the number of inputs a formula element has.
	 */
	public int getInputCount() {
		return input.length;
	}

// TODO getInputTypes / getOutputTypes
	/**
	 * @param index Number of the input (0=left...max-1=right)
	 * @return Returns an array of all possible classes for the input.
	 */
	public abstract Class[] getInputTypes(int index);
	
	
	/**
	 * @return Returns an array of all possible classes for the output.
	 */
	public abstract Class[] getOutputTypes();


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
	 * A Formula-Object tries to connect to an input of this Formula-Object.
	 * @param in Which Formula-Object tries to connect
	 * @param whichInput To which input
	 * @return Is Formula-Object allowed to connect 
	 */
	public abstract boolean isValidInput(Formula in, int whichInput);

	/**
	 * A Formula-Object tries to connect to output of this Formula-Object.
	 * @param in Which Formula-Object tries to connect.
	 * @param whichInput Which input tries to connect.
	 * @return Is Formula-Object allowed to connect.
	 */
	public abstract boolean isValidOutput(Formula in, int whichInput);

	public static Formula[] getTreeList() {
		return treeList;
	}

	public static void setTreeList(Formula[] treeListParameter) {
		treeList = treeListParameter;
	}

}