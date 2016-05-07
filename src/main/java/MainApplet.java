package main.java;

import java.util.ArrayList;

import org.w3c.dom.Node;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String[] file = {"", "starwars-episode-1-interactions.json", "starwars-episode-2-interactions.json", 
							"starwars-episode-3-interactions.json", "starwars-episode-4-interactions.json", 
							"starwars-episode-5-interactions.json", "starwars-episode-6-interactions.json", 
							"starwars-episode-7-interactions.json"};
	
	private final static int width = 1200, height = 650;
	private int centerX = width/2, centerY = height/2, bigCircleRadius = 400;
	private ArrayList<Character> characters;
	private int[] characterNum = new int[8];
	private ArrayList<Character> count;  
	private int curEpisode=1;
	
	public void setup() {
		size(width, height);
		characters = new ArrayList<Character>();
		count = new ArrayList<Character>();
		characterNum[0] = 0;
		smooth();
		loadData();
	}

	public void draw() {
		background(255);
		int i;
		// title
		textSize(30);
		fill(10,150);
		text("Star Wars",width/2-70, 30);
		
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
		
		
		for(i=this.characterNum[this.curEpisode-1]; i<this.characterNum[this.curEpisode]; i++){
			characters.get(i).display();
			characters.get(i).drag();
			
			characters.get(i).drawConnections(count);
			
		}
	}
	
	private boolean inBigCircle(){
		if(sqrt(sq(centerX - mouseX) + sq(centerY - mouseY)) < bigCircleRadius/2 ) {	
			return true;
		} 
		else{
			return false;
		}
	}
	
	private boolean inButton(int x, int y){
		int w = 150, h = 50;
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h){
			return true;
		}
		return false;
	}

	
	/* Change episode */
	public void keyPressed(){
		if(key == '1'){
			this.curEpisode = 1;
		}
		else if(key == '2'){
			this.curEpisode = 2;
		}
		else if(key == '3'){
			this.curEpisode = 3;
		}
		else if(key == '4'){
			this.curEpisode = 4;
		}
		else if(key == '5'){
			this.curEpisode = 5;
		}
		else if(key == '6'){
			this.curEpisode = 6;
		}
		else if(key == '7'){
			this.curEpisode = 7;
		}
	}
	
	
	public void mousePressed(){
		for(int i=this.characterNum[0]; i<this.characterNum[1]; i++){
			characters.get(i).clicked();
		}
	}
	
	public void mouseReleased(){
		int i;
		float[] angle = new float[100];
		float[] vertX = new float[100];
		float[] vertY = new float[100];
		
		// drag into circle
		for(i=this.characterNum[0]; i<this.characterNum[1]; i++){
			if(characters.get(i).draging){
				if(inBigCircle()){
					if(!count.contains(characters.get(i))){	// avoid double add
						count.add(characters.get(i));
						characters.get(i).setInCricle(true);
					}
				}
				else{
					if(count.contains(characters.get(i))){	// avoid null exception
						count.remove(count.indexOf(characters.get(i)));
						characters.get(i).setInCricle(false);
					}
				}
			}
		}
		
		///////  animation in circle  ///////
		angle[0] = 0;
		vertX[0] = this.bigCircleRadius/2;
		vertY[0] = 0;
		// build circle position
		for(i=1; i<count.size(); i++){
			angle[i] =(float) ((float)angle[i-1] + 2*PI/count.size());
			vertX[i] = cos(angle[i])*this.bigCircleRadius/2;
			vertY[i] = sin(angle[i])*this.bigCircleRadius/2;
		}
		// map nodes to positions
		for(i=0; i<count.size(); i++){
			count.get(i).x = vertX[i] + this.centerX; 
			count.get(i).y = vertY[i] + this.centerY;
		}
		
		
		for(i=this.characterNum[0]; i<this.characterNum[1]; i++){
			characters.get(i).stopDraging();
		}
		
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
				int s = tmp.getInt("source");
				int t = tmp.getInt("target");
				int v = tmp.getInt("value");
				if(v > 10){
					v = 10;
				}
				characters.get(s).addTarget(characters.get(t), v);
			}
		}
	}

}
