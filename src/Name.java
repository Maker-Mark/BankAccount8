//NAME: Mark Goldstein

public class Name {

	private String last;
	private String first;

	// Default constructor
	public Name() 
	{
		last = "";
		first = "";
	}
	// Constructor when first and last name is sent
	public Name(String firstName, String lastName) 
	{
		last = lastName;
		first = firstName;	
	}
	//Copy constructor
	public Name (Name n) {
		last = n.last;
		first = n.first;
	}

	//Setters and Getters for first and last name
	public void setFirst (String f)
	{
		first = f;
	}
	public void setLast (String l) 
	{
		last = l;
	}
	public String getFirst() 
	{
		return first;
	}
	public String getLast()
	{
		return last;
	}

	// To string method 
	public String toString() {
		String accString;
		accString = String.format("%-10s %-10s" , first, last );
		return accString;
	}
	//.equals method to compare two objects
	public boolean equals(Name name) {
		return (first.equals(name.first) &&  
				(last.equals(name.last)));
	}
}

