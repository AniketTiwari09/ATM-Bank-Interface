import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank
{
    private String name; //name of the Bank
    private ArrayList<User> users; //account holders of the bank
    private ArrayList<Account> accounts; //accounts of bank
    public Bank(String name) //creating new bank object with empty list of users and accounts
    {
        this.name = name;
        users = new ArrayList<User>();
        accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID() //generating universally unique ID for user
    {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        // continue looping until we get a unique ID
        do
        {
            // generate a random number
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        }
        while (nonUnique);
        return uuid;
    }

    public String getNewAccountUUID() //generating universally unique ID for an account
    {
        String uuid;
        Random rng = new Random(); //random ID created for an account with each ATM usage
        int len = 10;
        boolean nonUnique = false;
        // continue the loop until we get a unique ID
        do
        {
            uuid = "";
            for (int c = 0; c < len; c++)
            {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            for (Account a : this.accounts)
            {
                if (uuid.compareTo(a.getUUID()) == 0)
                {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }


    public User addUser(String firstName, String lastName, String pin) //creating new user of the bank
    {
        // create a new User and add it to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);
        return newUser;
    }

    public void addAccount(Account newAccount)
    {
        this.accounts.add(newAccount);
    }

    public User userLogin(String userID, String pin) //getting user object to associate with particular userID and pin, if Valid
    {
        for (User u : this.users) {
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        return null;
    }

    public String getName()  //getting name of the bank
    {
        return this.name;
    }
}