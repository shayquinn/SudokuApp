package sudoku;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;





public class SudokuApp extends JFrame implements ActionListener, DocumentListener, ChangeListener {
	JPanel[] JPlist = new JPanel[9];
	JPanel[] JPlist2 = new JPanel[27];

	JTextField[][][] fields = new JTextField[9][3][3];
	String puzzelString;
	char[][][] intArray = new char[9][3][3];
	char[][][] solvedArray = new char[9][3][3];
	char[][][] new3d = new char[9][3][3];
	char[][][] emptyArray = new char[9][3][3];
	
	static ArrayList<Color> colorListArray = new ArrayList<Color>();
	static final int MIN = 0;
	static final int MAX = 599;
	static final int INT = 400;	
	
	private JPanel panes;
	private JSlider s1;
	//private JSlider s2;
	//private JSlider s3;

	boolean savedBull = false;
	boolean checkingbull = false;

	int count = 0;

	private final int frameWidth = 500;
	private final int frameHeight = 500;

	private File openFile;

	private JPanel pane;
	private JPanel paneCenter;
	private JPanel paneBottom;
	private JMenuBar menuBar;
	private JMenu menu;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	
	JMenuItem newMenuItem1;
	JMenuItem newMenuItem2;
	JMenuItem newMenuItem3;
	JMenuItem newMenuItem4;
	JMenuItem newMenuItem5;
	JMenuItem newMenuItem6;
	JOptionPane optionPane;
	JPanel panex;
	JDialog dialog;
	JPanel pc;

	public SudokuApp() {
		this.setSize(this.frameWidth, this.frameHeight);
		this.setTitle("Sudoku Solver_");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		pane = createPanel();
		this.getContentPane().add(pane);
		colorList();
		getPanel();
		color(emptyArray);
                
	}

	private JPanel createPanel() {
		pane = new JPanel(new BorderLayout());
		pane.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		menuBar = createMenuBar();
		menuBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		paneCenter = new JPanel(new GridLayout(9, 1));
		paneCenter.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		paneCenter.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		paneCenter.setBackground(Color.black);

		for (int i = 0; i < 9; i++) {
			JPlist[i] = new JPanel(new GridLayout(1, 9));
			JPlist[i].setBorder(new EmptyBorder(10, 10, 10, 10));
			JPlist[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
			JPlist[i].setBackground(Color.black);

			if (i == 0) {
				JPlist[i].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
			} else if (i == 2) {
				JPlist[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			} else if (i == 3) {
				JPlist[i].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
			} else if (i == 5) {
				JPlist[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			} else if (i == 6) {
				JPlist[i].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
			} else if (i == 8) {
				JPlist[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			}

			for (int j = 0; j < 3; j++) {
				JPlist2[j + (3 * i)] = new JPanel(new GridLayout(1, 3));
				JPlist2[j + (3 * i)].setBorder(new EmptyBorder(10, 10, 10, 10));
				JPlist2[j + (3 * i)].setBorder(javax.swing.BorderFactory.createEmptyBorder());
				JPlist2[j + (3 * i)].setBackground(Color.black);
				if (j == 0) {
					JPlist2[j + (3 * i)].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
				} else if (j == 1) {
					JPlist2[j + (3 * i)].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
				} else if (j == 2) {
					JPlist2[j + (3 * i)].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
				}

				for (int l = 0; l < 3; l++) {
					Border blackline = BorderFactory.createLineBorder(Color.black);
					fields[i][j][l] = new JTextField();
					fields[i][j][l].getDocument().addDocumentListener(this);
					fields[i][j][l].setBorder(javax.swing.BorderFactory.createEmptyBorder());
					fields[i][j][l].setHorizontalAlignment(JTextField.CENTER);
					fields[i][j][l].setFont(fields[i][j][l].getFont().deriveFont(20.0f));
					fields[i][j][l].setBorder(blackline);

					JPlist2[j + (3 * i)].add(fields[i][j][l]);
				}
				JPlist[i].add(JPlist2[j + (3 * i)]);
			}
			paneCenter.add(JPlist[i]);
		}
		paneBottom = new JPanel(new BorderLayout());
		paneBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		b1 = new JButton("Clear");
		b1.setActionCommand("c");
		b1.addActionListener(this);
		b1.setMnemonic(KeyEvent.VK_C);
		b1.setToolTipText("ALT-C");
		b1.setFont(b1.getFont().deriveFont(20.0f));

		b2 = new JButton("Solve");
		b2.setActionCommand("ss");
		b2.addActionListener(this);
		b2.setMnemonic(KeyEvent.VK_S);
		b2.setToolTipText("ALT-S");
		b2.setFont(b2.getFont().deriveFont(20.0f));

		b3 = new JButton("Generator");
		b3.setActionCommand("g");
		b3.addActionListener(this);
		b3.setMnemonic(KeyEvent.VK_G);
		b3.setToolTipText("ALT-G");
		b3.setFont(b3.getFont().deriveFont(20.0f));

		paneBottom.add(b1, BorderLayout.LINE_START);
		paneBottom.add(b2, BorderLayout.LINE_END);
		paneBottom.add(b3, BorderLayout.CENTER);
		pane.add(menuBar, BorderLayout.PAGE_START);
		pane.add(paneCenter, BorderLayout.CENTER);
		pane.add(paneBottom, BorderLayout.PAGE_END);

		return pane;
	}

	private JMenuBar createMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.setToolTipText("ALT-F");

		newMenuItem5 = new JMenuItem("New");
		newMenuItem5.setMnemonic(KeyEvent.VK_N);
		KeyStroke keyStrokeToOpen3 = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
		newMenuItem5.setAccelerator(keyStrokeToOpen3);
		newMenuItem5.setActionCommand("n");
		newMenuItem5.addActionListener(this);

		newMenuItem1 = new JMenuItem("Open");
		newMenuItem1.setMnemonic(KeyEvent.VK_O);
		KeyStroke keyStrokeToOpen1 = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		newMenuItem1.setAccelerator(keyStrokeToOpen1);
		newMenuItem1.setActionCommand("o");
		newMenuItem1.addActionListener(this);

		newMenuItem2 = new JMenuItem("Save");
		newMenuItem2.setMnemonic(KeyEvent.VK_S);
		KeyStroke keyStrokeToOpen2 = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		newMenuItem2.setAccelerator(keyStrokeToOpen2);
		newMenuItem2.setActionCommand("s");
		newMenuItem2.addActionListener(this);

		newMenuItem3 = new JMenuItem("Save As...");
		newMenuItem3.setActionCommand("sa");
		newMenuItem3.addActionListener(this);

		newMenuItem4 = new JMenuItem("Exit");
		newMenuItem4.setActionCommand("e");
		newMenuItem4.addActionListener(this);
		
		newMenuItem6 = new JMenuItem("Color");
		newMenuItem6.setMnemonic(KeyEvent.VK_C);
		KeyStroke keyStrokeToOpen6 = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
		newMenuItem6.setAccelerator(keyStrokeToOpen6);
		newMenuItem6.setActionCommand("cc");
		newMenuItem6.addActionListener(this);

		menu.add(newMenuItem5);
		menu.add(newMenuItem1);
		menu.add(newMenuItem2);
		menu.add(newMenuItem3);
		menu.add(newMenuItem6);
		menu.addSeparator();
		menu.add(newMenuItem4);
		menuBar.add(menu);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();
		 if (e.getActionCommand().equals("g")) {
			SudokuGenerator sg = new SudokuGenerator();
			sg.nextBoard(35);
			String ch = "";
				for (int x = 0; x < 9; x++) {
					for (int y = 0; y < 9; y++) {
						ch=ch+sg.board[x][y];
					}
				}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int l = 0; l < 3; l++) {
					intArray[i][j][l] = ch.charAt(l + ((3 * j) + (9 * i)));
			}
		}
		}
		printTwoD();
		solve();
		}else if (e.getActionCommand().equals("c")) {
			clear();
		} else if (e.getActionCommand().equals("ss")) {
			printTwoDsolved(solvedArray);
		} else if (e.getActionCommand().equals("o")) {
			int returnVal = fc.showOpenDialog(SudokuApp.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				openFile = file;
				read(openFile);
			}
		} else if (e.getActionCommand().equals("s")) {
			Object[] options = { "Yes", "No" };
			int n = JOptionPane.showOptionDialog(null, "Are you sure you want to save?", "Exit Sudoku Solver",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (n == JOptionPane.YES_OPTION) {
				write();
			} else if (n == JOptionPane.NO_OPTION) {
			}
		} else if (e.getActionCommand().equals("sa")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file to save");
			int userSelection = fileChooser.showSaveDialog(SudokuApp.this);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				write(fileToSave);
			}
		} else if (e.getActionCommand().equals("e")) {
			Object[] options1 = { "Yes", "No" };
			int n2 = JOptionPane.showOptionDialog(null, "Are you sure you want to quit Sudoku Solver?",
					"Exit Sudoku Solver", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1,
					options1[0]);
			if (n2 == JOptionPane.YES_OPTION) {
				//System.out.println(changeCheck());
				if (changeCheck()) {
					System.exit(0);
				} else {
					Object[] options3 = { "Yes", "No" };
					int n3 = JOptionPane.showOptionDialog(null, "Would you like to save befor exiting?",
							"Exit Sudoku Solver", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							options3, options3[1]);
					if (n3 == JOptionPane.YES_OPTION) {
						write();
					} else if (n3 == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				}
			} else if (n2 == JOptionPane.NO_OPTION) {
				clear();
			}

		} else if (e.getActionCommand().equals("n")) {
			openFile = null;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 3; j++) {
					for (int l = 0; l < 3; l++) {
						fields[i][j][l].setText("");
						fields[i][j][l].setBackground( new Color(255, 255, 255));
					}
				}
			}
		}else if (e.getActionCommand().equals("cc")) {			
			optionPane = new JOptionPane();
		    optionPane.setMessage(new Object[] { "Select a color value: ", panes });
		    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		    dialog = optionPane.createDialog(null, "My Slider");
		    dialog.setVisible(true);	    		  
		}
	}
	
	public void getPanel() {
		panes = new JPanel();
		s1 = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INT);
	 	s1.setName("sl1");
	 	s1.setMajorTickSpacing(50);
        s1.setPaintTicks(true);
       // s1.setPaintLabels(true);
        s1.addChangeListener(this);
        pc = new JPanel();
        pc.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.black));
        pc.setSize(50, 50);
        panes.add(pc);
		panes.add(s1);		
	}

	public void insertUpdate(DocumentEvent e) {
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 3; j++) {
						for (int l = 0; l < 3; l++) {
							if (fields[i][j][l].getText().trim().isEmpty()) {
								new3d[i][j][l] = '0';
							} else {
								new3d[i][j][l] = fields[i][j][l].getText().trim().charAt(0);
							}
						}
					}
				}
			checkCell();
	}

	public void changedUpdate(DocumentEvent arg0) {	}

	public void removeUpdate(DocumentEvent arg0) {
			for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 3; j++) {
						for (int l = 0; l < 3; l++) {
							if (fields[i][j][l].getText().trim().isEmpty()) {
								new3d[i][j][l] = '0';
							} else {
								new3d[i][j][l] = fields[i][j][l].getText().trim().charAt(0);
							}
						}
					}
				}
			checkCell();
	}
		
	public void stateChanged(ChangeEvent e) {
		checkCell();
	}
	
	public void colorList(){
		for (int r=0; r<100; r++) colorListArray.add(new Color(r*255/100,       255,         0));
		for (int g=100; g>0; g--) colorListArray.add(new Color(      255, g*255/100,         0));
		for (int b=0; b<100; b++) colorListArray.add(new Color(      255,         0, b*255/100));
		for (int r=100; r>0; r--) colorListArray.add(new Color(r*255/100,         0,       255));
		for (int g=0; g<100; g++) colorListArray.add(new Color(        0, g*255/100,       255));
		for (int b=100; b>0; b--) colorListArray.add(new Color(        0,       255, b*255/100));		                       
    }

	public void checkCell(){
		Color cc = colorListArray.get(s1.getValue());
		int cr = 255-cc.getRed(); int cr1 = (255-cr)/10;
		int cg = 255-cc.getGreen(); int cg1 = (255-cg)/10;
		int cb = 255-cc.getBlue(); int cb1 = (255-cb)/10;
		boolean testbull = false;
		int ii = 0; int jj = 0; int ll = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 3; j++) {
					for (int l = 0; l < 3; l++) {
						if(!(new3d[i][j][l]=='0')){
							if(!(new3d[i][j][l]==solvedArray[i][j][l])){
								testbull = true;
								ii = i; jj = j; ll = l;
							}
						}
					}
				}
			}
			if(testbull==true){	
				color(new3d);
				fields[ii][jj][ll].setBackground(
						new Color(cr+(cr1*5), cg+(cg1*5), cb+(cb1*5)));
			}else{
				color(new3d);
			}
	}

	public void solve() {
		String checkString = "";
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 3; j++) {
						for (int l = 0; l < 3; l++) {
							if (fields[i][j][l].getText().trim().isEmpty()||fields[i][j][l].getText().equals(" ")) {
								checkString = checkString + "0";
							} else {
								checkString = checkString + fields[i][j][l].getText().trim();
							}
						}
					}
				}
                              
                         Sudoku s2 = new Sudoku(checkString);   
				for (int m = 0; m < 9; m++) {
					for (int n = 0; n < 3; n++) {
						for (int o = 0; o < 3; o++) {
							solvedArray[m][n][o] = s2.getSolutionString().charAt(o + ((3 * n) + (9 * m)));
						}
					}
				}
	}

	public void clear() {
		openFile = null;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int l = 0; l < 3; l++) {
					fields[i][j][l].setText("");
					color(emptyArray);
				}
			}
		}
	}

	// open
	public void read(File openFile2) {
		try {
			Scanner scc = new Scanner(openFile2);
			while (scc.hasNextLine()) {
				String in = scc.nextLine();
				puzzelString = in;
			}
			scc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		twoD();
		solve();
	}

	// save
	public void write() {
		String out = "";
		if (!(openFile == null)) {
			try {
				FileWriter fw = new FileWriter(openFile, false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 3; j++) {
						for (int l = 0; l < 3; l++) {
							if (fields[i][j][l].getText().trim().isEmpty()) {
								out = out + "0";
							} else {
								out = out + fields[i][j][l].getText().trim();
							}
						}
					}
				}
				pw.println(out);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Save path is null");
		}
	}

	// save as
	public void write(File f) {
		String out = "";
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f, false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 3; j++) {
					for (int l = 0; l < 3; l++) {
						if (fields[i][j][l].getText().trim().isEmpty()) {
							out = out + "0";
						} else {
							out = out + fields[i][j][l].getText().trim();
						}
					}
				}
			}
			pw.println(out);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void twoD() {
		clear();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int l = 0; l < 3; l++) {
					intArray[i][j][l] = puzzelString.charAt(l + ((3 * j) + (9 * i)));						
					if (intArray[i][j][l] == '0') {
						intArray[i][j][l] = ' ';
					}

				}
			}
		}
		printTwoD();
		color(intArray);
	}

	public void printTwoD() {
		clear();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int l = 0; l < 3; l++) {
					color(intArray);
					if(intArray[i][j][l]=='0'){
						fields[i][j][l].setText(" ");
					}else{
						fields[i][j][l].setText(Character.toString(intArray[i][j][l]));
					}
				}
			}
		}
			color(intArray);
	}

	public void printTwoDsolved(char[][][] a) {
		clear();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int l = 0; l < 3; l++) {
						fields[i][j][l].setText(Character.toString(a[i][j][l]));					
				}
			}
		}
		color(a);
	}

	public void color(char[][][] a) {
		Color c = colorListArray.get(s1.getValue());
		int t = 255;
		int cr = c.getRed(); int cr1 = (t-cr)/10;
		int cg = c.getGreen(); int cg1 = (t-cg)/10;
		int cb = c.getBlue(); int cb1 = (t-cb)/10;
		
		int cro = 255-c.getRed(); int cro1 = (t-cro)/10;		
		int cgo = 255-c.getGreen(); int cgo1 = (t-cgo)/10;
		int cbo = 255-c.getBlue(); int cbo1 = (t-cbo)/10;
		
		menuBar.setBackground(new Color(cro+(cro1*7), cgo+(cgo1*7), cbo+(cbo1*7)));
		newMenuItem1.setBackground(new Color(cro+(cro1*7), cgo+(cgo1*7), cbo+(cbo1*7)));
		newMenuItem2.setBackground(new Color(cro+(cro1*7), cgo+(cgo1*7), cbo+(cbo1*7)));
		newMenuItem3.setBackground(new Color(cro+(cro1*7), cgo+(cgo1*7), cbo+(cbo1*7)));
		newMenuItem4.setBackground(new Color(cro+(cro1*7), cgo+(cgo1*7), cbo+(cbo1*7)));
		newMenuItem5.setBackground(new Color(cro+(cro1*7), cgo+(cgo1*7), cbo+(cbo1*7)));
		newMenuItem6.setBackground(new Color(cro+(cro1*7), cgo+(cgo1*7), cbo+(cbo1*7)));
		paneBottom.setBackground(new Color(cro+(cro1*7), cgo+(cgo1*7), cbo+(cbo1*7)));
		b1.setBackground(new Color(cr+(cr1*7), cg+(cg1*7), cb+(cb1*7)));
		b2.setBackground(new Color(cr+(cr1*7), cg+(cg1*7), cb+(cb1*7)));
		b3.setBackground(new Color(cr+(cr1*7), cg+(cg1*7), cb+(cb1*7)));
		pc.setBackground(colorListArray.get(s1.getValue()));
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int l = 0; l < 3; l++) {
					 if(a[i][j][l]=='0'){
						fields[i][j][l].setBackground(new Color(cr+(cr1*9), cg+(cg1*9), cb+(cb1*9)));
					}else if(a[i][j][l]=='1'){
						fields[i][j][l].setBackground(new Color(cr+(cr1*8), cg+(cg1*8), cb+(cb1*8)));
					}else if(a[i][j][l]=='2'){
						fields[i][j][l].setBackground(new Color(cr+(cr1*7), cg+(cg1*7), cb+(cb1*7)));
					}else if(a[i][j][l]=='3'){
						fields[i][j][l].setBackground(new Color(cr+(cr1*6), cg+(cg1*6), cb+(cb1*6)));
					}else if(a[i][j][l]=='4'){
						fields[i][j][l].setBackground(new Color(cr+(cr1*5), cg+(cg1*5), cb+(cb1*5)));
					}else if(a[i][j][l]=='5'){
						fields[i][j][l].setBackground(new Color(cr+(cr1*4), cg+(cg1*4), cb+(cb1*4)));
					}else if(a[i][j][l]=='6'){
						fields[i][j][l].setBackground(new Color(cr+(cr1*3), cg+(cg1*3), cb+(cb1*3)));
					}else if(a[i][j][l]=='7'){
						fields[i][j][l].setBackground(new Color(cr+(cr1*2), cg+(cg1*2), cb+(cb1*2)));
					}else if(a[i][j][l]=='8'){
						fields[i][j][l].setBackground(new Color(cr+cr1, cg+cg1, cb+cb1));
					}else if(a[i][j][l]=='9'){
						fields[i][j][l].setBackground(c);
					}
				}
			}
		}
		}

	public boolean changeCheck() {
		boolean test = true;
		String testString = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int l = 0; l < 3; l++) {
					if (fields[i][j][l].getText().trim().isEmpty()) {
						testString = testString + "0";
					} else {
						testString = testString + fields[i][j][l].getText().trim();
					}
				}
			}
		}
		for (int l = 0; l < 81; l++) {
			if (!(testString.charAt(l) == puzzelString.charAt(l))) {
				test = false;
			}
		}
		return test;
	}

	public static void main(String[] args) {
		new SudokuApp().setVisible(true);
	}

 


}

