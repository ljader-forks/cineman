package pl.com.bottega.cineman.model.commands;import java.math.BigDecimal;import java.util.Map;public class DefineMoviePricesCommand implements Validatable {	private Map<String, BigDecimal> prices;	private Long movieId;	public DefineMoviePricesCommand() {	}	public Map<String, BigDecimal> getPrices() {		return prices;	}	public void setPrices(Map<String, BigDecimal> prices) {		this.prices = prices;	}	public void setMovieId(Long movieId) {		this.movieId = movieId;	}	public Long getMovieId() {		return movieId;	}	@Override	public void validate(ValidationErrors errors) {		// TODO: 2017-04-22	}}