import java.util.ArrayList;

public class Account
{
    //name, ID and Userr object and list of transaction of this accounts
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank theBank) //new Account Instance
    {

        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();

    }

    public String getUUID() //getting the account number
    {
        return this.uuid;
    }

    public void addTransaction(double amount) //adding new transaction
    {
        Transaction newTrans = new Transaction(amount, this);
        this.transactions.add(newTrans);
    }

    public void addTransaction(double amount, String memo)
    {
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);

    }

    public double getBalance() //getting the balance of this account by adding ammount to account
    {
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;

    }

    public String getSummaryLine() //get Summary of transaction of account
    {
        double balance = this.getBalance();
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }
        else
        {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    public void printTransacHistory()  //printing Transaction history for the interface in ATM Class
    {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--)
        {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }
}