//NAME: Mark Goldstein

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CDAccount extends BankAccount
{
	//Making calendar objects for date comparison 
	private Calendar maturityDate = Calendar.getInstance() ;
	private Calendar termOf = Calendar.getInstance();
	private Calendar today = Calendar.getInstance();

	public CDAccount() 
	{
		super();	
	}

	//copy cons
	public CDAccount(CDAccount c) 
	{
		//copy constructor
		super(c);
		this.maturityDate = c.maturityDate;
		this.termOf = c.termOf;
	}

	//Constructor For Paramatized CD Account
	public CDAccount(int year, int month, int date,
			String first, String last,
			String social,int accountNum,
			String type, double bal ) {
		super(first, last, social, 
				accountNum, type, bal);
		maturityDate.set(year, month, date);
		termOf.set(year, month, date);
		termOf.add(Calendar.DAY_OF_MONTH, -5);
	}

	//Makes sure the TermOfCd (5 days Before the Maturity date) is
	//Met before going to make the withdrawal
	public boolean  makeWithdrawal( double amount, boolean y) {
		if(termOf.getTimeInMillis() <= today.getTimeInMillis() )
		{
			accBal -= amount;
			Transaction transaction = new 
					Transaction("Withdrawal CD", amount);
			trans.add(transaction);
			//Adding transaction with associated index
			clacAmountType(amount);
			// Calculates the amount and type of account
			return true;
		}
		return false;
	}
	//Checks termOfCD to make sure withdraw is valid
	public boolean  makeDeposit( double amount, boolean y) 
	{
		if(!today.before(termOf)) {
			accBal += amount;
			Transaction transaction = new 
					Transaction("Deposit", amount);
			trans.add(transaction);
			//Adding transaction with associated index
			clacAmountType(amount);
			// Calculates the amount and type of account
			return true;
		}
		return false;

	}
	//Number of months, from date of opening
	public String getMature() {

		int year = maturityDate.get(Calendar.YEAR);
		int month = maturityDate.get(Calendar.MONTH);
		int day = maturityDate.get(Calendar.DAY_OF_MONTH);
		return  year +" " + month +" " + day;
	}
	//Gets date set for comparison
	public String getDate() {
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH);
		int day = today.get(Calendar.DAY_OF_MONTH);
		return  year +" " + month +" " + day;
	}
	//Gets the difference in months from date set (2018, 11, 2) to
	//the CD Account's maturity date
	public  int getMonths() throws ParseException 
	{
		SimpleDateFormat f = 
				new SimpleDateFormat("yyyy-mm-dd");
		Date d2 = f.parse( maturityDate.get(Calendar.YEAR) + 
				"-"+ maturityDate.get(Calendar.MONTH) +
				"-"+ maturityDate.get(Calendar.DAY_OF_MONTH));
		Date d1 = f.parse(today.get(Calendar.YEAR) + 
				"-"+ today.get(Calendar.MONTH)+"-"+ 
				today.get(Calendar.DAY_OF_MONTH));
		int n = differenceInMonths(d1, d2);
		return n;
	}
	
	//Returns difference in months between calendar objects
	private  int differenceInMonths(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		int diff = 0;
		if (c2.after(c1)) {
			while (c2.after(c1)) {
				c1.add(Calendar.MONTH, 1);
				if (c2.after(c1)) {
					diff++;
				}
			}
		} else if (c2.before(c1)) {
			while (c2.before(c1)) {
				c1.add(Calendar.MONTH, -1);
				if (c1.before(c2)) {
					diff--;
				}
			}
		}
		return diff;
	}
}
