package pl.com.bottega.cineman.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.cineman.model.PriceCalculator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional		// TODO acceptance test suites should not be run within a transaction; please use DbCleaner instead
public class PriceTest {

	@Autowired
	PriceCalculator priceCalculator;

	@Test
	public void shouldCalculatePrices() {
		/*CalculatePriceCommand command = new CalculatePriceCommand();
		command.setShowId(94L);

		ReservationItem reservationItems1 = new ReservationItem();
		reservationItems1.setKind("regular");
		reservationItems1.setCount(2);
		ReservationItem reservationItems2 = new ReservationItem();
		reservationItems2.setKind("school");
		reservationItems2.setCount(1);

		CalculationItem calculationItem1 = new CalculationItem(reservationItems1, BigDecimal.valueOf(10));
		CalculationItem calculationItem2 = new CalculationItem(reservationItems2, BigDecimal.valueOf(10));

		Set<ReservationItem> reservationItems = new HashSet<>();
		reservationItems.add(reservationItems1);
		reservationItems.add(reservationItems2);
		command.setTickets(reservationItems);

		Collection<CalculationItem> calculationItems = new HashSet<>();
		calculationItems.add(calculationItem1);
		calculationItems.add(calculationItem2);
		CalculationResult calculationResult = new CalculationResult(calculationItems);

		priceCalculator.calculatePrices(command);*/
	}

}
