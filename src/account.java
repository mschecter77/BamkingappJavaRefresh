public class account {
    int accountNumber;
    float balance;


    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean withdraw(float amount) {
               if (this.balance - amount < 0)
               {
                   return false;
               }

                this.balance = this.balance - amount;
               return true;
    }
    public void deposit(float amount ){
        this.balance = this.balance +amount;
    }
}