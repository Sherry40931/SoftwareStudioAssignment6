package main.java;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

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
	private ArrayList<Integer> values = new ArrayList<Integer>();
	private boolean showName, isInCircle=false;
	public boolean draging=false;
	private int centerX = 1200/2, centerY = 650/2, bigCircleRadius = 400;
	
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
	
	public void drawConnections(ArrayList<Character> inCircleNode){
//		for(int i=0; i<inCircleNode.size(); i++){
//			System.out.println(inCircleNode.get(i).name);
//		}
		for(int i=0; i<inCircleNode.size(); i++){
			if(targets.contains(inCircleNode.get(i)) && this.isInCircle){
				int targetIndex = targets.indexOf(inCircleNode.get(i));
				float targetX = inCircleNode.get(i).x;
				float targetY = inCircleNode.get(i).y;
				
				this.parent.strokeWeight(values.get(targetIndex));
				this.parent.stroke(0, 150);
				this.parent.noFill();
				this.parent.bezier(x, y, (this.centerX+x)/2, (this.centerY+y)/2, (this.centerX+targetX)/2, (this.centerY+targetY)/2, targetX, targetY);
			}
		}
	}
	
	public void setInCricle(boolean condition){
		this.isInCircle = condition;
	}
	
	/*
	 * Add the target to the array list when loading file.
	 */
	public void addTarget(Character target, int nodeValue){
		targets.add(target);
		values.add(nodeValue);
	}
	
	public ArrayList<Character> getTargets(){
		return this.targets;
	}
}
