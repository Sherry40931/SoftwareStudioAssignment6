package main.java;

import java.util.ArrayList;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	public float x, y, radius=40;
	private float xMove=0, yMove=0;
	private String name;
	private PApplet parent;
	private int value;
	private int[] color = new int[3];
	private ArrayList<Character> targets = new ArrayList<Character>();
	private boolean showName;
	public boolean draging=false;
	
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
		overCircle();
		this.parent.fill(this.color[0], this.color[1], this.color[2], 180);
		this.parent.stroke(0, 0);
		this.parent.ellipse(x, y, radius, radius);
		
		if(showName){
			// show name
			this.parent.fill(2, 202, 119, 255);
			this.parent.rect(x, y+5, 120, 20);
			this.parent.fill(255, 255);
			this.parent.textSize(14);
			this.parent.text(this.name,x+20,y+20);
			// be bigger
			this.radius = 45;
		}
		else{
			this.radius = 40;
		}
//		this.parent.fill(0);
	}
	
	public boolean overCircle() {		
		if(PApplet.sqrt(PApplet.sq(this.x - this.parent.mouseX) + PApplet.sq(this.y - this.parent.mouseY)) < this.radius/2 ) {	
			showName = true;
			return true;
		} 
		else{
			showName = false;
			return false;
		}
	}
	
	public void clicked(){
		if(overCircle()){
			xMove = this.x - this.parent.mouseX;
			yMove = this.y - this.parent.mouseY;
			draging = true;
		}
		else{
			draging = false;
		}
	}
	
	public void stopDraging(){
		draging = false;
	}
	
	public void drag(){
		if(draging){
			this.x = this.parent.mouseX + xMove;
			this.y = this.parent.mouseY + yMove;
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
