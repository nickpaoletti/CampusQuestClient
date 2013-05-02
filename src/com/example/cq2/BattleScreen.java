package com.example.cq2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class BattleScreen extends Activity {

	ApplicationData globalData;
	int currentTarget;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_battle_screen);
	    // TODO Auto-generated method stub
	    
		globalData = ((ApplicationData)this.getApplication());
		populateEnemies();
	    
	    currentTarget = 0;
	    
	    TextView gridCoords = (TextView) findViewById(R.id.gridCoords);
	    gridCoords.setText("(" + globalData.getUser().getCoords() + ")");
	}

	
	@Override
	public void onStart(){
		super.onStart();
		Spinner playerSpinner = (Spinner) findViewById(R.id.spinner1);
		playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { 
		        showEnemy(i);
		    } 

		    public void onNothingSelected(AdapterView<?> adapterView) {
		        return;
		    } 
		});
		 
	}
	
	
	public void goBack(View v){
		Intent intent = new Intent(this,MapScreen.class);
		 
		//start the second Activity
		this.startActivity(intent);
		
	}
	
	public void attackEnemy(View v) throws IOException{
		EditText attackingTroopsBox = (EditText) findViewById(R.id.attackingTroops);
		int attackingTroops = Integer.parseInt(attackingTroopsBox.getText().toString());
		
		Server.connect();
		Server.write(new Message(4, globalData.getUser().getUsername(), globalData.getPlayers().get(currentTarget).getUsername()));
		Response pointlessResponse = Server.read();
		Server.disconnect();
		
		Server.connect();
		Server.write(new Message(5, globalData.getUser().getUsername(), globalData.getPlayers().get(currentTarget).getUsername(), attackingTroops));
		Response response = Server.read();
		Server.disconnect();
		

		TextView playerTroops = (TextView) findViewById(R.id.playerTroops);
		
		globalData.getUser().setTroops(globalData.getUser().getTroops() - attackingTroops);
		globalData.getPlayers().get(currentTarget).setEngaged(true);
		
		playerTroops.setText("" + (globalData.getUser().getTroops()));
		
		TextView enemyTroops = (TextView) findViewById(R.id.enemyTroops);
		enemyTroops.setTextColor(Color.RED);
		
		Intent intent = new Intent(this,MapScreen.class);
		 
		//start the second Activity
		this.startActivity(intent);
	}
	
	private void showEnemy(int i){
		TextView enemyName = (TextView) findViewById(R.id.enemyName);
	    enemyName.setText("" + globalData.getPlayers().get(i).getUsername());
	    
	    TextView enemyTroops = (TextView) findViewById(R.id.enemyTroops);
	    enemyTroops.setText("" + globalData.getPlayers().get(i).getTroops());
	    
	    if (globalData.getPlayers().get(i).isEngaged()){
	    	enemyTroops.setTextColor(Color.RED);
	    }
	    else {
	    	enemyTroops.setTextColor(Color.BLACK);
	    }
	    
	    currentTarget = i;
	    
	}

	private void populateEnemies(){
		Spinner playerSpinner = (Spinner) findViewById(R.id.spinner1);

		ArrayList<Player> players = globalData.getPlayers();
		List<String> playerList = new ArrayList<String>();
		
		for (Player p: players){
				playerList.add(p.getUsername());
		}
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, playerList);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    playerSpinner.setAdapter(adapter);
	    
	    TextView playerName = (TextView) findViewById(R.id.playerName);
	    playerName.setText(globalData.getUser().getUsername());
	    
	    TextView playerTroops = (TextView) findViewById(R.id.playerTroops);
	    playerTroops.setText("" + globalData.getUser().getTroops());
	    
    	Button claim = (Button) findViewById(R.id.claim);
    	ImageView icon = (ImageView) findViewById(R.id.imageView2);

    	
	    if (players.size() == 0){
	    	claim.setVisibility(View.VISIBLE);
	    	icon.setBackgroundResource(R.drawable.none);
	    }
	    else {
	    	claim.setVisibility(View.INVISIBLE);
	    	icon.setBackgroundResource(R.drawable.ajay);
	    }
	}
	
	public void claim(View v) throws IOException{
		Server.connect();
		Server.write(new Message(3, globalData.getUser().getUsername()));
		Response response = Server.read();
		Server.disconnect();
		
		Intent intent = new Intent(this,MapScreen.class);
		 
		//start the second Activity
		this.startActivity(intent);
	}
	
}
