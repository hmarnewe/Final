package rocket.app.view;

import java.text.NumberFormat;

import eNums.eAction;
import exceptions.RateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	private MainApp mainApp;

	// ok - RocketClient.RocketMainController

	// Create private instance variables for:
	// TextBox - txtIncome
	// TextBox - txtExpenses
	// TextBox - txtCreditScore
	// TextBox - txtHouseCost
	// ComboBox - loan term... 15 year or 30 year
	// Labels - various labels for the controls
	// Button - button to calculate the loan payment
	// Label - to show error messages (exception throw, payment exception)

	@FXML
	private TextField txtIncome;

	@FXML
	private TextField txtExpenses;

	@FXML
	private TextField txtCreditScore;

	@FXML
	private TextField txtHouseCost;

	@FXML
	private TextField txtDownPayment;

	@FXML
	private ComboBox<String> cmbTerm;

	@FXML
	private Label term;

	@FXML
	ObservableList<String> cmbList = FXCollections.observableArrayList("15 years", "16 years");

	@FXML
	public void initialize() {
		cmbTerm.setItems(cmbList);
	}

	@FXML
	private Label lblMortgagePayment;

	@FXML
	private Button btnCalculatePayment;

	@FXML
	private Label creditScore;

	@FXML
	private Label houseCost;

	@FXML
	private Label downPayment;

	@FXML
	private Label thrownException;

	@FXML
	private Label income;

	@FXML
	private Label expenses;

	@FXML
	private TextField txtRate;

	@FXML
	private TextField txtMonthlyPayment;

	@FXML
	private Label paymentException;

	@FXML
	private Button exit;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	// ok - RocketClient.RocketMainController
	// Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event) {
		Object message = null;
		// ok - RocketClient.RocketMainController

		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		// ok - RocketClient.RocketMainController
		// set the loan request details... rate, term, amount, credit score,
		// downpayment
		// I've created you an instance of lq... execute the setters in lq

		lq.setdAmount(Double.parseDouble(txtHouseCost.getText()));

		lq.setiDownPayment(Integer.parseInt(txtDownPayment.getText()));

		lq.setIncome(Double.parseDouble(txtIncome.getText()));

		lq.setExpenses(Double.parseDouble(txtExpenses.getText()));

		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));

		try {
			lq.setdRate(RateBLL.getRate(lq.getiCreditScore()));
		} catch (RateException e) {
			lq.setdRate(-1);
		}

		if (cmbTerm.getValue() == "15 years") {
			lq.setiTerm(15);
		} else {
			lq.setiTerm(30);
		}

		a.setLoanRequest(lq);

		// send lq as a message to RocketHub
		mainApp.messageSend(lq);
	}

	private static NumberFormat cf = NumberFormat.getCurrencyInstance();

	public void HandleLoanRequestDetails(LoanRequest lRequest)
	// ok - RocketClient.HandleLoanRequestDetails
	// lRequest is an instance of LoanRequest.
	// after it's returned back from the server, the payment (dPayment)
	// should be calculated.
	// Display dPayment on the form, rounded to two decimal places

	{
		double onePayment = lRequest.getIncome() * .28;
		double twoPayment = (lRequest.getIncome() * .36 - lRequest.getExpenses());
		double finalPayment;

		if (onePayment < twoPayment) {
			finalPayment = onePayment;
		} else {
			finalPayment = twoPayment;
		}

		double payment = lRequest.getdPayment();

		if (payment > finalPayment) {
			paymentException.setText(payment + finalPayment + "Payment too high");

		} else {
			lblMortgagePayment.setText("Monthly Mortgage Payment: " + cf.format(lRequest.getdPayment()));
		}

	}

	@FXML
	public void Exit(ActionEvent event) {
		System.exit(0);
	}

}
