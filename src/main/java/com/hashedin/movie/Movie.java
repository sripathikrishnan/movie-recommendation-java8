package com.hashedin.movie;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Preconditions;

public class Movie {

	private final int id;
	private final String name;
	private final DateTime releaseDate;
	private final URI imdbUrl;
	private final Set<Genre> genres;
	
	private int sumRatings;
	private int sumReviewers;
	
	public Movie(int id, String name, Date releaseDate, URI imdbURL, Set<Genre> genres) {
		Preconditions.checkArgument(id > 0);
		Preconditions.checkNotNull(name);
		Preconditions.checkNotNull(releaseDate);
		Preconditions.checkNotNull(imdbURL);
		Preconditions.checkNotNull(genres);
		
		this.id = id;
		this.name = name;
		this.releaseDate = new DateTime(releaseDate.getTime());
		this.imdbUrl = imdbURL;
		this.genres = Collections.unmodifiableSet(genres);
	}
	
	public void addRating(Rating rating) {
		Preconditions.checkNotNull(rating);
		sumRatings += rating.getRating();
		sumReviewers++;
	}

	public double getAverageRating() {
		if (sumReviewers == 0) {
			return Double.NaN;
		}
		else {
			return (double)sumRatings / sumReviewers;
		}
	}
	
	public int getTotalRating() {
		return this.sumRatings;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public DateTime getReleaseDate() {
		return releaseDate;
	}

	public URI getImdbUrl() {
		return imdbUrl;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	@Override
	public String toString() {
		return "Movie [name=" + name + ", rating=" + getAverageRating() + "]";
	}
}
