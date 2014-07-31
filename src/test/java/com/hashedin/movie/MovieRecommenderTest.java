package com.hashedin.movie;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class MovieRecommenderTest {

	@Test
	public void testMostPopularMovieInYear() throws IOException {
		Map<Integer, String> expectedPopularMovie = ImmutableMap.<Integer, String>builder()
				.put(1975, "One Flew Over the Cuckoo's Nest (1975)")
				.put(1980, "Empire Strikes Back, The (1980)")
				.put(1994, "Pulp Fiction (1994)")
				.build();
				
		for(Map.Entry<Integer, String> entry : expectedPopularMovie.entrySet()) {
			int year = entry.getKey();
			String expectedMovieName = entry.getValue();
			
			Movie m = getMovieRecommender().getMostPopularMovieReleasedIn(year);
			assertEquals(m.getName(), expectedMovieName);
		}
	}
	
	@Test
	public void testMostPopularMovie() throws IOException {
		assertEquals(getMovieRecommender().getMostPopularMovie().getName(), "Star Wars (1977)");
	}

	private MovieRecommender getMovieRecommender() throws IOException {
		InputStream movieStream = loadStream("movie.data");
		InputStream ratingsStream = loadStream("ratings.data");
		Map<Integer, Movie> allMovies =  new Parser().parseMovies(movieStream, ratingsStream);
		MovieRecommender recommender = new MovieRecommender(allMovies.values());
		return recommender;
	}
	
	private InputStream loadStream(String fileName) {
		return this.getClass().getClassLoader().getResourceAsStream(fileName);
	}

}
