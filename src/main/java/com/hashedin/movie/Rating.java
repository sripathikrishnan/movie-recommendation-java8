package com.hashedin.movie;

import com.google.common.base.Preconditions;

public class Rating {

	private final int movieId;
	private final int userId;
	private final int rating;
	
	public Rating(int userId, int movieId, int rating) {
		Preconditions.checkArgument(userId < 1000);
		Preconditions.checkArgument(movieId < 1800);
		Preconditions.checkArgument(rating <= 5);
		this.userId = userId;
		this.movieId = movieId;
		this.rating = rating;
	}

	public int getMovieId() {
		return movieId;
	}

	public int getUserId() {
		return userId;
	}

	public int getRating() {
		return rating;
	}
}
