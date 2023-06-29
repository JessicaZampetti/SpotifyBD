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
		add(pannello);
		
	}
	
	public void enteredFrame(String nome,String cognome,String email,String password) {
		JFrame Access= new AccessFrame(nome,cognome,connessione,email,password); 
		Access.setSize(800,600);
		Access.setTitle("Sezione Operativa");
		Access.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Access.setVisible(true);
	}
	
	public JButton createLoginButton() {
		JButton b = new JButton("Login");
		class buttonListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame frame=new JFrame();
				JPanel panel=new JPanel();
				JPanel panel1=new JPanel();
				JTextField t1= new JTextField(10);
				JTextField t2= new JTextField(10);
				JLabel l1=new JLabel("E-Mail");
				JLabel l2=new JLabel("Password");
			    JButton b1=new JButton("Ok");
				class okListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String e_mail=t1.getText();	
						String password=t2.getText();
						
						String sql = "SELECT * FROM account_utente WHERE email=? AND password=?";
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
									frame.setVisible(false);
									enteredFrame(rs.getString(3),rs.getString(4),e_mail,password);
								}
								rs.close();
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
				frame.setSize(300,150);
				panel.add(l1);
				panel.add(t1);
				panel1.add(l2);
				panel1.add(t2);
				JPanel p= new JPanel ();
				p.setLayout(new BorderLayout());
				p.add(panel,BorderLayout.CENTER);
				p.add(panel1,BorderLayout.SOUTH);
				frame.add(b1, BorderLayout.SOUTH);
				frame.add(p,BorderLayout.CENTER);
				frame.setVisible(true);
			}
		}
		b.addActionListener(new buttonListener());
		return b;
	}
	public JButton createRegistrationButton() {
		JButton r = new JButton("Registrazione");
		class buttonListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame registration = new JFrame();
				JTextField email=new JTextField(10);
				JTextField password=new JTextField(10);
				JTextField nome=new JTextField(10);
				JTextField cognome=new JTextField(10);
				JTextField nazionalità=new JTextField(10);
				JTextField data_nascita=new JTextField(10);
				
				JLabel email_L=new JLabel("       E-Mail");
				JLabel password_L=new JLabel("       Password");
				JLabel nome_L=new JLabel("       Nome");
				JLabel cognome_L=new JLabel("       Cognome");
				JLabel nazionalità_L=new JLabel("       Nazionalità");
				JLabel data_nascita_L=new JLabel("       Data Nascita");
				JLabel l=new JLabel("");
				JPanel p=new JPanel();
				
				JPanel panel= new JPanel();
				panel.setLayout(new GridLayout(6,2));
				
				JPanel p_N=new JPanel();
				p_N.add(nome);
				
				JPanel p_N_p=new JPanel();
				p_N_p.add(nome_L);
				panel.add(p_N_p);
				panel.add(p_N);
				
				JPanel p_C=new JPanel();
				p_C.add(cognome);
				
				JPanel p_C_p=new JPanel();
				p_C_p.add(cognome_L);
				panel.add(p_C_p);
				panel.add(p_C);
				
				JPanel p_NA=new JPanel();
				p_NA.add(nazionalità);
				
				JPanel p_NA_p=new JPanel();
				p_NA_p.add(nazionalità_L);
				panel.add(p_NA_p);
				panel.add(p_NA);
				
				JPanel p_D=new JPanel();
				p_D.add(data_nascita);
				
				JPanel p_D_p=new JPanel();
				p_D_p.add(data_nascita_L);
				panel.add(p_D_p);
				panel.add(p_D);
				
				JPanel p_E=new JPanel();
				p_E.add(email);
				
				JPanel p_E_p=new JPanel();
				p_E_p.add(email_L);
				panel.add(p_E_p);
				panel.add(p_E);
				
				JPanel p_P=new JPanel();
				p_P.add(password);
				
				JPanel p_P_p=new JPanel();
				p_P_p.add(password_L);
				panel.add(p_P_p);
				panel.add(p_P);
				
				JButton b=new JButton("Ok");
				
				class okListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String st= "INSERT INTO account_utente (email,password,nome,cognome,data_nascita,nazionalità) VALUES (?,?,?,?,?,?)";
						String n_S=nome.getText();
						String c_S=cognome.getText();
						String na_S=nazionalità.getText();
						String d_S=data_nascita.getText();
						String e_S= email.getText();
						String p_S=password.getText();
						try {
							
							PreparedStatement s= connessione.prepareStatement(st);
						
							s.setString(1, e_S);
							s.setString(2, p_S);
							s.setString(3, n_S);
							s.setString(4, c_S);
							s.setString(6, na_S);
							s.setString(5, d_S);
							s.executeUpdate();
							registration.setVisible(false);
						}catch(SQLException e) {
						
							if(e.getSQLState().equals("23000") && e.getErrorCode()==1062) {
								l.setText("E-mail o password gia esistenti nel database");
							}
							else if(e.getSQLState().equals("22001")&& e.getErrorCode()==1292) {
								l.setText("Formato della data non corretto...\nSeguire il seguente ordine: YYYY-MM-DD");
							}
						}
						
					}
	
				}
				b.addActionListener(new okListener());
				p.add(l);
				p.add(b);
				registration.add(panel,BorderLayout.CENTER);
				registration.add(p,BorderLayout.SOUTH);
				
				registration.setSize(400,650);
				registration.setTitle("Registrazione");
				registration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				registration.setVisible(true);
			}
			
		}
		r.addActionListener(new buttonListener());
		return r;
	}

	Connection connessione;
	JFrame operativo;
	JButton login;
	JButton registrazione;
	JLabel etichetta;
	JPanel pannello;

}
