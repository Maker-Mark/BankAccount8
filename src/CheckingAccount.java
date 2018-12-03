//NAME: Mark Goldstein
import java.util.Calendar;
public class CheckingAccount extends BankAccount
{
	public CheckingAccount() 
	{
		super();
	}

	public CheckingAccount( String first, 
			String last, String social,int accountNum,
			String type, double bal ) {
		super(first, last, social, accountNum, type, bal); 

	}
	public CheckingAccount(CheckingAccount c) {
		super(c);
	}

	public boolean makeDeposit( int year, int month,
			int date,  double amount) {
		long daysDiff = calcDiff(year, month, date );
		if( daysDiff <= 180) {
			accBal += amount; //Makes deposit 
			Transaction transaction = new Transaction
					("Deposit Check", amount);
			trans.add(transaction);
			//Adding transaction with associated index
			clacAmountType(amount);
			// Calculates the amount and type of account
			return true;
		}
		return false;
	}

	public static long calcDiff(int year, int m, int d) 
	{
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2018, 11, 30);
		calendar1.set(year, m, d);
		long milsecs1= calendar1.getTimeInMillis();
		long milsecs2 = calendar2.getTimeInMillis();
		long diff = milsecs2 - milsecs1;
		long ddays = diff / (24 * 60 * 60 * 1000);
		return ddays;
	}
	
	@Override
	//Ensures balance is valid and adds on fee if account
	//balance is less than 2500
	//Adds the correct transaction 
	public boolean makeWithdrawal(int accNumber, double amt)
	{
		final double fee = 1.50;
		boolean feeCharge = false;
		if (accBal < 2500 && accBal > 1.50) {
			accBal -= 1.50;
			feeCharge = true;
		}
		if(amt > 0) {
			accBal -= amt; //Makes withdrawal 
			if(feeCharge) {
				Transaction transaction = new 
						Transaction( "Withdrawal+$1.50 fee",
								amt + 1.50);	
				trans.add(transaction);
				clacAmountType(-(amt + 1.50));

			} else {
				Transaction transaction = new
						Transaction( "Withdrawal", amt);
				trans.add(transaction);
				clacAmountType(-(amt));
				return true;
			}
			// message for less than
		}
		return false;
	}
	@Override
	public double getAccBal() 
	{
		return accBal;
	}
}
