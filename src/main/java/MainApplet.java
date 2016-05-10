package main.java;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";	//the inner path to json file
	private String[] file = {"", "starwars-episode-1-interactions.json", "starwars-episode-2-interactions.json", 
							"starwars-episode-3-interactions.json", "starwars-episode-4-interactions.json", 
							"starwars-episode-5-interactions.json", "starwars-episode-6-interactions.json", 
							"starwars-episode-7-interactions.json"};
	private final static int width = 1200, height = 650;
	private int centerX = width/2, centerY = height/2, bigCircleRadius = 400;
	private ArrayList<Character> characters;
	private int[] characterNum = new int[8];	//an array storing the character number from each episode
	private ArrayList<Character> inCircleNodes;  //an arraylist recording which characters are in the big circle 
	private int curEpisode=1, mousePointOnCharNum = -1, mousePressOnCharNum = -1;
	private boolean allIn=false;
	
	public void setup() {
		size(width, height);
		characters = new ArrayList<Character>();
		inCircleNodes = new ArrayList<Character>();
		characterNum[0] = 0;
		smooth();
		loadData();		//load all json files to get characters attributes
	}

	public void draw() {
		background(255);	//white background
		// title
		textSize(30);
		fill(10,150);
		text("Star Wars "+this.curEpisode,width/2-70, 30);
		
		// big circle with hovering effect 
		stroke(132, 219, 0);
		fill(0,0);
		if(inBigCircle()){
			strokeWeight(10);
		}
		else{
			strokeWeight(4);
		}
		ellipse(width/2, height/2, 400, 400);
		
		
		// button ADD ALL with hovering effect & pressing effect
		if(inButton(width*3/4, 40)){
			if(mousePressed){	// if pressed
				fill(2, 0, 127, 255);
				stroke(0, 0);
				rect(width*3/4, 40, 150, 50);
			}
			else{
				fill(2, 202, 119, 200);
				stroke(0, 0);
				rect(width*3/4, 40, 150, 50);
			}
		}
		else{
			fill(2, 202, 119, 255);
			stroke(0, 0);
			rect(width*3/4, 40, 150, 50);
		}
		fill(255,255);
		textSize(20);
		text("ADD ALL",width*3/4+30, 70);
		
		
		// button CLEAR with hovering effect & pressing effect
		if(inButton(width*3/4, 140)){
			if(mousePressed){	// if pressed
				fill(2, 0, 127, 255);
				rect(width*3/4, 140, 150, 50);
			}
			else{
				fill(2, 202, 119, 200);
				rect(width*3/4, 140, 150, 50);
			}
		}
		else{
			fill(2, 202, 119, 255);
			rect(width*3/4, 140, 150, 50);
		}
		fill(255,255);
		textSize(20);
		text("CLEAR",width*3/4+40, 170);
		
		//display all characters and find the one mouse pointing on, then show its name
		//also if the character is in the big circle, draw its link to another character in the big circle
		for(int i=this.characterNum[this.curEpisode-1]; i<this.characterNum[this.curEpisode]; i++){
			characters.get(i).display();
			if(characters.get(i).overCircle()) mousePointOnCharNum = i;
			characters.get(i).drag();
			if(characters.get(i).isCharInCircle()) characters.get(i).drawConnections(inCircleNodes);
		}
		if(mousePointOnCharNum >= 0){
			characters.get(mousePointOnCharNum).displayName();
			mousePointOnCharNum = -1;
		}
	}
	
	//function to tell if the mouse is in the big circle, if so we made a hover effect
	private boolean inBigCircle(){
		if(sqrt(sq(centerX - mouseX) + sq(centerY - mouseY)) < bigCircleRadius/2 ) {	
			return true;
		} 
		else{
			return false;
		}
	}
	
	//function to tell if the mouse is on the button
	private boolean inButton(int x, int y){
		int w = 150, h = 50;
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h){
			return true;
		}
		return false;
	}
	
	//function to reset all characters in big circle to the original place and clear the inCircleNodes arraylist
	public void reset(){
		for(Character inCircleChar : inCircleNodes){
			inCircleChar.setInCricle(false);
			inCircleChar.returnOriginalPlace();
		}
		this.inCircleNodes.clear();
		this.allIn = false;
	}
	
	//function to add all characters to the big circle
	public void addAll(){
		for(int i=this.characterNum[this.curEpisode-1]; i<this.characterNum[this.curEpisode]; i++){
			if(!inCircleNodes.contains(characters.get(i))){
				this.inCircleNodes.add(this.characters.get(i));
				characters.get(i).setInCricle(true);
			}
		}
		this.allIn = true;
	}

	// key in the episode number to change episode
	public void keyPressed(){
		if(key == '1'){
			this.curEpisode = 1;
			reset();
		}
		else if(key == '2'){
			this.curEpisode = 2;
			reset();
		}
		else if(key == '3'){
			this.curEpisode = 3;
			reset();
		}
		else if(key == '4'){
			this.curEpisode = 4;
			reset();
		}
		else if(key == '5'){
			this.curEpisode = 5;
			reset();
		}
		else if(key == '6'){
			this.curEpisode = 6;
			reset();
		}
		else if(key == '7'){
			this.curEpisode = 7;
			reset();
		}
	}
	
	//if user press the mouse, the function tell where the mouse is pressed and the corresponding motion
	public void mousePressed(){
		mousePressOnCharNum = -1;
		for(int i=this.characterNum[this.curEpisode-1]; i<this.characterNum[this.curEpisode]; i++){
			if(characters.get(i).overCircle())mousePressOnCharNum = i;
		}
		if(mousePressOnCharNum >= 0) characters.get(mousePressOnCharNum).clicked();
		
		// button CLEAR
		if(inButton(width*3/4, 140)){
			reset();
		}
		
		// button ADD ALL
		if(inButton(width*3/4, 40)){
			if(!this.allIn){	// avoid exception
				addAll();
			}
		}
	}
	
	//once user release the mouse, the function determines where the mouse released and do corresponing work
	public void mouseReleased(){
		int i;
		float[] angle = new float[100];
		float[] vertX = new float[100];
		float[] vertY = new float[100];
		
		if(mousePressOnCharNum >= 0 && characters.get(mousePressOnCharNum).draging){
			if(inBigCircle()){		//if release in the big circle
				if(!inCircleNodes.contains(characters.get(mousePressOnCharNum))){	// avoid double add
					inCircleNodes.add(characters.get(mousePressOnCharNum));
					characters.get(mousePressOnCharNum).setInCricle(true);
				}
			}
			else{
				//if the node was in the big circle
				if(inCircleNodes.contains(characters.get(mousePressOnCharNum))){	// avoid null exception
					inCircleNodes.remove(inCircleNodes.indexOf(characters.get(mousePressOnCharNum)));
					characters.get(mousePressOnCharNum).setInCricle(false);
				}
				characters.get(mousePressOnCharNum).returnOriginalPlace();
			}
		}
		
		if(this.inCircleNodes.size() == this.characterNum[this.curEpisode] - this.characterNum[this.curEpisode-1]){
			this.allIn = true;
		}
		
		//  animation in circle  
		angle[0] = 0;
		vertX[0] = this.bigCircleRadius/2;
		vertY[0] = 0;
		// build circle position
		for(i=1; i<inCircleNodes.size(); i++){
			angle[i] =(float) ((float)angle[i-1] + 2*PI/inCircleNodes.size());
			vertX[i] = cos(angle[i])*this.bigCircleRadius/2;
			vertY[i] = sin(angle[i])*this.bigCircleRadius/2;
		}
		// map nodes to positions
		for(i=0; i<inCircleNodes.size(); i++){
			inCircleNodes.get(i).x = vertX[i] + this.centerX; 
			inCircleNodes.get(i).y = vertY[i] + this.centerY;
		}
		
		if(mousePressOnCharNum >= 0) characters.get(mousePressOnCharNum).stopDraging();
		
	}
	
	private void loadData(){
		JSONArray nodes, links;
		JSONObject data, tmp;
		int i, episode;
		float x=40, y=40;
		
		for(episode=1; episode<=7; episode++){
			data = loadJSONObject(path+file[episode]);
			nodes = data.getJSONArray("nodes");
			links = data.getJSONArray("links");
			
			// record the number of characters of each episode
			this.characterNum[episode] = nodes.size() + this.characterNum[episode-1];
			x=40; y=40;
			for(i=0; i<nodes.size(); i++){
				tmp = nodes.getJSONObject(i);
				
				String n = tmp.getString("name");
				int v = tmp.getInt("value");
				String c = tmp.getString("colour");
				
				characters.add(new Character(this, n, x, y, v, c));
				x += 60;
				// next line
				if(x >= 240){
					y += 60;
					x = 40;
				}
			}
			for(i=0; i<links.size(); i++){
				tmp = links.getJSONObject(i);
				int s = tmp.getInt("source") + this.characterNum[episode-1];
				int t = tmp.getInt("target") + this.characterNum[episode-1];
				int v = tmp.getInt("value");
				if(v > 10){
					v = v/4;
				}
				characters.get(s).addTarget(characters.get(t), v);
			}
		}
	}
}
