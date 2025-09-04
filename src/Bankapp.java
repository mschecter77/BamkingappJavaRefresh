import javax.swing.*;
import java.awt.*;

public class Bankapp extends JFrame{
 account bankaccount = new account();
 private JTextField accountNumber;
 private JTextField amount;
 private JLabel balance;
 private JButton deposit;
 private  JButton withdraw;
 private JList<String> recordList;
 public Bankapp(){
  setTitle("Bank Account");
  setSize(400, 300);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setLocationRelativeTo(null);
  JPanel dataPanel = new JPanel();
  dataPanel.setLayout(new BorderLayout());

  JPanel buttonPanel = new JPanel();
  JPanel recordPanel = new JPanel();
  withdraw = new JButton("Withdraw");
  deposit = new JButton("Deposit");
  accountNumber = new JTextField(10);
  dataPanel.add(accountNumber,BorderLayout.WEST);
  balance = new JLabel("$");
  dataPanel.add(balance,BorderLayout.EAST);
  amount = new JTextField(10);
  buttonPanel.add(deposit);
  buttonPanel.add(withdraw);
  buttonPanel.add(amount);
  recordList = new JList<>();
  recordPanel.add(recordList);
  add(dataPanel, BorderLayout.NORTH);
  add(recordPanel, BorderLayout.CENTER);
  add(buttonPanel, BorderLayout.SOUTH);
 }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bankapp().setVisible(true);
        });
    }
}
