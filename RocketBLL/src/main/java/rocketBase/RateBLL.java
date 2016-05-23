package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import exceptions.RateException;

import rocketDomain.RateDomainModel;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();

	public static double getRate(int GivenCreditScore) throws RateException {

		// ok - RocketBLL RateBLL.getRate - make sure you throw any exception

		// Call RateDAL.getAllRates... this returns an array of rates
		// write the code that will search the rates to determine the
		// interest rate for the given credit score
		// hints: you have to sort the rates... you can do this by using
		// a comparator... or by using an OrderBy statement in the HQL

		ArrayList<RateDomainModel> rates = _RateDAL.getAllRates();
		double interestRate = -1.0;
		RateDomainModel rateDomainModel = null;
		for (RateDomainModel rate : rates) {
			if (rate.getiMinCreditScore() == GivenCreditScore) {
				interestRate = rate.getdInterestRate();
				rateDomainModel = rate;
			}
		}

		// ok - RocketBLL RateBLL.getRate
		// obviously this should be changed to return the determined rate

		if ((rateDomainModel == null) || (interestRate == -1)) {
			throw new RateException(rateDomainModel);
		} else {
			return interestRate;
		}
	}

	// adding attribites, getters, and setters
	private RateBLL Income;
	private RateBLL Expenses;

	public RateBLL getIncome() {
		return Income;
	}

	public void setIncome(RateBLL income) {
		Income = income;
	}

	public RateBLL getExpenses() {
		return Expenses;
	}

	public void setExpenses(RateBLL expenses) {
		Expenses = expenses;
	}

	// ok - RocketBLL RateBLL.getPayment
	// how to use:
	// https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html

	public static double getPayment(double r, double n, double p, double f, boolean t) {
		return FinanceLib.pmt(r / 12, n, p, f, t);

	}
}
