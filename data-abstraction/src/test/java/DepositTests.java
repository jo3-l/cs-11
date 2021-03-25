import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class DepositTests {
    private Deposit deposit;
    private double amount;
    private Date date;
    private String account;

    @Before
    public void setup() {
        amount = 53.05;
        date = new Date();
        account = Customer.CHECKING;

        deposit = new Deposit(amount, date, account);
    }

    @Test
    public void testDepositToString() {
        String expected = "Deposit of: $" + amount + " Date: " + date + " into account: " + account;
        assertEquals(expected, deposit.toString());
    }

    @Test
    public void testDepositGetAmount() {
        assertEquals(amount, deposit.getAmount(), 0);
    }

    @Test
    public void testDepositGetDate() {
        assertEquals(date, deposit.getDate());
    }

    @Test
    public void testDepositGetAccount() {
        assertEquals(account, deposit.getAccount());
    }
}
