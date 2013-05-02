package com.example.cq2;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class MapScreen extends Activity implements LocationListener, OnClickListener{

	Geocoder geocoder;
	LocationManager manager;
	double latitude;
	double longitude;
	ApplicationData globalData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_screen);
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		geocoder = new Geocoder(this.getApplicationContext(), Locale.getDefault());
		globalData = ((ApplicationData)this.getApplication());

		View button = findViewById(R.id.button2);
	    button.setOnClickListener(this);
	    
	    //TextView gold = (TextView) findViewById(R.id.gold);
	    //gold.setText(globalData.getUser().getGold());
	    System.out.println( globalData.getUser().getUsername() + " " + globalData.getUser().getTroops() + " " + globalData.getUser().getGold() );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_screen, menu);
		return true;
	}

	public void findLocation(View view) throws IOException{
		
		//Get most recent latitude and longitude
		manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		
		System.out.println("Latitude: " + latitude + " Longitude: " + longitude);
		globalData.getUser().setLat(latitude);
		globalData.getUser().setLng(longitude);
		
		//If a latitude and longitude has been found, send it to the server.
		if (!(latitude == 0.0 && longitude == 0.0)){
			Server.connect();
			Server.write(new Message(1, globalData.getUser().getUsername(), latitude,longitude));
			Response response = Server.read();
			Server.disconnect();
			
			//Obtain grid coordinates from the server.
			globalData.getUser().setCoords((response.gridRow+1) + "," + (response.gridColumn+1));
			System.out.println("User coords: " + globalData.getUser().getCoords());
			
			TextView addressDisplay = (TextView) findViewById(R.id.textView3);
			addressDisplay.setText("(" + globalData.getUser().getCoords() + ")");
			
			mapDisplay();
		}
		else {
			//If no latitude and longitude are found, display an error.
			TextView addressDisplay = (TextView) findViewById(R.id.textView3);
			addressDisplay.setText("No location found. Try again soon.");
		}
	}
	
	//When location is changed, update latitude and longitude.
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	public void onClick(View v) {
		// Scout location and get players present at it.
		if(v.getId() == R.id.button2){
			try {
				Server.connect();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Server.write(new Message(2, globalData.getUser().getUsername()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Read in a message from the server
			Response response = null;
			try {
				response = Server.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Server.disconnect();
			
			//Update player information if the server has more recent information.
			if (response.clockTick > globalData.getLastTick()){
				Response playerResponse = null;
				try {
					Server.write(new Message(0, globalData.getUser().getUsername()));
					playerResponse = Server.read();
					Server.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				globalData.getUser().setTroops(playerResponse.userTroops);
				globalData.getUser().setGold(playerResponse.userGold);
				
				TextView gold = (TextView) findViewById(R.id.gold);
			    gold.setText(globalData.getUser().getGold());
				
				globalData.setLastTick(playerResponse.clockTick);
			}
			
			//Get a list of players present at that grid location.
			ArrayList<Player> players = new ArrayList<Player>(response.playersPresent.size());
			for (int i = 0; i < response.playersPresent.size(); i++){
				Player newPlayer = new Player(response.playersPresent.get(i), response.troopCounts.get(i));
				players.add(newPlayer);
			}
			globalData.setPlayers(players);
			
			//define a new Intent for the second Activity
			Intent intent = new Intent(this,BattleScreen.class);
	 
			//start the second Activity
			this.startActivity(intent);
		}
		
	}
	
	//Display icon on grid coordinates of map.
	public void mapDisplay(){
		String coords = globalData.getUser().getCoords();
		ImageView map = (ImageView) findViewById(R.id.imageView1);

		if (coords.equals("1,1")){
			map.setImageResource(R.drawable.map11);
		}
		else if (coords.equals("1,2")){
			map.setImageResource(R.drawable.map12);
		}
		else if (coords.equals("1,3")){
			map.setImageResource(R.drawable.map13);
		}
		else if (coords.equals("2,1")){
			map.setImageResource(R.drawable.map21);
		}
		else if (coords.equals("2,2")){
			map.setImageResource(R.drawable.map22);
		}
		else if (coords.equals("2,3")){
			map.setImageResource(R.drawable.map23);
		}
		else if (coords.equals("3,1")){
			map.setImageResource(R.drawable.map21);
		}
		else if (coords.equals("3,2")){
			map.setImageResource(R.drawable.map32);
		}
		else if (coords.equals("3,3")){
			map.setImageResource(R.drawable.map33);
		}
	}	
}
