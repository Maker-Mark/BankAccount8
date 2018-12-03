//NAME: Mark Goldstein
/**
 * @version 7
 * @date 12/2/2018
 */

import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
public class BankAccountV8 {


	public static void main(String[] args) throws IOException, 
	ParseException {
		// constant definitions
		char choice; // menu item selected
		boolean not_done = true; // loop control flag

		Bank bank = new Bank();// default bank object creation

		// Open input test cases file
		//						File testFile = new File("myinput.txt");
		File testFile = new File("mytestcases.txt");

		// Create Scanner object
		Scanner kybd = new Scanner(testFile);
		//		Scanner kybd = new Scanner(System.in);

		// open the output file
		PrintWriter outFile = new PrintWriter("myoutput.txt");
		//		PrintWriter outFile = new PrintWriter(System.out);
		boolean trans = true;
		/* fill and print initial database */
		readAccts(bank);
		outFile.println("MARK GOLDSTEIN");
		printAccts(bank, outFile, !trans);
		/* prompts for a transaction and then calls */
		/* on functions to process the requested transaction */
		do {
			menu();
			choice = kybd.next().charAt(0);
			switch (choice) {
			case 'q':
			case 'Q':
				not_done = false;
				outFile.println("Final Database:\n");
				printAccts(bank, outFile, trans);
				break;
			case 'b':
			case 'B':
				balance(bank, outFile, kybd);
				break;
			case 'i':
			case 'I':
				accountInfo(bank, outFile, kybd);
				break;
			case 'h':
			case 'H':
				accountInfoPlus(bank, outFile, kybd);
				break;
			case 'c':
			case 'C':
				closeAccount(bank, outFile, kybd);
				break;
			case 'R':
			case 'r':
				reOpenAccount(bank, outFile, kybd);
				break;
			case 'd':
			case 'D':
				deposit(bank, outFile, kybd);
				break;
			case 'w':
			case 'W':
				withdrawal(bank, outFile, kybd);
				break;
			case 'n':
			case 'N':
				newAcct(bank, outFile, kybd);
				break;
			case 'x':
			case 'X':
				deleteAcct(bank, outFile, kybd);
				break;
			default:
				outFile.println("Error: " + choice + 
						" is an invalid selection -  try again");
				outFile.println();
				outFile.flush();
				break;
			}
			// give user a chance to look at output before printing menu
			//pause(kybd);
		} while (not_done);
		// close the output file
		outFile.close();
		// close the test cases input file
		kybd.close();
		System.out.println();
		System.out.println("The program is terminating");
	}

	/*
	 * Method menu(): 
	 * Input: none 
	 * Process: Prints the menu of transaction choices
	 * Output: Prints the menu of transaction choices
	 */
	public static void menu() {
		System.out.println();
		System.out.println("Select one of the following transactions:");
		System.out.println("\t****************************");
		System.out.println("\t    List of Choices         ");
		System.out.println("\t****************************");
		System.out.println("\t     W -- Withdrawal");
		System.out.println("\t     D -- Deposit");
		System.out.println("\t     N -- New Account");
		System.out.println("\t     B -- Balance Inquiry");
		System.out.println("\t     C -- Close Account");
		System.out.println("\t     R -- Reopen Account");
		System.out.println("\t     I -- Account Information");
		System.out.println("\t     H -- Account Information with Transactions");
		System.out.println("\t     X -- Delete Account");
		System.out.println("\t     Q -- Quit");
		System.out.print("\tEnter your selection: ");
		System.out.println();
	}


	/*
	 * Method readAccts(): 
	 * Input: "myinput.txt", which uses a constructor to initialize
	 * the data members of a new account.
	 * 
	 * Process: Reads the initial database of accounts, balances,
	 * social security number and account type into database.
	 * 
	 * Output: Fills in the initial BankAccount array via Bank object.
	 */

	public static void readAccts(Bank bank) 
			throws IOException {

		// Open database input file
		// Create File object for initial database
		File dbFile = new File("myinput.txt");
		Scanner sc = new Scanner(dbFile);
		//Strings to read in and assign account info local variable
		String line, first, last, social, type;
		int accNum;
		double amount;

		while (sc.hasNext()) {
			line = sc.nextLine();
			StringTokenizer lineTok = new StringTokenizer(line);	
			//sets depositor,name and bank acct in one step sends name and 
			//makes nested name with used params
			first = lineTok.nextToken();
			last = lineTok.nextToken();
			social = lineTok.nextToken();
			accNum = Integer.parseInt(lineTok.nextToken());
			type = lineTok.nextToken();
			amount = Double.parseDouble(lineTok.nextToken());
			if( type.equalsIgnoreCase("CD")) 
			{
				int date, month, year;
				year = Integer.parseInt(lineTok.nextToken());
				month = Integer.parseInt(lineTok.nextToken());
				date = Integer.parseInt(lineTok.nextToken());
				CDAccount bankAcc = new CDAccount (year, month, 
						date, first, last,social, accNum, type, 
						amount);
				bank.openNewAccount(accNum, bankAcc);
			} 
			else if(type.equalsIgnoreCase("Savings")) 
			{
				SavingsAccount bankAcc = new SavingsAccount (first, 
						last,			social, 
						accNum, type, amount);
				bank.openNewAccount(accNum, bankAcc);

			} 
			else 
			{
				CheckingAccount bankAcc = new CheckingAccount 
						(first, last,
								social, accNum, type, amount);
				bank.openNewAccount(accNum, bankAcc);
			}			
		}
		// closes the input file
		sc.close();
	}
	/*
	 * Method printAccts(): 
	 * Input: Bank object
	 * outFile - reference to the output file 
	 * Process: Prints the database of bank accounts and
	 * their respective attributes.
	 * 
	 * Output: Prints the database of account
	 */

	public static void printAccts(Bank bank, PrintWriter outFile, boolean trans)
	{
		BankAccount myBankAcc = new BankAccount();
		outFile.println("\t\t\t\t\t\tDatabase of Bank Accounts\n");
		outFile.printf("First \t   Last       Social Security# Account#"
				+ "\t  Account Type   Balance    Status \n \n");
		outFile.println("/---------------------------------------"
				+ "------------------------------------------\\");
		for (int index = 0; index < bank.getNumAcc(); index++) {
			myBankAcc = bank.getAcct(index);
			outFile.println( myBankAcc);
			if (trans){

				if(myBankAcc.accType.equalsIgnoreCase("CD")) {
					CDAccount myAcc = bank.getCDAcct(index);
					outFile.printf("Format (yyyy-mm-dd) \n" + 
							"Current Date: " +myAcc.getDate() +
							"\nMaturity Date:" + myAcc.getMature() + "\n");
				}

				outFile.printf(" \nTransaction History:  \n" );

				ArrayList <Transaction> transaction = new ArrayList<Transaction>();

				for(int i = 0 ; i < myBankAcc.getTransactions().size(); i++) {
					transaction.add(
							new Transaction(myBankAcc.getTransactions().get(i)));

				}
				for(int i = 0; i < myBankAcc.getNumTrans() ;  i++ ) {
					//Prints Transactions using auto-toString
					outFile.printf("%s%n",  transaction.get(i));	
				}
			}
			outFile.println("\\---------------------------------------"
					+ "------------------------------------------/");
		}
		outFile.printf("Total in CD:           $%10.2f%n" , Bank.getTotCD());
		outFile.printf("Total in Savings:      $%10.2f%n" , Bank.getTotSav());
		outFile.printf("Total in Checkings:    $%10.2f%n" , Bank.getTotCh());
		outFile.printf("Total Amount in Bank:  $%10.2f%n%n" , Bank.getTotalAmt());
		//Flushes the output file
		outFile.flush();
	}

	public static void	closeAccount(Bank bank, PrintWriter outFile, Scanner kybd) {
		int temp, index;

		System.out.println("Enter the account number you wish to close:");
		if(kybd.hasNextInt()) {
			temp = kybd.nextInt();
			index = bank.findAcct(temp);
			//Checks validity of account
			if(index >= 0) {
				switch(bank.checkAccType(index)) {
				case "Savings":
					SavingsAccount sAcc = bank.getSavingsAcct(index);
					sAcc.closeAcct();
					sAcc.addTransaction(bank.getSavingsAcct(index),"Account Closed");
					bank.setSavAcct(index, sAcc);
					break;
				case "Checkings":
					CheckingAccount cAcc = bank.getCheckingAcct(index);
					cAcc.closeAcct();
					cAcc.addTransaction(bank.getCheckingAcct(index),"Account Closed");
					bank.setChAcct(index, cAcc);
					break;
				case "CD":
					CDAccount CDAcc = bank.getCDAcct(index);
					CDAcc.closeAcct();
					CDAcc.addTransaction(bank.getCDAcct(index),"Account Closed");
					bank.setCDAcct(index, CDAcc);
					break;

				}
			}
			outFile.println("Transaction Requested: Close Account");
			outFile.println("Sucessfully closed  account " + temp);

		} else { 

			System.out.println("Error: Account number must be 6 consecutive integers");
		}
		outFile.println();
		outFile.flush();
	}

	public static void	reOpenAccount(Bank bank, PrintWriter outFile, Scanner kybd) {

		int temp, index ;
		System.out.println("Enter the account number you wish to reopen:");
		if(kybd.hasNextInt()) {
			temp = kybd.nextInt();
			index = bank.findAcct(temp);
			if(index >= 0) {
				switch(bank.checkAccType(index)) {
				case "Savings":
					SavingsAccount sAcc = bank.getSavingsAcct(index);
					sAcc.reOpenAcct();
					sAcc.addTransaction(bank.getSavingsAcct(index),"Account Re-Opened");
					bank.setSavAcct(index, sAcc);
					break;
				case "Checkings":
					CheckingAccount cAcc = bank.getCheckingAcct(index);
					cAcc.reOpenAcct();
					cAcc.addTransaction(bank.getCheckingAcct(index),"Account Re-Opened");
					bank.setChAcct(index, cAcc);
					break;
				case "CD":
					CDAccount CDAcc = bank.getCDAcct(index);
					CDAcc.reOpenAcct();
					CDAcc.addTransaction(bank.getCDAcct(index),"Account Re-Opened");
					bank.setCDAcct(index, CDAcc);
					break;
				}
			}
			outFile.println("Transaction Requested: Reopen Account");
			outFile.println("Sucessfully reopened account " + temp);

		} else { 
			System.out.println("Error: Account number must be 6 consecutive integers");
		}
		outFile.println();
		outFile.flush();
	}

	public static void accountInfoPlus(Bank bank, PrintWriter outFile, Scanner kybd) {

		//Makes temp string to ensure validity
		String tempInput;
		int temp = 0 ;// temporary index that will be tested for validity.
		System.out.println("Enter Social Secial Security Number"
				+ " to Get Account Information");
		outFile.flush();

		if (kybd.hasNextInt()) //validates input as integer
		{
			tempInput = kybd.next();	
			//temp is the signal, either will be a index, positive if
			// account is there, or one of the negative, which will 
			//trigger an error message.
			temp = bank.findAcctSSN(tempInput);
			if(temp > -1) {
				outFile.println("Transaction Requested: Account Information");
				outFile.print("Sucessfully found account linked to SS# \"" 
						+ tempInput+ "\" below:\n\n");
				outFile.printf("First \t   Last       Social Security# Account#"
						+ "\t  Account Type   Balance     Status \n \n");
				outFile.println("/---------------------------------------------"
						+ "------------------------------------\\");
				outFile.printf("%s%n", bank.getAcct((temp)));
				outFile.printf("Transaction History: \n" );
				ArrayList <Transaction> trans = new ArrayList<Transaction>();
				switch(bank.checkAccType(temp)) {
				case "Savings":
					SavingsAccount accSav = bank.getSavingsAcct(temp);
					accSav.addTransaction(bank.getSavingsAcct(temp),
							"Account Info Plus  ");
					trans = bank.getSavingsAcct(temp).getTransactions();

					for(int i = 0; i < bank.getSavingsAcct(temp)
							.getNumTrans() ;  i++ ) {	
						outFile.printf("%-20s ", trans.get(i).getTransType());
						if ( trans.get(i).getTransAmt() > 0) {
							outFile.printf( "$%10.2f ", trans.get(i).getTransAmt());
						}
						outFile.println();
					}
					//Setting back the copied and modified account
					bank.setSavAcct(temp, accSav);
					break;
				case "Checkings":
					CheckingAccount accCh = bank.getCheckingAcct(temp);
					accCh.addTransaction(bank.getCheckingAcct(temp),
							"Account Info Plus ");
					trans = bank.getCheckingAcct(temp).getTransactions();

					for(int i = 0; i < bank.getAcct(temp).getNumTrans() ;  i++ ) {	
						outFile.printf("%-20s ", trans.get(i).getTransType());
						if ( trans.get(i).getTransAmt() > 0) {
							outFile.printf( "$%10.2f ", trans.get(i).getTransAmt());
						}
						outFile.println();
					}
					//Setting back the copied and modified account
					bank.setChAcct(temp, accCh);
					break;
				case "CD":
					CDAccount accCD = bank.getCDAcct(temp);
					accCD.addTransaction(bank.getCDAcct(temp),
							"Account Info Plus ");
					trans = bank.getCDAcct(temp).getTransactions();
					for(int i = 0; i < bank.getCDAcct(temp).getNumTrans() ;  i++ ) {	
						outFile.printf("%-20s ", trans.get(i).getTransType());
						if ( trans.get(i).getTransAmt() > 0) {
							outFile.printf( "$%10.2f ", trans.get(i).getTransAmt());
						}
						outFile.println();
					}
					//Setting back the copied and modified account
					bank.setCDAcct(temp, accCD);
					break;

				}
				outFile.printf("\\----------------------------"
						+ "-----------------------------------------------------/");
				outFile.flush();
			}
			else if( temp == -1)
			{//Error message for valid but not found
				outFile.println("Transaction Requested: Account Information");
				outFile.printf("Error: No account with Social Security#:\""
						+ tempInput + "\" found. \n");
			}
			else if (temp ==  -2)
			{ //Error for invalid length
				outFile.println("Transaction Requested: Account Information");
				outFile.print("Error: Social Security "
						+ "numbers must be 9 integers long!\n");
			}
		}
		else 
		{//Error for invalid input. ie letters
			tempInput = kybd.next();
			outFile.println("Transaction Requested: Account Information");
			outFile.print("Error: \""+tempInput + "\" is an invalid entry."
					+ "\nSocial Security numbers"
					+ " must be 9 consecutive integers.\n");	
		}
		outFile.println();
		outFile.flush();
	}
	/*
	 * Method accountInfo():
	 * Input:Takes a BankAccount object from the array the number of accounts,
	 * scanner and outfile.
	 * Process: Allows user to query database by inputting
	 * the accounts' associated social security number.If the number is incorrect
	 * or invalid, an error message occurs.
	 * Output:Prints the account information associated with the inputed social
	 * security number.
	 */
	public static void accountInfo(Bank bank, 
			PrintWriter outFile, Scanner kybd) {
		//Makes temp string to ensure validity
		String tempInput;
		int temp = 0 ;// temporary index that will be tested for validity.
		System.out.println("Enter Social Secial Security Number"
				+ " to Get Account Information");
		outFile.flush();
		if (kybd.hasNextInt()) //validates input as integer
		{
			tempInput = kybd.next();	
			//temp is the signal, either will be a index, positive if
			// account is there, or one of the negative, which will 
			//trigger an error message.
			temp = bank.findAcctSSN(tempInput);
			if(temp > -1) {

				outFile.println("Transaction Requested: Account Information");
				outFile.print("Sucessfully found account linked to SS# \"" 
						+ tempInput+ "\" below:\n\n");
				outFile.printf("First \t   Last       Social Security# Account#"
						+ "\t  Account Type   Balance    Status \n");
				outFile.println("/---------------------------------------"
						+ "------------------------------------------\\");
				outFile.println(bank.getAcct(temp));
				outFile.printf("\\----------------------------"
						+ "-----------------------------------------------------/");
				switch(bank.checkAccType(temp)) {
				case "Savings":
					SavingsAccount accSav = bank.getSavingsAcct(temp);
					accSav.addTransaction(bank.getAcct(temp),
							"Account Info  ");
					bank.setSavAcct(temp, accSav);
					break;
				case "Checkings":
					CheckingAccount accCh = bank.getCheckingAcct(temp);
					accCh.addTransaction(bank.getAcct(temp),
							"Account Info  ");
					bank.setChAcct(temp, accCh);
					break;
				case "CD":
					CDAccount accCD = bank.getCDAcct(temp);
					accCD.addTransaction(bank.getAcct(temp),
							"Account Info  ");
					bank.setCDAcct(temp, accCD);
					break;
				}

				//Setting back the copied and modified account
				outFile.flush();

			}
			else if( temp == -1)
			{//Error message for valid but not found
				outFile.println("Transaction Requested: Account Information");

				outFile.printf("Error: No account with Social Security#:\""
						+ tempInput + "\" found. \n");
			}
			else if (temp ==  -2)
			{ //Error for invalid length
				outFile.println("Transaction Requested: Account Information");
				outFile.print("Error: Social Security "
						+ "numbers must be 9 integers long!\n");
			}
		}
		else 
		{//Error for invalid input. ie letters
			tempInput = kybd.next();
			outFile.println("Transaction Requested: Account Information");
			outFile.print("Error: \""+tempInput + "\" is an invalid entry."
					+ "\nSocial Security numbers"
					+ " must be 9 consecutive integers.\n");	
		}
		outFile.println();
		outFile.flush();
	}

	/*
	 * Method balance(): 
	 * Input: Bank object
	 * outFile - reference to output file kybd - reference to
	 *  the "test cases" input file.
	 * Process: Prompts for the requested account Calls findAcct()
	 * method from bank object to see if the account exists If the 
	 * account exists, the balance is printed Otherwise, an error message
	 *  is printed .
	 * 
	 * Output: If the account exists, the balance is
	 * printed Otherwise, an error message is printed
	 */

	public static void balance(Bank bank, 
			PrintWriter outFile, Scanner kybd) 
	{
		int requestedAccount;
		int index;
		String accLength; // Sets up new account as string to ensure validity
		System.out.println();
		System.out.println("Enter the account number: ");
		if (kybd.hasNextInt()) {
			accLength = kybd.next();
			if (accLength.length() != 6 || Integer.parseInt(accLength) < 100000) 
			{
				outFile.println("Transaction Requested: Balance");
				outFile.printf("Error: Account number entered invalid!" + 
						"\nAccount numbers must be a 6-digit integer "
						+ "\nbetween 100000 and 999999.\n");

			} 
			else 
			{
				requestedAccount = Integer.parseInt(accLength);
				// call findAcct to search if requestedAccount exists

				index = bank.findAcct( requestedAccount);
				if (index == -1) // invalid account
				{
					outFile.println("Transaction Requested: Balance Inquiry");
					outFile.println("Error: Account number " + requestedAccount 
							+ " does not exist.");
				} 
				else // valid account
				{
					outFile.println("Transaction Requested: Balance Inquiry");
					outFile.println("Account Number: " + requestedAccount);
					outFile.printf("Current Balance: $%.2f", 
							bank.getAcct(index).getAccBal());
					BankAccount newBankAcc = bank.getAcct(index); //making a copy
					newBankAcc.addTransaction(bank.getAcct(index),
							"Balance Inquiry");
					//Setting back the copied and mod account
					bank.setAcct(index, new BankAccount(newBankAcc));

					outFile.println();
				}
			}
		} 
		else 
		{ 
			// Message for invalid account number entered
			accLength = kybd.next();
			outFile.println("Transaction Requested: Balance");
			outFile.printf("Error: Account number entered invalid!"
					+ "\nAccount numbers must be a 6-digit integer "
					+ "\nbetween 100000 and 999999.\n");
		}
		outFile.println();
		outFile.flush(); // flush the output buffer

	}

	/*
	 * Method deposit(): 
	 * Input: Bank object
	 * 
	 * Process: Prompts for the requested account Calls Bank object
	 * findacct() to see if the  account exists If the account exists,
	 * prompts for the amount to deposit if
	 * the amount is valid, it makes the deposit and prints the new balance
	 * Otherwise, an error message is printed. 
	 * 
	 * Output: For a valid deposit, the deposit transaction is printed 
	 * Otherwise, an error message is printed
	 */

	public static void deposit(Bank bank, 
			PrintWriter outFile, Scanner kybd) 
	{
		int requestedAccount;
		int index;
		double amountToDeposit;
		String accLength; 

		// Sets up account as string to ensure validity
		System.out.println();
		System.out.print("Enter the account number: ");
		if (kybd.hasNextInt()) 
		{
			accLength = kybd.next();

			if (accLength.length() != 6 || Integer.parseInt(accLength) < 100000) 
			{
				outFile.println("Transaction Requested: Deposit");
				outFile.printf("Error: Account number entered invalid!"
						+ "\nAccount numbers must be a 6-digit integer "
						+ "\nbetween 100000 and 999999.\n");
			}
			// read-in the account number
			requestedAccount = Integer.parseInt(accLength); 

			// call findAcct bank method to search if requestedAccount exists
			index = bank.findAcct(requestedAccount);

			if (index == -1) // invalid account
			{
				outFile.println("Transaction Requested: Deposit");
				outFile.println("Error: Account number " + 
						requestedAccount + " does not exist.");
			} 
			else // valid account
			{
				System.out.println("Enter amount to deposit: "); 
				if (kybd.hasNextDouble()) {
					amountToDeposit = kybd.nextDouble(); 
					// read-in the amount to deposit

					if (amountToDeposit <= 0.00) {
						// invalid amount to deposit
						outFile.println("Transaction Requested: Deposit");
						outFile.println("Account Number: " + requestedAccount);
						outFile.printf("Error: $%.2f is an invalid "
								+ "amount", amountToDeposit);
						outFile.println();
					} 
					else 
					{
						if(bank.getAcct(index).getStatus().equalsIgnoreCase("Open"))
						{
							outFile.println("Transaction Requested: Deposit");
							outFile.println("Account Number: " + requestedAccount);
							outFile.printf("Old Balance: $%.2f", 
									bank.getAcct(index).getAccBal());
							outFile.println();
							outFile.printf("Amount to Deposit: %.2f \n" 
									, amountToDeposit);
							boolean s = false;
							switch(bank.checkAccType(index)) {
							case "Savings":
								SavingsAccount sAcc = bank.getSavingsAcct(index);
								sAcc.makeDeposit( amountToDeposit);
								bank.setSavAcct(index, sAcc);
								break;
							case "Checkings":
								CheckingAccount cAcc = bank.getCheckingAcct(index);
								System.out.println("Enter the Month, date and year of "
										+ "check separated by spaces");
								int month, date, year;
								month = kybd.nextInt();
								date = kybd.nextInt();
								year = kybd.nextInt();
								if(cAcc.makeDeposit( year, month, date, amountToDeposit)) {

									bank.setChAcct(index, cAcc);
								} else {
									outFile.println("Invalid Date on check. "
											+ "Cannot deposit!");

								}
								break;
							case "CD":
								CDAccount CDAcc = bank.getCDAcct(index);
								if(CDAcc.makeDeposit( amountToDeposit, true)){
									CDAcc.addTransaction(bank.getCDAcct(index),
											"Deposit");
									bank.setCDAcct(index, CDAcc);
								}else {
									outFile.printf("Cannot make deposit. "
											+ "Your Maturity date is:\n" + 
											CDAcc.getMature());
									outFile.println();
									outFile.flush();
								}
								break;
							}
							outFile.printf("New Balance: $%.2f", 
									bank.getAcct(index).getAccBal());
							outFile.println();
						} else {
							outFile.println("Transaction Requested: Deposit");
							outFile.print("ERROR: Account " + 
									requestedAccount+ " is closed!");
							outFile.println();
						}
					}
				} 
				else 
				{ // invalid amount to deposit
					accLength = kybd.next();
					outFile.println("Transaction Requested: Deposit");
					outFile.println("Account Number: " + requestedAccount);
					outFile.printf("Error: Deposit entered invalid!"
							+ " Deposit must be a positive integer or double.");
					outFile.println();
				}
			}
		} 
		else 
		{ // Message for invalid account number entered
			accLength = kybd.next();
			outFile.println("Transaction Requested: Deposit");
			outFile.printf("Error: Account number entered invalid!"
					+ "\nAccount numbers must be a 6-digit integer "
					+ "\nbetween 100000 and 999999.\n");
		}
		outFile.println();
		outFile.flush(); // flush the output buffer
	}

	/*
	 * Method withdrawal(): 
	 * Input: Account number followed by amount to withdraw
	 * 
	 * Process: Ensures account exists and removes amount selected, otherwise
	 * displays error message. 
	 * 
	 * Output: Lists old balance, then new balance successfully withdrawn.
	 */
	public static void withdrawal(Bank bank, 
			PrintWriter outFile, Scanner kybd)
					throws ParseException {
		int requestedAccount;
		int index;
		double amountToWithdraw;
		String accLength; 
		// Sets up new account as string to ensure validity
		System.out.println();
		System.out.print("Enter the account number: "); 
		// prompt for account number

		if (kybd.hasNextInt()) 
		{ // Validates input
			accLength = kybd.next(); 
			// Sets validated integer as next string for testing
			if (accLength.length() != 6 || 
					Integer.parseInt(accLength) < 100000) 
			{
				outFile.println("Transaction Requested: Withdrawal");
				outFile.printf("Error: Account number entered invalid!"
						+ "\nAccount numbers must be a 6-digit integer "
						+ "\nbetween 100000 and 999999.\n");
			} 
			else 
			{
				// Calls findAcct to search if requestedAccount exists
				requestedAccount = Integer.parseInt(accLength);
				index = bank.findAcct(requestedAccount);
				if (index == -1) 
				{ // Signals invalid account 
					outFile.println("Transaction Requested: Withdrawal");
					outFile.println("Error: Account"
							+ " number " + 
							requestedAccount + " does not exist");
				} 
				else 
				{ // Signals valid account
					// Prompts for withdrawal
					System.out.print("Enter amount to Withdraw: "); 
					// Ensures correct input
					if (kybd.hasNextInt()) {
						amountToWithdraw = kybd.nextDouble();

						// Criteria for invalid deposit request
						if (amountToWithdraw <= 0.00) {
							outFile.println("Transaction Requested: Withdrawal");
							outFile.println("Account Number: " + requestedAccount);
							outFile.printf("Error: $%.2f is an"
									+ " invalid amount",amountToWithdraw);
							outFile.println();

						} 
						else if (bank.getAcct(index).getAccBal() < amountToWithdraw) 
						{// User trying to withdraw more than they have
							outFile.println("Transaction Requested: Withdrawal");
							outFile.println("Account Number: " + requestedAccount);
							outFile.println("Error: Insufficient funds!");
							outFile.printf("You cannot"
									+ " take out $" + amountToWithdraw

									+ " You currently have $%.2f",
									bank.getAcct(index).getAccBal());
							outFile.println();
						}
						else 
						{
							outFile.println("Transaction Requested: Withdrawal");
							outFile.println("Account Number: " +
									requestedAccount);
							outFile.printf("Old Balance: "
									+ "$%.2f ", bank.getAcct(index).getAccBal());

							outFile.printf("\nAmount to Withdrawal: $%.2f", 
									amountToWithdraw);
							// Making successful withdrawal
							if(bank.getAcct(index).getStatus().
									equalsIgnoreCase("Open")) {
								if (bank.getAcct(index).accType.
										equalsIgnoreCase("Checkings"))
								{	
									CheckingAccount acc =  bank.getCheckingAcct(index);
									acc.makeWithdrawal(requestedAccount,
											amountToWithdraw);
									bank.setChAcct(index, acc);
									outFile.printf("\nNew Balance: "
											+ "$%.2f", bank.getAcct(index).getAccBal());
									outFile.println();
									outFile.flush();
								}
								if( bank.getAcct(index).accType.equalsIgnoreCase("CD")) 
								{
									CDAccount bankAcc = bank.getCDAcct(index);
									double old = bank.getAcct(index).getAccBal();
									// Making successful withdrawal
									bankAcc.makeWithdrawal(amountToWithdraw, true);
									bank.setCDAcct(index, bankAcc);
									if(bank.getAcct(index).getAccBal() != old ) {
										outFile.printf("\nNew Balance: "
												+ "$%.2f", bank.getAcct(index)
												.getAccBal());}
									else {
										outFile.printf("\nCannot Witdraw! "
												+ "CD maturity date not met.");
										outFile.printf("\nYour Maturity "
												+ "Date is(yyyy-mm-dd):\n"+ 
												bank.getCDAcct(index).getMature() + 
												"\nThat's "+ 
												bank.getCDAcct(index).getMonths() + 
												" months away");
										outFile.printf("\nBalance is: "
												+ "$%.2f", bank.getAcct(index)
												.getAccBal());
									}
									outFile.println();
									outFile.flush();

								} 
								else if(bank.getAcct(index).
										accType.equalsIgnoreCase("Savings")) 
								{

									SavingsAccount bankAcc = bank.getSavingsAcct(index);
									// Making successful withdrawal
									bankAcc.makeWithdrawal(requestedAccount,
											amountToWithdraw);	
									bank.setSavAcct(index, bankAcc);
									outFile.printf("\nNew Balance: "
											+ "$%.2f", bank.getAcct(index).getAccBal());
									outFile.println();

								}
							}
							else 
							{
								outFile.println("Transaction Requested: Withdrawal");
								outFile.println("ERROR: Account " +
										requestedAccount +
										" is Closed!");
							}
						}
					}
					else 
					{
						accLength = kybd.next();
						outFile.println("Transaction Requested: Withdrawal");
						outFile.println("Error: Withdrawal must be numbers!");
					}
				}
			}
		} 
		else
		{ // Message for invalid account number entered
			accLength = kybd.next();
			outFile.println("Transaction Requested: Withdrawal");
			outFile.printf("Error: Account number entered invalid!"
					+ "\nAccount numbers must be a 6-digit integer "
					+ "\nbetween 100000 and 999999.\n");
		}
		outFile.println();
		outFile.flush();
	}

	/*
	 * Method newAcct(): 
	 * Input:New Account number
	 *
	 * Process:Uses find account to check if account already exists. If account
	 * number is valid but taken, teller is notified. If account number is not
	 * valid, error message and instructions displayed. Once a valid new account
	 * number is entered, the social security number and account type is
	 * checked and assigned to the new account object being created.
	 *
	 * Output: Displays the successful creation of the new account with paired
	 * social security number.
	 *
	 */

	public static void newAcct(Bank bank, 
			PrintWriter outFile, Scanner kybd) 
	{
		int accountNew , accountNum,  index;
		double accountBal;
		String accLength;
		// Sets up new account as string to ensure validity
		char choice;
		String  temp,
		socSec,
		first, 
		last, 
		type = null;
		System.out.println("Enter New Account Number:");
		// Checks read-in the account number
		if (kybd.hasNextInt()) { // Validates input
			accLength = kybd.next();

			if (accLength.length() != 6 || Integer.parseInt(accLength) < 100000) 
			{
				outFile.println("Transaction Requested: New Account");
				outFile.printf("Error: Account number entered invalid!"
						+ "\nAccount numbers must be a 6-digit integer "
						+ "\nbetween 100000 and 999999.\n");
				outFile.println();
				outFile.flush();
			} 
			else 
			{
				accountNew = Integer.parseInt(accLength); //Sets account number
				// Calls findAcct to search if requestedAccount exists
				index = bank.findAcct( accountNew);
				if (index != -1) // invalid: account in-use
				{
					outFile.println("Transaction Requested: Create New Account");
					outFile.println("Error: Account number " + accountNew + 
							" is already in use.\n");
					outFile.flush();
				}

				//Setting values for first and last name
				System.out.println("Enter your first name:");
				first = kybd.next();
				System.out.println("Enter your last name:");
				last = kybd.next();
				System.out.println("Enter Social Security number:");
				outFile.flush();
				//Ensures validity of social security number
				if(kybd.hasNextInt() ) 
				{
					temp = kybd.next();
					if(temp.length()!=9 || temp.length() > 9 ) 
					{
						outFile.println("ERROR:Invalid entry."
								+ " Social Security number "+ temp +
								" must be 9 digits long\n");
						outFile.flush();
					}
					socSec = temp;//setting socSec to right account
					//Making new Bank account object
					//Call new account here?
					int success = bank.findAcctSSN(temp);
					//Prompt for user to choose an account type
					if(success == -1) 
					{ 
						System.out.println("Select an account type from"
								+ " following options:");
						System.out.println();
						System.out.println("\t****************************");
						System.out.println("\t    List of Choices         ");
						System.out.println("\t****************************");
						System.out.println("\t     C -- Checking");
						System.out.println("\t     S -- Savings");
						System.out.println("\t     D -- CD ");
						System.out.println();

						//Determines what user selects
						choice = kybd.next().charAt(0);
						switch (choice) {
						case 'c':
						case 'C':
							type = "Checkings";
							break;
						case 's':
						case 'S':
							type = "Savings";
							break;
						case 'D':
						case 'd':
							type = "CD";
							break;
						default:
							outFile.println("Error: " + choice + 
									" is an invalid selection -  try again");
							outFile.println();
							outFile.flush();
							break;
						}
						//prompts for initial deposit
						System.out.println("Enter your inital opening deposit:");
						accountBal = (kybd.nextDouble());
						accountNum = accountNew;	
						if( type.equalsIgnoreCase("CD")) 
						{
							int date, month, year;
							System.out.println("Enter month date and year"
									+ " of term of CD"
									+ "separeted by spaces");
							year = kybd.nextInt();
							month = kybd.nextInt();
							date = kybd.nextInt();
							//Making bank object to send to bank.openNewAccount
							CDAccount bankAcc = new CDAccount (year, month, 
									date,
									first, last,socSec, accountNum, type,
									accountBal);
							bank.openNewAccount(accountNum, bankAcc);
							outFile.println("Transaction Requested: Create New Account");
							outFile.printf("New "+ type +
									" Account with account number \"" + accountNew 
									+ "\" \nwith social security number \"" + socSec +
									" was created. "
									+ "\nThe Maturity date is:"+ month 
									+" " + date +" " + year);
							outFile.printf("\n");
							outFile.flush();

						} 
						else if(type.equalsIgnoreCase("Savings")) 
						{

							SavingsAccount bankAcc = new SavingsAccount (first, 
									last,socSec, 
									accountNum, type, accountBal);
							bank.openNewAccount(accountNum, bankAcc);
							outFile.println("Transaction Requested: Create New Account");
							outFile.printf("New "+ type +
									" Account with account number \"" + accountNew 
									+ "\" \nwith social security number \"" + socSec
									+ "\nwas created.");
							outFile.printf("\n");
							outFile.flush();
						} 
						else 
						{
							CheckingAccount bankAcc = new CheckingAccount 
									(first, last,
											socSec, accountNum, type, accountBal);
							bank.openNewAccount(accountNum, bankAcc);
							outFile.println("Transaction Requested: Create New Account");
							outFile.printf("New "+ type +
									" Account with account number \"" + accountNew 
									+ "\" \nwith social security number \"" + socSec
									+ "\nwas created.");
							outFile.printf("\n");
							outFile.flush();
						}
					}
				}
				else 
				{ // Message for invalid account number entered
					accLength = kybd.next();
					outFile.println("Transaction Requested: New Account");
					outFile.printf("Error: Account number entered invalid!"
							+ "\nAccount numbers must be a 6-digit integer "
							+ "\nbetween 100000 and 999999.\n");
					outFile.println();
					outFile.flush();

				}
				outFile.println();
				outFile.flush();
			}
		}
	}

	/*
	 * Method deleteAcct(): 
	 * Input: Existing account number
	 *
	 * Process: Ensures account balance is at 0.00 and delete account. 
	 * Makes sure account info is valid, and that
	 * the balance of the account is at 0. 
	 * 
	 * Output: If account is invalid or not empty
	 * displays the amount needed to withdraw to complete the 
	 * transaction.
	 */
	public static void deleteAcct(Bank bank, 
			PrintWriter outFile, Scanner kybd) {
		int delAcct, index = 0;
		String delTemp, accLength; 
		// Setting up accounts as strings to test validity
		System.out.println("Enter Account Number for deletion:");
		if (kybd.hasNextInt()) {
			accLength = kybd.next();
			delAcct = Integer.parseInt(accLength);

			delTemp = Integer.toString(delAcct);
			index = bank.findAcct(delAcct);
			if (index == -1) {
				outFile.println("Transaction Requested: Delete Account");
				outFile.println("Error: Account entered does not exist!");
			} 
			else if (Integer.parseInt(delTemp) <= 100000 || delTemp.length() 
					!= 6) 
			{
				outFile.println("Transaction Requested: Delete Account");
				outFile.printf("Error: Account number entered invalid!"
						+ "\nAccount numbers must be a 6-digit integer "
						+ "\nbetween 100000 and 999999.\n\n");
			} 
			else if (bank.getAcct(bank.findAcct(delAcct)).getAccBal() != 0.00) 
			{
				outFile.println("Transaction Requested: Delete Account");
				outFile.printf("Error: Account "+delTemp+" is not empty."
						+ "\nRemove $");
				outFile.printf("%.2f %s", 
						bank.getAcct(bank.findAcct(delAcct)).getAccBal(), 
						"from account before deleting.\n");
			} 
			else 
			{ 
				bank.getAcct(index).closeAcct(); //Closing account
				bank.deleteAcct(index);
				outFile.println("Transaction Requested: Delete Account");
				outFile.println("Successfully deleted account number: " +
						delAcct);
			}
		} 
		else 
		{ //Error message for invalid account entered
			accLength = kybd.next();
			outFile.println("Transaction Requested: New Account");
			outFile.printf("Error: Account number entered invalid!"
					+ "\nAccount numbers must be a 6-digit integer "
					+ "\nbetween 100000 and 999999.\n\n");
		}
		outFile.println();
		outFile.flush();
	}
	/* Method pause() */
	public static void pause(Scanner keyboard) {
		String tempstr;
		System.out.println();
		System.out.print("press ENTER to continue");
		tempstr = keyboard.nextLine(); // flush previous ENTER
		tempstr = keyboard.nextLine(); // wait for ENTER
	}
}
