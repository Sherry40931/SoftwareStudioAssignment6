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
	private String[] file = {"starwars-episode-1-interactions.json", "starwars-episode-2-interactions.json", 
							"starwars-episode-3-interactions.json", "starwars-episode-4-interactions.json", 
							"starwars-episode-5-interactions.json", "starwars-episode-6-interactions.json", 
							"starwars-episode-7-interactions.json"};
	
	private final static int width = 1200, height = 650;
	private ArrayList<Character> characters;
	
	public void setup() {
		size(width, height);
		characters = new ArrayList<Character>();
		smooth();
		loadData();
	}

	public void draw() {
		background(255);
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
		
		for(episode=0; episode<7; episode++){
			data = loadJSONObject(path+file[episode]);
			nodes = data.getJSONArray("nodes");
			links = data.getJSONArray("links");
			for(i=0; i<nodes.size(); i++){
				tmp = nodes.getJSONObject(i);
				
				String n = tmp.getString("name");
				int v = tmp.getInt("value");
				String c = tmp.getString("colour");
				
	//			characters.add(new Character(this, n, x, y));
			}
			for(i=0; i<links.size(); i++){
				tmp = links.getJSONObject(i);
				int s = tmp.getInt("source");
				int t = tmp.getInt("target");
				int v = tmp.getInt("value");
	//			characters.get(s).addTarget(characters.get(t));
			}
		}
	}

}
