import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
    public static final String CHECKING = "Checking";
    public static final String SAVING = "Saving";
    private final int OVERDRAFT = -100;

    private int accountNumber;

    private List<Deposit> deposits = new ArrayList<>();
    private List<Withdraw> withdraws = new ArrayList<>();

    private double checkBalance;
    private double savingBalance;

    // Not used, but mentioned in the assignment
    private double savingRate;

    private String name;

    // Note that we do not have a default constructor as every customer should have a name and account number.
    // However, I have included a constructor that allows the user to leave out `checkBalance` and `savingBalance`
    // which will be defaulted to zero.
    public Customer(String name, int accountNumber) {
        this(name, accountNumber, 0, 0);
    }

    public Customer(String name, int accountNumber, double checkBalance, double savingBalance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.checkBalance = checkBalance;
        this.savingBalance = savingBalance;
    }

    // These are needed for tests so we can observe behavior during tests.

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the list of deposits.
    public List<Deposit> getDeposits() {
        return deposits;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the list of withdraws.
    public List<Withdraw> getWithdraws() {
        return withdraws;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the check balance in the account.
    public double getCheckBalance() {
        return checkBalance;
    }

    // Requires: Nothing
    // Modifies: Nothing
    // Effects: Returns the saving balance in the account.
    public double getSavingBalance() {
        return savingBalance;
    }

    // Requires: double amount, Date date, String account. account should be one of Customer.CHECKING or Customer.SAVING,
    // and amount should be a positive number.
    // Modifies: this, deposits
    // Effects: Logs the deposit, adds the amount to the appropriate account, and returns the new balance.
    public double deposit(double amount, Date date, String account) {
        deposits.add(new Deposit(amount, date, account));

        if (account.equals(CHECKING)) {
            checkBalance += amount;
            return checkBalance;
        } else {
            savingBalance += amount;
            return savingBalance;
        }
    }

    // Requires: double amount, Date date, String account. account should be one of Customer.CHECKING or Customer.SAVING,
    // and amount should be a positive number.
    // Modifies: this, withdraws
    // Effects: If the balance in the account after the given amount is withdrawn would be overdrawn, returns the original
    // amount of money in the account and does nothing else. Otherwise the withdrawal is logged, the money is withdrawn
    // from the appropriate account, and the new balance is returned.
    public double withdraw(double amount, Date date, String account) {
        if (checkOverdraft(amount, account)) return account.equals(CHECKING) ? checkBalance : savingBalance;

        withdraws.add(new Withdraw(amount, date, account));
        if (account.equals(CHECKING)) {
            checkBalance -= amount;
            return checkBalance;
        } else {
            savingBalance -= amount;
            return savingBalance;
        }
    }

    // Requires: double amount, String account. account should be one of Customer.CHECKING or Customer.SAVING, and amount
    // should be a positive number.
    // Modifies: Nothing.
    // Effects: Returns whether the balance in the account after the given amount is withdrawn would be overdrawn.
    private boolean checkOverdraft(double amount, String account) {
        double currentBalance = account.equals(CHECKING) ? checkBalance : savingBalance;
        return currentBalance - amount < OVERDRAFT;
    }

    // Do not modify!
    public void displayDeposits() {
        for (Deposit deposit : deposits) {
            System.out.println(deposit);
        }
    }

    // Do not modify!
    public void displayWithdraws() {
        for (Withdraw withdraw : withdraws) {
            System.out.println(withdraw);
        }
    }
}
