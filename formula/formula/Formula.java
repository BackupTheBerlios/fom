/* $Id: Formula.java,v 1.58 2004/10/20 16:04:54 shadowice Exp $
 * Created on 05.04.2004
 */
package formula;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import gui.*;
import utils.*;

/**
 * This is the top-level class for all formula-elements. It's not usable in itself as it is abstract.
 * It only provides a general set of methods that apply to all other formula-classes that extend this class.
 *
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 * @version $Revision: 1.58 $
 */
public abstract class Formula extends Container implements Cloneable {

	// Constants for drawing Formula Elements. (BOXHEIGHT + RESULTHEIGHT) and FORMULAWIDHT should be devideable by 4.
	public static final int BOXHEIGHT 			= 23;
	public static final int CONNECTHEIGHT 		= 4;
	public static final int RESULTHEIGHT 		= 16;
	public static final int FORMULAHEIGHT 		= BOXHEIGHT + RESULTHEIGHT +  2*CONNECTHEIGHT;
	public static final int FORMULAWIDTH 		= 108;

	// Constants for paintStatus
	public static final int PAINTSTATUS_STANDARD	= 1;
	public static final int PAINTSTATUS_SELECTED	= 2;
	public static final int PAINTSTATUS_INSERTING	= 4;
	public static final int PAINTSTATUS_MOVING		= 8;
	public static final int PAINTSTATUS_CALCULATING = 16;

	protected static final Font DEFAULT_FONT		= new Font("sansserif", Font.PLAIN, 11);
	protected static final Font CALC_FONT			= new Font("sansserif", Font.BOLD, 11);

	protected int paintStatus = PAINTSTATUS_STANDARD;

	protected Dimension dimension = new Dimension(FORMULAWIDTH,FORMULAHEIGHT);

	// Input/Output for Formula Elements
	protected Formula[] input;
	protected Formula output;

	// Name of Fromula Element
	protected String formulaName;

	// Input and output pins:
	protected PinPoint[] inputPins;
	protected PinPoint outputPin;

	// Image for double buffering
	private Image bufferImage;
	private int lastBufferWidth = 0;
	private int lastBufferHeight = 0;

	private Vector keyListener = new Vector();


	/**
	 * Creates a new formula object.
	 */
	public Formula() {
		super();
		setSize(dimension);
	}

	
	/**
	 * Creates a new formula object.
	 * @param ap root AppletPanel
	 */
	public Formula(AppletPanel ap) {
		super();
		setSize(dimension);
	}


	/**
	 * Calculates the result of this formula-object and stores it in result (result is defined in subclasses).
	 * @throws Throws a FormulaException when there aren't all inputs connected with a formula-object.
	 */
	public abstract void calc() throws FormulaException;


	/**
	 * @return Returns, if Formula is already calculated.
	 */
	public abstract boolean isResultCalculated();


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
	 * @return returns true if the formula object has a boolean result
	 */
	public abstract boolean hasBooleanResult();
	
	
	/**
	 * @return returns true if the formula object has a double result
	 */
	public abstract boolean hasDoubleResult();
	
	
	/**
	 * Will return a string-value as a result, if possible. If not, a FormulaException
	 * will be thrown.
	 * @return The result of all calc-operations up to this formula-element, casted into a string.
	 */
	public abstract String getStringResult();
	
	
	/**
	 * @return Name of the formula-element.
	 */
	public final String getFormulaName() {
		return formulaName;
	}


	/**
	 * Sets the name of this formula to a new value.
	 * @param name new name
	 */
	public final void setFormulaName(String name) {
		this.formulaName = name;
	}


	/* (non-Javadoc)
	 * @see java.awt.Component#isDoubleBuffered()
	 */
	/** @return true if doublebuffer is enabled */	
	public boolean isDoubleBuffered() {
		return true;
	}
	
	
	public void update(Graphics g) {
		if ((bufferImage == null) || (getSize().width > lastBufferWidth) || (getSize().height > lastBufferHeight)) {
			lastBufferWidth = getSize().width;
			lastBufferHeight = getSize().height;
			bufferImage = createImage(lastBufferWidth,lastBufferHeight);
		}
		Graphics bufferGraphics = bufferImage.getGraphics();
		paint(bufferGraphics);
		g.drawImage(bufferImage,0,0,this);
		bufferGraphics.dispose();
	}


	/**
	 * Paints the formula element. For a different visible style
	 * some formula elements may overwrite this method.
	 *
	 * @param g Graphics object for painting.
	 */
	public void paint(Graphics g) {
		String resultString = "";
		if (hasBooleanResult()) {
			resultString = getStringResult();
		} else if(hasDoubleResult()) {
			try {
				resultString = FOMToolkit.getFormatedString(getDoubleResult(),14);
			} catch (FormulaException fe) {
				fe.printStackTrace(System.err);
			}
		}
				
		super.paint(g);

		g.setFont(DEFAULT_FONT);

		//Standard
		if ((paintStatus & PAINTSTATUS_STANDARD) != 0) {
			g.setColor(Color.black);
			g.drawRect(0, CONNECTHEIGHT, FORMULAWIDTH-1, FORMULAHEIGHT-2*CONNECTHEIGHT);
			g.drawLine(0, CONNECTHEIGHT+RESULTHEIGHT, FORMULAWIDTH-1, CONNECTHEIGHT+RESULTHEIGHT);
			for (int i=0; i<getInputCount(); i++){
				g.drawLine((i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT-CONNECTHEIGHT, (i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT);
			}
			g.drawLine(FORMULAWIDTH/2, CONNECTHEIGHT, FORMULAWIDTH/2, 0);
		//Selected Element
		} else if ((paintStatus & PAINTSTATUS_SELECTED) != 0) {
			g.setColor(Color.blue);
			g.drawRect(0, CONNECTHEIGHT, FORMULAWIDTH-1, FORMULAHEIGHT-2*CONNECTHEIGHT);
			g.drawLine(0, CONNECTHEIGHT+RESULTHEIGHT, FORMULAWIDTH-1, CONNECTHEIGHT+RESULTHEIGHT);
			for (int i=0; i<getInputCount(); i++){
				g.drawLine((i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT-CONNECTHEIGHT, (i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT);
			}
			g.drawLine(FORMULAWIDTH/2, CONNECTHEIGHT, FORMULAWIDTH/2, 0);
		//Move Element
		} else if (((paintStatus & PAINTSTATUS_MOVING) != 0) || ((paintStatus & PAINTSTATUS_INSERTING) != 0)) {
			g.setColor(Color.lightGray);
			g.drawRect(0, CONNECTHEIGHT, FORMULAWIDTH-1, FORMULAHEIGHT-2*CONNECTHEIGHT);
			g.drawLine(0, CONNECTHEIGHT+RESULTHEIGHT, FORMULAWIDTH-1, CONNECTHEIGHT+RESULTHEIGHT);
			for (int i=0; i<getInputCount(); i++){
				g.drawLine((i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT-CONNECTHEIGHT, (i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT);
			}
			g.drawLine(FORMULAWIDTH/2, CONNECTHEIGHT, FORMULAWIDTH/2, 0);
/* 			//Dotted Line (too slow(?))
			g.setColor(Color.gray);
			for (int i=0; i<FORMULAWIDTH/4+1; i++) {
				g.drawLine(i*4, CONNECTHEIGHT, i*4+2-1, CONNECTHEIGHT);
				g.drawLine(i*4, CONNECTHEIGHT+RESULTHEIGHT, i*4+2-1, CONNECTHEIGHT+RESULTHEIGHT);
				g.drawLine(i*4, FORMULAHEIGHT-CONNECTHEIGHT, i*4+2-1, FORMULAHEIGHT-CONNECTHEIGHT);
			}
			for (int i=0; i<(BOXHEIGHT+RESULTHEIGHT)/4+1; i++) {
				g.drawLine(0, i*4+CONNECTHEIGHT, 0, i*4+2+CONNECTHEIGHT);
				g.drawLine(FORMULAWIDTH-1, i*4+CONNECTHEIGHT, FORMULAWIDTH-1, i*4+2+CONNECTHEIGHT);
			}
			for (int i=0; i<getInputCount(); i++){
				g.drawLine((i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT-CONNECTHEIGHT, (i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT);
			}
			g.drawLine(FORMULAWIDTH/2, CONNECTHEIGHT, FORMULAWIDTH/2, 0);
*/
		}
		String name = getFormulaName();
		g.drawString(name, (FORMULAWIDTH-g.getFontMetrics().stringWidth(name))/2, RESULTHEIGHT+CONNECTHEIGHT+BOXHEIGHT/2+g.getFontMetrics().getHeight()/2);
		if (resultString != null) {
			if ((paintStatus & PAINTSTATUS_CALCULATING) != 0) {
				g.setFont(CALC_FONT);
			}
			g.drawString(resultString, (FORMULAWIDTH-g.getFontMetrics().stringWidth(resultString))/2, RESULTHEIGHT/2+CONNECTHEIGHT+g.getFontMetrics().getHeight()/2-2); // Ergebnis der Rechnung
		}
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
	public final Formula getInput(int index) {
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
	public final int getWidthOfTree() {
		int widthOfTree = 0;
		if (getInputCount() == 0)
			widthOfTree = 1;
		else {
			for (int i = 0; i < getInputCount(); i++) {
				if (input[i] != null) {
					widthOfTree += input[i].getWidthOfTree();
				} else {
					widthOfTree++;
				}
							
		}}
		return widthOfTree;
	}
	

	/**
	 * Connects another formula to this output.
	 * @param out The other formula-object.
	 */
	public final void setOutput(Formula out) {
		this.output = out;
	}
	

	/**
	 * @return Returns the formula-object that is connected to this output.
	 */
	public final Formula getOutput() {
		return output;
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
	 * @return Validates, that subtree is complete and isn't missing some inputs.
	 */
	public final boolean isCompleteSubTree() {
		boolean complete = true;
		for (int i = 0; complete && (i < getInputCount()); i++) {
			if (input[i] == null) {
				complete = false;
			} else {
				complete = input[i].isCompleteSubTree();
			}
		}
		return complete;
	}


	/**
	 * Checks, on which input a specific Formula-Object is.
	 * @param toFind Formula-Object, which is searched.
	 * @param whereToFind Formula-Object, whose inputs have to be checked.
	 * @return Returns value of input index, -1 if it isn't found.
	 */
	protected final int indexOfInput(Formula toFind, Formula whereToFind) {
		int index = -1;
		for (int i = 0; index == -1 && i < whereToFind.getInputCount(); i++) {
			if (toFind == whereToFind.input[i])
				index = i; 
		}
		return index;
	}


	/**
	 * Sets the paint status, that will affect the behavior of the paint method.
	 * @param status one of the PAINTSTATUS_*
	 */
	public final void setPaintStatus(int status) {
		paintStatus = status;
		repaint();
	}
	

	/**
	 * @return returns current paint status (how this formula object is painted)
	 */
	public final int getPaintStatus() {
		return paintStatus;
	}


	/**
	 * @return returns the input-pins for this formula
	 */
	public PinPoint[] getInputPins() {
		return inputPins;
	}


	/**
	 * @return returns the output-pin for this formula
	 */
	public PinPoint getOutputPin() {
		return outputPin;
	}


	/**
	 * Sets the input pins for this formula.
	 * @param pins array of input-pins
	 */
	public void setInputPins(PinPoint[] pins) {
		inputPins = pins;
	}


	/**
	 * Sets the output pin for this formula.
	 * @param pin output-pin
	 */
	public void setOutputPin(PinPoint pin) {
		outputPin = pin;
	}


	/**
	 * Moves a formula to a new position and also moves the input-pins
	 * of this formula to a relative position.
	 * @param x x coordinates of new position
	 * @param y y coordinates of new position
	 */
	public void moveTo(int x, int y) {
		Point oldLocation = getLocation();
		int xOffset = x - oldLocation.x;
		int yOffset = y - oldLocation.y;
		for (int i=0;i<inputPins.length;i++) {
			inputPins[i].translate(xOffset,yOffset);
		}
		outputPin.translate(xOffset,yOffset);
		setLocation(oldLocation.x+xOffset,oldLocation.y+yOffset);
	}


	/**
	 * Initializes a formula object with a given AppletPanel.
	 * @param ap AppletPanel of this program's instance
	 */
	// NOTE extra method only for VariableBoolean and VariableNumber!
	public void init(AppletPanel ap) { }
	
	
	/**
	 * Clones a formula object. If the object has an inputs != null those will be cloned too.
	 * Clone used on the first element of a whole formula tree will clone the complete tree. 
	 */
	public Object clone() {
		try {
			Formula clonedForm = (Formula)getClass().newInstance();		//(Formula)super.clone(); <--- doesn't work with container!
	    	clonedForm.setLocation(getLocation());
	    	clonedForm.setFormulaName(new String(getFormulaName()));
	    	//clonedForm.formulaName = new String(formulaName);
	    	clonedForm.inputPins = (PinPoint[])inputPins.clone();
	    	for (int i=0;i<clonedForm.inputPins.length;i++) {
	    		clonedForm.inputPins[i] = (PinPoint)inputPins[i].clone();
	    		clonedForm.inputPins[i].setFormula(clonedForm);
	    	}
	    	clonedForm.outputPin = (PinPoint)outputPin.clone();
	    	clonedForm.outputPin.setFormula(clonedForm);
	    	
	    	// clone inputs:
	    	clonedForm.input = (Formula[])input.clone();
	    	for (int i=0;i<getInputCount();i++) {
	    		if (getInput(i) != null) {
		    		Formula form = (Formula)getInput(i).clone();
		    		form.setOutput(clonedForm);
		    		clonedForm.setInput(form,i);
		    		clonedForm.inputPins[i].setTarget(form.getOutputPin());
		    		form.getOutputPin().setTarget(clonedForm.inputPins[i]);
	    		}
	    	}
	    	KeyListener[] kListener = getKeyListeners();
	    	for (int i=0;i<kListener.length;i++) {
				clonedForm.addKeyListener(kListener[i]);	
	    	}
			return clonedForm;
		} catch (InstantiationException e) {
			e.printStackTrace(System.err);
		} catch (IllegalAccessException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}


	//"Java 1.1/1.2 fixes":
	public final void addKeyListener(KeyListener listener) {
		super.addKeyListener(listener);
		keyListener.addElement(listener);		
	}

	public KeyListener[] getKeyListeners() {
		KeyListener[] kl = new KeyListener[keyListener.size()];
		for(int i=0;i<keyListener.size();i++) {
			kl[i] = (KeyListener)keyListener.elementAt(i);
		}
		return kl;
	}

	//"Quick-fixes" :)
	public final Dimension preferredSize() {
		return dimension;
	}
	
	public final Dimension getPreferredSize() {
		return dimension;
	}
	
	public final Dimension getMinimumSize() {
		return dimension;
	}
	
}