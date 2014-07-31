package com.hashedin.movie;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

public class Parser {

	private static final int POS_ID = 0;
	private static final int POS_NAME = 1;
	private static final int POS_RELEASE_DATE = 2;
	private static final int POS_IMDB_URL = 3;
	private static final int POS_START_GENRE = 4;
	private static final int POS_END_GENRE = POS_START_GENRE + Genre.values().length;

	private static final int POS_USER_ID = 0;
	private static final int POS_MOVIE_ID = 1;
	private static final int POS_RATING = 2;
	
	public Map<Integer, Movie> parseMovies(InputStream movieStream, InputStream ratingsStream) throws IOException {
		Map<Integer, Movie> movies = parseMoviesInternal(movieStream);
		List<Rating> ratings = parseRatingsInternal(ratingsStream);
		
		//Add ratings to each Movie
		for(Rating r : ratings) {
			Movie m = movies.get(r.getMovieId());
			if(m != null) {
				m.addRating(r);
			}
		}
		
		return Collections.unmodifiableMap(movies);
	}

	private List<Rating> parseRatingsInternal(InputStream ratingsStream) throws IOException {
		@SuppressWarnings("unchecked")
		List<String> lines = IOUtils.readLines(ratingsStream);
		return lines.stream()
				.map(Parser::parseRatingsLine)
				.collect(Collectors.toList());
	}

	private Map<Integer, Movie> parseMoviesInternal(InputStream movieStream) throws IOException {
		@SuppressWarnings("unchecked")
		List<String> lines = IOUtils.readLines(movieStream);
		
		return lines.stream()
				.map(Parser::parseMovieLine)
				.collect(Collectors.toMap(m->m.getId(), m->m));
	}

	private static Rating parseRatingsLine(String line) {
		String tokens[] = line.split("\\s");
		int userId = Integer.parseInt(tokens[POS_USER_ID]);
		int movieId = Integer.parseInt(tokens[POS_MOVIE_ID]);
		int rating = Integer.parseInt(tokens[POS_RATING]);
		
		return new Rating(userId, movieId, rating);
	}
	
	private static Movie parseMovieLine(String line) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String tokens[] = line.split("\\|");
		int id = Integer.parseInt(tokens[POS_ID]);
		String name = tokens[POS_NAME];
		Date releaseDate;
		try {
			releaseDate = sdf.parse(tokens[POS_RELEASE_DATE]);
		}
		catch(ParseException pe) {
			releaseDate = new Date();
		}
		URI imdbUri;
		try {
			imdbUri = new URI(tokens[POS_IMDB_URL]);
		}
		catch(URISyntaxException e) {
			imdbUri = null;
		}
		
		Set<Genre> genres = new HashSet<Genre>();
		for(int i=POS_START_GENRE; i<POS_END_GENRE; i++) {
			if(tokens[i].equals("1")) {
				int genreId = i - POS_START_GENRE;
				genres.add(Genre.fromId(genreId));
			}
		}
		return new Movie(id, name, releaseDate, imdbUri, genres);
	}
}
