package com.example.cq2;

import java.io.IOException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginScreen extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_login_screen);
	    // TODO Auto-generated method stub
	}
	
	public void login(View v) throws UnknownHostException, IOException{
		EditText userNameField = (EditText) findViewById(R.id.username);
		//EditText passwordField = (EditText) findViewById(R.id.password);
		
		//Should put in some error checking Dialog here, but little touches like that are second priority for now.
		String username = userNameField.getText().toString();
		
		Server.connect();
		Server.write(new Message(0, username));
		// TODO Read in a message from the server
		Response response = Server.read();
		Server.disconnect();
		
		ApplicationData globalData = ((ApplicationData)this.getApplication());
		globalData.setUser(new Player(username, response.userTroops, response.userGold));
		globalData.setLastTick(0);
		//start the Map Screen activity
		this.startActivity(new Intent(this, MapScreen.class));
	}

}
