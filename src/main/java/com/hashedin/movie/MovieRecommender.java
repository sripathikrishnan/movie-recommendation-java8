package com.hashedin.movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

public class MovieRecommender {

	private final List<Movie> allMovies;
	
	public MovieRecommender(Collection<Movie> allMovies) {
		Preconditions.checkNotNull(allMovies);
		Preconditions.checkArgument(allMovies.size() > 0);
		
		this.allMovies = Collections.unmodifiableList(new ArrayList<Movie>(allMovies));
	}
	
	public Movie getMostPopularMovieReleasedIn(int year) {
		return this.allMovies.stream()
				.filter(m -> m.getReleaseDate().getYear() == year)
				.max(MovieRecommender::getPopularMovieBySumRating)
				.get();
	}
	
	public Movie getMostPopularMovieByGenre(Genre g) {
		return this.allMovies.stream()
				.filter(m -> m.getGenres().contains(g))
				.max(MovieRecommender::getPopularMovieBySumRating)
				.get();
	}
	
	public Movie getMostPopularMovie() {
		return this.allMovies.stream()
			.max(MovieRecommender::getPopularMovieBySumRating)
			.get();
	}
	
	private static int getPopularMovieBySumRating(Movie m1, Movie m2) {
		return (int)(m1.getTotalRating() - m2.getTotalRating());
	}
}
