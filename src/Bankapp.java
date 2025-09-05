import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bankapp extends JFrame {

 private account bankaccount = new account();
 private JTextField accountNumber;
 private JTextField amount;
 private JLabel balance;
 private JButton deposit;
 private JButton withdraw;
 private DefaultListModel<String> recordModel;
 private JList<String> recordList;


 public Bankapp() {
  setTitle("Bank Account");
  setSize(400, 300);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setLocationRelativeTo(null);


  JPanel dataPanel = new JPanel();
  dataPanel.setLayout(new GridLayout(2, 2));

  JPanel buttonPanel = new JPanel();
  JPanel recordPanel = new JPanel();


  withdraw = new JButton("Withdraw");
  deposit = new JButton("Deposit");
  accountNumber = new JTextField(10);

  dataPanel.add(new JLabel("Account#"));
  dataPanel.add(accountNumber);

  dataPanel.add(new JLabel("Balance $"));
  balance = new JLabel("0.00"); // start with 0
  dataPanel.add(balance);

  buttonPanel.add(new JLabel("Amount $"));
  amount = new JTextField(10);
  buttonPanel.add(amount);
  buttonPanel.add(deposit);
  buttonPanel.add(withdraw);


  recordModel = new DefaultListModel<>();
  recordList = new JList<>(recordModel);

  recordPanel.setLayout(new BorderLayout());
  recordPanel.add(new JScrollPane(recordList), BorderLayout.CENTER);


  add(dataPanel, BorderLayout.NORTH);
  add(recordPanel, BorderLayout.CENTER);
  add(buttonPanel, BorderLayout.SOUTH);


  deposit.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    try {
     float dep = Float.parseFloat(amount.getText());
     bankaccount.deposit(dep);
     balance.setText(String.valueOf(bankaccount.getBalance()));
     recordModel.addElement("Deposited $" + dep + " → Balance $" + bankaccount.getBalance());
    } catch (NumberFormatException ex) {
     JOptionPane.showMessageDialog(Bankapp.this, "Enter a valid number");
    }
   }
  });


  withdraw.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    try {
     float amt = Float.parseFloat(amount.getText());
     boolean result = bankaccount.withdraw(amt);

     if (result) {
      balance.setText(String.valueOf(bankaccount.getBalance()));
      recordModel.addElement("Withdrew $" + amt + " → Balance $" + bankaccount.getBalance());
     } else {
      recordModel.addElement("Withdrawal of $" + amt + " failed (Insufficient funds)");
     }
    } catch (NumberFormatException ex) {
     JOptionPane.showMessageDialog(Bankapp.this, "Enter a valid number");
    }
   }
  });
 }


 public static void main(String[] args) {
  SwingUtilities.invokeLater(() -> {
   new Bankapp().setVisible(true);
  });
 }
}
