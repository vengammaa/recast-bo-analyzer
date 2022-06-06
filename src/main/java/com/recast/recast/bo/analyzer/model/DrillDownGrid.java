package com.recast.recast.bo.analyzer.model;

public class DrillDownGrid {
	
	
private String	Universe_ID;
private String	Universe_Location;
public String getUniverse_ID() {
	return Universe_ID;
}
public void setUniverse_ID(String universe_ID) {
	Universe_ID = universe_ID;
}
public String getUniverse_Location() {
	return Universe_Location;
}
public void setUniverse_Location(String universe_Location) {
	Universe_Location = universe_Location;
}
@Override
public String toString() {
	return "DrillDownGrid [Universe_ID=" + Universe_ID + ", Universe_Location=" + Universe_Location + "]";
}


}
