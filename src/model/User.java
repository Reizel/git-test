package model;

import java.util.List;

public class User extends BaseObject {
	public static String tableRelation = "users";
	private String name;
	private String surname;
	private int age;
	private String addres = null;
	private int salary;
	public static List<String>columnNames;
	
	
	public User(){
		
	}
	public User(String name, String surname, int age, String addres, int salary) {

		this.name = name;
		this.surname = surname;
		this.age = age;
		this.addres = addres;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;

	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddres() {
		return addres;
	}

	public void setAddres(String addres) {
		this.addres = addres;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
}