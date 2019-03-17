

package abc;
import java.util.ArrayList;
import java.util.Collections;

public class Bank {

	private ArrayList<Account> account = new ArrayList<>();		//accounts in bank
	
    public void addAccount(Account newAccount) {
        this.account.add(newAccount);
    }
	
	public int getNumberOfAccounts(){
        return this.account.size();
    }
	
    @Override
    public String toString(){
        return account + "......" ;
    }
 
    public boolean transfer(long accountFromID, long accountToID, double amount) {
        if (accountFromID == accountToID) {
            return false;
        }
        Account sourceAccount = null;
        Account targetAccount = null;
 
        for(Account account : this.account) {
            if (account.getAccountID() == accountFromID) {
                sourceAccount = account;
            }
            if (account.getAccountID() == accountToID) {
                targetAccount = account;
            }
        }
        
        if (checkTransfer(sourceAccount, targetAccount, amount)) {
            sourceAccount.withdraw(amount);
            targetAccount.deposit(amount);
            return true;
        }
        else {
            return false;
        }
    }
    protected boolean checkTransfer(Account accountFrom, Account accountTo, double amount) {
        // if accountFrom or accountTo don't exist or both variables point at the same object, don't proceed
        if (accountFrom == null || accountTo == null || accountFrom == accountTo) {
            return false;
        }
         
        //return accountFrom.checkWithdraw(amount) && accountTo.checkDeposit(amount);
        return accountFrom.checkWithdraw(amount, accountTo) && accountTo.checkDeposit(amount, accountFrom);
    }
    
    

    
}

