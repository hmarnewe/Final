package exceptions;

import rocketDomain.RateDomainModel;

public class RateException extends Exception {

	// ok - RocketBLL RateException - RateDomainModel should be an attribute of
	// RateException
	// * Add RateRomainModel as an attribute
	// * Create a constructor, passing in RateDomainModel
	// * Create a getter (no setter, set value only in Constructor)

	private RateDomainModel Rdm;

	public RateException(RateDomainModel rdm) {
		super();
		Rdm = rdm;
	}

	public RateDomainModel getRte() {
		return Rdm;
	}

}
