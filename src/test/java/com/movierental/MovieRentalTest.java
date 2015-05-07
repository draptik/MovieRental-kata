package com.movierental;

import static com.movierental.RentalAssertionBuilder.expectAmountOwed;
import static com.movierental.RentalAssertionBuilder.rent;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MovieRentalTest {

	private static final Movie TRANSFORMERS = new Movie("Transformers", Movie.REGULAR);
	private static final Movie NEMO = new Movie("Nemo", Movie.CHILDRENS);
	private static final Movie DIE_HARD_4 = new Movie("Die Hard 4", Movie.NEW_RELEASE);

	private Customer customer;
	@Parameter(0)
	public Rentals rentals;
	@Parameter(1)
	public double amountOwed;
	@Parameter(2)
	public int points;

	@Parameters(name = "Amount owed={1}, points= {2} for rentals: {0}")
	public static Collection<Object[]> getRentalsAssertions() {
		return Arrays.asList(new Object[][] { //

				expectAmountOwed(2).andPoints(1).forRentals(//
						rent(TRANSFORMERS).during(1).days()), //

						expectAmountOwed(2).andPoints(1).forRentals(//
								rent(TRANSFORMERS).during(2).days()), //

						expectAmountOwed(3.5).andPoints(1).forRentals(//
								rent(TRANSFORMERS).during(3).days()), //

						expectAmountOwed(5).andPoints(1).forRentals(//
								rent(TRANSFORMERS).during(4).days()), //

						expectAmountOwed(1.5).andPoints(1).forRentals(//
								rent(NEMO).during(1).days()), //

						expectAmountOwed(1.5).andPoints(1).forRentals(//
								rent(NEMO).during(2).days()), //

						expectAmountOwed(1.5).andPoints(1).forRentals(//
								rent(NEMO).during(3).days()), //

						expectAmountOwed(3).andPoints(1).forRentals(//
								rent(NEMO).during(4).days()), //

						expectAmountOwed(4.5).andPoints(1).forRentals(//
								rent(NEMO).during(5).days()), //

						expectAmountOwed(3).andPoints(1).forRentals(//
								rent(DIE_HARD_4).during(1).days()), //

						expectAmountOwed(6).andPoints(2).forRentals(//
								rent(DIE_HARD_4).during(2).days()), //

						expectAmountOwed(9).andPoints(2).forRentals(//
								rent(DIE_HARD_4).during(3).days()), //

						expectAmountOwed(5).andPoints(2).forRentals(//
								rent(TRANSFORMERS).during(3).days(), //
								rent(NEMO).during(3).days()), //

						expectAmountOwed(32).andPoints(4).forRentals(//
								rent(TRANSFORMERS).during(7).days(), //
								rent(NEMO).during(5).days(), //
								rent(DIE_HARD_4).during(6).days()), //
				});
	}

	@Before
	public void setup() {
		customer = new Customer("Alice");
		for (Rental rental : rentals) {
			customer.addRental(rental);
		}
	}

	@Test
	public void should_customer_name_be_the_one_who_rented_movies() {
		String statement = customer.statement();
		assertThat(statement, containsString("Rental Record for " + customer.getName() + "\n"));
	}

	@Test
	public void should_rented_movies_names_be_those_which_were_rented() {
		String statement = customer.statement();
		for (Rental rental : rentals) {
			assertThat(statement, containsString("\t" + rental.getMovie().getTitle() + "\t"));
		}
	}

	@Test
	public void should_renter_points_be_well_calculated() {
		String statement = customer.statement();
		assertThat(statement, containsString("You earned " + points + " frequent renter points"));
	}

	@Test
	public void should_amount_owed_be_well_calculated() {
		String statement = customer.statement();
		assertThat(statement, containsString("Amount owed is " + amountOwed + "\n"));
	}
}
