package stockApp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AnyClient implements Observer {

	private JFrame frame;
	private JTextField inputSymbol;
	private JTextField inputStatus;
	private JTextPane txtPaneDate;
	private TextArea txtArea;

	//Start the application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnyClient window = new AnyClient();
					window.frame.setVisible(true);
					UIManager.setLookAndFeel(
				            UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Initialize the UI
	public AnyClient() {
		start();
	}

	private void start() {
		//Get instance of Stock Broker and Stock Monitor
		//Stock Broker must be notified of any stock creation
		StockBroker.getInstance();
		//Stock Monitor is notified of both stock creation AND updates
		StockMonitor.getInstance().addObserver(this);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Forgot this originally, caused OOME!!
		frame.setBounds(200, 200, 600, 300);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setTitle("Stock Monitor");
		
		JButton btnAdd = new JButton("Add/Update");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Stock stockExists = StockBroker.getInstance().getStock(inputSymbol.getText());
				//For now application assumes any additions or updates from UI are in dollars
				//Future upgrade - handle other forms of currency
				if(stockExists == null)
				{
					new Stock(inputSymbol.getText(), new StockStatus(new Date(), new Money("USD", Double.parseDouble(inputStatus.getText()))));
				} 
				else
				{
					stockExists.addStatus(new StockStatus(new Date(), new Money("USD", Double.parseDouble(inputStatus.getText()))));
				}
				inputSymbol.setText("");
				inputStatus.setText("");
			}
		});
		
		JTextPane txtPaneSymbol = new JTextPane();
		txtPaneSymbol.setBackground(UIManager.getColor("Button.background"));
		txtPaneSymbol.setText("Symbol");
		txtPaneSymbol.setBounds(6, 214, 126, 16);
		frame.getContentPane().add(txtPaneSymbol);
		
		inputSymbol = new JTextField();
		inputSymbol.setBounds(6, 242, 134, 28);
		frame.getContentPane().add(inputSymbol);
		inputSymbol.setColumns(10);
		
		txtPaneDate = new JTextPane();
		txtPaneDate.setText("Price");
		txtPaneDate.setBackground(UIManager.getColor("Button.background"));
		txtPaneDate.setBounds(167, 214, 126, 16);
		frame.getContentPane().add(txtPaneDate);
		
		inputStatus = new JTextField();
		inputStatus.setBounds(167, 242, 134, 28);
		frame.getContentPane().add(inputStatus);
		inputStatus.setColumns(10);
		
		btnAdd.setBackground(Color.black);
		btnAdd.setForeground(Color.pink);
		btnAdd.setBounds(333, 243, 117, 29);
		frame.getContentPane().add(btnAdd);
		
		Font font = new Font("Helvetica", Font.BOLD, 16);
		txtArea = new TextArea();
		txtArea.setFont(font);
		txtArea.setEditable(false);
		txtArea.setBounds(5, 10, 240, 175);
		txtArea.setBackground(Color.pink);
		frame.getContentPane().add(txtArea);
		
		//Create thread to add or update a stock every 10 seconds for demo purposes
		Runnable stockActions = new Runnable() 
		{
		    public void run() 
		    {
		    	final String[] symbols = {"KEH", "ATZ", "KAS", "MCH", "JRZ", "KOD", "MAIZ"};
		    	final Money[] money = {new Money("USD", 0.99), new Money("USD", 1.88), new Money("USD", 23.77), new Money("USD", 34.66), new Money("USD", 45.55), new Money("USD", 56.44)};
		        Random random = new Random();
		        int stock = random.nextInt(symbols.length);
		        int amount = random.nextInt(money.length);
		        
		        Stock existingStock = StockBroker.getInstance().getStock(symbols[stock]);
				if(existingStock == null)
				{
					new Stock(symbols[stock], new StockStatus(new Date(), money[amount]));
				} 
				else
				{
					existingStock.addStatus(new StockStatus(new Date(), money[amount]));
				}
		    }
		};
		// single thread to update stock values
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(stockActions, 0, 10, TimeUnit.SECONDS);
	}

	@Override
	public void update(Observable o, Object arg) {
		txtArea.setText(o.toString());
		}
	}

