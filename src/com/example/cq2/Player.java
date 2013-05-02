package com.example.cq2;

public class Player {
	private String username;
	private double lat;
	private double lng;
	private String coords;
	private int troops;
	private int gold;
	private String imageUrl; //Perhaps optional!
	private boolean engaged;
	
	public Player(){
		this.setUsername("");
		this.setLat(Double.MAX_VALUE);
		this.setLng(Double.MAX_VALUE);
		this.setCoords("0,0");
		this.setTroops(Integer.MAX_VALUE);
		this.setGold(Integer.MAX_VALUE);
		this.setImageUrl("http://i.imgur.com/itVcSSC.png");
	}
	
	public Player(String username, int troops, int gold){
		this.setUsername(username);
		this.setLat(Double.MAX_VALUE);
		this.setLng(Double.MAX_VALUE);
		this.setCoords("0,0");
		this.setTroops(troops);
		this.setGold(gold);
		this.setImageUrl("http://i.imgur.com/itVcSSC.png");
	}
	
	public Player(String username, int troops){
		this.setUsername(username);
		this.setLat(Double.MAX_VALUE);
		this.setLng(Double.MAX_VALUE);
		this.setCoords("0,0");
		this.setTroops(troops);
		this.setGold(Integer.MAX_VALUE);
		this.setImageUrl("http://i.imgur.com/itVcSSC.png");
	}
	
	public Player(String username, String coords, int troops){
		this.setUsername(username);
		this.setLat(Double.MAX_VALUE);
		this.setLng(Double.MAX_VALUE);
		this.setCoords(coords);
		this.setTroops(troops);
		this.setGold(Integer.MAX_VALUE);
		this.setImageUrl("http://i.imgur.com/itVcSSC.png");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	public int getTroops() {
		return troops;
	}

	public void setTroops(int troops) {
		this.troops = troops;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isEngaged() {
		return engaged;
	}

	public void setEngaged(boolean engaged) {
		this.engaged = engaged;
	}
	
	
}
