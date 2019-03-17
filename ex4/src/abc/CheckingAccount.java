

package abc;

public class CheckingAccount extends Account {
    private double creditLimit = -1000;
     
    // creditLimit is set by default
    public CheckingAccount (Date creationDate, Person owner) {
        super(creationDate, owner);
    }
     
    // creditLimit can be set upon creating an instance of CheckingAccount
    public CheckingAccount (Date creationDate, Person owner, double creditLimit) {
        super(creationDate, owner);
        this.creditLimit = creditLimit;
    }
     
    @Override
    public boolean checkWithdraw (double amount, Account targetAccount) {
        // only allow to transfer positive numbers
        if (amount > 0) {
            // only possible to withdraw money from a Checking Account
            // if there's enough money on it and credit limit is not exceeded
            if (this.getBalance() - amount > this.creditLimit) {
                return true;
            }
        }
        return false;
    }
     
    // It's always possible to deposit money on this type of account,
    // so there's no need to override the checkDeposit-function of super class
}

