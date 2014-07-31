package com.hashedin.movie;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class GenreTest {

	@Test
	public void testFromString() {
	
		Map<String, Genre> successLookups = ImmutableMap.<String, Genre>builder()
			.put("scifi", Genre.SCI_FI)
			.put("sci-fi", Genre.SCI_FI)
			.put("sci fi", Genre.SCI_FI)
			.put("Sci Fi", Genre.SCI_FI)
			.build();
		
		for(Map.Entry<String, Genre> test : successLookups.entrySet()) {
			assertEquals(String.format("Cannot find Genre object for %s", test.getKey()), 
					Genre.fromString(test.getKey()), test.getValue());
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidGenre() {
		Genre.fromString("Invaid Genre");
	}

	@Test
	public void testGetGenreFromId() {
		for(Genre g : Genre.values()) {
			assertEquals(g, Genre.fromId(g.getId()));
		}
	}
}
