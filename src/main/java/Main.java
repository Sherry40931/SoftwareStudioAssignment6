package main.java;
import javax.swing.JFrame;

@SuppressWarnings("serial")		//ignore the serial warning from serial Main class
public class Main extends JFrame{
	private final static int windowWidth = 1200, windowHeight = 670;
	
	public static void main(String [] args){
		MainApplet applet = new MainApplet();		//new the main applet object
		applet.init();
		applet.start();
		applet.setFocusable(true);
		
		JFrame window = new JFrame("Star Wars Network Analysis");	//new the container jframe
		window.setContentPane(applet);								//add the main applet in it
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(windowWidth, windowHeight);
		window.setVisible(true);
	}
}
