package com.hashedin.movie;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

public enum Genre {

	UNKNOWN(0),
	ACTION(1),
	ADVENTURE(2),
	ANIMATION(3),
	CHILDREN(4),
	COMEDY(5),
	CRIME(6),
	DOCUMENTARY(7),
	DRAMA(8),
	FANTASY(9),
	FILM_NOIR(10),
	HORROR(11),
	MUSICAL(12),
	MYSTERY(13),
	ROMANCE(14),
	SCI_FI(15),
	THRILLER(16),
	WAR(17),
	WESTERN(18);
	
	private int id;
	
	private static final Map<String, Genre> GENRE_LOOKUP = createLookupTable();
	private Genre(int _id) {
		this.id = _id;
	}
	
	private static Map<String, Genre> createLookupTable() {
		Map<String, Genre> lookupTable = new HashMap<String, Genre>();
		for(Genre genre : Genre.values()) {
			String lookupKey = removeNonAlphabets(genre.toString());
			lookupTable.put(lookupKey, genre);
			lookupTable.put(Integer.toString(genre.getId()), genre);
		}
		return Collections.unmodifiableMap(lookupTable);
	}

	private static String removeNonAlphabets(String str) {
		return str.toLowerCase().replaceAll("[^a-zA-Z]", "");
	}
	
	public int getId() {
		return this.id;
	}
	
	/**
	 * Looks up a Genre from a genre name. 
	 * 
	 * Does some basic normalization, so Sc-Fi, ScFi, scfi etc should all work
	 * 
	 * @param genreName
	 * @return A Genre object if found, or IlegalArgumentException if it cannot be found
	 */
	public static Genre fromString(String genreName) {
		Preconditions.checkNotNull(genreName, "Genre Name cannot be null");
		String lookupKey = removeNonAlphabets(genreName);
		Genre g = GENRE_LOOKUP.get(lookupKey);
		if(g == null) {
			throw new IllegalArgumentException(String.format("Genre %s does not exist", genreName));
		}
		return g;
	}

	public static Genre fromId(int genreId) {
		Preconditions.checkArgument(genreId >=0 && genreId < Genre.values().length);
		return GENRE_LOOKUP.get(Integer.toString(genreId));
	}
}
