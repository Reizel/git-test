package main;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import dao.CityDao;

import model.BaseObject;
import model.City;

public class Main {

	public static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("Старт программы!");
	
		
		CityDao citydao = CityDao.getInstance();
		
		logger.info("Вывод всех городов");
		
		ArrayList<City> arr = citydao.getList();
		for (BaseObject obj : arr) {
			System.out.println(obj);
		}
		
	}
}
