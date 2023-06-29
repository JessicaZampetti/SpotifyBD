import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FrameGen extends JFrame{

	public FrameGen() {
		Connection con=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url= "jdbc:mysql://localhost:3306/piattaformamusicale";
			con= DriverManager.getConnection(url,"root", "D00modossol@?");
			System.out.println("Connessione riuscita");
		}catch(ClassNotFoundException e) {
			System.out.println("1");
		}catch(Exception e) {
			System.out.println("2");
		}
		if(con==null) {
			System.out.println("Null");
		}
		connessione = con;
		login=createLoginButton();
		registrazione=createRegistrationButton();
		etichetta= new JLabel("Piattaforma Musicale");
		etichetta.setFont(new Font("Cooper Black", Font.PLAIN, 16));
		etichetta.setBounds(70,10,185,100);
		pannello=new JPanel();
		pannello.setLayout(null);
		login.setBounds(90,125,125,40);
		registrazione.setBounds(90,200,125,40);
		pannello.add(etichetta);
		pannello.add(login);
		pannello.add(registrazione);
		/*pannello.setLayout(new BorderLayout());
		pannello.add(etichetta,BorderLayout.NORTH);
		pannello.add(login,BorderLayout.EAST);
		pannello.add(registrazione,BorderLayout.WEST);*/
		add(pannello);
		
	}
	
	public JButton createLoginButton() {
		JButton b = new JButton("Login");
		class buttonListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame frame=new JFrame();
				JPanel panel=new JPanel();
				JTextField t1= new JTextField(10);
				JTextField t2= new JTextField(10);
				JLabel l1=new JLabel("E-Mail");
				JLabel l2=new JLabel("Password");
			    JButton b1=new JButton("Ok");
				class okListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String e_mail=t1.getText();	
						String password=t2.getText();
						try{Statement st1 = connessione.createStatement();
						int risultato= st1.executeUpdate("INSERT INTO account_utente (email,password,nome,cognome,data_nascita,nazionalità) VALUES ('bd@gmail','Ciao','pinco','pallo','2022-01-13','IT')");
						System.out.println(risultato);
						if(risultato>0) {
							System.out.println(risultato);
						}else System.out.println("Errore");
						}catch(SQLException exc) {
							System.out.println("Errore inserimento");
							while(exc!=null) {
								System.out.println("Messaggio: "+exc.getMessage());
								System.out.println("SQlState: "+exc.getSQLState());
								System.out.println("Error code: "+exc.getErrorCode());
								exc=exc.getNextException();
							}
						}
						/*try{Statement st2 = connessione.createStatement();
						String usql = "DELETE FROM account_utente WHERE email='bd@gmail'";
						int ris= st2.executeUpdate(usql);
						if(ris>0) {
							System.out.println(ris);
						}else System.out.println("Errore");
						}catch(SQLException ex) {
							System.out.println("Errore inserimento");
							while(ex!=null) {
								System.out.println("Messaggio: "+ex.getMessage());
								System.out.println("SQlState: "+ex.getSQLState());
								System.out.println("Error code: "+ex.getErrorCode());
								ex=ex.getNextException();
							}
						}*/
						String sql = "SELECT email,password FROM account_utente WHERE email=? AND password=?";
						try{
							PreparedStatement st=connessione.prepareStatement(sql);
							st.setString(1, e_mail);
							st.setString(2, password);
							ResultSet rs=st.executeQuery();
							if(rs.next()==false){
								t1.setText("");
								t2.setText("");
							} else {
								System.out.println(rs.getString(1));
								System.out.println(rs.getString(2));
								if(rs.getString(1).equals(e_mail) && rs.getString(2).equals(password)) {
									//enteredFrame();
								}
							}
						} catch(SQLException e) {
							System.out.println("SQL exc");
							while(e!=null) {
								System.out.println("Messaggio: "+e.getMessage());
								System.out.println("SQlState: "+e.getSQLState());
								System.out.println("Error code: "+e.getErrorCode());
								e=e.getNextException();
							}
						}
					}
				}
				b1.addActionListener(new okListener());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Accesso");
				frame.setSize(300,400);
				panel.add(l1);
				panel.add(t1);
				panel.add(l2);
				panel.add(t2);
				frame.add(b1, BorderLayout.SOUTH);
				frame.add(panel);
				frame.setVisible(true);
			}
		}
		b.addActionListener(new buttonListener());
		return b;
	}
	public JButton createRegistrationButton() {
		JButton r = new JButton("Registrazione");
		return r;
	}

	Connection connessione;
	JFrame operativo;
	JButton login;
	JButton registrazione;
	JLabel etichetta;
	JPanel pannello;

}
