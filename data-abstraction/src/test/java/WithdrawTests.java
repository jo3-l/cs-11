import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class WithdrawTests {
    private Withdraw withdraw;
    private double amount;
    private Date date;
    private String account;

    @Before
    public void setup() {
        amount = 855.25;
        date = new Date();
        account = Customer.SAVING;

        withdraw = new Withdraw(amount, date, account);
    }

    @Test
    public void testWithdrawToString() {
        String expected = "Withdraw of: $" + amount + " Date: " + date + " into account: " + account;
        assertEquals(expected, withdraw.toString());
    }

    @Test
    public void testWithdrawGetAmount() {
        assertEquals(amount, withdraw.getAmount(), 0);
    }

    @Test
    public void testWithdrawGetDate() {
        assertEquals(date, withdraw.getDate());
    }

    @Test
    public void testWithdrawGetAccount() {
        assertEquals(account, withdraw.getAccount());
    }
}
