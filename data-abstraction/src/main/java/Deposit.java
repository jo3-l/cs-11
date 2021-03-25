import java.util.Date;

public class Deposit {
    private double amount;
    private Date date;
    private String account;

    public Deposit(double amount, Date date, String account) {
        this.amount = amount;
        this.date = date;
        this.account = account;
    }

    // Needed for tests.

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns a string in the format "Deposit of: $[amount] Date: [date] into account: [account]"
    @Override
    public String toString() {
        return "Deposit of: $" + amount + " Date: " + date + " into account: " + account;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the amount of the deposit.
    public double getAmount() {
        return amount;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the date the deposit was made.
    public Date getDate() {
        return date;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the account for the deposit.
    public String getAccount() {
        return account;
    }
}
