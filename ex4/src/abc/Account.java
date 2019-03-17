


package abc;
import java.util.Comparator;

public class Account {
	private long accountID = 0;
	private double balance = 0;
	private Date creationDate;
	private Person owner;
	private static long numberOfAccounts = 0;
	
	public Account(Date creationDate, Person owner){
	       this.creationDate = creationDate;
	       this.owner = owner;
	       // every accountID is unique and is created automatically
	       this.accountID = numberOfAccounts++;
	   }
	
	   // adds money to account by the passed amount
	   protected boolean deposit(double amount) { 
	       balance += amount;
	       return true;
	   }
	   
//	    public String toString(){
//	        String str =  "account holder id is  " + this.accountID + " for " + this.owner.getName() + " and balance is " + this.balance + "\n";
//	        return str;
//	    }
	    
	   @Override
	public String toString() {
		return "Account [accountID=" + accountID + ", balance=" + balance + ", owner=" + owner + "]";
	}

	// removes money from account by the passed amount 
	   protected boolean withdraw(double amount){
	       this.balance = this.balance - amount;
	       return true;
	   }
	    
	   // check conditions. This function is overridden in subclasses
	   protected boolean checkWithdraw(double amount, Account targetAccount) {
	        // only allow to transfer positive numbers
	        if (amount > 0) {
	            return true;
	        }
	        return false;
	   }
	    
	   // check conditions. This function is overridden in subclasses   
	   protected boolean checkDeposit(double amount, Account sourceAccount) {
	       if (amount > 0) {
	           return true;
	       }
	       return false;
	   }
	    
	   // returns account ID
	   public long getAccountID(){
	       return this.accountID;
	   }
	    
	   // returns account balance
	   public double getBalance(){
	       return this.balance;
	   }
	    
	   // returns account owner
	

		public Person getOwner() {
			   return owner;
		}

		// returns account creation date
		public Date getcreationDate() {
			   return creationDate;
		}
		
	
	}



