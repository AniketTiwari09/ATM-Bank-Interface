import java.util.Date;

public class Transaction
{
    private double amount; //amount of the transaction
    private Date timestamp; //time and date of this transaction
    private String memo; //memo for this transaction
    private Account inAccount; //account in which transaction was done
    public Transaction(double amount, Account inAccount)
    {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount) //creating new transaction with memo
    {
        this(amount, inAccount); //calling the constructor first
        this.memo = memo;
    }

    public double getAmount() // getting the transaction amount
    {
        return this.amount;
    }

    //help get a string to summarize the transaction
    public String getSummaryLine()
    {
        if (this.amount >= 0)
        {
            return String.format("%s, $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else
        {
            return String.format("%s, $(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
        }
    }
}