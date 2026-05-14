package calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class CalculatorHandler implements ActionListener{
	private Frame gui;
	public CalculatorHandler(Frame gui) {
        this.gui = gui;
    }
	@Override
	public void actionPerformed(ActionEvent e) {
        
	    String cmd = e.getActionCommand();

	    // Numbers or decimal point
	    if (cmd.matches("[0-9]") || cmd.equals("00") || cmd.equals(".")) {

	        if (gui.isCalculated()) { 	//clear and start from beginig
	         	gui.textField1().setText("");
	            gui.textField2().setText("");
	            gui.setOperator(""); 
	            gui.setNum1(0);
	            gui.setNum2(0);
	            gui.setCalculated(false);
	        }
	        if (cmd.equals(".") && gui.getTextField2().isEmpty()) {
	        	gui.textField2().setText("0.");
	            return;
	        }
	        //handling repeated decimal points
	        if (cmd.equals(".") && gui.getTextField2().contains(".")) {
	            return;
	        }
           
	        gui.textField2().setText(gui.getTextField2() + cmd);
            
	    }

	    else if (cmd.equals("+") || cmd.equals("-")
	            || cmd.equals("×") || cmd.equals("÷")
	            || cmd.equals("%")) {

	        String text = gui.getTextField2();
	        if (text.isEmpty()) return;

	        double currentVal = Double.parseDouble(text);

	        if (!gui.getOperator().isEmpty()) {

	            double result = gui.getLogic().calculate(
	                    gui.getNum1(),
	                    currentVal,
	                    gui.getOperator()
	            );

	            if (Double.isNaN(result)) return;
	            String entry =
	                    gui.getLogic().formatNum(gui.getNum1()) + " "
	                    + gui.getOperator() + " "
	                    + gui.getLogic().formatNum(currentVal) + " = "
	                    + gui.getLogic().formatNum(result);

	            gui.getHistoryManager().add(entry);
	            gui.updateHistoryArea();
	            gui.setNum1(result);
	        } 
	        else {
	            gui.setNum1(currentVal);
	        }

	        gui.setOperator(cmd);
	        
	        gui.textField1().setText(
	                gui.getLogic().formatNum(gui.getNum1()) + " " + cmd
	        );

	        gui.textField2().setText("");

	        gui.setCalculated(false);
	    }

	    // Equal
	    else if (cmd.equals("=")) {
	    	if (gui.isCalculated()) {//already calculated
	    		 return;
	    	}
	        String text = gui.getTextField2();
	        if (text.isEmpty()) {//if the user didnt enter the second num
	            return;
	        }
	        if (gui.getOperator().isEmpty()) {//if the user enter only one number print it
	        	 gui.setNum2(Double.parseDouble(text));
	         gui.textField1().setText(gui.getLogic().formatNum(gui.getNum2())+" "+"=");
	        	 gui.textField2().setText(gui.getLogic().formatNum(gui.getNum2()) );
	        	 gui.getHistoryManager().add(gui.getLogic().formatNum(gui.getNum2()) + " " + "= " +gui.getLogic().formatNum(gui.getNum2()));
             gui.updateHistoryArea();
	        	 gui.setCalculated(true);
	        	 return;
	        }

	        gui.setNum2(Double.parseDouble(text));
	        double result = gui.getLogic().calculate(gui.getNum1(), gui.getNum2(), gui.getOperator());
	        //if the return type of the calculation is isNaN means error
	        if (Double.isNaN(result)) return;

	        gui.textField1().setText(gui.getLogic().formatNum(gui.getNum1()) + " " +gui.getOperator()+" "+ gui.getLogic().formatNum(gui.getNum2()) + " ="); // show full exp
	        gui.textField2().setText(gui.getLogic().formatNum(result)); // show the result
            //push the exp into the history stack
	        gui.getHistoryManager().add(gui.getLogic().formatNum(gui.getNum1()) + " " + gui.getOperator() + " " + gui.getLogic().formatNum(gui.getNum2()) + " = " +gui.getLogic().formatNum(result));
	        gui.updateHistoryArea();
	        gui.setNum1(result);
	        gui.setOperator("");
	        gui.setCalculated(true);
	    }
	    
	    else if (cmd.equals("History")) {
            //act like a toogle button 
	        gui.setHistoryVisible(!gui.isHistoryVisible());
	        gui.getHistoryPanel().setVisible(gui.isHistoryVisible());
	        //getTopLevelAncestor->to get the frame(most container)
	        //The Casting->Since getTopLevelAncestor() returns a generic Component
	        JFrame frame = (JFrame) gui.getHistoryPanel().getTopLevelAncestor();
	        //it tells the Layout Manager to re-calculate the sizes and positions of all components
	        frame.revalidate();
	        //It tells the window to redraw itself on the screen.
	        frame.repaint();
	    }

	    // Backspace
	    else if (cmd.equals("⌫")) {
	        if (gui.isCalculated()) {//if we already calculate an exp then backapace delete all exp
	          	gui.textField1().setText("");
	            gui.textField2().setText("");
	            gui.setCalculated(false);
	            return; 
	        }

	        String currentText =gui.getTextField2();

	        //ensure that there is an string in the textfield 2 to delete it
	        if (!currentText.isEmpty()) {
	            // delete the last char
	            String newText = currentText.substring(0, currentText.length() - 1);         
	            // set the textfield to the new string after deletion
	            gui.textField2().setText(newText);
	        }
	    }
	    
	    // AC
	    else if (cmd.equals("AC")) {
             //clear ans reset every thing 
	    	    gui.textField1().setText("");
	        gui.textField2().setText("");
	        gui.setNum1(0);
	        gui.setNum2(0);
	        gui.setOperator(""); 
	        gui.setCalculated(false);
	    }
	    else if (cmd.equals("OFF")) {
	       //disable every comp and clear text fields     
	       gui.textField1().setText("");
	       gui.textField2().setText("");
	       for (Component c:	gui.getPanel1().getComponents()) {
	    	   c.setEnabled(false);
	       }
	       for (Component c:	gui.getPanel2().getComponents()) {
	    	   c.setEnabled(false);
	       }
	       gui.getHistoryBtn().setEnabled(false);
	       gui.getThemeMenu().setEnabled(false);
	       gui.setNum1(0);
	       gui.setNum2(0);
	       gui.setOperator(""); 
	       gui.setCalculated(false);
	       
	    }
	    else if (cmd.equals("ON")) {
		   //enable all comp 
		   for (Component c:	gui.getPanel1().getComponents()) {
	    	   c.setEnabled(true);
	       }
	       for (Component c:	gui.getPanel2().getComponents()) {
	    	   c.setEnabled(true);
	       }
	       gui.getHistoryBtn().setEnabled(true);
	       gui.getThemeMenu().setEnabled(true);
		   }
	    else if (cmd.equals("🌙 Dark")) {
	   
	    	gui.getPanel1().setBackground(Color.BLACK);
	    	gui.getPanel2().setBackground(Color.BLACK);
	    gui.textField1().setBackground(Color.BLACK);
	    gui.textField1().setForeground(Color.WHITE);
	    gui.textField2().setForeground(Color.WHITE);
	    gui.textField2().setBackground(Color.BLACK);
	    gui.getMyMenuBar().setBackground(Color.BLACK);

	    gui.getHistoryPanel().setBackground(Color.BLACK);
	    gui.getHistoryArea().setBackground(Color.BLACK);
	    gui.getHistoryArea().setForeground(Color.WHITE);
	    gui.getHistoryTitle().setForeground(Color.WHITE);
		
		gui.getClearHistoryBtn().setForeground(Color.WHITE);
		gui.getClearHistoryBtn().setBackground(Color.BLACK);
		gui.getBottomPanel().setBackground(Color.BLACK);

	    }
	    else if (cmd.equals("☀️ Light")) {
	    
	    	gui.getPanel1().setBackground(Color.WHITE);
	    	gui.getPanel2().setBackground(Color.WHITE); 
	    gui.textField1().setForeground(Color.BLACK);
	    gui.textField2().setForeground(Color.BLACK);
	    gui.textField1().setBackground(Color.WHITE);
	    gui.textField2().setBackground(Color.WHITE);
	    gui.getMyMenuBar().setBackground(Color.WHITE);
	    
	    gui.getClearHistoryBtn().setForeground(Color.BLACK);
	    gui.getClearHistoryBtn().setBackground(Color.WHITE);

		gui.getHistoryPanel().setBackground(Color.WHITE);
		gui.getHistoryArea().setBackground(Color.WHITE);
		gui.getHistoryArea().setForeground(Color.BLACK);
		gui.getHistoryTitle().setForeground(Color.BLACK);
		gui.getBottomPanel().setBackground(Color.WHITE);

	    }
	    
	    else if (cmd.equals("🗑️")) {
	     	gui.getHistoryManager().clear();// clear stack
	        gui.updateHistoryArea();  // update the text area with new clear stack       
	        gui.getHistoryArea().setText("No history yet");  //Double-check
	    }
	   
	}
	
	

}
