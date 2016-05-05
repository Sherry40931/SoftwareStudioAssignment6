package main.java;

import java.util.ArrayList;

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
	private ArrayList<Character> characters;
	private int[] characterNum = new int[8];
	
	public void setup() {
		size(width, height);
		characters = new ArrayList<Character>();
		characterNum[0] = 0;
		smooth();
		loadData();
	}

	public void draw() {
		background(255);
		int i;
		// title
		textSize(30);
		text("Star Wars",width/2-70, 30);
		
		// big circle
		strokeWeight(4);
		stroke(132, 219, 0);
		fill(0,0);
		ellipse(width/2, height/2, 400, 400);
		
		// button ADD ALL
		fill(2, 202, 119, 255);
		stroke(0, 0);
		rect(width*3/4, 40, 150, 50);
		fill(255,255);
		textSize(20);
		text("ADD ALL",width*3/4+30, 70);
		
		// button CLEAR
		fill(2, 202, 119, 255);
		rect(width*3/4, 140, 150, 50);
		fill(255,255);
		textSize(20);
		text("CLEAR",width*3/4+40, 170);
		
		for(i=this.characterNum[0]; i<this.characterNum[1]; i++){
			characters.get(i).display();
		}
	}
	
	/* Change episode */
	public void keyPressed(){
		if(key == '1'){
			
		}
		else if(key == '2'){
			
		}
		else if(key == '3'){
			
		}
		else if(key == '4'){
			
		}
		else if(key == '5'){
			
		}
		else if(key == '6'){
			
		}
		else if(key == '7'){
			
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
				characters.get(s).addTarget(characters.get(t), v);
			}
		}
	}

}
