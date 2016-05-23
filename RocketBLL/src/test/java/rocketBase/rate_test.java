package rocketBase;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.RateException;

public class rate_test {

	// ok - RocketBLL rate_test
	// Check to see if a known credit score returns a known interest rate
	@Test
	public void knownRateTest() throws RateException {
		assertEquals(RateBLL.getRate(800), 3.5, .01);
	}

	// ok - RocketBLL rate_test
	// Check to see if a RateException is thrown if there are no rates for a
	// given
	// credit score
	@Test(expected = RateException.class)
	public void unknownRateTest() throws RateException {
		RateBLL.getRate(725);
	}

	// getPayment Test

	@Test
	public void getPaymentTest() {
		assertTrue(RateBLL.getPayment(0.04, 360, 300000, 0, false) == 1432.25);

	}
}