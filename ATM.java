import java.util.Scanner;
public class ATM
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("American Bank");
        User aUser = theBank.addUser("John", "Doe", "1234"); //add user creating a Savings account

        Account newAccount = new Account("Checking", aUser, theBank); //add checking account for user
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
        User curUser;

        while (true)
        {
            curUser = ATM.mainMenuPrompt(theBank, sc); //staying in login prompt until successful login
            ATM.printUserMenu(curUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) //print the ATM's login menu
    {
        String userID;
        String pin;
        User authUser;
        do
        {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName()); //prompt the user for ID/pin combo until login is verified
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userID, pin); //trying to get user corresponding to ID and pin
            if (authUser == null)
            {
                System.out.println("Incorrect user ID/pin combination. " + "Please try again");
            }

        } while(authUser == null); 	// loop runs until it's authorised
        return authUser;

    }

    public static void printUserMenu(User theUser, Scanner sc) 
    {
        theUser.printAccountsSummary(); //printing summary of the user's account

        int choice;
        
        do
        {
            System.out.println("What would you like to do?");
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdraw");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5.");
            }
        } while (choice < 1 || choice > 5);
        switch (choice) //choice proceeding according to user's choice
        {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }

        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }
 
    /* help transfer the funds from one account to another using same UUID */
    public static void transferFunds(User theUser, Scanner sc)
    {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to " + "transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts())
            {
                System.out.println("Invalid account. Please try again.");
            }
        }
        while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcntBalance(fromAcct);

        // get account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account to " + "transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than balance " +
                        "of $.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        //At the end, transfer is done
        theUser.addAcntTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcntUUID(toAcct)));
        theUser.addAcntTransaction(toAcct, amount, String.format("Transfer from account %s", theUser.getAcntUUID(fromAcct)));

    }

    //Processing the fund withdraw from account
    public static void withdrawFunds(User theUser, Scanner sc) {  //Withdraw funds using the case choice in the atm interface
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account to " +  "withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcntBalance(fromAcct);

        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than balance " + "of $%.02f.\n", acctBal);
            }
        }
        while (amount < 0 || amount > acctBal);

        sc.nextLine();
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        theUser.addAcntTransaction(fromAcct, -1*amount, memo);

    }

    //Processing the fund deposit to an account
    public static void depositFunds(User theUser, Scanner sc) {

        int toAcct;
        double amount;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account to " + "deposit to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do {
            System.out.printf("Enter the amount to deposit: $");
            amount = sc.nextDouble();
            if (amount < 0)
            {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);

        sc.nextLine();
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        theUser.addAcntTransaction(toAcct, amount, memo);

    }

    public static void showTransHistory(User theUser, Scanner sc) //shows transaction history of the account
    {
        int theAcnt;
        do
        {
            System.out.printf("Enter the number (1-%d) of the account\nwhose " + "transactions you want to see: ", theUser.numAccounts());
            theAcnt = sc.nextInt()-1;
            if (theAcnt < 0 || theAcnt >= theUser.numAccounts())
            {
                System.out.println("Invalid account. Please try again.");
            }
        }
        while (theAcnt < 0 || theAcnt >= theUser.numAccounts());
        //printing the transaction history
        theUser.printAcntTransHistory(theAcnt);
    }

}