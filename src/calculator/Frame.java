package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Cursor;

public class Frame {
	private JTextField textfield1;
	private JTextField textfield2;
	private double num1 = 0;
	private double num2 = 0;
	private boolean calculated=false;
	private String operator = "";
    private JMenuBar menubar;
    private JMenu thememenu ;
    private JButton history;
    private JPanel panel1;
    private JPanel panel2;
    private HistoryManager historyManager = new HistoryManager();
    private CalculatorLogic logic = new CalculatorLogic();
    private CalculatorStyle style = new CalculatorStyle();
    private JPanel historyPanel;
    private JTextArea historyArea;
    private boolean historyVisible = false;
    private JLabel historyTitle = new JLabel("History");
    private JButton clearHistoryBtn;
    private JPanel bottomPanel ;

	public Frame() {
		CalculatorHandler handler = new CalculatorHandler(this);
		JFrame frame=new JFrame("Calculator");
		frame.setSize(650, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.setIconImage(new ImageIcon("Images/calc.png").getImage());//create icone for frame
		frame.setLocationRelativeTo(null);
		menubar =new JMenuBar();
		menubar.setOpaque(true);
		menubar.setBackground(Color.BLACK);
		menubar.setLayout(new GridLayout(1,3,1,1));
		//BorderFactory->class,createEmptyBorder->static method
		menubar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
 
		JMenu iomenu=new JMenu("Mode                         ▶");
		style.addarrow(iomenu,"Mode");
		style.stylemenu(iomenu);
		iomenu.setForeground(Color.WHITE);
		JMenuItem on=new JMenuItem("ON");
		JMenuItem off=new JMenuItem("OFF");
		style.styleitem(on);
		style.styleitem(off);
		on.addActionListener(handler);
		off.addActionListener(handler);
		iomenu.add(off);
		iomenu.add(on);
		menubar.add(iomenu);
		
		thememenu=new JMenu("Theme                       ▶");
		style.addarrow(thememenu,"Theme");
		style.stylemenu(thememenu);
		JMenuItem light=new JMenuItem("☀️ Light");
		JMenuItem dark=new JMenuItem("🌙 Dark");
		style.styleitem(light);
		style.styleitem(dark);
		light.addActionListener(handler);
		dark.addActionListener(handler);
		thememenu.add(light);
		thememenu.add(dark);
		
		history=new JButton("History");
		style.styleButton(history, new Color(50, 50, 50));
		history.addActionListener(handler);
		history.setFont(new Font("Tahoma", Font.BOLD, 18));
		//make text in the center SwingConstants->interface,CENTER->constant
		history.setHorizontalAlignment(SwingConstants.CENTER);
		menubar.add(history);
		menubar.add(thememenu);
	    //Set it as the official menu bar in its reserved location."on the frame
		frame.setJMenuBar(menubar);
		
		panel1=new JPanel();
		panel1.setBackground(Color.BLACK);
		panel1.setLayout(new GridLayout(2, 1, 5, 5));
		panel1.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
		//setPreferredSize cause we use border layout for frame
		panel1.setPreferredSize(new Dimension(500, 130));
	    textfield1=new JTextField();
		textfield1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textfield1.setForeground(Color.LIGHT_GRAY);
        textfield1.setBackground(Color.BLACK);
		textfield1.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		textfield1.setHorizontalAlignment(JTextField.RIGHT);
		textfield1.setEditable(false);
		panel1.add(textfield1);
		textfield2=new JTextField();
		textfield2.setEditable(false);
		textfield2.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		textfield2.setForeground(Color.WHITE);
        textfield2.setBackground(Color.BLACK);
		textfield2.setFont(new Font("Tahoma", Font.BOLD, 30));
		textfield2.setHorizontalAlignment(JTextField.RIGHT);
		panel1.add(textfield2);
		
		panel2=new JPanel();
		panel2.setBackground(Color.BLACK);
		panel2.setLayout(new GridLayout(5, 4, 5, 5));
		panel2.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
		String [] buttonLabels={
				"AC","⌫","%","÷",
				"7", "8", "9", "×",
				"4", "5", "6", "-",
				"1", "2", "3", "+",
				"00", "0", ".", "="
		};
		for (String label:buttonLabels ) {
			 JButton button=new JButton(label);
	
			 if (label.equals("÷") || label.equals("×") ||
				        label.equals("-") || label.equals("+")||label.equals("=")) {

				 style.styleButton(button, new Color(255, 153, 51)); //op->orange
				    
		 } else if (label.equals("AC")||label.equals("⌫")||label.equals("%")) {
			 style.styleButton(button, new Color(110, 110, 110)); //dark gray
         } else {
        	 style.styleButton(button, new Color(50, 50, 50)); // numbers->gray
		    }
			 button.addActionListener(handler);
			 panel2.add(button);
		}
		
	//panel for two panels 1,2 which will be added to the frame
		JPanel mainCalcContainer = new JPanel(new BorderLayout());
		mainCalcContainer.setBackground(Color.BLACK);
		mainCalcContainer.add(panel1, BorderLayout.NORTH);
		mainCalcContainer.add(panel2, BorderLayout.CENTER);
		frame.add(mainCalcContainer, BorderLayout.CENTER);
		
		historyPanel = new JPanel(new BorderLayout());
		historyPanel.setBackground(Color.BLACK);
		historyPanel.setPreferredSize(new Dimension(280, 500));
		historyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		historyTitle = new JLabel("History");
		historyTitle.setForeground(Color.WHITE);
		historyTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		historyTitle.setHorizontalAlignment(JLabel.LEFT);
		historyTitle.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 0));

		clearHistoryBtn = new JButton("🗑️"); 
		clearHistoryBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
		clearHistoryBtn.setForeground(Color.WHITE);
		clearHistoryBtn.setBackground(Color.BLACK);
		clearHistoryBtn.setFocusPainted(false);
		clearHistoryBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		clearHistoryBtn.addActionListener(handler);
		clearHistoryBtn.setFocusPainted(false);//Removes the focus ring around the icon.
		clearHistoryBtn.setContentAreaFilled(false);//Makes the button's background transparent.
		clearHistoryBtn.setBorderPainted(false);//Hides the outer border of the button.

        //panel for button to be able to put the panel on south of history panel
		bottomPanel = new JPanel();
		//aligns all components to the right side of the panel.
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setBackground(Color.BLACK);
		bottomPanel.add(clearHistoryBtn);
		historyPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		historyArea = new JTextArea("No history yet");
		historyArea.setEditable(false);
		historyArea.setBackground(Color.BLACK);
		historyArea.setForeground(Color.WHITE);
		historyArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		//it prevents the text from extending horizontally forever
		historyArea.setLineWrap(true);
		//the entire number or expression moves to the next line together
		//prevent middle split
		historyArea.setWrapStyleWord(true);
		//JScrollPane takes textarea as a parameter
		JScrollPane scrollPane = new JScrollPane(historyArea);
		scrollPane.setBackground(Color.BLACK);
		scrollPane.getViewport().setBackground(Color.BLACK);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(null);
		historyPanel.add(historyTitle, BorderLayout.NORTH);
		historyPanel.add(scrollPane, BorderLayout.CENTER);
		historyPanel.setVisible(false);
		
		frame.add(historyPanel, BorderLayout.EAST);		
		frame.setVisible(true);
	}

	
	    
	public void updateHistoryArea() {

		 historyArea.setText(historyManager.getAll());
	}
	public JTextField textField1() { return textfield1; }
    public JTextField textField2() { return textfield2; }
    public String getTextField1() { return textfield1.getText(); }
    public String getTextField2() { return textfield2.getText(); }
    public double getNum1() { return num1; }
    public double getNum2() { return num2; }
    public void setNum1(double num1) { this.num1 = num1; }
    public void setNum2(double num2) { this.num2 = num2; }
    public String getOperator() { return operator; }
    public void setOperator(String op) { this.operator = op; }
    public boolean isCalculated() { return calculated; }
    public void setCalculated(boolean c) { this.calculated = c; }
    public CalculatorLogic getLogic() { return logic; }
    public HistoryManager getHistoryManager() {return historyManager;}
 // Getters للـ Panels عشان الألوان والـ Visibility
    public JPanel getPanel1() { return panel1; }
    public JPanel getPanel2() { return panel2; }
    public JPanel getHistoryPanel() { return historyPanel; }
    public JPanel getBottomPanel() { return bottomPanel; }

    // Getters للمكونات التانية
    public JMenuBar getMyMenuBar() { return menubar; } // سميته كدة عشان ميتعارضش مع ميثود جاهزة
    public JTextArea getHistoryArea() { return historyArea; }
    public JLabel getHistoryTitle() { return historyTitle; }
    public JButton getClearHistoryBtn() { return clearHistoryBtn; }
    public JButton getHistoryBtn() { return history; }
    public JMenu getThemeMenu() { return thememenu; }

    // Getter و Setter للـ History Visibility
    public boolean isHistoryVisible() { return historyVisible; }
    public void setHistoryVisible(boolean visible) { this.historyVisible = visible; }

}