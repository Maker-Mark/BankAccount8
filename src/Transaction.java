//NAME: Mark Goldstein

public class Transaction {

	private String type;
	private double amount;

	//Default constructor 
	public Transaction() 
	{
		type = "one";
		amount = 0;
	}
	//Constructor given account number, type and amount
	public Transaction( String typeGiven, double amt) 
	{
		type = typeGiven;
		amount = amt;
	}
	//Copy constructor
	public Transaction(Transaction t) 
	{
		type = t.type;
		amount = t.amount;
	}

	//Constructor given account number and type only to allow
	// removal of amount with no amount 
	public Transaction(int accountNum, String typeGiven) 
	{
		type = typeGiven;
		amount = 0;	//Set amount flag for non-amount transaction
	}
	//Sets transaction type
	public void setTransType(String givenType) 
	{
		type = givenType;
	}
	public String getTransType() {

		return type;
	}
	//Sets transaction amount
	public void setTransAmt(double transAmt) 
	{
		amount = transAmt;
	}
	//Gets transaction amount
	public double getTransAmt() 
	{
		return amount;
	}

	//toString method
	public String toString() 
	{
		String accString;
		accString = String.format("%-20s $%17.2f" ,
				type, amount );
		return accString;
	}



}
