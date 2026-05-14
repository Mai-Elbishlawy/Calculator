package calculator;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class CalculatorStyle {

	public CalculatorStyle() {
		// TODO Auto-generated constructor stub
	}
	public void styleButton(JButton button,Color c) {
	    button.setFocusPainted(false);//Removes the focus ring around the icon.
	    button.setForeground(Color.WHITE);
	    button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
	    if (button.getText().equals("⌫")) {
	        // special font support this char
	        button.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
	    } else {
	    	button.setFont(new Font("Tahoma", Font.BOLD, 20));  
	    }
	    button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
	    button.setBackground(c);
	    button.setContentAreaFilled(false); //Makes the button's background transparent.ً
	    button.setOpaque(true); // makes the component opaque to display the custom background color.
	    //Hover effect when the mouse make event
	    button.addMouseListener(new MouseAdapter() {
	        public void mouseEntered(MouseEvent evt) {
	            button.setBackground(c.brighter());//brighter color of the org color
	        }
	        public void mouseExited(MouseEvent evt) {
	            button.setBackground(c); // return the org color
	        }
	        public void mousePressed(MouseEvent evt) {
	            button.setBackground(c.darker()); //darker color of the org color
	        }
	        // لما تسيبي الماوس يرجع للون الـ Hover أو الأصلي
	        public void mouseReleased(MouseEvent evt) {
	            button.setBackground(c);// return the org color
	        }
	    });
	}
	public void stylemenu(JMenu m) {
		m.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		m.setBackground(new Color(50, 50, 50));
		m.setOpaque(true);
		m.setForeground(Color.WHITE);
		m.setContentAreaFilled(true);
		m.setBorderPainted(false);
		m.getPopupMenu().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}
	public void styleitem(JMenuItem im) {
		im.setFont(new Font("SansSerif",Font.PLAIN,16));
		im.setPreferredSize(new Dimension(211,30));
		im.setBackground(new Color(50, 50, 50));
		im.setOpaque(true);
		im.setForeground(Color.WHITE);
		im.setBorderPainted(false);
		im.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));			
	}
	public void addarrow(JMenu menu, String title) {
	    menu.addMenuListener(new MenuListener() {
	        @Override
	        public void menuSelected(MenuEvent e) {
	            //whhen the menu is open the arrow look down
	            menu.setText(title + "                         ▼"); 
	        }

	        @Override
	        public void menuDeselected(MenuEvent e) {
	            //whhen the menu is close the arrow look right
	            menu.setText(title + "                         ▶");
	        }

	        @Override
	        public void menuCanceled(MenuEvent e) {
	        	//when menu closed suddenly
	            menu.setText(title + "                         ▶");
	        }
	    });
	}
}
