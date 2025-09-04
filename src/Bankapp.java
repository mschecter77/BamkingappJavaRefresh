import javax.swing.*;
import java.awt.*;

public class Bankapp extends JFrame{
 account bankaccount = new account();
 private JTextField accountNumber;
 private JTextField amount;
 private JLabel balance;
 private JButton deposit;
 private  JButton withdraw;
 private DefaultListModel<String> recordModel;
 private JList<String> recordList;
 public Bankapp(){
  setTitle("Bank Account");
  setSize(400, 300);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setLocationRelativeTo(null);
  JPanel dataPanel = new JPanel();
  dataPanel.setLayout(new GridLayout(2,2));

  JPanel buttonPanel = new JPanel();
  JPanel recordPanel = new JPanel();
  withdraw = new JButton("Withdraw");
  deposit = new JButton("Deposit");
  accountNumber = new JTextField(10);
  dataPanel.add (new JLabel ("Account#"));
  dataPanel.add(accountNumber);
  dataPanel.add (new JLabel ("Balance $" ));
  balance = new JLabel();
  dataPanel.add(balance);



  buttonPanel.add(deposit);
  buttonPanel.add(withdraw);
  buttonPanel.add (new JLabel ("Amount $" ));
  amount = new JTextField(10);
  buttonPanel.add(amount);
  recordModel = new DefaultListModel<>();
  recordList = new JList<>(recordModel);
  add(dataPanel,BorderLayout.NORTH);
  add(recordPanel,BorderLayout.CENTER);
  add(buttonPanel,BorderLayout.SOUTH);
  recordPanel.setLayout(new BorderLayout());
  recordPanel.add(new JScrollPane(recordList), BorderLayout.CENTER);
 }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bankapp().setVisible(true);
        });
    }
}
