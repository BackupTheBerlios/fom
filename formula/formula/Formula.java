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
 * @version 0.1.5 (21.04.2004)
 */
public abstract class Formula extends Container implements Cloneable {

	public static final int BOXHEIGHT = 50;
	public static final int RESULTHEIGHT = 25;
	public static final int BOXWIDTH = 120;
	public static final int FORMULAHEIGHT = BOXHEIGHT + RESULTHEIGHT;

	protected Formula[] input;
	protected Formula output;

	protected String formulaName;


	/**
	 * Creates a new formula object.
	 */
	public Formula() {
		//Maurice: Was bedeutet das hier?
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
	public final String getFormulaName() {
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
	public final int getInputCount() {
		return input.length;
	}


	/**
	 * Checks if the output of another formula-object is compatible with one of the inputs.
	 * @param in Output of another formula.
	 * @param index Index-number of input.
	 * @return True: other formula-element can be attached to this input.
	 */
	public abstract boolean isValidInput(Formula in, int index);


	/**
	 * Checks if the input of another formula-object is compatible with the output.
	 * @param out Input of another formula.
	 * @return True: other formula-element can be attached to this output.
	 */
	public abstract boolean isValidOutput(Formula out);


	/**
	 * Clears all results that have been saved by calc-operations.
	 */
	public abstract void clearResult();


	/**
	 * @param index Number of the input (left=0, right=inputCount-1)
	 * @return The formula, that is connected to this input, or null.
	 */
	public final Formula getInput(int index) {
		return input[index];
	}


	/**
	 * Connects another formula-object to this input.
	 * @param in The other formula-object.
	 * @param index Number of the input (left=0).
	 */
	public final void setInput(Formula in, int index) {
		this.input[index] = in;
	}


	/**
	 * @return Returns the formula-object that is connected to this output.
	 */
	public final Formula getOutput() {
		return output;
	}


	/**
	 * Connects another formula to this output.
	 * @param out The other formula-object.
	 */
	public final void setOutput(Formula out) {
		this.output = out;
	}


	/**
	 * @return Returns the string-equvalent of this formula-object (if inputs not connected)
	 * or the resulting formula as string for a (sub)tree with this object as root.
	 */
	//Maurice: Warum kann ich das hier nicht hinzufügen?
	public abstract String toString();// throws FormulaExeption;

	/**
	 * @return Returns the maximal number of formula-objects in this subtree which are side by side.
	 */
	//TODO Funktioniert noch nicht, da Variablen/Konstanten nicht erkannt werden.
	public final int getWidthOfTree() {
		int WidthOfTree = 0;
		for (int i = 0; i <= input.length-1; i++) {
			if (input[i] != null)
				WidthOfTree += input[i].getWidthOfTree();
		}
		return WidthOfTree;
	}

}