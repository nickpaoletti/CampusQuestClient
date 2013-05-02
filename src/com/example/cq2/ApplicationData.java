package com.example.cq2;

import java.util.ArrayList;

import android.app.Application;

public class ApplicationData extends Application{
	private Player user = new Player();
	private ArrayList<Player> players = new ArrayList<Player>();
	private int lastTick;
	
	public Player getUser() {
		return user;
	}
	public void setUser(Player user) {
		this.user = user;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public int getLastTick() {
		return lastTick;
	}
	public void setLastTick(int lastTick) {
		this.lastTick = lastTick;
	}
	
	
	
}
