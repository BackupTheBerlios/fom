/*
 * Created on 27.06.2004
 */
package formula;
import java.awt.TextField;
//import java.awt.*;

/**
 * Class for constant numbers.
 * 
 * @author Maurice Gilden, Heiko Mattes, Benjamin Riehle
 */
public class ConstantNumber extends ConstVarFormula {

//	TODO Mit Maurice besprechen

	protected TextField inputNumber;

	public void setResult(Number result) {
		this.result = result;
	}

	public final String toString() {
		return result.toString();
	}

	public ConstantNumber() {
		formulaName = "ConstantNumber";
		result = new Double (0);
		inputNumber = new TextField();
		inputNumber.setBounds(2, RESULTHEIGHT+CONNECTHEIGHT+2, FORMULAWIDTH-2, FORMULAHEIGHT-CONNECTHEIGHT-2);
	}

//	public void paint(Graphics g) {
//		String resultString;
//		resultString = getStringResult();
//		g.setFont(new Font("Arial", Font.PLAIN, 10));	
//		//inputNumber.setLocation(2, RESULTHEIGHT+CONNECTHEIGHT+2);
//		//((Graphics2D)g).scale(scaleX, scaleY);
//		super.paint(g);
//		//Standard Aussehen
//		if (paintStatus == PAINTSTATUS_STANDARD) {
//			g.setColor(Color.BLACK);
//			g.drawRect(0, CONNECTHEIGHT, FORMULAWIDTH, FORMULAHEIGHT-2*CONNECTHEIGHT);
//			g.drawLine(0, CONNECTHEIGHT+RESULTHEIGHT, FORMULAWIDTH, CONNECTHEIGHT+RESULTHEIGHT);
//			for (int i=0; i<getInputCount(); i++){
//				g.drawLine((i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT-CONNECTHEIGHT, (i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT);
//			}
//			g.drawLine(FORMULAWIDTH/2 +1, CONNECTHEIGHT, FORMULAWIDTH/2 +1, 0);
//			g.drawString(formulaName, (FORMULAWIDTH-g.getFontMetrics().stringWidth(formulaName))/2, RESULTHEIGHT+CONNECTHEIGHT+BOXHEIGHT/2+g.getFontMetrics().getHeight()/2); // Name des Elements
//			if (resultString != null) {
//				g.drawString(resultString, (FORMULAWIDTH-g.getFontMetrics().stringWidth(resultString))/2, RESULTHEIGHT/2+CONNECTHEIGHT+g.getFontMetrics().getHeight()/2); // Ergebnis der Rechnung
//			}
//		//Element markiert
//		} else if (paintStatus == PAINTSTATUS_SELECTED) {
//			g.setColor(Color.BLUE);
//			g.drawRect(0, CONNECTHEIGHT, FORMULAWIDTH, FORMULAHEIGHT-2*CONNECTHEIGHT);
//			g.drawLine(0, CONNECTHEIGHT+RESULTHEIGHT, FORMULAWIDTH, CONNECTHEIGHT+RESULTHEIGHT);
//			for (int i=0; i<getInputCount(); i++){
//				g.drawLine((i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT-CONNECTHEIGHT, (i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT);
//			}
//			g.drawLine(FORMULAWIDTH/2 +1, CONNECTHEIGHT, FORMULAWIDTH/2 +1, 0);
//			g.drawString(formulaName, (FORMULAWIDTH-g.getFontMetrics().stringWidth(formulaName))/2, RESULTHEIGHT+CONNECTHEIGHT+BOXHEIGHT/2+g.getFontMetrics().getHeight()/2); // Name des Elements
//			if (resultString != null) {
//				g.drawString(resultString, (FORMULAWIDTH-g.getFontMetrics().stringWidth(resultString))/2, RESULTHEIGHT/2+CONNECTHEIGHT+g.getFontMetrics().getHeight()/2); // Ergebnis der Rechnung
//			}
//		//Element bewegen
//		} else { //if ((paintStatus == PAINTSTATUS_MOVING) || (paintStauts == PAINTSTATUS_INSERTING)) {
//			g.setColor(Color.BLACK);
//			for (int i=0; i<FORMULAWIDTH/4; i++) {
//				g.drawLine(i*4, CONNECTHEIGHT, i*4+2, CONNECTHEIGHT);
//				g.drawLine(i*4, CONNECTHEIGHT+RESULTHEIGHT, i*4+2, CONNECTHEIGHT+RESULTHEIGHT);
//				g.drawLine(i*4, FORMULAHEIGHT-CONNECTHEIGHT, i*4+2, FORMULAHEIGHT-CONNECTHEIGHT);
//			}
//			for (int i=0; i<(BOXHEIGHT+RESULTHEIGHT)/4; i++) {
//				g.drawLine(0, i*4+CONNECTHEIGHT, 0, i*4+2+CONNECTHEIGHT);
//				g.drawLine(FORMULAWIDTH, i*4+CONNECTHEIGHT, FORMULAWIDTH, i*4+2+CONNECTHEIGHT);
//			}
//			for (int i=0; i<getInputCount(); i++){
//				g.drawLine((i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT-CONNECTHEIGHT, (i+1)*FORMULAWIDTH/(getInputCount()+1), FORMULAHEIGHT);
//			}
//			g.drawLine(FORMULAWIDTH/2 +1, CONNECTHEIGHT, FORMULAWIDTH/2 +1, 0);
//			g.drawString(formulaName, (FORMULAWIDTH-g.getFontMetrics().stringWidth(formulaName))/2, RESULTHEIGHT+CONNECTHEIGHT+BOXHEIGHT/2+g.getFontMetrics().getHeight()/2); // Name des Elements
//		}
//	}

}