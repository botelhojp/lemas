package lemas.trust.data;

import java.util.Hashtable;

import openjade.ontology.Rating;

public class RatingCache {
	
	private static Hashtable<Integer, Rating> cache = new Hashtable<Integer, Rating>();
	
	public static void put(Integer id, Rating value){
		cache.put(id, value);
	}
	
	public static Rating remove(Integer id){
		return cache.remove(id);
	}

}
