


package abc;
public class SavingAccount extends Account {
	private Date fixedDate = null;
	
	public SavingAccount (Date creationDate, Person owner) {
        super (creationDate, owner);
    }
	
	// set fixedDate
	public void fixAccount (Date endDate) {
        this.fixedDate = endDate;
    }
	
	@Override
    protected boolean checkDeposit (double amount, Account sourceAccount) {
		// check if the source account is a Saving Account.
        // if yes, than the owner have to be the same as the target account 
    	if (!this.getOwner().getName().equals(sourceAccount.getOwner().getName())) {
    		return false;
    	}
    	
		// only allow to transfer positive numbers
		if (amount > 0) {
			// only possible to deposit money on a Saving Account when it's not fixed (fixedDate is not set)
			// and it was not created before 01.01.2000
	        if  (fixedDate == null && !this.getcreationDate().isBefore(new Date(1,1,2000))) {
	            return true;
	        }
		}
        return false;
    }
	
	@Override
	public boolean checkWithdraw (double amount, Account targetAccount) {
		// check if the source account is a Saving Account.
        // if yes, than the owner have to be the same as the target account
    	if (!this.getOwner().getName().equals(targetAccount.getOwner().getName())) {
    		return false;
    	}
    	
    	// check if possible
		if (amount > 0) {
			// only possible to withdraw money from a Saving Account
			// if there's enough money on it AND the owners of both accounts are same
			if (this.getBalance() >= amount && fixedDate == null) {
				return true;
			}
		}
		return false;
	}
}