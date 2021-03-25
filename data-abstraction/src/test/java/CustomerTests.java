import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class CustomerTests {
    private Customer customer;
    private double initialCheckBalance;
    private double initialSavingBalance;
    private Date date;
    private double delta;

    @Before
    public void setup() {
        initialCheckBalance = 1202.30;
        initialSavingBalance = 533.45;
        date = new Date();
        // When we perform operations on doubles sometimes precision is lost, so we pass this relatively small number
        // as the delta instead of 0 to account for that case.
        delta = 0.005;

        customer = new Customer("Lisa", 1, initialCheckBalance, initialSavingBalance);
    }

    @Test
    public void testGetDeposits() {
        assertTrue(customer.getDeposits().isEmpty());
    }

    @Test
    public void testGetWithdraws() {
        assertTrue(customer.getWithdraws().isEmpty());
    }

    @Test
    public void testGetCheckBalance() {
        assertEquals(initialCheckBalance, customer.getCheckBalance(), delta);
    }

    @Test
    public void testGetSavingBalance() {
        assertEquals(initialSavingBalance, customer.getSavingBalance(), delta);
    }

    @Test
    public void testCheckingDepositOnce() {
        double depositedAmount = 12.05;
        customer.deposit(depositedAmount, date, Customer.CHECKING);

        // Check that it was added to the checking balance.
        assertEquals(initialCheckBalance + depositedAmount, customer.getCheckBalance(), delta);
        // Check that the saving balance was unaffected.
        assertEquals(initialSavingBalance, customer.getSavingBalance(), delta);

        // Check that a deposit was added to the `deposits` list.
        List<Deposit> deposits = customer.getDeposits();

        assertEquals(1, deposits.size());

        Deposit deposit = deposits.get(0);
        assertEquals(depositedAmount, deposit.getAmount(), delta);
        assertEquals(date, deposit.getDate());
        assertEquals(Customer.CHECKING, deposit.getAccount());
    }

    @Test
    public void testSavingsDepositOnce() {
        double depositedAmount = 15.05;
        customer.deposit(depositedAmount, date, Customer.SAVING);

        // Check that it was added to the savings balance.
        assertEquals(initialSavingBalance + depositedAmount, customer.getSavingBalance(), delta);
        // Check that the checking balance was unaffected.
        assertEquals(initialCheckBalance, customer.getCheckBalance(), delta);

        // Check that a deposit was added to the `deposits` list.
        List<Deposit> deposits = customer.getDeposits();

        assertEquals(1, deposits.size());

        Deposit deposit = deposits.get(0);
        assertEquals(depositedAmount, deposit.getAmount(), delta);
        assertEquals(date, deposit.getDate());
        assertEquals(Customer.SAVING, deposit.getAccount());

        // Check that the `withdraws` list is still empty.
        assertTrue(customer.getWithdraws().isEmpty());
    }

    @Test
    public void testSavingsDepositAndCheckingDeposit() {
        double savingsDepositedAmount = 17.05;
        double checkingDepositedAmount = 13.60;
        customer.deposit(savingsDepositedAmount, date, Customer.SAVING);
        customer.deposit(checkingDepositedAmount, date, Customer.CHECKING);

        // Check that the savings balance and the checking balance were both modified correctly.
        assertEquals(initialSavingBalance + savingsDepositedAmount, customer.getSavingBalance(), delta);
        assertEquals(initialCheckBalance + checkingDepositedAmount, customer.getCheckBalance(), delta);

        // Check that two deposits were added to the `deposits` list in the order that they were made.
        List<Deposit> deposits = customer.getDeposits();

        assertEquals(2, deposits.size());

        Deposit firstDeposit = deposits.get(0);
        assertEquals(savingsDepositedAmount, firstDeposit.getAmount(), delta);
        assertEquals(date, firstDeposit.getDate());
        assertEquals(Customer.SAVING, firstDeposit.getAccount());

        Deposit secondDeposit = deposits.get(1);
        assertEquals(checkingDepositedAmount, secondDeposit.getAmount(), delta);
        assertEquals(date, secondDeposit.getDate());
        assertEquals(Customer.CHECKING, secondDeposit.getAccount());

        // Check that the `withdraws` list is still empty.
        assertTrue(customer.getWithdraws().isEmpty());
    }

    @Test
    public void testCheckingWithdrawNoOverdrawOnce() {
        double withdrawAmount = 15.05;
        double newBalance = customer.withdraw(withdrawAmount, date, Customer.CHECKING);
        double expectedNewBalance = initialCheckBalance - withdrawAmount;

        assertEquals(expectedNewBalance, newBalance, delta);
        // Check that the checking balance was modified as well.
        assertEquals(expectedNewBalance, customer.getCheckBalance(), delta);
        // Check that the savings balance was unaffected.
        assertEquals(initialSavingBalance, customer.getSavingBalance(), delta);

        // Check that a withdrawal was added to the `withdraws` list.
        List<Withdraw> withdraws = customer.getWithdraws();

        assertEquals(1, withdraws.size());

        Withdraw withdraw = withdraws.get(0);
        assertEquals(withdrawAmount, withdraw.getAmount(), delta);
        assertEquals(date, withdraw.getDate());
        assertEquals(Customer.CHECKING, withdraw.getAccount());

        // Check that the `deposits` list is still empty.
        assertTrue(customer.getDeposits().isEmpty());
    }

    @Test
    public void testSavingsWithdrawNoOverdrawOnce() {
        double withdrawAmount = 10.05;
        double newBalance = customer.withdraw(withdrawAmount, date, Customer.SAVING);
        double expectedNewBalance = initialSavingBalance - withdrawAmount;

        assertEquals(expectedNewBalance, newBalance, delta);
        // Check that the savings balance was modified as well.
        assertEquals(expectedNewBalance, customer.getSavingBalance(), delta);
        // Check that the checking balance was unaffected.
        assertEquals(initialCheckBalance, customer.getCheckBalance(), delta);

        // Check that a withdrawal was added to the `withdraws` list.
        List<Withdraw> withdraws = customer.getWithdraws();

        assertEquals(1, withdraws.size());

        Withdraw withdraw = withdraws.get(0);
        assertEquals(withdrawAmount, withdraw.getAmount(), delta);
        assertEquals(date, withdraw.getDate());
        assertEquals(Customer.SAVING, withdraw.getAccount());

        // Check that the `deposits` list is still empty.
        assertTrue(customer.getDeposits().isEmpty());
    }

    @Test
    public void testCheckingWithdrawOverdrawnOnce() {
        double withdrawAmount = initialCheckBalance + 101;
        double balance = customer.withdraw(withdrawAmount, date, Customer.CHECKING);

        // Since the amount we wanted to withdraw should cause the account to be overdrawn the balance should have been
        // unaffected.
        assertEquals(initialCheckBalance, balance, delta);
        // Same for the checking balance, should not have been affected.
        assertEquals(initialCheckBalance, customer.getCheckBalance(), delta);
        // Savings balance should be unaffected.
        assertEquals(initialSavingBalance, customer.getSavingBalance(), delta);

        // No withdraws should have been registered.
        assertTrue(customer.getWithdraws().isEmpty());

        // `deposits` list should still be empty as well.
        assertTrue(customer.getDeposits().isEmpty());
    }

    @Test
    public void testSavingWithdrawOverdrawnOnce() {
        double withdrawAmount = initialSavingBalance + 101;
        double balance = customer.withdraw(withdrawAmount, date, Customer.SAVING);

        // Since the amount we wanted to withdraw should cause the account to be overdrawn the balance should have been
        // unaffected.
        assertEquals(initialSavingBalance, balance, delta);
        // Same for the saving balance, should not have been affected.
        assertEquals(initialSavingBalance, customer.getSavingBalance(), delta);
        // Checking balance should be unaffected.
        assertEquals(initialCheckBalance, customer.getCheckBalance(), delta);

        // No withdraws should have been registered.
        assertTrue(customer.getWithdraws().isEmpty());

        // `deposits` list should still be empty as well.
        assertTrue(customer.getDeposits().isEmpty());
    }

    @Test
    public void testCheckingWithdrawAlmostOverdrawOnce() {
        // +100 should be OK, it's just on the boundary of being an overdraw and not being one.
        double withdrawAmount = initialCheckBalance + 100;
        double newBalance = customer.withdraw(withdrawAmount, date, Customer.CHECKING);
        double expectedNewBalance = initialCheckBalance - withdrawAmount;

        assertEquals(expectedNewBalance, newBalance, delta);
        // Check that the checking balance was modified as well.
        assertEquals(expectedNewBalance, customer.getCheckBalance(), delta);
        // Check that the savings balance was unaffected.
        assertEquals(initialSavingBalance, customer.getSavingBalance(), delta);

        // Check that a withdrawal was added to the `withdraws` list.
        List<Withdraw> withdraws = customer.getWithdraws();

        assertEquals(1, withdraws.size());

        Withdraw withdraw = withdraws.get(0);
        assertEquals(withdrawAmount, withdraw.getAmount(), delta);
        assertEquals(date, withdraw.getDate());
        assertEquals(Customer.CHECKING, withdraw.getAccount());

        // Check that the `deposits` list is still empty.
        assertTrue(customer.getDeposits().isEmpty());
    }

    @Test
    public void testSavingWithdrawAlmostOverdrawOnce() {
        double withdrawAmount = initialSavingBalance + 100;
        double newBalance = customer.withdraw(withdrawAmount, date, Customer.SAVING);
        double expectedNewBalance = initialSavingBalance - withdrawAmount;

        assertEquals(expectedNewBalance, newBalance, delta);
        // Check that the savings balance was modified as well.
        assertEquals(expectedNewBalance, customer.getSavingBalance(), delta);
        // Check that the checking balance was unaffected.
        assertEquals(initialCheckBalance, customer.getCheckBalance(), delta);

        // Check that a withdrawal was added to the `withdraws` list.
        List<Withdraw> withdraws = customer.getWithdraws();

        assertEquals(1, withdraws.size());

        Withdraw withdraw = withdraws.get(0);
        assertEquals(withdrawAmount, withdraw.getAmount(), delta);
        assertEquals(date, withdraw.getDate());
        assertEquals(Customer.SAVING, withdraw.getAccount());

        // Check that the `deposits` list is still empty.
        assertTrue(customer.getDeposits().isEmpty());
    }

    @Test
    public void testSavingWithdrawAndCheckingWithdrawOnce() {
        double savingWithdrawAmount = 103.50;
        double checkingWithdrawAmount = 13.60;

        double expectedSavingBalance = initialSavingBalance - savingWithdrawAmount;
        double expectedCheckingBalance = initialCheckBalance - checkingWithdrawAmount;

        double newSavingBalance = customer.withdraw(savingWithdrawAmount, date, Customer.SAVING);
        double newCheckingBalance = customer.withdraw(checkingWithdrawAmount, date, Customer.CHECKING);

        assertEquals(expectedCheckingBalance, newCheckingBalance, delta);
        assertEquals(expectedSavingBalance, newSavingBalance, delta);

        // Check that the savings balance and the checking balance were both modified correctly.
        assertEquals(expectedSavingBalance, customer.getSavingBalance(), delta);
        assertEquals(expectedCheckingBalance, customer.getCheckBalance(), delta);

        // Check that two withdraws were added to the `withdraws` list in the order that they were made.
        List<Withdraw> withdraws = customer.getWithdraws();

        assertEquals(2, withdraws.size());

        Withdraw firstWithdraw = withdraws.get(0);
        assertEquals(savingWithdrawAmount, firstWithdraw.getAmount(), delta);
        assertEquals(date, firstWithdraw.getDate());
        assertEquals(Customer.SAVING, firstWithdraw.getAccount());

        Withdraw secondWithdraw = withdraws.get(1);
        assertEquals(checkingWithdrawAmount, secondWithdraw.getAmount(), delta);
        assertEquals(date, secondWithdraw.getDate());
        assertEquals(Customer.CHECKING, secondWithdraw.getAccount());

        // Check that the `deposits` list is still empty.
        assertTrue(customer.getDeposits().isEmpty());
    }
}
