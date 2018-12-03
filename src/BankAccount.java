//NAME: Mark Goldstein
import java.util.ArrayList;

public  class BankAccount {
	//data members of BankAccount object
	protected Depositor accDet;
	protected int accNum;
	protected String accType;
	protected String status  = "Open";
	protected double accBal;
	protected ArrayList<Transaction> trans;

	//Default constructor
	public BankAccount()
	{
		trans = new ArrayList <Transaction>();
		accDet = new Depositor();
		accNum = 0;
		accType = "none";
		accBal = 0.00;
		status  = "Open";
		Transaction transaction = new Transaction ("Default", accBal);
		trans.add(transaction);

	}

	//Copy constructor for BankAccount
	public BankAccount (BankAccount b) 
	{
		status = b.status;
		accDet = new Depositor(b.getAccDet());
		accType = b.accType;
		accNum = b.accNum;
		accBal = b.accBal;
		trans = new ArrayList <Transaction>();
		for(int i =0; i < b.trans.size();i++) {
			trans.add(new Transaction (b.trans.get(i)));
		}
	}
	//Constructor for initializing object with values
	public BankAccount(String first, String last, String social,
			int accountNum,
			String type, double bal)
	{
		trans = new ArrayList <Transaction>();
		accNum = accountNum;
		accType = type;
		accBal= bal;
		accDet = new Depositor(first, last, social);
		first = accDet.getNameOnAcc().getFirst();
		last = accDet.getNameOnAcc().getLast();
		social= accDet.getSocSec();
		status  = "Open";
		Transaction transaction = new Transaction ("Open Account",bal);
		trans.add(transaction);
		//Calculating amount when calling this constructor
		//ie open new account
		clacAmountType(bal);
	}



	// Uses static data members of bank to edit running totals
	public void clacAmountType(double d) 
	{

		if (accType.equalsIgnoreCase("CD")) {
			Bank.setTotCD(d);
		} else if (accType.equalsIgnoreCase("Savings")){
			Bank.setTotSav(d);
		} else if (accType.equalsIgnoreCase("Checkings")){
			Bank.setTotCh(d);	
		}
		Bank.setTotalAmt();
	}

	//Getter to get BankAccount 
	//Object's depositor
	public Depositor getAccDet()
	{
		return new Depositor(accDet);  
	}

	/*
	 * Method makeDeposit():
	 * Process: Determines valid amount and does deposit
	 * Output: none
	 */

	public  void makeDeposit(int accNumber, int index, double amount) {
		//Assuumes all are checks

		accBal += amount; //Makes deposit 

		Transaction transaction = new Transaction(  "Deposit", amount);
		trans.add(transaction);//Adding transaction with associated index
		clacAmountType(amount);// Calculates the amount and type of account
	}

	public boolean makeWithdrawal(int accNumber, double amt) 
	{
		if( amt >= 0) {
			accBal -= amt; //Makes withdrawal 
			Transaction transaction = new Transaction( "Withdrawal", amt);
			trans.add(transaction);
			clacAmountType(-(amt));
			return true;
		}
		else 
		{
			return false; // message for less than
		}
	}
	//Gets ArrayList of transactions attached to account
	public  ArrayList<Transaction>  getTransactions()
	{
		return trans ;
	}
	//Adds transaction given account object and type of
	//transaction and account number
	public void addTransaction(BankAccount bankAcc,
			String type, double amount) 
	{
		trans.add(new Transaction (type, amount)); 
		// Adds transaction via copy constructor

	}
	//Adds transaction given account object and type of transaction 
	public void addTransaction(BankAccount bankAcc, String type) 
	{
		trans.add(new Transaction (bankAcc.getAccNum(), type));
		// Adds transaction via copy constructor
	}

	//Method for setting data member accDet,
	//which is of Depositor-object type
	public  void setAccDet(String first, String last,
			String social)
	{
		accDet.setNameOnAcc(first, last);
		accDet.setSocSec(social);
	}

	//Changes account status 
	public void closeAcct() 
	{
		status  = "Closed";
	}

	//Changes account status 
	public  void reOpenAcct() 
	{
		status  = "Open";		
	}

	//Setters and getters for rest of data members
	public void setAccNum(int n)
	{
		accNum = n;
	}

	//Sets account type
	public void setAccType(String type)
	{
		accType = type;
	}

	//Sets account balance 
	public void setAccBal(double bal)
	{

		accBal = bal;
	}

	//Gets account balance
	public double getAccBal()
	{
		return accBal;
	}

	//Get account type
	public String getAccType()
	{
		return accType;
	}

	//Gets account number
	public int getAccNum()
	{
		return accNum;
	}


	//Gets number of transactions 
	public int getNumTrans() {
		return trans.size();
	}

	//Gets status 
	public String getStatus() {
		return status;
	}

	//Uses .equals to test if account object have same account number
	public int equals(BankAccount acc) 
	{
		if (accNum == acc.getAccNum() && accDet.getSocSec()
				.equals(acc.getAccDet().getSocSec())) {
			return accNum;
		} else {
			return -1;
		}
	}
	//.equals method to check equality of objects
	public boolean equals(BankAccount acc, boolean b) 
	{
		// if we want to compare accNum
		if (b = false) {
			return accNum == acc.getAccNum(); //Returns value if did accNum compare
		}
		//Compares Social
		return accDet.getSocSec().equals(acc.getAccDet().getSocSec());
	}
	//.toString method for ease of printing
	public String toString() {
		String accString;
		accString = String.format(accDet +" %-10d %-14s "
				+ "$%10.2f %-7s" , accNum,
				accType,  accBal, status );
		return accString;
	}

}
