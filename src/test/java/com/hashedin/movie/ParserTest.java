package com.hashedin.movie;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

public class ParserTest {

	@Test
	public void test() throws IOException {
		InputStream movieStream = loadStream("movie.data");
		InputStream ratingsStream = loadStream("ratings.data");
		
		Map<Integer, Movie> movies =  new Parser().parseMovies(movieStream, ratingsStream);
		
		assertNotNull(movies);
		assertTrue(movies.size() > 0);
		
		Movie toyStory = movies.get(1);
		assertEquals(toyStory.getName(), "Toy Story (1995)");
		
		assertTrue(toyStory.getGenres().contains(Genre.CRIME));
		assertTrue(toyStory.getGenres().contains(Genre.COMEDY));
		assertTrue(toyStory.getGenres().contains(Genre.CHILDREN));
	}
	
	private InputStream loadStream(String fileName) {
		return this.getClass().getClassLoader().getResourceAsStream(fileName);
	}

}
