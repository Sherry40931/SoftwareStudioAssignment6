package main.java;

import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	public float x, y, radius=40;
	private String name;
	private PApplet parent;
	private int value;
	private int[] color = new int[3];
	private ArrayList<Character> targets = new ArrayList<Character>();
	private boolean showName;
	
	/*
	 * Store these variables when instance created.
	 */
	public Character(PApplet parent, String name, float x, float y, int value, String color){
		this.parent = parent;
		this.name = name;
		this.x = x;
		this.y = y;
		this.value = value;
		this.color = convertHexColor(color);
	}
	
	private int[] convertHexColor(String color){
		int[] colorRGB = new int[3]; 
		colorRGB[0] = Integer.valueOf(color.substring(3, 4), 16)*255/16;
		colorRGB[1] = Integer.valueOf(color.substring(5, 6), 16)*255/16;
		colorRGB[2] = Integer.valueOf(color.substring(7, 8), 16)*255/16;
		return colorRGB;
	}
	/*
	 * Use display() to draw the character on the sketch.
	 */
	public void display(){
		update();
		this.parent.fill(this.color[0], this.color[1], this.color[2], 180);
		this.parent.stroke(0, 0);
		this.parent.ellipse(x, y, radius, radius);
		
		if(showName){
			this.parent.fill(2, 202, 119, 255);
			this.parent.rect(x, y+5, 120, 20);
			this.parent.fill(255, 255);
			this.parent.textSize(14);
			this.parent.text(this.name,x+20,y+20);
		}
//		this.parent.fill(0);
	}
	
	boolean overCircle(float x, float y, float diameter) {
		  float disX = x - this.parent.mouseX;
		  float disY = y - this.parent.mouseY;
		  if(PApplet.sqrt(PApplet.sq(disX) + PApplet.sq(disY)) < diameter/2 ) {
		    return true;
		  } else {
		    return false;
		 }
	}
	
	void update() {
		  if(overCircle(this.x, this.y, this.radius) ) {
		    showName = true;
		  }
		  else{
			 showName = false;
		  }
	}
	
	/*
	 * Add the target to the array list when loading file.
	 */
	public void addTarget(Character target, int value){
		targets.add(target);
	}
	
	public ArrayList<Character> getTargets(){
		return this.targets;
	}
}
