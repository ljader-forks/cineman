package pl.com.bottega.cineman.application.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.cineman.application.*;
import pl.com.bottega.cineman.model.*;
import pl.com.bottega.cineman.model.commands.*;

import java.util.List;

import static pl.com.bottega.cineman.model.commands.Validatable.*;

@Transactional
public class StandardAdminPanel implements AdminPanel {

	private CinemaRepository cinemaRepository;
	private MovieRepository movieRepository;
	private ShowingRepository showingRepository;
	private CinemaCatalog cinemaCatalog;
	private MovieCatalog movieCatalog;

	public StandardAdminPanel(
			CinemaRepository cinemaRepository,
			MovieRepository movieRepository,
			ShowingRepository showingRepository,
			CinemaCatalog cinemaCatalog,
			MovieCatalog movieCatalog) {
		this.cinemaRepository = cinemaRepository;
		this.movieRepository = movieRepository;
		this.showingRepository = showingRepository;
		this.cinemaCatalog = cinemaCatalog;
		this.movieCatalog = movieCatalog;
	}

	@Override
	public void createCinema(CreateCinemaCommand command) {
		ValidationErrors errors = new ValidationErrors();
		command.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);

		checkDuplicatedCinemas(command);

		Cinema cinema = new Cinema(command);
		cinemaRepository.put(cinema);
	}

	private void checkDuplicatedCinemas(CreateCinemaCommand command) {
		List<CinemaDto> cinemaDtos = cinemaCatalog.getCinemas();
		for (CinemaDto cinemaDto : cinemaDtos) {
			if (cinemaDto.getCity().equals(command.getCity()) && cinemaDto.getName().equals(command.getName()))
				throw new DuplicateRecordException();
		}
	}

	@Override
	public void createMovie(CreateMovieCommand command) {
		ValidationErrors errors = new ValidationErrors();
		command.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);

		checkDuplicatedMovies(command);

		Movie movie = new Movie(command);
		movieRepository.put(movie);
	}

	private void checkDuplicatedMovies(CreateMovieCommand command) {
		List<MovieDto> movieDtos = movieCatalog.getMovies();
		for (MovieDto movieDto : movieDtos) {
			if (movieDto.getTitle().equals(command.getTitle())
					&& movieDto.getDescription().equals(command.getDescription())
					&& movieDto.getActors().equals(command.getActors())
					&& movieDto.getGenres().equals(command.getGenres())
					&& movieDto.getMinAge().equals(command.getMinAge())
					&& movieDto.getLength().equals(command.getLength()))
				throw new DuplicateRecordException();
		}
	}

	@Override
	public void createShowings(CreateShowingsCommand command) {
//		ValidationErrors errors = new ValidationErrors();
//		command.validate(errors);
//		if (!errors.isValid())
//			throw new InvalidCommandException(errors);
		Cinema cinema = cinemaRepository.get(command.getCinemaId());
		Movie movie = movieRepository.get(command.getMovieId());
		ShowingFactory showingFactory = new ShowingFactory();
		List<Showing> showings = showingFactory.createShowings(command, cinema, movie);
		for (Showing showing : showings)
			showingRepository.put(showing);
	}

}
