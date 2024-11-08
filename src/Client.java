public class Client implements Runnable{
    private final BankAccount account;
    private final String clientName;

    public Client(BankAccount account,String clientName){
        this.account=account;
        this.clientName=clientName;
    }

    public void run(){

        //Perform transactions
        account.deposit(100);
        try{Thread.sleep(50);}catch (InterruptedException e){e.printStackTrace();}
        account.withdrawal(50);
        try{Thread.sleep(50);}catch (InterruptedException e){e.printStackTrace();}
        account.withdrawal(30);
        }
    }

