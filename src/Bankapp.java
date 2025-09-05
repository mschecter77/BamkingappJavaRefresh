import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bankapp extends JFrame {

 private JTextField accountNumber;
 private JTextField amount;
 private JLabel balance;
 private JButton deposit;
 private JButton withdraw;
 private JButton addAccount;
 private DefaultListModel<String> recordModel;
 private JList<String> recordList;
 private DefaultListModel<account> bankAccounts;
 private JComboBox<account> accounts;

 public Bankapp() {
  setTitle("Bank Account");
  setSize(700, 600);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setLocationRelativeTo(null);


  JPanel dataPanel = new JPanel();
  dataPanel.setLayout(new GridLayout(3, 3));

  JPanel buttonPanel = new JPanel();
  JPanel recordPanel = new JPanel();


  bankAccounts = new DefaultListModel<>();
  addAccount = new JButton("Add Account");
  withdraw = new JButton("Withdraw");
  deposit = new JButton("Deposit");
  accountNumber = new JTextField(10);
  accounts = new JComboBox<>();

  dataPanel.add(new JLabel("Choose existing account: "));
  dataPanel.add(accounts);
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
  buttonPanel.add(addAccount);

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
     account selected = (account) accounts.getSelectedItem();
     if (selected != null) {
      selected.deposit(dep);
      balance.setText(String.valueOf(selected.getBalance()));
      recordModel.addElement("Deposited $" + dep + " → Balance $" + selected.getBalance());
     } else {
      JOptionPane.showMessageDialog(Bankapp.this, "Please select an account first");
     }
    } catch (NumberFormatException ex) {
     JOptionPane.showMessageDialog(Bankapp.this, "Enter a valid number");
    }
   }
  });


  withdraw.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    if (accounts.getSelectedItem() == null) {
     JOptionPane.showMessageDialog(Bankapp.this, "Please select an account first");
    } else {
     try {
      float amt = Float.parseFloat(amount.getText());
      account selected = (account) accounts.getSelectedItem();
      boolean result = selected.withdraw(amt);

      if (result) {
       balance.setText(String.valueOf(selected.getBalance()));
       recordModel.addElement("Withdrew $" + amt + " → Balance $" + selected.getBalance());
      } else {
       recordModel.addElement("Withdrawal of $" + amt + " failed (Insufficient funds)");
      }
     } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(Bankapp.this, "Enter a valid number");
     }
    }
   }
  });


  addAccount.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    try {
     account indacc = new account();
     indacc.setAccountNumber(Integer.parseInt(accountNumber.getText()));
     bankAccounts.addElement(indacc);
     accounts.addItem(indacc);
    } catch (NumberFormatException ex) {
     JOptionPane.showMessageDialog(Bankapp.this, "Enter a valid account number");
    }
   }
  });
  accounts.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
    account selected = (account) accounts.getSelectedItem();
    if (selected != null) {
     balance.setText(String.valueOf(selected.getBalance()));
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
