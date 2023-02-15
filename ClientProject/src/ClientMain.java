
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class ClientMain extends JFrame {

	private JPanel contentPane;
	private JTextField searchTextField;

	ObjectInputStream instream;
	ObjectOutputStream outstream;

	Socket sock;

	// HashMap<String, String> map = new HashMap<>();

	JTextArea textArea = new JTextArea();
	private JTextField TitleTextfield;
	private JTextField DirectorTextfield;
	private JTextField YearTextfield;
	private JTextField KindTextfield;
	private JTextField DescriptionTextfield;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					try {
						ClientMain frame = new ClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientMain() {

		try {

			sock = new Socket("localhost", 5555);

			instream = new ObjectInputStream(sock.getInputStream());
			outstream = new ObjectOutputStream(sock.getOutputStream());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1019, 618);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		textArea.setFont(new Font("Campria" , Font.PLAIN ,  16));
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(542, 70, 442, 429);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textArea);

		searchTextField = new JTextField();
		searchTextField.setFont(new Font("Cambria", Font.PLAIN, 15));
		searchTextField.setBounds(542, 20, 312, 40);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);

		JButton SearchButton = new JButton("SEARCH");
		SearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchMovie();
			}
		});
		SearchButton.setFont(new Font("Cambria", Font.PLAIN, 14));
		SearchButton.setBounds(864, 20, 120, 40);
		contentPane.add(SearchButton);

		JButton ConnectButton = new JButton("CONNECT");

		ConnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
				ConnectButton.setEnabled(false);
			}
		});

		ConnectButton.setFont(new Font("Cambria", Font.PLAIN, 17));
		ConnectButton.setBounds(67, 435, 178, 76);
		contentPane.add(ConnectButton);

		JButton DisconnectButton = new JButton("DISCONNECT");
		DisconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disconnect();

			}
		});
		DisconnectButton.setFont(new Font("Cambria", Font.PLAIN, 17));
		DisconnectButton.setBounds(275, 435, 183, 76);
		contentPane.add(DisconnectButton);

		JLabel TitleLabel = new JLabel("Title");
		TitleLabel.setForeground(Color.WHITE);
		TitleLabel.setBackground(Color.WHITE);
		TitleLabel.setFont(new Font("Cambria", Font.PLAIN, 17));
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TitleLabel.setBounds(69, 39, 96, 27);
		contentPane.add(TitleLabel);

		JLabel DirectorLabel = new JLabel("Director");
		DirectorLabel.setForeground(Color.WHITE);
		DirectorLabel.setBackground(Color.WHITE);
		DirectorLabel.setFont(new Font("Cambria", Font.PLAIN, 17));
		DirectorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		DirectorLabel.setBounds(69, 76, 96, 27);
		contentPane.add(DirectorLabel);

		JLabel KindLabel = new JLabel("Kind");
		KindLabel.setForeground(Color.WHITE);
		KindLabel.setBackground(Color.WHITE);
		KindLabel.setFont(new Font("Cambria", Font.PLAIN, 17));
		KindLabel.setHorizontalAlignment(SwingConstants.CENTER);
		KindLabel.setBounds(69, 150, 96, 27);
		contentPane.add(KindLabel);

		JLabel YearLabel = new JLabel("Year of Release");
		YearLabel.setForeground(Color.WHITE);
		YearLabel.setBackground(Color.WHITE);
		YearLabel.setFont(new Font("Cambria", Font.PLAIN, 17));
		YearLabel.setHorizontalAlignment(SwingConstants.CENTER);
		YearLabel.setBounds(51, 113, 133, 27);
		contentPane.add(YearLabel);

		JLabel DescriptionLabel = new JLabel("Description");
		DescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		DescriptionLabel.setForeground(Color.WHITE);
		DescriptionLabel.setFont(new Font("Cambria", Font.PLAIN, 17));
		DescriptionLabel.setBackground(Color.WHITE);
		DescriptionLabel.setBounds(69, 187, 96, 27);
		contentPane.add(DescriptionLabel);

		TitleTextfield = new JTextField();
		TitleTextfield.setBounds(226, 40, 170, 19);
		contentPane.add(TitleTextfield);
		TitleTextfield.setColumns(10);

		DirectorTextfield = new JTextField();
		DirectorTextfield.setColumns(10);
		DirectorTextfield.setBounds(226, 81, 170, 19);
		contentPane.add(DirectorTextfield);

		YearTextfield = new JTextField();
		YearTextfield.setColumns(10);
		YearTextfield.setBounds(226, 120, 170, 19);
		contentPane.add(YearTextfield);

		KindTextfield = new JTextField();
		KindTextfield.setColumns(10);
		KindTextfield.setBounds(226, 155, 170, 19);
		contentPane.add(KindTextfield);

		DescriptionTextfield = new JTextField();
		DescriptionTextfield.setColumns(10);
		DescriptionTextfield.setBounds(226, 192, 170, 19);
		contentPane.add(DescriptionTextfield);

		JButton AddButton = new JButton("ADD");
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertMovie();
				
				//clear all text fields
				TitleTextfield.setText("");
				DirectorTextfield.setText("");
				YearTextfield.setText("");
				KindTextfield.setText("");
				DescriptionTextfield.setText("");
				textArea.setText("");
				searchTextField.setText("");
			}
		});
		AddButton.setFont(new Font("Cambria", Font.PLAIN, 14));
		AddButton.setBounds(169, 234, 96, 40);
		contentPane.add(AddButton);
	}

	// method to connect client with server
	public void connect() {
		try {

			// send BEGIN
			outstream.writeObject("BEGIN");
			outstream.flush();

			JOptionPane.showMessageDialog(null, instream.readObject()); // Show confirm from server

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// method to disconnect client from server
	public void disconnect() {

		try {
			// send END
			outstream.writeObject("END");
			outstream.flush();

			JOptionPane.showMessageDialog(null, new String("ENDED"));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// method to insert a movie
	public void insertMovie() {
		try {

			// send RQ_INSERT
			outstream.writeObject("RQ_INSERT");
			outstream.flush();

			Movie movie = new Movie(TitleTextfield.getText().trim(), DirectorTextfield.getText().trim(),
					Integer.parseInt(YearTextfield.getText().trim()), KindTextfield.getText().trim(),
					DescriptionTextfield.getText().trim()); // create movie object

			// send Movie object
			outstream.writeObject(movie);
			outstream.flush();

			JOptionPane.showMessageDialog(null, instream.readObject()); // print confirm

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void searchMovie() {
		try {
			
			textArea.setText("");
			
			//send "RQ_SEARCH"
			outstream.writeObject("RQ_SEARCH");
			outstream.flush();

			// send search result
			String strin ; 
			
			outstream.writeObject((String) searchTextField.getText().trim()); // send search
			outstream.flush();
			
			do {
				strin = (String) instream.readObject();
				
				if(!strin.equals("OK"))
					textArea.setText(textArea.getText() + "" + strin+"\n");
				
				
			}while(!strin.equals("OK"));

			
			strin="";
			
			
		

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
