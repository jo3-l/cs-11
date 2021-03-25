import java.util.Date;

public class Withdraw {
    private double amount;
    private Date date;
    private String account;

    public Withdraw(double amount, Date date, String account) {
        this.amount = amount;
        this.date = date;
        this.account = account;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns a string in the format "Withdraw of: $[amount] Date: [date] into account: [account]"
    @Override
    public String toString() {
        return "Withdraw of: $" + amount + " Date: " + date + " into account: " + account;
    }

    // Needed for tests.

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the amount withdrawn.
    public double getAmount() {
        return amount;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the date this withdraw was made.
    public Date getDate() {
        return date;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the account this withdraw is for.
    public String getAccount() {
        return account;
    }
}
