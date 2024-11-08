public class BankAccount {
    private Long accountNumber;
    private double balance;

    public Long getAccountNumber(){return accountNumber;}
    public double getBalance(){return balance;}
    public void setAccountNumber(Long accountNumber){
        this.accountNumber=accountNumber;
    }
    public void setBalance(double balance){
        this.balance=balance;
    }

    //Synchronized methods for deposit and withdrawal
    public synchronized void deposit(int amount){
        balance+=amount;
        System.out.println("Deposited "+amount+" to "+accountNumber+" . Current Balance: "+balance);
    }

    public synchronized boolean withdrawal(int amount){
        if(balance>=amount){
            balance-=amount;
            System.out.println("Withdrew "+amount+" from "+accountNumber+" . Current Balance: "+balance);
            return true;
        }else{
            System.out.println("Insufficient balance for withdrawal in "+accountNumber);
            return false;
        }
    }
}
