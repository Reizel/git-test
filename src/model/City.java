package model;

import java.util.List;


public class City extends BaseObject {
	public static String tableRelation = "cities";
	private String name;
	public static List<String>columnNames;
	
	
	public City(){
		
	}
	public City(String name){
		this.name=name;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString(){
		return "id="+this.id+"| name="+this.name;
	}
	
}
