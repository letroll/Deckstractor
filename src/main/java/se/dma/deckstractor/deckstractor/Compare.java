package main.java.se.dma.deckstractor.deckstractor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import javax.swing.Timer;
import java.net.URL;
import java.nio.file.*;


import javax.imageio.ImageIO;

//Just testing a pull request
public class Compare
{	
	
	//Timer Interval
	final static int interval = 1;
	
	//ID Strings
	public static String[] ID = new String[535];
	public static String[] Name = new String[535];
	public static String[] HearthPwnID = new String[535];
	
	//Create Button, Pane and timer
    public static JEditorPane editorPane = new JEditorPane();
    public static JLabel totalcards = new JLabel("Cards found: 0");
    public static Timer timer;
    public static Timer timerMore;
    
    //Current card compare int
   	public static int i = 0;
   	
   	//Img recognition parameters
   	public double test = 0;
   	public double percentdiffallowed = 9;
   	public double extradifftwenty = 1;
   	
   	//Search parameters
   	public int StartID = -1;
   	public int EndID = -1;
    public static int ChosenClass = -1;
    public static String ChosenClassStr = "";
   	
   	
   	public static void main(String[] args){
		Compare com = new Compare();
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){
			System.out.println("Unable to load Windows look and feel");
		}
		
	}
	
   	//Current deck.
   	public static int[] CardNumb = new int[30];
   	public static int[] CardCount = new int[30];

    public static int CurrentSlot = 0;
    public static int TotCards = 0;

   	
	public Compare(){
		
		for(int x = 0; x < 30; x++){ 
			CardNumb[x] = -1;
			CardCount[x] = 0;
		}
		
		//Populate Strings
		populateStringArray();
		
		//Create frame
		JFrame frame = new JFrame("Deckstractor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(220,590);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.setAlwaysOnTop (true);
		frame.setFocusable(false);
		frame.setFocusableWindowState(false);
		frame.setLocation(1695,200);
		
			
		//Create event handler
		thehandler handler = new thehandler();
		

		//Create timer
		timer = new Timer(interval, handler);
		timerMore = new Timer(interval, handler);
		
		//editorPane options
		editorPane.setEditable(true);
		editorPane.setPreferredSize(new Dimension(190, 500));
						
		JMenuBar bar = new JMenuBar();
		frame.setJMenuBar(bar);
		
			JMenu Extract = new JMenu("Extract");
			bar.add(Extract);
		
				JMenuItem Druid = new JMenuItem("..as Druid");
				Extract.add(Druid);
				Druid.addActionListener(handler);
				JMenuItem Hunter = new JMenuItem("..as Hunter");
				Extract.add(Hunter);
				Hunter.addActionListener(handler);
				JMenuItem Mage = new JMenuItem("..as Mage");
				Extract.add(Mage);
				Mage.addActionListener(handler);
				JMenuItem Paladin = new JMenuItem("..as Paladin");
				Extract.add(Paladin);
				Paladin.addActionListener(handler);
				JMenuItem Priest = new JMenuItem("..as Priest");
				Extract.add(Priest);
				Priest.addActionListener(handler);
				JMenuItem Rogue = new JMenuItem("..as Rogue");
				Extract.add(Rogue);
				Rogue.addActionListener(handler);
				JMenuItem Shaman = new JMenuItem("..as Shaman");
				Extract.add(Shaman);
				Shaman.addActionListener(handler);
				JMenuItem Warlock = new JMenuItem("..as Warlock");
				Extract.add(Warlock);
				Warlock.addActionListener(handler);
				JMenuItem Warrior = new JMenuItem("..as Warrior");
				Extract.add(Warrior);
				Warrior.addActionListener(handler);
				Extract.addSeparator();
				JMenuItem ExtractMore = new JMenuItem("Second Extraction (If decklist has scroll)");
				Extract.add(ExtractMore);
				ExtractMore.addActionListener(handler);
			
		
		
			JMenu Export = new JMenu("Export");
			bar.add(Export);
		
				JMenuItem HearthPwn = new JMenuItem("Export to HearthPwn");
				Export.add(HearthPwn);
				HearthPwn.addActionListener(handler);
				
				JMenuItem Textfile = new JMenuItem("Export as Text File");
				Export.add(Textfile);
				Textfile.addActionListener(handler);
				
				JMenuItem Textfile2 = new JMenuItem("Export as Text File (Card by Card)");
				Export.add(Textfile2);
				Textfile2.addActionListener(handler);
				
				JMenuItem xml = new JMenuItem("Export as XML");
				Export.add(xml);
				xml.addActionListener(handler);
		
			JMenu Help = new JMenu("Help");
			bar.add(Help);
			
				JMenuItem Instructions = new JMenuItem("Instructions");
				Help.add(Instructions);
				Instructions.addActionListener(handler);

				JMenuItem MissingCards = new JMenuItem("Missing Cards");
				Help.add(MissingCards);
				MissingCards.addActionListener(handler);
				
				JMenuItem SearchTemplate = new JMenuItem("Creating Search Template");
				Help.add(SearchTemplate);
				SearchTemplate.addActionListener(handler);
				
			JMenu Template = new JMenu("Template");
			bar.add(Template);
			
				JMenuItem SingleTemplate = new JMenuItem("Create Single Card Template");
				Template.add(SingleTemplate);
				SingleTemplate.addActionListener(handler);
	
				JMenuItem DoubleTemplate = new JMenuItem("Create Double Card Template");
				Template.add(DoubleTemplate);
				DoubleTemplate.addActionListener(handler);
				
		//Add stuff to frame
		

		frame.add(totalcards);
		frame.add(editorPane);
		frame.revalidate();
	
	} //Constructor end.
	
	//Action listener
	public class thehandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource()==timer){	

				//Timer pulse
				if(i==0){
					ImgMatch(i);
					i++;
					totalcards.setText("Cards found: " + TotCards);
				}else if(i==20){
					ImgMatch(i);
					timer.stop();
					i=0;
					totalcards.setText("Cards found: " + TotCards);
					if (TotCards<30){
						String str = "<html><font color=\"red\"> Only " + TotCards + " where found, please scroll <br> ALL the way down in your decklist <br> and then press \"Second Extraction\".</font>";
						totalcards.setText(str);
					}

				}else{
					ImgMatch(i);
					i++;
					if (TotCards>29){
						timer.stop();
						i=0;
						totalcards.setText("Cards found: " + TotCards);
					}
					totalcards.setText("Cards found: " + TotCards);
				}
				UpdateWindow();
				

			}else if(event.getSource()==timerMore){
				
				//Timer pulse
				if(i==0){
					ImgMatch(i);
					timerMore.stop();
					i=0;
				}else{
					ImgMatch(i);
					if (TotCards>29){
						timerMore.stop();
						i=0;
					}
					i--;
				}
				totalcards.setText("Cards found: " + TotCards);
				UpdateWindow();
			}else if("..as Warrior".equals(event.getActionCommand())){
				ChosenClass=1;
				ChosenClassStr="warrior";
			   	StartID = 0;
			   	EndID = 33;
			   	StartSearch();
			}else if("..as Warlock".equals(event.getActionCommand())){
				ChosenClass=2;
				ChosenClassStr="warlock";
			   	StartID = 34;
			   	EndID = 67;
			   	StartSearch();
			}else if("..as Shaman".equals(event.getActionCommand())){
				ChosenClass=3;
				ChosenClassStr="shaman";
			   	StartID =68;
			   	EndID =101;
			   	StartSearch();
			}else if("..as Rogue".equals(event.getActionCommand())){
				ChosenClass=4;
				ChosenClassStr="rogue";
			   	StartID =102;
			   	EndID =135;
			   	StartSearch();
			}else if("..as Priest".equals(event.getActionCommand())){
				ChosenClass=5;
				ChosenClassStr="priest";
			   	StartID =136;
			   	EndID =169;
			   	StartSearch();
			}else if("..as Paladin".equals(event.getActionCommand())){
				ChosenClass=6;
				ChosenClassStr="paladin";
			   	StartID =170;
			   	EndID =203;
			   	StartSearch();
			}else if("..as Mage".equals(event.getActionCommand())){
				ChosenClass=7;
				ChosenClassStr="mage";
			   	StartID =204;
			   	EndID =237;
			   	StartSearch();
			}else if("..as Hunter".equals(event.getActionCommand())){
				ChosenClass=8;
				ChosenClassStr="hunter";
			   	StartID =238;
			   	EndID =271;
			   	StartSearch();
			}else if("..as Druid".equals(event.getActionCommand())){
				ChosenClass=9;
				ChosenClassStr="druid";
			   	StartID =272;
			   	EndID =305;
			   	StartSearch();
			}else if("Export as Text File".equals(event.getActionCommand())){
				RemoveSpace();
				String str = CreateTxtNum();
				CreateFile(str, ".txt");
			}else if("Export as Text File (Card by Card)".equals(event.getActionCommand())){
				RemoveSpace();
				String str = CreateTxt();
				CreateFile(str, ".txt");	
			}else if("Export as XML".equals(event.getActionCommand())){
				RemoveSpace();
				String xmlclass = JOptionPane.showInputDialog("Choose a name for your deck.");
				String str = CreateXML(xmlclass);
				CreateFile(str, ".xml");
			}else if("Export to HearthPwn".equals(event.getActionCommand())){
				RemoveSpace();
				HearthPwn();
			}else if("Create Single Card Template".equals(event.getActionCommand())){
				Robot robot = null;
				try {
					robot = new Robot ();
				} catch (AWTException m) {
					m.printStackTrace();
				}
				BufferedImage i = null;
				i = robot.createScreenCapture(new Rectangle(1510,121,50,25));
				File output = new File("SingleImgTemplate.jpeg");
				try {
					ImageIO.write(i, "jpeg", output );
				} catch (IOException p) {
					p.printStackTrace();
				}
			}else if("Create Double Card Template".equals(event.getActionCommand())){
				Robot robot = null;
				try {
					robot = new Robot ();
				} catch (AWTException m) {
					m.printStackTrace();
				}
				BufferedImage i = null;
				i = robot.createScreenCapture(new Rectangle(1510,121,50,25));
				File output = new File("DoubleImgTemplate.jpeg");
				try {
					ImageIO.write(i, "jpeg", output );
				} catch (IOException p) {
					p.printStackTrace();
				}
			}else if("Creating Search Template".equals(event.getActionCommand())){
				JTextArea textarea= new JTextArea("The program needs Search Templates to be able to detect cards, one for single cards and one for doubles. " +
						"\nThe program is missing some of these templates because I personally don't have the cards. See the list" +
						"\nin \"Missing Cards\" under Help." +  
						"\n" + 
						"\nTo create a template follow the stepts below:" +
						"\n" + 
						"\n1. Make a new deck." +
						"\n" +
						"\n2. Put a single card or double card in the deck (depending on if you want to make a single template or a double)" +
						"\n" +
						"\n3. Click \"Template\" then choose single or double." +
						"\n" +
						"\n4. Rename the image to the corresponding ID (found in the missing cards.txt in the deckstactor folder)." +
						"\n" +
						"\n5. Put the renamed template in the single or double template folder." +
						"\n" +
						"\n6. DONE! Your program should now be able to detect the new card." +
						"\n" +					
						"\nIt would be of great help if you could email me these new templates at deckstractor@gmail.com so that " +
						"\nI can make the database complete for future releases." +
						"\nWhen sending me pictures, name the image with the corresponding id AND also if its a single or double " +
						"\npicture: Forexample: CS2_037_Single Please also put the name of the cards in the email subject, thanks!" +
						"\nIf you want to get credited (and you are the first person to send that template) just let me know in the email."
						);
				 textarea.setEditable(true);
				 JOptionPane.showMessageDialog(null, textarea, "Creating Search Template", JOptionPane.PLAIN_MESSAGE);
			}else if("Missing Cards".equals(event.getActionCommand())){
				JTextArea textarea= new JTextArea(
						" The Deckstractor database is not yet complete, therefore it can't detect all cards. " +
						"\n I don't have the following cards and I am therefore unable to make search-templates for them myself:" + 
						"\n" +
						"\n Legendary:" +
						"\n" +
						"\n Iron Juggernaut, Mal'Ganis, Al'Akir the Windlord, Neptulon, Trade Prince Gallywix," + 
						"\n Vol'jin, Bolvar Fordragon, Flame Leviathan, King Krush, Gahz'rilla, Malorne," +
						"\n Alexstrasza, Baron Geddon, Gruul, Hogger, King Mukla, Lorewalker Cho," +
						"\n Millhouse Manastorm, Nat Pagle, Nozdormu, Onyxia, The Black Knight, Tinkmaster Overspark, " +
						"\n Ysera, Blingtron 3000, Foe Reaper 4000, Mekgineer Thermaplugg, Mimiron's Head," +
						"\n Mogor the Ogre, Sneed's Old Shredder, Toshley, Troggzor the Earthinator, Elite Tauren Chieftain" +
						"\n" + 
						"\n Epic: " +
						"\n" + 
						"\n Twisting Nether, Anima Golem, Ancestor's Call, Preparation, Lightbomb, Shadowbomber," +
						"\n Coghammer, Quartermaster, Feign Death, Steamwheedle Sniper, Tree of Life, Clockwork Giant, " +
						"\n Mini-Mage, Piloted Sky Golem" + 
						"\n" +
						"\n" +
						"\n There are also some epics that I only have 1 of. The program can therefor only detect if you have one " +
						"\n of those, I still need the search-template for having two." +
						"\n" +
						"\n Epic(Missing double-search-template):" +
						"\n" + 
						"\n Dark Wispers, Avenging Wrath, Bane of Doom, Big Game Hunter, Bouncing Blade, Cogmaster's Wrench," +
						"\n Crush, Far Sight, Gladiator's Longbow, Hungry Crab, Junkbot, Lay on Hands, Pit Lord, Recombobulator, " +
						"\n Sabotage, Sword of Justice, Wee Spellstopper," +
						"\n" + 
						"\n The program should be able to detect ALL Rares or lower quality, either one or two cards." + 
						"\n" + 
						"\n If you have some of these cards and want to make the program able to detect them, or even better, help me" +
						"\n make the original program able to detect them. Then please check out \"Creating Search Template\" under help."
						);
				 textarea.setEditable(true);
				 JOptionPane.showMessageDialog(null, textarea, "Creating Search Template", JOptionPane.PLAIN_MESSAGE);
			}else if("Instructions".equals(event.getActionCommand())){
				JOptionPane.showMessageDialog(null, " * Deckstractor only works with 1920x1080 resolution. The \"Fullscreen\" option must be checked. \n   The progam takes printscreens of the deck and without fullscreen and 1920x1080 the cordinates \n   won't align properly." +
						"\n \n* If your deck has a scrollbar make sure it's at the very top when you extract your deck. \n   After every card shown has been extracted, scroll ALL the way down and press \n   \"Secondary extraction\"."
						);
	        }else if("Second Extraction (If decklist has scroll)".equals(event.getActionCommand())){
				GetScreenExtra();
				CurrentSlot=29;
				i=8;
				timerMore.start();
				} //End of Export as text file. 
			}
		}//End of the handler.

	
	//Take printscreens for normal search
	public static void GetScreen(){
	
		int pleft = 1510;
		int pheight = 25;
		int pwidth = 50;
    		    
		Robot robot = null;
			
		try {
			robot = new Robot ();
		} catch (AWTException m) {
			m.printStackTrace();
		}
		    
		//Capture screens distance to top:		
		int[] top = new int[21];
		top[0] = 120;
		top[1] = 161;
		top[2] = 201;
		top[3] = 242;
		top[4] = 282;
		top[5] = 322;
		top[6] = 363;
		top[7] = 403;
		top[8] = 444;
		top[9] = 484;
		top[10] = 525;
		top[11] = 565;
		top[12] = 606;
		top[13] = 646;
	    top[14] = 687;
	    top[15] = 727;
		top[16] = 767;
	    top[17] = 808;
	    top[18] = 848;
	    top[19] = 889;
	    top[20] = 929;
		    
		for(int x = 0; x < 21; x++){
			BufferedImage i = null;
			i = robot.createScreenCapture(new Rectangle(pleft,(top[x] + 1),pwidth,pheight) );
			File output = new File("TempCards\\" + x + ".jpeg");
			try {
				ImageIO.write(i, "jpeg", output );
			} catch (IOException p) {
				p.printStackTrace();
			}
		}
			
	} //End of GetScreen
	
	
	//Take printscreens for search after scroll
	public static void GetScreenExtra(){
		
		int pleft = 1510;
		int pheight = 25;
		int pwidth = 50;
    		    
		Robot robot = null;
			
		try {
			robot = new Robot ();
		} catch (AWTException m) {
			m.printStackTrace();
		}
		    
		//Capture screens distance to top:		
		int[] top = new int[21];
		top[0] = 606;
		top[1] = 646;
	    top[2] = 687;
	    top[3] = 727;
		top[4] = 767;
	    top[5] = 808;
	    top[6] = 849;
	    top[7] = 889;
	    top[8] = 930;

		for(int x = 0; x < 9; x++){
			BufferedImage i = null;
			i = robot.createScreenCapture(new Rectangle(pleft,(top[x] - 5),pwidth,pheight) );
			File output = new File("TempCards\\" + x + ".jpeg");
			try {
				ImageIO.write(i, "jpeg", output );
			} catch (IOException p) {
				p.printStackTrace();
			}
		}
					
	} //End of GetScreenExtra
	
	
	//Match images
	public void ImgMatch(int x){
			//editorPane.setText(editorPane.getText() + x + "\n");
		boolean found = false;
		
		//Class search
		for (int y = StartID; y < (EndID+1); y++){
			//Search for matching double cards
			// Create a compare object specifying the 2 images for comparison.
			Path path = Paths.get("DoubleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
				test = ImgDiffPercent("TempCards\\" + x + ".jpeg", "DoubleImgTemplate\\" + ID[y] + ".jpeg");
				if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
					found = true;
					CardNumb[CurrentSlot] = y;
				    CardCount[CurrentSlot] = 2;
				    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
				    if (CurrentSlot>20){
				    	CurrentSlot--;	
				    }else{
				    	CurrentSlot++;
				    }
				    
				    TotCards = TotCards +2;
				    //Match found!
														
				}
			}
			//Search for matching single cards
			path = Paths.get("SingleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
				
				test = ImgDiffPercent("TempCards\\" + x + ".jpeg", "SingleImgTemplate\\" + ID[y] + ".jpeg");
				if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
					found = true;
					CardNumb[CurrentSlot] = y;
				    CardCount[CurrentSlot]++;
				    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
				    if (CurrentSlot>20){
				    	CurrentSlot--;	
				    }else{
				    	CurrentSlot++;
				    }
				    TotCards++;
												
				}						
			}
			if (found==true){
			break;
			}
		}
		
		
		
		//Neutral search
		for (int y = 306; y < 535; y++){
				//Search for matching double cards
				// Create a compare object specifying the 2 images for comparison.
				Path path = Paths.get("DoubleImgTemplate\\" + ID[y] + ".jpeg");
				if (Files.exists(path)) {
					test = ImgDiffPercent("TempCards\\" + x + ".jpeg", "DoubleImgTemplate\\" + ID[y] + ".jpeg");
					if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
						found = true;
						CardNumb[CurrentSlot] = y;
					    CardCount[CurrentSlot] = 2;
					    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
					    if (CurrentSlot>20){
					    	CurrentSlot--;	
					    }else{
					    	CurrentSlot++;
					    }
					    TotCards = TotCards +2;
					    //Match found!
						
																		
					}
				}
				//Search for matching single cards
				path = Paths.get("SingleImgTemplate\\" + ID[y] + ".jpeg");
				if (Files.exists(path)) {
					
					test = ImgDiffPercent("TempCards\\" + x + ".jpeg", "SingleImgTemplate\\" + ID[y] + ".jpeg");
					if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
						found = true;
						CardNumb[CurrentSlot] = y;
					    CardCount[CurrentSlot]++;
					    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
					    if (CurrentSlot>20){
					    	CurrentSlot--;	
					    }else{
					    	CurrentSlot++;
					    }
					    TotCards++;
													
					}						
				}
				if (found==true){
				break;
				}
			}
			
			
			
		
		// This only runs is Normal test fails###
		if(found==false){
			for (int y = 306; y < 535; y++){
				
			//One pixel up, double.
			Path path = Paths.get("DoubleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
				
				
				test = ImgDiffPercentUp("TempCards\\" + x + ".jpeg", "DoubleImgTemplate\\" + ID[y] + ".jpeg");
				if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
					found = true;
					CardNumb[CurrentSlot] = y;
				    CardCount[CurrentSlot] = 2;
				    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
				    if (CurrentSlot>20){
				    	CurrentSlot--;	
				    }else{
				    	CurrentSlot++;
				    }
				    TotCards = TotCards +2;
				    //Match found!
																
				}
			}
			//One pixel up
				//Search for matching single cards
			path = Paths.get("SingleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
				
				test = ImgDiffPercentUp("TempCards\\" + x + ".jpeg", "SingleImgTemplate\\" + ID[y] + ".jpeg");
				if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
					found = true;
					CardNumb[CurrentSlot] = y;
				    CardCount[CurrentSlot]++;
				    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
				    if (CurrentSlot>20){
				    	CurrentSlot--;	
				    }else{
				    	CurrentSlot++;
				    }
				    TotCards++;
											
				}						
			}
			//One pixel down, double
			path = Paths.get("DoubleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
					
					
					test = ImgDiffPercentDown("TempCards\\" + x + ".jpeg", "DoubleImgTemplate\\" + ID[y] + ".jpeg");
					if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
						found = true;
						CardNumb[CurrentSlot] = y;
					    CardCount[CurrentSlot] = 2;
					    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
					    if (CurrentSlot>20){
					    	CurrentSlot--;	
					    }else{
					    	CurrentSlot++;
					    }
					    TotCards = TotCards +2;
					    //Match found!															
					}
				}
			//One pixel down
					//Search for matching single cards
				path = Paths.get("SingleImgTemplate\\" + ID[y] + ".jpeg");
				if (Files.exists(path)) {
					
					test = ImgDiffPercentDown("TempCards\\" + x + ".jpeg", "SingleImgTemplate\\" + ID[y] + ".jpeg");
					if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
						found = true;
						CardNumb[CurrentSlot] = y;
					    CardCount[CurrentSlot]++;
					    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
					    if (CurrentSlot>20){
					    	CurrentSlot--;	
					    }else{
					    	CurrentSlot++;
					    }
					    TotCards++;
													
					}						
				}
			if (found==true){
				break;
			}
			} //End of extra test loop
		} //End of second if-statement
			
		
		
		} //End of ImgMatch
			
	
	//Export to hearthPwn
	public static void HearthPwn (){
		String URL = "http://www.hearthpwn.com/deckbuilder/";
				
		
		URL = URL + ChosenClassStr + "#";
		
		for (int m = 0; m < 30; m++){
			if (CardNumb[m]>-1){
				URL = URL + HearthPwnID[CardNumb[m]] + ":" + CardCount[m] + ";";
			}
		}
		openWebpage(URL);
	}
	
	//Create card by card .txt
	public static String CreateTxt(){
		String str = "";
		for (int m = 0; m < 30; m++){
			if (CardNumb[m]>-1){
				if (CardCount[m]==2){
					str = str + Name[CardNumb[m]] + "\r\n" + Name[CardNumb[m]] + "\r\n";
				}else if (CardCount[m]==1){
					str = str + Name[CardNumb[m]] + "\r\n";
				}else{
				}
			}
		}
		return str;
	}
	
	//Create normal .txt
	public static String CreateTxtNum(){
		String str = "";
		for (int m = 0; m < 30; m++){
			if (CardNumb[m]>-1){
				if (CardCount[m]==2){
					str = str + "2x: " + Name[CardNumb[m]] + "\r\n";
				}else if (CardCount[m]==1){
					str = str + "1x: " + Name[CardNumb[m]] + "\r\n";
				}else{
				}
			}
		}
		return str;
	}
	
	//Create XML file
	public static String CreateXML(String name){
		String str = "";
		str = str + "<Deck>" + "\r\n";
		str = str + " <Cards>" + "\r\n";
		for (int m = 0; m < 30; m++){
			if (CardNumb[m]>-1){
			str = str + "  <Card>" + "\r\n";
			str = str + "   <Id>" + ID[CardNumb[m]] + "</Id>" + "\r\n";
			str = str + "   <Count>" + CardCount[m] + "</Count>" + "\r\n";
			str = str + "  </Card>" + "\r\n";
			}
		}
		str = str + " </Cards>" + "\r\n";
		str = str + " <Class>" + capitalizeFirstLetter(ChosenClassStr)+ "</Class>" + "\r\n";
		str = str + " <Name>" + name + "</Name>" + "\r\n";
		str = str + " <Note />" + "\r\n";
		str = str + " <Tags />" + "\r\n";
		str = str + " <Url />" + "\r\n";
		str = str + "</Deck>" + "\r\n";
		
		return str;
	}
	
	//Write .txt/XML files to disk
	public static void CreateFile(String str, String end){
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    File f = fileChooser.getSelectedFile();
		    String filePath = f.getPath();
		    if(!filePath.toLowerCase().endsWith(end))
		    {
		        f = new File(filePath + end);
		    }
		    try (PrintStream out = new PrintStream(new FileOutputStream(f))) {
		        out.print(str);
		    } catch (FileNotFoundException ee) {
				ee.printStackTrace();
			}
		}
	}
	
	//Update GUI after every new card found
	public static void UpdateWindow(){
		editorPane.setText(" ");
		for (int m = 0; m < 30; m++){
			if(CardCount[m]>0){
				editorPane.setText(editorPane.getText() + CardCount[m] +"x: " + Name[CardNumb[m]] + "\n ");
			}				
		}
	}
	
	//Remove spaces in array keeping track of cards when search is done, also put one gold and one normal cards togeather as one.
	public static void RemoveSpace(){
		for (int m = 0; m < 29; m++){
			if(CardNumb[m]==CardNumb[m+1]){
				CardCount[m]=2;
				CardCount[m+1]=0;
				CardNumb[m+1]=-1;
			}				
		}
		for (int m = 0; m < 29; m++){
			if(CardCount[m]==-1){
				CardCount[m]=CardCount[m+1];
				CardNumb[m]=CardNumb[m+1];
			}				
		}

	}
	
	//Open URL
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//Start new search
	public static void StartSearch(){
		//Button pressed
			editorPane.setText(" ");
			for(int x = 0; x < 30; x++){ 
				CardNumb[x] = -1;
				CardCount[x] = 0;
			}
		   	CurrentSlot=0;
		   	TotCards=0;
			editorPane.setText(" ");
			i=0;
			GetScreen();
			timer.start();
			
	}
	
	//Img compare function
	//Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
	public static double ImgDiffPercent(String IMG1, String IMG2)
	{
	    BufferedImage img1 = null;
	    BufferedImage img2 = null;
	    try {
	      //URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
	      //URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
	      img1 = ImageIO.read(new File(IMG1));
	      img2 = ImageIO.read(new File(IMG2));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }

	    
	    int width1 = img1.getWidth(null);
	    int width2 = img2.getWidth(null);
	    int height1 = img1.getHeight(null);
	    int height2 = img2.getHeight(null);
	    if ((width1 != width2) || (height1 != height2)) {
	      System.err.println("Error: Images dimensions mismatch");
	      System.exit(1);
	    }
	    long diff = 0;
	    for (int y = 0; y < height1; y++) {
	      for (int x = 0; x < width1; x++) {
	        int rgb1 = img1.getRGB(x, y);
	        int rgb2 = img2.getRGB(x, y);
	        int r1 = (rgb1 >> 16) & 0xff;
	        int g1 = (rgb1 >>  8) & 0xff;
	        int b1 = (rgb1      ) & 0xff;
	        int r2 = (rgb2 >> 16) & 0xff;
	        int g2 = (rgb2 >>  8) & 0xff;
	        int b2 = (rgb2      ) & 0xff;
	        diff += Math.abs(r1 - r2);
	        diff += Math.abs(g1 - g2);
	        diff += Math.abs(b1 - b2);
	      }
	    }
	    double n = width1 * height1 * 3;
	    double p = diff / n / 255.0;
	    return (double) (p*100.0);
	    
	}
	
	//Img compare function one pixel up
	//Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
	public static double ImgDiffPercentUp(String IMG1, String IMG2)
	{
	    BufferedImage cimg1 = null;
	    BufferedImage cimg2 = null;
	    try {
	      //URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
	      //URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
	      cimg1 = ImageIO.read(new File(IMG1));
	      cimg2 = ImageIO.read(new File(IMG2));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    BufferedImage img1 = cimg1.getSubimage(0, 0, 50, 24);
	    BufferedImage img2 = cimg2.getSubimage(0, 1, 50, 24);
	    
	    int width1 = img1.getWidth(null);
	    int width2 = img2.getWidth(null);
	    int height1 = img1.getHeight(null);
	    int height2 = img2.getHeight(null);
	    if ((width1 != width2) || (height1 != height2)) {
	      System.err.println("Error: Images dimensions mismatch");
	      System.exit(1);
	    }
	    long diff = 0;
	    for (int y = 0; y < height1; y++) {
	      for (int x = 0; x < width1; x++) {
	        int rgb1 = img1.getRGB(x, y);
	        int rgb2 = img2.getRGB(x, y);
	        int r1 = (rgb1 >> 16) & 0xff;
	        int g1 = (rgb1 >>  8) & 0xff;
	        int b1 = (rgb1      ) & 0xff;
	        int r2 = (rgb2 >> 16) & 0xff;
	        int g2 = (rgb2 >>  8) & 0xff;
	        int b2 = (rgb2      ) & 0xff;
	        diff += Math.abs(r1 - r2);
	        diff += Math.abs(g1 - g2);
	        diff += Math.abs(b1 - b2);
	      }
	    }
	    double n = width1 * height1 * 3;
	    double p = diff / n / 255.0;
	    return (double) (p*100.0);
	    
	}

	//Img compare function one pixel down
	//Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
	public static double ImgDiffPercentDown(String IMG1, String IMG2)
	{
	    BufferedImage cimg1 = null;
	    BufferedImage cimg2 = null;
	    try {
	      //URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
	      //URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
	      cimg1 = ImageIO.read(new File(IMG1));
	      cimg2 = ImageIO.read(new File(IMG2));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    BufferedImage img1 = cimg1.getSubimage(0, 1, 50, 24);
	    BufferedImage img2 = cimg2.getSubimage(0, 0, 50, 24);
	    
	    int width1 = img1.getWidth(null);
	    int width2 = img2.getWidth(null);
	    int height1 = img1.getHeight(null);
	    int height2 = img2.getHeight(null);
	    if ((width1 != width2) || (height1 != height2)) {
	      System.err.println("Error: Images dimensions mismatch");
	      System.exit(1);
	    }
	    long diff = 0;
	    for (int y = 0; y < height1; y++) {
	      for (int x = 0; x < width1; x++) {
	        int rgb1 = img1.getRGB(x, y);
	        int rgb2 = img2.getRGB(x, y);
	        int r1 = (rgb1 >> 16) & 0xff;
	        int g1 = (rgb1 >>  8) & 0xff;
	        int b1 = (rgb1      ) & 0xff;
	        int r2 = (rgb2 >> 16) & 0xff;
	        int g2 = (rgb2 >>  8) & 0xff;
	        int b2 = (rgb2      ) & 0xff;
	        diff += Math.abs(r1 - r2);
	        diff += Math.abs(g1 - g2);
	        diff += Math.abs(b1 - b2);
	      }
	    }
	    double n = width1 * height1 * 3;
	    double p = diff / n / 255.0;
	    return (double) (p*100.0);
	    
	}
	
	
	//Capitalize first letter
	public static String capitalizeFirstLetter(String original){
	    if(original.length() == 0)
	        return original;
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
	
	//Populate arrays, nothing of interest below this point, seriously.
	public void populateStringArray() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/cardnames")));
			String line;
			int counter = 0;
			while ((line = br.readLine()) != null) {
				Name[counter] = line;
				counter++;
			}
			br.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/blizzard-ids")));
			String line;
			int counter = 0;
			while ((line = br.readLine()) != null) {
				ID[counter] = line;
				counter++;
			}
			br.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/hearthpwn-ids")));
			String line;
			int counter = 0;
			while ((line = br.readLine()) != null) {
				ID[counter] = line;
				counter++;
			}
			br.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}