package Leisure;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

public class Login extends JDialog{
	// Declare Components of our JDialog...
	
	// It has a JPanel with two JLabels...
	private JPanel jpOuterPanel;
	private JPanel jpContainer;
	private JPanel jpLabels;
	private JLabel jlblUserName, jlblPassword;

	// ...a JPanel with a JTextField and  JPasswordField...
	private JPanel jpTextFields;	
   private RoundedJTextField jtfUsername;
   private RoundedJPasswordField jtfPassword;

	// ...and a JPanel with two JButtons
	private JPanel jpButtons;	
   private JButton jbtLogin, jbtCancel;

   private String userNameEntered, passwordEntered;
   private final String requiredPassword = "LetMeIn";

   public Login(){
	   // Constructor - SetLayout & Add Components here...	
	   
	   jpOuterPanel = new JPanel(new GridLayout(2,1,25,25));
	   jpOuterPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
      // Panel 1 - Two JLabels
/*  		jpLabels = new JPanel(new GridLayout(2, 1));
    	jpLabels.add(jlblUserName=new JLabel("Username"));
		jpLabels.add(jlblPassword=new JLabel("Password"));*/
	   jpOuterPanel.setPreferredSize(new Dimension(300, 300));

	   // Panel 2 - A JTextField and a JPasswordField
      jpTextFields = new JPanel(new GridLayout(2, 1, 50, 10));
      jpTextFields.add(jtfUsername = new RoundedJTextField("Username", 15));
      jpTextFields.add(jtfPassword = new RoundedJPasswordField("Password", 15));
      jtfPassword.setEchoChar((char)0);
      /*pass.setText("Password");*/
		/*jtfPassword.setEchoChar('*');*/

	   // Panel32 - Three JButtons
      jpButtons = new JPanel();
      jpButtons.add(jbtLogin = new JButton("Login"));
/*      jpButtons.add(jbtCancel = new JButton("Cancel"));*/

		// Set up the JFrame
      jpOuterPanel.add(jpTextFields, BorderLayout.CENTER);
      jpOuterPanel.add(jpButtons, BorderLayout.SOUTH);
      getContentPane().requestFocusInWindow();
      /*add(jpLabels, BorderLayout.WEST);
      add(jpTextFields, BorderLayout.CENTER);
      add(jpButtons, BorderLayout.SOUTH);
*/
      add(jpOuterPanel);
		// Add an ANONYMOUS LISTENER CLASS to jbtOK
      jbtLogin.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
				// getText() in jtfUsername and jtfPassword
        	
        	 
        	 //StaffDbHandler staffDBHandler = new StaffDbHandler();
            userNameEntered = jtfUsername.getText();
            passwordEntered = new String(jtfPassword.getPassword());
				
            
            
            //if(staffDBHandler.loginCheck(userNameEntered, passwordEntered)){
					// CONSTRUCT a PizzaMenu object called frame, and display it
				/*	leisureCentreProject lpProject = new leisureCentreProject();
					lpProject.setVisible(true);
					dispose();
//
				//}*/
				//else{
					//JOptionPane.showMessageDialog(null,"INCORRECT CREDENTIALS ENTERED",
		//													"PASSWORD ERROR",JOptionPane.ERROR_MESSAGE);
				//}
			//}
		//});	
      
      jtfPassword.addFocusListener(new FocusListener() {

          public void focusGained(FocusEvent e) {
        	  jtfPassword.setEchoChar('*');
              if (jtfPassword.getText().equals("Password")) {
            	  jtfPassword.setText("");
              }
          }

          public void focusLost(FocusEvent e) {
              if ("".equalsIgnoreCase(jtfPassword.getText().trim())) {
            	  jtfPassword.setEchoChar((char)0);
            	  jtfPassword.setText("Password");
              }
          }});
      
      jtfUsername.addFocusListener(new FocusListener() {

          public void focusGained(FocusEvent e) {
              if (jtfUsername.getText().equals("Username")) {
            	  jtfUsername.setText("");
              }
          }

          public void focusLost(FocusEvent e) {
              if ("".equalsIgnoreCase(jtfUsername.getText().trim())) {
            	  jtfUsername.setText("Username");
              }
          }});
      	
		// Add an ANONYMOUS LISTENER CLASS to jbtCancel
/*      jbtCancel.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
				System.out.println("CANCEL CLICKED!");				
			}*/
	//	});				
   
      //}
         }});}
   public static void main(String[] args){
		// CONSTRUCT a SecurityDialog object called dialog
      Login dialog = new Login();

		dialog.setTitle("Leisure Centre Login");
		dialog.pack();	
		// Add the DISPOSE_ON_CLOSE operation to dialog
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.requestFocusInWindow();
  }
}