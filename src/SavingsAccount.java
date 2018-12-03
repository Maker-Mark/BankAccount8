//NAME: Mark Goldstein
public class SavingsAccount extends BankAccount 
{

	public SavingsAccount(){
		super();
	}

	public SavingsAccount(SavingsAccount s ){
		super(s);
	}

	public SavingsAccount( String first, String last,
			String social,int accountNum,
			String type, double bal ) {

		super(first, last, social, accountNum,
				type, bal); 
	}

	public  void makeDeposit( double amount) 
	{
		accBal += amount; //Makes deposit 
		Transaction transaction = new
				Transaction(  "Deposit", amount);
		trans.add(transaction);
		//Adding transaction with associated index
		clacAmountType(amount);
		// Calculates the amount and type of account
	}

	public boolean makeWithdrawal(int accNumber, double amt) 
	{
		if( amt >= 0) {
			accBal -= amt; //Makes withdrawal 
			Transaction transaction = new 
					Transaction( "Withdrawal", amt);
			trans.add(transaction);
			clacAmountType(-(amt));
			return true;
		}else {
			return false; // message for less than
		}
	}
}
