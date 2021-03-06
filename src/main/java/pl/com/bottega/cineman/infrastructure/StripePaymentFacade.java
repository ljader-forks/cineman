package pl.com.bottega.cineman.infrastructure;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import pl.com.bottega.cineman.model.ChargeResult;
import pl.com.bottega.cineman.model.CreditCard;
import pl.com.bottega.cineman.model.PaymentFacade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class StripePaymentFacade implements PaymentFacade {

	private String private_api_key;

	public StripePaymentFacade(String private_api_key) {
		this.private_api_key = private_api_key;
	}

	@Override
	public ChargeResult charge(CreditCard creditCard, BigDecimal amount) {
		RequestOptions requestOptions = createRequestOptions();
		Map<String, Object> chargeParams = createChargeParams(creditCard, amount);
		ChargeResult chargeResult;
		try {
			Charge charge = Charge.create(chargeParams, requestOptions);
			chargeResult = createChargeResult(charge);
		} catch (StripeException ex) {
			chargeResult = createChargeResult(ex);
		}
		return chargeResult;
	}

	private RequestOptions createRequestOptions() {
		return (new RequestOptions.RequestOptionsBuilder()).setApiKey(private_api_key).build();
	}

	private Map<String, Object> createChargeParams(CreditCard creditCard, BigDecimal amount) {
		int stripeAcceptedAmount = convertToStripeAcceptedAmount(amount);
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", stripeAcceptedAmount);
		chargeParams.put("currency", "PLN");
		chargeParams.put("description", "Payment for cinema tickets");
		chargeParams.put("source", createSourceParams(creditCard));
		return chargeParams;
	}

	private Map<String, Object> createSourceParams(CreditCard creditCard) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("object", "card");
		params.put("number", creditCard.getNumber());
		params.put("cvc", creditCard.getCvc());
		params.put("exp_year", creditCard.getExpirationYear().toString());
		params.put("exp_month", creditCard.getExpirationMonth().ordinal());
		return params;
	}

	private int convertToStripeAcceptedAmount(BigDecimal amount) {
		return amount.multiply(BigDecimal.valueOf(100L)).intValueExact();
	}

	private ChargeResult createChargeResult(Charge charge) {
		ChargeResult result = new ChargeResult();
		result.setChargeId(charge.getId());
		result.setAmount(charge.getAmount());
		result.setCreatedAt(charge.getCreated());
		result.setCurrency(charge.getCurrency());
		result.setPaid(charge.getPaid());
		result.setStatus(charge.getStatus());
		return result;
	}

	private ChargeResult createChargeResult(StripeException exception) {
		ChargeResult result = new ChargeResult();
		result.setPaid(false);
		result.setStatus("failed");
		result.setErrorMessage(exception.getMessage());
		return result;
	}

}
