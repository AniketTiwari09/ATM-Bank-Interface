import java.security.MessageDigest;
import java.util.ArrayList;
public class User 
{
    private String firstName; //first name of the user
    private String lastName; //last name of the user
    private String uuid; //ID number of the user
    private byte pinHash[]; //hash pin of the user
    private ArrayList<Account> accounts;
    public User (String firstName, String lastName, String pin, Bank theBank) {

        this.firstName = firstName;         // set user's name
        this.lastName = lastName;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); //        // store the pin's in MD5 hash, rather than the original value
            this.pinHash = md.digest(pin.getBytes());
        } catch (Exception e) {
            System.err.println("error, caught exeption : " + e.getMessage());
            System.exit(1);
        }
        //getting new random and unique ID for the user
        this.uuid = theBank.getNewUserUUID();

        this.accounts = new ArrayList<Account>(); //list of accounts created

        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
        //Printing the log message

    }

    public String getUUID()  //get the user ID number
    {
        return this.uuid;
    }

    public void addAccount(Account anAcnt)  //Adding account for user
    {
        this.accounts.add(anAcnt);
    }
    public int numAccounts()
    {
        return this.accounts.size();
    }
    public double getAcntBalance(int acntIdx) //getting the balance of the account
    {
        return this.accounts.get(acntIdx).getBalance();
    }

    public String getAcntUUID(int acntIdx)
    {
        return this.accounts.get(acntIdx).getUUID();
    }

    public void printAcntTransHistory(int acntIdx)
    {
        this.accounts.get(acntIdx).printTransacHistory();
    }


    public void addAcntTransaction(int acntIdx, double amount, String memo) //add transaction to particular amount
    {
        this.accounts.get(acntIdx).addTransaction(amount, memo);
    }


    public boolean validatePin(String aPin) //check whether pin is validated or not by being matched
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),
                    this.pinHash);
        } catch (Exception e) {
            System.err.println("error, caught exeption : " + e.getMessage());
            System.exit(1);
        }

        return false;
    }
    
    public void printAccountsSummary() //Print the summaries of the accounts for this user
    {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++)
        {
            System.out.printf("%d) %s\n", a+1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
}