package pl.com.bottega.cineman.infrastructure;/** * Created by Bartek on 2017-04-09. */public class CinemaNotFoundException extends RuntimeException {	public CinemaNotFoundException(Long id) {		super(String.format("Cinema with id %s does not exist", id));	}}