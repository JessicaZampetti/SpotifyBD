import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccessFrame extends JFrame {

	
	public AccessFrame(String n,String co,Connection c,String em,String pass) {
		email=em;
		password=pass;
		nome=n;
		cognome=co;
		connessione=c;
		west=new JPanel();
		west.setLayout(new GridLayout(15,1));
		north=new JPanel();
		north.setLayout(new BorderLayout());
		setWestPanel();
		setNorthPanel();
		tA= new JTextArea(100,200);
		tA.setEditable(false);
		JScrollPane sc=new JScrollPane(tA);
		add(west,BorderLayout.WEST);
		add(north,BorderLayout.NORTH);
		add(sc,BorderLayout.CENTER);
	}
	
	
	public void setNorthPanel() {
		JLabel l1= new JLabel("NOME: "+nome);
		JLabel l2= new JLabel("COGNOME: "+cognome);
		
		north.add(l1,BorderLayout.CENTER);
		north.add(l2,BorderLayout.SOUTH);
	}
	
	

	
	public int maxNum(String attr, String tabella) {
		int max=0;
	
		try{
			Statement statement= connessione.createStatement();
			String s= "SELECT "+attr+" FROM "+tabella;
			ResultSet rs = statement.executeQuery(s);
			while(rs.next())
				if(rs.getInt(1)>max) max=rs.getInt(1);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return max;
	}
	
	public void setWestPanel() {
		JButton op2= new JButton("OPERAZIONE 2");
		JButton op3= new JButton("OPERAZIONE 3");
		JButton op4= new JButton("OPERAZIONE 4");
		JButton op5= new JButton("OPERAZIONE 5");
		JButton op6= new JButton("OPERAZIONE 6");
		JButton op7= new JButton("OPERAZIONE 7");
		JButton op8= new JButton("OPERAZIONE 8");
		JButton op9= new JButton("OPERAZIONE 9");
		JButton op10= new JButton("OPERAZIONE 10");
		JButton op11= new JButton("OPERAZIONE 11");
		JButton op12= new JButton("OPERAZIONE 12");
		JButton op13= new JButton("OPERAZIONE 13");
		JButton op14= new JButton("OPERAZIONE 14");
		JButton op15= new JButton("OPERAZIONE 15");
		JButton op16= new JButton("Finito");
		
		
		class op2Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {

					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
					LocalDate localDate = LocalDate.now(); 
					String data_S=dtf.format(localDate);
					
					String data_SC=dtf.format(localDate.plusDays(30));
					try {
							String abb= "INSERT INTO Abbonamento (numero_Abbonamento,tipo) VALUES(?,'Famiglia')";
							PreparedStatement abbonamento= connessione.prepareStatement(abb);
							String s= "INSERT INTO Sottoscrive (password,email,numero_Abbonamento,data_sottoscrizione,data_scadenza) VALUES(?,?,?,?,?)";
							PreparedStatement st= connessione.prepareStatement(s);
							int num_trovato= maxNum("numero_Abbonamento","abbonamento");
							abbonamento.setInt(1,num_trovato+1);
							abbonamento.executeUpdate();
							st.setInt(3, num_trovato+1);
							st.setString(2, email);
							st.setString(1,password);
							st.setString(4,data_S);
							st.setString(5,data_SC);
							st.executeUpdate();
							st.close();
							Statement stat=connessione.createStatement();
							stat.executeQuery("INSERT INTO premium (password,email) VALUES('"+password+"','"+email+"')");
							num_trovato++;
							tA.append("INSERIMENTO RIUSCITO\nE-Mail: "+email+"\nPassword: "+password+"\nNumero Abbonamento: "+num_trovato+"\nData Sottoscrizione: "+data_S+"\nData Scadenza: "+data_SC+"\n");
						}catch(SQLException e) {
							tA.append(e.getMessage());
						}
				}
			
				
				
			}
		
			
		
		
		
		setOp3Listener(op3);
		setOp4Listener(op4);
		setOp5Listener(op5);
		setOp6Listener(op6);
		setOp7Listener(op7);
		setOp8Listener(op8);
		setOp9Listener(op9);
		setOp10Listener(op10);
		setOp11Listener(op11);
		setOp12Listener(op12);
		setOp13Listener(op13);
		setOp14Listener(op14);
		setOp15Listener(op15);
		setOp16Listener(op16);
		
		
		
		op2.addActionListener(new op2Listener());
		west.add(op2);
		west.add(op3);
		west.add(op4);
		west.add(op5);
		west.add(op6);
		west.add(op7);
		west.add(op8);
		west.add(op9);
		west.add(op10);
		west.add(op11);
		west.add(op12);
		west.add(op13);
		west.add(op14);
		west.add(op15);
		west.add(op16);
	}
	
	
	public void setOp3Listener(JButton op) {
		class op3Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JTextField t1=new JTextField(10);
				JLabel l1=new JLabel("Nome");
				JPanel p1=new JPanel();
				p1.add(l1);
				p1.add(t1);
				JButton b=new JButton ("INVIA");
				JFrame f=new JFrame();
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String nome= t1.getText();
						int cod=maxNum("codice_P","Playlist")+1;
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
						LocalDate localDate = LocalDate.now(); 
						String data_C=dtf.format(localDate);
						try{
							PreparedStatement st= connessione.prepareStatement("INSERT INTO Playlist (codice_P,nome,numero_canzoni,data_creazione,password,email) VALUES(?,?,0,?,?,?) ");
							st.setInt(1, cod);
							st.setString(2, nome);
							st.setString(3, data_C);
							st.setString(4, password);
							st.setString(5, email);
							st.executeUpdate();
							tA.append("INSERIMENTO RIUSCITO:\nCodice Playlist= "+cod+"\nNome= "+nome+"\nNumero canzoni= 0\nData Creazione= "+data_C+"\nE-Mail= "+email+"\nPassword= "+password);
						}catch(SQLException e) {
							tA.append(e.getMessage());
						}
						f.setVisible(false);
					}
				}
				b.addActionListener(new buttonListener());
	
				f.add(b,BorderLayout.SOUTH);
				f.add(p1,BorderLayout.CENTER);
				f.setSize(250,150);
				f.setVisible(true);
				
			}
		}
		op.addActionListener(new op3Listener());
	}
	
	
	public void setOp4Listener(JButton op4) {
		class op4Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame f= new JFrame();
				JTextField tF1= new JTextField(10);
				JTextField tF2= new JTextField(10);
				JTextField tf3=new JTextField(10);
				JLabel l1=new JLabel("Nome Canzone");
				JLabel l2=new JLabel("Durata");
				JLabel l3= new JLabel("Nome Playlist");
				
				JPanel p1=new JPanel();
				JPanel p2= new JPanel();
				JPanel p3=new JPanel();
				JPanel p4= new JPanel();
				JPanel p5 = new JPanel();
				JPanel p6=new JPanel();
				JPanel p=new JPanel();
				p.setLayout(new GridLayout(3,3));
				p1.add(l3);
				p2.add(tf3);
				p3.add(l1);
				p4.add(tF1);
				p5.add(l2);
				p6.add(tF2);
				p.add(p1);
				p.add(p2);
				p.add(p3);
				p.add(p4);
				p.add(p5);
				p.add(p6);
				
				f.add(p,BorderLayout.CENTER);
				
				JButton b= new JButton("INVIA");
				
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String nome_P= tf3.getText();
						String nome = tF1.getText();
						String durata= tF2.getText();
						int codice_album=0;
						int codice_playlist=0;
						try {
							PreparedStatement st= connessione.prepareStatement("SELECT codice FROM Canzone WHERE nome=? AND durata= ?");
							st.setString(1, nome);
							st.setString(2, durata);
							ResultSet rs= st.executeQuery();
							
							if(rs.next()==false) {
								tA.append("Canzone non presente nell'Album");
								throw new RuntimeException();
							}else {
								codice_album=rs.getInt(1);
							}
							
					
							Statement st1= connessione.createStatement();
							ResultSet rs1= st1.executeQuery("SELECT codice_P FROM Playlist WHERE nome= '"+nome_P+"' AND email='"+email+"' AND password='"+password+"'");
	
						
							if(rs1.next()==false) {
								tA.append("Playlist non presente nella tua raccolta...Prova con un altro nome\n");
								throw new RuntimeException();
							}
							else {
								codice_playlist=rs1.getInt(1);
							}
							
							PreparedStatement ps= connessione.prepareStatement("INSERT INTO Ha (codice_p,nome,durata,codice) VALUES(?,?,?,?)");
							ps.setInt(1, codice_playlist);
							ps.setString(2,nome);
							ps.setString(3, durata);
							ps.setInt(4,codice_album);
							ps.executeUpdate();
							tA.append("CANZONE INSERITA CON SUCCESSO");
							ps=connessione.prepareStatement("UPDATE Playlist SET numero_canzoni=numero_canzoni+1 WHERE codice_P=?");
							ps.setInt(1, codice_playlist);
							ps.executeUpdate();
						}catch(SQLException e) {
							tA.append(e.getMessage());
						}catch(RuntimeException e) {
							
						}
						f.setVisible(false);
					}
				}
				
				b.addActionListener(new buttonListener());
				f.add(b,BorderLayout.SOUTH);
				
				f.setSize(200,200);
				f.setVisible(true);
				
			}
		}
		op4.addActionListener(new op4Listener());
	}
	
	
	
	public void setOp5Listener(JButton op5) {

		class op5Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				p.setLayout(new GridLayout(4,2));
				JPanel pn=new JPanel();
				pn.setLayout(new BorderLayout());
			
				JLabel l1= new JLabel("Nome Canzone");
				JLabel l2= new JLabel("Durata Canzone");
				JLabel l3 = new JLabel("Genere");
				JLabel l4= new JLabel("Nome Album");
				JRadioButton r1= new JRadioButton("Si");
				JRadioButton r2= new JRadioButton("No");
				
				
				class r1Listener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						NSFW=true;
					}
				}
				
				class r2Listener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						NSFW=false;
					}
				}
				
				r1.addActionListener(new r1Listener());
				r2.addActionListener(new r2Listener());
				JTextField t1= new JTextField(10);
				JTextField t2= new JTextField(10);
				JTextField t3= new JTextField(10);
				JTextField t4= new JTextField(10);
				
				JPanel p1= new JPanel();
				p1.add(l1);
				JPanel p2= new JPanel();
				p2.add(t1);
				
				JPanel p3= new JPanel();
				p3.add(l2);
				JPanel p4=new JPanel();
				p4.add(t2);
				
				JPanel p5=new JPanel();
				p5.add(l3);
				
				JPanel p6=new JPanel();
				p6.add(t3);
				
				JPanel p7=new JPanel();
				p7.add(l4);
				
				JPanel p8=new JPanel();
				p8.add(t4);
				
				JPanel pR=new JPanel();
				
				bG.add(r1);
				bG.add(r2);
				pR.setBorder(new TitledBorder(new EtchedBorder(),"NSFW"));
				pR.add(r1);
				pR.add(r2);
				JButton b= new JButton("INVIA");
				
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String nome_c=t1.getText();
						String durata=t2.getText();
						String genere=t3.getText();
						String nome_album=t4.getText();
						String bool;
						if(NSFW==true) {
							bool="True";
						}else bool="False";
						int codice_album=0;
			
						try {
							Statement st= connessione.createStatement();
							ResultSet rS=st.executeQuery("SELECT codice FROM Album WHERE nome='"+nome_album+"'");
							if(rS.next()==false) {
								tA.append("Nome dell album non corretto...riprovare");
								return;
							}
							codice_album= rS.getInt(1);
							st.close();
							Statement st1=connessione.createStatement();
							st1.executeUpdate("INSERT INTO Canzone (codice,durata,genere,nome,NSFW) VALUES("+codice_album+",'"+durata+"','"+genere+"','"+nome_c+"',"+bool+")");
							tA.append("INSERIMENTO RIUSCITO:\nNome Canzone="+nome_c+"\nDurata="+durata+"\nGenere="+genere+"\nCodice="+codice_album+"\nNSFW="+bool);
						}catch(SQLException e) {
							tA.append(e.getMessage()+"\n");
						}
						f.setVisible(false);
					}
				}
				b.addActionListener(new buttonListener());
				p.add(p1);
				p.add(p2);
				p.add(p3);
				p.add(p4);
				p.add(p5);
				p.add(p6);
				p.add(p7);
				p.add(p8);
				pn.add(p,BorderLayout.CENTER);
				pn.add(pR,BorderLayout.SOUTH);
				f.add(pn,BorderLayout.CENTER);
				f.add(b,BorderLayout.SOUTH);
				f.setTitle("Operazione 5");
				f.setSize(400,400);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
				
				
			}
		}
		op5.addActionListener(new op5Listener());
	}
	
	public void setOp6Listener(JButton op6) {
		class op6Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame f= new JFrame();
				JLabel nome = new JLabel("Nome Artista");
				JLabel cognome=new JLabel("Cognome Artista");
				JLabel email= new JLabel("E-Mail");
				JLabel pseudonimo =new JLabel("Pseudonimo");
				JTextField nome_T=new JTextField(10);
				JTextField cognome_T=new JTextField(10);
				JTextField email_T=new JTextField(10);
				JTextField pseudonimo_T=new JTextField(10);
				JPanel pannello= new JPanel();
				pannello.setLayout(new GridLayout(4,2));
				
				
				JPanel p1= new JPanel();
				p1.add(nome);
				JPanel p2= new JPanel();
				p2.add(nome_T);
				JPanel p3= new JPanel();
				p3.add(cognome);
				JPanel p4= new JPanel();
				p4.add(cognome_T);
				JPanel p5= new JPanel();
				p5.add(email);
				JPanel p6= new JPanel();
				p6.add(email_T);
				
				JPanel p7=new JPanel();
				p7.add(pseudonimo);
				JPanel p8=new JPanel();
				p8.add(pseudonimo_T);
				pannello.add(p1);
				pannello.add(p2);
				pannello.add(p3);
				pannello.add(p4);
				pannello.add(p5);
				pannello.add(p6);
				pannello.add(p7);
				pannello.add(p8);
				
				JButton b= new JButton("INVIA");
				
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String name= nome_T.getText();
						String surname=cognome_T.getText();
						String em=email_T.getText();
						String p=pseudonimo_T.getText();
						
						try {
							PreparedStatement pS= connessione.prepareStatement("INSERT INTO Artista1 (nome,cognome,pseudonimo) VALUES(?,?,?)");
							pS.setString(1, name);
							pS.setString(2, surname);
							pS.setString(3, p);
							pS.executeUpdate();
							pS=connessione.prepareStatement("INSERT INTO Artista (codice_Artista,cognome,email,nome) VALUES(?,?,?,?)");
							int cod=maxNum("codice_Artista","Artista")+1;
							pS.setInt(1,cod);
							pS.setString(2, surname);
							pS.setString(3, em);
							pS.setString(4,name);
							
							pS.executeUpdate();
							tA.append("INSERIMENTO RIUSCITO:\nNome Artista= "+name+"\nCognome Artista= "+surname+"\nE-Mail= "+em+"\nPseudonimo= "+p+"\nCodice Artista= "+cod+"\n");
						}catch (SQLException e) {
							tA.append(e.getMessage());
						}
						f.setVisible(false);
					}
				}
				
				b.addActionListener(new buttonListener());
				f.add(pannello,BorderLayout.CENTER);
				f.add(b,BorderLayout.SOUTH);
				f.setSize(400,400);
				f.setTitle("Operazione 6");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
 			}
		}
		op6.addActionListener(new op6Listener());
	}
	
	public void setOp7Listener(JButton op7) {
		class op7Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				tA.append("Di seguito, verranno stampati tutti i dati relativi alle Playlist\n");
				try {
					Statement st=connessione.createStatement();
					ResultSet rs=st.executeQuery("SELECT * FROM Playlist");
					
				if(rs.next()==false) {
					tA.append("Non sono ancora state memorizzate delle playlist\n");
					return;
				}else do {
					tA.append("Codice Playlist="+rs.getInt(1)+" Nome Playlist="+rs.getString(2)+" Numero Canzoni="+rs.getInt(3)+" Data Creazione="+rs.getDate(4)+" E-Mail Utente="+rs.getString(6)+" Password Utente="+rs.getString(5)+"\n");
				}while(rs.next());
					
				}catch(SQLException e) {
					tA.append(e.getMessage());
				}
			}
		}
		op7.addActionListener(new op7Listener());
	}
	
	
	public void setOp8Listener(JButton op8) {
		class op8Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame f=new JFrame();
				JPanel p=new JPanel();
				p.setLayout(new GridLayout(3,2));
				
				JLabel nome= new JLabel("Nome Etichetta");
				JLabel contatto= new JLabel ("Contatto");
				JLabel tipoE=new JLabel("Tipo Etichetta");
				
				JTextField nome_T=new JTextField(10);
				JTextField contatto_T=new JTextField(10);
				JTextField tipoE_T=new JTextField(10);
				
				JPanel p1= new JPanel();
				p1.add(nome);
				JPanel p2= new JPanel();
				p2.add(nome_T);
				JPanel p3= new JPanel();
				p3.add(contatto);
				JPanel p4= new JPanel();
				p4.add(contatto_T);
				JPanel p5= new JPanel();
				p5.add(tipoE);
				JPanel p6= new JPanel();
				p6.add(tipoE_T);
				
				p.add(p1);
				p.add(p2);
				p.add(p3);
				p.add(p4);
				p.add(p5);
				p.add(p6);
				
				JButton b=new JButton ("INVIA");
				
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String n= nome_T.getText();
						String c= contatto_T.getText();
						String tipo= tipoE_T.getText();
						
						try {
							PreparedStatement pS= connessione.prepareStatement("INSERT INTO etichetta_discografica (nome,contatto,tipo) VALUES(?,?,?)");
							pS.setString(1, n);
							pS.setString(2, c);
							pS.setString(3, tipo);
							pS.executeUpdate();
							tA.append("INSERIMENTO RIUSCITO:\nNome Etichetta="+n+"\nContatto="+c+"\nTipo Etichetta="+tipo+"\n");
						}catch(SQLException e) {
							tA.append(e.getMessage()+"\n");
						}
						f.setVisible(false);
					}
				}
				b.addActionListener(new buttonListener());
				f.add(p,BorderLayout.CENTER);
				f.add(b,BorderLayout.SOUTH);
				f.setTitle("Operazione 8");
				f.setSize(400,400);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
		}
		op8.addActionListener(new op8Listener());
	}
	
	
	public void setOp9Listener(JButton op9){
		class op9Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				try {
					Statement st=connessione.createStatement();
					ResultSet rs=st.executeQuery("SELECT * FROM premium WHERE password='"+password+"' AND email='"+email+"'");
					if(rs.next()==false) {
						tA.append("c'è bisogno di un abbonamento per effettuare la seguente operazione\n");
						return;
					}
				}catch(SQLException e) {
					
				}
				JFrame f= new JFrame();
				JPanel p=new JPanel();
				p.setLayout(new GridLayout(3,2));
				JLabel nome_C= new JLabel("Nome Canzone");
				JLabel durata_C=new JLabel("Durata");
				JLabel nome_A=new JLabel("Nome Album");
				
				JTextField nome_C_T=new JTextField(10);
				JTextField durata_C_T=new JTextField(10);
				JTextField nome_A_T=new JTextField(10);
				
				JPanel p1=new JPanel();
				p1.add(nome_C);
				JPanel p2=new JPanel();
				p2.add(nome_C_T);
				JPanel p3=new JPanel();
				p3.add(durata_C);
				JPanel p4=new JPanel();
				p4.add(durata_C_T);
				JPanel p5=new JPanel();
				p5.add(nome_A);
				JPanel p6=new JPanel();
				p6.add(nome_A_T);
				
				p.add(p1);
				p.add(p2);
				p.add(p3);
				p.add(p4);
				p.add(p5);
				p.add(p6);
				
				JButton b=new JButton("INVIA");
				
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String nome=nome_C_T.getText();
						String durata=durata_C_T.getText();
						String nome_album=nome_A_T.getText();
						int codice_album=0;
						boolean verification=false;
						try {
							Statement pS=connessione.createStatement();
							ResultSet rS=pS.executeQuery("SELECT codice FROM Album WHERE nome='"+nome_album+"'");
							if(rS.next()==false) {
								tA.append("Nome Album non esistente... riprovare\n");
								return;
							}else do {
								codice_album=rS.getInt(1);
								Statement t=connessione.createStatement();
								ResultSet r=t.executeQuery("SELECT * FROM Canzone WHERE nome='"+nome+"' AND durata='"+durata+"' AND codice="+codice_album);
								if(r.next()) {
									verification=true;
									break;
								}
							}while(rS.next());
							
							if(verification==false) {
								tA.append("Canzone inserita non esistente nel DataBase\n");
								return;
							}
							
							PreparedStatement pS1=connessione.prepareStatement("INSERT INTO Scarica_C (password,email,durata,nome,codice) VALUES(?,?,?,?,?)");
							pS1.setString(1,password);
							pS1.setString(2, email);
							pS1.setString(3, durata);
							pS1.setString(4, nome);
							pS1.setInt(5,codice_album);
							pS1.executeUpdate();
							tA.append("INSERIMENTO RIUSCITO:\nNome Canzone="+nome+"\nDurata="+durata+"\nCodice Album="+codice_album+"\n");
						}catch(SQLException e) {
							tA.append(e.getMessage());
						}
						
						f.setVisible(false);
					}
				}
				
				b.addActionListener(new buttonListener());
				f.add(p,BorderLayout.CENTER);
				f.add(b,BorderLayout.SOUTH);
				f.setTitle("Operazione 9");
				f.setSize(400,400);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
		}
		op9.addActionListener(new op9Listener());
	}
	
	
	public void setOp10Listener(JButton op10) {
		class op10Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame f= new JFrame();
				JPanel p=new JPanel();
				p.setLayout(new GridLayout(3,2));
				JLabel nome= new JLabel("Nome Album");
				JLabel tipo=new JLabel("Tipo");
				JLabel data_p=new JLabel("Data Pubblicazione");
				
				JTextField nome_T=new JTextField(10);
				JTextField tipo_T=new JTextField(10);
				JTextField data_p_T=new JTextField(10);
				
				JPanel p1=new JPanel();
				p1.add(nome);
				JPanel p2=new JPanel();
				p2.add(nome_T);
				JPanel p3=new JPanel();
				p3.add(tipo);
				JPanel p4=new JPanel();
				p4.add(tipo_T);
				JPanel p5=new JPanel();
				p5.add(data_p);
				JPanel p6=new JPanel();
				p6.add(data_p_T);
				
				p.add(p1);
				p.add(p2);
				p.add(p3);
				p.add(p4);
				p.add(p5);
				p.add(p6);
				
				JButton b=new JButton("INVIA");
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String n=nome_T.getText();
						String t=tipo_T.getText();
						String d= data_p_T.getText();
						int codice= maxNum("codice","album")+1;
						try {
							PreparedStatement st= connessione.prepareStatement("INSERT INTO Album (codice,data_pubblicazione,nome,tipo) VALUES(?,?,?,?)");
							st.setInt(1, codice);
							st.setString(2, d);
							st.setString(3, n);
							st.setString(4, t);
							st.executeUpdate();
							tA.append("INSERIMENTO RIUSCITO:\nCodice Album="+codice+"\nData pubblicazione="+d+"\nNome="+n+"\nTipo Album="+t+"\n");
						}catch(SQLException e) {
							tA.append(e.getMessage());
						}
						f.setVisible(false);
					}
				}
				b.addActionListener(new buttonListener());
				f.add(p,BorderLayout.CENTER);
				f.add(b,BorderLayout.SOUTH);
				f.setSize(400,400);
				f.setTitle("Operazione 10");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
			
		}
		
		op10.addActionListener(new op10Listener());
	}
	
	public void setOp11Listener(JButton op11) {
		class op11Listener implements ActionListener{
			public void actionPerformed (ActionEvent event) {
				JTextField t1=new JTextField(10);
				JLabel l1=new JLabel("Codice");
				JPanel p1=new JPanel();
				p1.add(l1);
				p1.add(t1);
				JButton b=new JButton ("INVIA");
				JFrame f=new JFrame();
				
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						int codice=Integer.parseInt(t1.getText());
						
						try {
							Statement st=connessione.createStatement();
							ResultSet rs=st.executeQuery("SELECT indirizzo_ip FROM Dispositivo WHERE codice="+codice);
							if(rs.next()==false) {
								tA.append("Non esiste alcun dispositivo associato al codice inserito...riprovare\n");
								return;
							}
							String ind_ip=rs.getString(1);
							tA.append("DISPOSITIVO TROVATO:\nIndirizzo_ip="+ind_ip+"\n");
						}catch(SQLException e) {
							tA.append(e.getMessage());
						}
						f.setVisible(false);
					}
					
				}
				
				b.addActionListener(new buttonListener());
				f.add(p1,BorderLayout.CENTER);
				f.add(b,BorderLayout.SOUTH);
				f.setTitle("Operazione 11");
				f.setSize(150,150);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			
			}
		}
		op11.addActionListener(new op11Listener());
	}
	
	
	public void setOp12Listener(JButton op12) {
		class op12Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				try {
					Statement st=connessione.createStatement();
					ResultSet rs=st.executeQuery("SELECT COUNT(*) FROM Canzone WHERE NSFW=True");
					rs.next();
					int contatore = rs.getInt(1);
					tA.append("Numero di canzoni con contenuto esplicito="+contatore+"\n");
				}catch(SQLException e) {
					tA.append(e.getMessage());
				}
			}
		}
		op12.addActionListener(new op12Listener());
	}
	
	public void setOp13Listener(JButton op13) {
		class op13Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JTextField t1=new JTextField(10);
				JLabel l1=new JLabel("Nome Album");
				JPanel p1=new JPanel();
				p1.add(l1);
				p1.add(t1);
				JButton b=new JButton ("INVIA");
				JFrame f=new JFrame();
				
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String n_album=t1.getText();
						
						try{
							Statement verifica=connessione.createStatement();
							ResultSet rsV=verifica.executeQuery("SELECT * FROM Premium WHERE email='"+email+"' AND password='"+password+"'");
							if(rsV.next()==false) {
								tA.append("C'è bisogno di un abbonamento per effettuare questa operazione\n");
								return;
							}
							
							Statement st =connessione.createStatement();
							ResultSet rs=st.executeQuery("SELECT codice FROM Album WHERE nome='"+n_album+"'");
							
							if(rs.next()==false) {
								tA.append("Non esistono album con tale nome...riprovare\n");
								return;
							}
							
							int codice=rs.getInt(1);
							
							st.executeUpdate("INSERT INTO Scarica_A (codice,password,email) VALUES("+codice+",'"+password+"','"+email+"')");
							tA.append("INSERIMENTO RIUSCITO:\nCodice Album="+codice+"\nPassword="+password+"\nEmail="+email+"\n");
						}catch(SQLException e) {
							tA.append(e.getMessage());
						}
						f.setVisible(false);
					}
				}
				b.addActionListener(new buttonListener());
				f.add(p1,BorderLayout.CENTER);
				f.add(b,BorderLayout.SOUTH);
				f.setTitle("Operazione 13");
				f.setSize(150,150);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
				
			}
		}
		op13.addActionListener(new op13Listener());
	}
	
	
	public void setOp14Listener(JButton op14) {
		class op14Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				JFrame f= new JFrame();
				JPanel p=new JPanel();
				p.setLayout(new GridLayout(2,2));
				JLabel nazionalita= new JLabel("Nazionalità Utente");
				JLabel genere=new JLabel("Genere");
		
				
				JTextField nazionalita_T=new JTextField(10);
				JTextField genere_T=new JTextField(10);
				
				
				JPanel p1=new JPanel();
				p1.add(nazionalita);
				JPanel p2=new JPanel();
				p2.add(nazionalita_T);
				JPanel p3=new JPanel();
				p3.add(genere);
				JPanel p4=new JPanel();
				p4.add(genere_T);
				
				
				p.add(p1);
				p.add(p2);
				p.add(p3);
				p.add(p4);
				
				JButton b=new JButton("INVIA");
				
				class buttonListener implements ActionListener{
					public void actionPerformed(ActionEvent event) {
						String naz=nazionalita_T.getText();
						String gen=genere_T.getText();
						
						try{
							PreparedStatement pS = connessione.prepareStatement("SELECT A.nome,A.cognome "
								+ "FROM Account_Utente AS A "
								+ "WHERE A.nazionalità=? AND "
								+ "        NOT EXISTS(SELECT * "
								+ "						FROM ASCOLTA AS k,Canzone AS C "
								+ "						WHERE k.email=A.email AND  "
								+ "k.password=A.password AND "
								+ "	k.durata=C.durata AND "
								+ "k.nome=C.nome AND "
								+ "k.codice=C.codice AND "
								+ "C.genere=?)");
							
							pS.setString(1,naz);
							pS.setString(2, gen);
							
							ResultSet rs=pS.executeQuery();
							
							if(rs.next()==false) {
								tA.append("Non esistono utenti con tale nazionalità\noppure non esiste alcun utente con tale nazionalita che non ascolta il genere inserito\n");
								return;
							}else do {
								tA.append("Nome Utente="+rs.getString(1)+"\nCognome Utente="+rs.getString(2)+"\n");
							}while(rs.next());
						}catch(SQLException e) {
							tA.append(e.getMessage());
						}
						finally{
							f.setVisible(false);
						}
					}
				}
				b.addActionListener(new buttonListener());
				f.add(p,BorderLayout.CENTER);
				f.add(b,BorderLayout.SOUTH);
				f.setTitle("Operazione 14");
				f.setSize(200,150);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
		}
		op14.addActionListener(new op14Listener());
	}
	
	public void setOp15Listener(JButton op15) {
		class op15Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				try {
					PreparedStatement ps=connessione.prepareStatement("SELECT P.nome "
							+ "FROM Playlist AS P "
							+ "WHERE NOT EXISTS (SELECT * "
							+ "        FROM Canzone AS C, Ha AS H "
							+ "                                      WHERE C.durata=H.durata AND "
							+ "   C.nome=H.nome AND "
							+ "   C.codice=H.codice AND "
							+ "   P.codice_P=H.codice_P AND "
							+ "   C.NSFW=True)");
					
					ResultSet rs=ps.executeQuery();
					
					if(rs.next()==false) {
						tA.append("Non esiste playlist senza canzoni esplicite\n");
						return;
					}else do {
						tA.append("Nome Playlist="+rs.getString(1)+"\n");
					}while(rs.next());
				}catch(SQLException e) {
					tA.append(e.getMessage());
				}
			}
		}
		op15.addActionListener(new op15Listener());
	}
	
	
	public void setOp16Listener(JButton op16) {
		class op16Listener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
			}
		}
		op16.addActionListener(new op16Listener());
	}
	
	private boolean NSFW;
	private ButtonGroup bG=new ButtonGroup();
	private String email;
	private String password;
	private Connection connessione;
	private JTextArea tA;
	private String nome;
	private String cognome;
	private JPanel west;
	private JPanel center;
	private JPanel north;
}
