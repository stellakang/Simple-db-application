package music_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.Date;
public class MainPage {
	static Scanner input1;
	static Scanner input2;
	static int idNum=-1;
	public static void main(String[] args)throws Exception{
		
		while(true)
		{  //start page
			input1=new Scanner(System.in);
			input2=new Scanner(System.in);
			System.out.println("<<<--------======|| MusicApplication ||======-------->>>\n\n1. Admin Register\n2. User Register\n"
			+ "3. Login\n4. Exit\n");
			
			System.out.print(">> Input number : ");
			int number=input1.nextInt();
			if(number==1)//admin register
			{
				adminRegister();//call function
			}
			else if(number==2)//user register
			{
				userRegister();
			}
			else if(number==3)//login
			{
				login();
			}
			else//exit
			{	System.out.println("Bye.");
				break;	
			}
		}
	}
	public static Connection getConnection() throws Exception//for connection
	{
		try{
			Connection conn=DriverManager.getConnection("jdbc:mariadb://localhost:3306/application","kangsj123","1234");
			return conn;
		}catch(Exception e){
			System.out.println(e);
		}
		
		return null;
	}
	
	public static void adminRegister()
	{
		System.out.println("\n------| Admin Register Page |------");
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		PreparedStatement statement;
		ResultSet result1, result2;
		while(true)
		{
			try{
				Connection con=getConnection();
				System.out.print("\n>> Choose your admin ID (less than 50 letters) : "); //choose id
				String id=input2.nextLine();
				if(id.length()>50)
					System.out.println("\n*** Please input id less than 50 letters. Register again!"); //id length should be shorter than 50 letters
				else
				{
					String sql1="SELECT * FROM admin WHERE ADMINID=?";
					statement = con.prepareStatement(sql1);
					statement.setString(1, id);
					result1 = statement.executeQuery();
					
					String sql2="SELECT * FROM user WHERE UID=?";
					statement=con.prepareStatement(sql2);
					statement.setString(1, id);
					result2=statement.executeQuery();
					if(resultSetSize(result1)!=0||resultSetSize(result2)!=0)
						System.out.println("\n*** It already exists. Please choose another ID!");
					else{
							System.out.print(">> Choose your admin PW (less than 50 letters) : "); //choose pw
							String pw=input2.nextLine();
							System.out.print(">> Input your PW again : ");
							String confirm=input2.nextLine();
							if(pw.equals(confirm))
							{
								String sql = "INSERT INTO admin (ADMINID, ADMINPW) VALUES (?, ?)"; //INSERT
								statement = con.prepareStatement(sql);
								statement.setString(1, id);
								statement.setString(2, pw);
								 
								int rowsInserted = statement.executeUpdate();
								if (rowsInserted > 0) {
								    System.out.println("\n*** A new admin was inserted successfully!\n");
								    
								    break;
								}
							}
							else
								System.out.println("\n*** PW is not correct. Please register again!");			
					}
				}
				con.close();
			}
			catch(Exception e){
				System.out.println(e);
				break;
			}
		}
	}
	public static void userRegister()
	{
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		System.out.println("\n------| User Register Page |------"); //user register
		PreparedStatement statement;
		ResultSet result1, result2;
		while(true)
		{
			try{
				Connection con=getConnection();
				System.out.print("\n>> Choose your user ID (less than 50 letters) : ");
				String id=input2.nextLine(); 
				String sql1="SELECT * FROM admin WHERE ADMINID=?";
				statement = con.prepareStatement(sql1);
				statement.setString(1, id);
				result1 = statement.executeQuery();
				
				String sql2="SELECT * FROM user WHERE UID=?";
				statement=con.prepareStatement(sql2);
				statement.setString(1, id);
				result2=statement.executeQuery();
				
				if(resultSetSize(result1)!=0||resultSetSize(result2)!=0)
					System.out.println("\n*** It already exists. Please choose another ID!");
				else{
					System.out.print(">> Choose your user PW (less than 50 letters) : ");
					String pw=input2.nextLine();
					System.out.print(">> Input your PW again : ");
					String confirm=input2.nextLine();
					if(pw.equals(confirm))
					{
						System.out.print(">> Write your email address : ");
						String email=input2.nextLine();
						String sql = "INSERT INTO user (UID, UPW,UEMAIL) VALUES (?, ?,?)";
						statement = con.prepareStatement(sql);
						statement.setString(1, id);
						statement.setString(2, pw);
						statement.setString(3, email); 
						int rowsInserted = statement.executeUpdate();
						if (rowsInserted > 0) {
						    System.out.println("\n*** A new user is inserted successfully!\n");
						    break;
						}
					}
					else
						System.out.println("\n*** PW is not correct. Please register again!");
				}
				con.close();
			}catch(Exception e){
				System.out.println(e);
				break;
			}
		}
	}
	public static void singerRegister()
	{
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		System.out.println("\n------| Singer Register Page |------");
		PreparedStatement statement;
		ResultSet result1;
		while(true)
		{
			try{
				Connection con=getConnection();
				System.out.print("\n>> Input singer Name (less than 50 letters) : ");
				String name=input2.nextLine(); 
				System.out.print(">> Input singer Type (1. solo /2. group /3. band /4. others) : ");
				int type=input1.nextInt(); 
				System.out.print(">> Input singer's Debutday (for example 19970101) : ");
				String date=input2.nextLine();
				System.out.print(">> Input singer's entertainment (less than 50 letters) : ");
				String entertainment=input2.nextLine(); 

				String sql1="SELECT * FROM singer WHERE SINGERNAME=? and SINGERTYPE=? and DEBUTDAY=? and ENTERTAINMENT=?";
				statement = con.prepareStatement(sql1);
				statement.setString(1, name);
				statement.setInt(2, type);
				statement.setString(3, date);
				statement.setString(4, entertainment);
				result1 = statement.executeQuery();
				
				if(resultSetSize(result1)!=0)
				{
					System.out.println("\n*** It already registered!\n");
					break;
				}
				else{
						String sql = "INSERT INTO singer (SINGERNAME,SINGERTYPE,DEBUTDAY,ENTERTAINMENT) VALUES (?, ?,?,?)";
						statement = con.prepareStatement(sql);
						statement.setString(1, name);
						statement.setInt(2, type);
						statement.setString(3, date); 
						statement.setString(4, entertainment);
						int rowsInserted = statement.executeUpdate();
						if (rowsInserted > 0) {
						    System.out.println("\n*** The new singer is registered successfully!\n");
						    break;
						}
				}
				con.close();
			}catch(Exception e){
				System.out.println(e);
				break;
			}
		}
	}
	public static void musicRegister()
	{
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		
		System.out.println("\n------| Music Register Page |------");
		PreparedStatement statement;
		ResultSet result1, result2;
		while(true)
		{
			try{
				Connection con=getConnection();
				System.out.print("\n>> Input music title (less than 50 letters) : ");
				String mname=input2.nextLine(); 
				
				System.out.println("\n1. rock\n2. pop\n3. balad\n4. hiphop\n5. ost\n6. R&B\n7. k-pop\n8. others");
				System.out.print("\n>> Choose the genre of music (input number) : ");
				int genr=input1.nextInt();
				System.out.print(">> Input Album name : ");
				String album=input2.nextLine();
				
				String sql2="SELECT * FROM singer";
				statement = con.prepareStatement(sql2);
				result2 = statement.executeQuery();
				int save=-1;
				System.out.println("\nSINGERNUM  SINGERNAME     SINGERTYPE      DEBUTDAY       ENTERTAINMENT");
				System.out.println("------------------------------------------------------------------------");
				while(result2.next())
				{
					String display;
					int aa=result2.getInt("SINGERTYPE");
					if(aa==1)
						display="solo";
					else if(aa==2)
						display="group";
					else if(aa==3)
						display="band";
					else
						display="others";
					System.out.printf("%5d    %12s  %13s  %12s  %15s\n",result2.getInt("SINGERNUM"),result2.getString("SINGERNAME"),
					display,result2.getString("DEBUTDAY"),result2.getString("ENTERTAINMENT"));
					save=result2.getInt("SINGERNUM");
				}
				
				System.out.print("\n>> Input SINGERNUM from above list, or input -1 to register new singer : ");
				int singernum=input1.nextInt();
				boolean play=true;
				if(singernum==-1)
				{
					singerRegister();
					singernum=++save;
					System.out.print("new singer is the singer of this music." );
				}
				String sql1="SELECT * FROM music WHERE TITLE=? and GENRE=? and ALBUMNAME=? and SINGERN=?";
				statement = con.prepareStatement(sql1);
				statement.setString(1, mname);
				statement.setString(1, mname);
				statement.setInt(2, genr);
				statement.setString(3, album); 
				statement.setInt(4, singernum);
				result1 = statement.executeQuery();
				
				if(resultSetSize(result1)!=0)
				{
					System.out.println("\n*** It already exists.\n");
					break;
				}
				else
				{
					String sql = "INSERT INTO music (TITLE,GENRE,ALBUMNAME,SINGERN) VALUES (?, ?, ?, ?)";
					statement = con.prepareStatement(sql);
					statement.setString(1, mname);
					statement.setInt(2, genr);
					statement.setString(3, album); 
					statement.setInt(4, singernum);
					int rowsInserted = statement.executeUpdate();
					if (rowsInserted > 0) {
					    System.out.println("\n*** The new music is registered successfully!\n");
					    break;
					}
				}	
				con.close();
			}catch(Exception e){
				System.out.println(e);
				break;
			}
		}
		
	}
	public static void musicList()
	{
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		
		System.out.println("\n------| Music List |------");
		PreparedStatement statement;
		ResultSet result2;
		while(true)
		{
			try{
				Connection con=getConnection();
				System.out.print("\n1. Show by Register order \n2. Show by Popular order \n3. Show by Genre\n4. Exit\n\n>>Input Number : ");
				int choice=input1.nextInt(); 
				if(choice==1)
				{
					System.out.println("---- Show by Register order ----\n");

					String sql2="SELECT * FROM singer,music WHERE SINGERN=SINGERNUM ORDER BY MNUM";
					statement = con.prepareStatement(sql2);
					result2 = statement.executeQuery();
					System.out.println("\n    MUSICNUM        TITLE      ADDNUM        GENRE         ALBUMNAME            SINGER");
					System.out.println("-----------------------------------------------------------------------------------------");
					while(result2.next())
					{
						String display;
						int aa=result2.getInt("GENRE");
						
						if(aa==1)
							display="rock";
						else if(aa==2)
							display="pop";
						else if(aa==3)
							display="balad";
						else if(aa==4)
							display="hiphop";
						else if(aa==5)
							display="ost";
						else if(aa==6)
							display="R&B";
						else if(aa==7)
							display="k-pop";
						else
							display="others";
						System.out.printf("%8d  %15s  %8d  %13s  %16s  %16s\n",result2.getInt("MNUM"),result2.getString("TITLE"),result2.getInt("ADDNUM"),
						display,result2.getString("ALBUMNAME"),result2.getString("SINGERNAME"));
					}
					System.out.println();
					addPlaylist();
				}
				else if(choice==2)
				{
					System.out.println("---- Show by Popular order ----\n"); //ADDNUM means the total number of inclusion to playlist of all users.
					String sql2="SELECT * FROM singer,music WHERE SINGERN=SINGERNUM ORDER BY (ADDNUM)*(-1)";
					statement = con.prepareStatement(sql2);
					result2 = statement.executeQuery();
					System.out.println("\n    MUSICNUM        TITLE      ADDNUM        GENRE         ALBUMNAME            SINGER");
					System.out.println("-----------------------------------------------------------------------------------------");
					while(result2.next())
					{
						String display;
						int aa=result2.getInt("GENRE");
						
						if(aa==1)
							display="rock";
						else if(aa==2)
							display="pop";
						else if(aa==3)
							display="balad";
						else if(aa==4)
							display="hiphop";
						else if(aa==5)
							display="ost";
						else if(aa==6)
							display="R&B";
						else if(aa==7)
							display="k-pop";
						else
							display="others";
						System.out.printf("%8d  %15s  %8d  %13s  %16s  %16s\n",result2.getInt("MNUM"),result2.getString("TITLE"),result2.getInt("ADDNUM"),
						display,result2.getString("ALBUMNAME"),result2.getString("SINGERNAME"));
					}
					System.out.println();
					addPlaylist();
				}
				else if(choice==3)
				{
					System.out.println("---- Show by Genre ----\n");
					String[] display={"","rock","pop","balad","hiphop","ost","R&B","k-pop","others"};
					
					for(int i=1;i<=8;i++)
					{
						String sql2="SELECT * FROM singer,music WHERE SINGERN=SINGERNUM and GENRE=? ";
					
						statement = con.prepareStatement(sql2);
						statement.setInt(1, i);
						result2 = statement.executeQuery();
						System.out.printf("%d. %s\n",i,display[i]);
						System.out.println("\n    MUSICNUM        TITLE      ADDNUM         ALBUMNAME            SINGER");
						System.out.println("-----------------------------------------------------------------------------------------");
						while(result2.next())
						{
							System.out.printf("%8d  %15s  %8d  %16s  %16s\n",result2.getInt("MNUM"),result2.getString("TITLE"),result2.getInt("ADDNUM"),
							result2.getString("ALBUMNAME"),result2.getString("SINGERNAME"));
						}
						System.out.println();
					}
					System.out.println();
					addPlaylist();
				}
				else
					break;
				con.close();
			}catch(Exception e){
				System.out.println(e);
				break;
			}
		}
	}
	public static void addPlaylist()
	{
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
	
		PreparedStatement statement;
		ResultSet result1,result2;
		while(true)
		{
			
			try {
				Connection con = getConnection();
			
				System.out.print("\n1. Add Music to my PlayList\n2. Show singer's information\n3. Exit\n>> Input Number : ");
				int choose=input1.nextInt();
				
				if(choose==1)
				{
					System.out.print("\n>> Input Music Number(MNUM) : ");
					int num=input1.nextInt();
					
					String sql1="SELECT * FROM playlist WHERE UNUM=? and MNUM=?";
					statement = con.prepareStatement(sql1);
					statement.setInt(1, idNum);
					statement.setInt(2,num);
					result1 = statement.executeQuery();
					
					if(resultSetSize(result1)!=0)
					{
						System.out.println("\n*** It already added.\n");
						break;
					}
					System.out.print("\n>> Do you want to set this music as your favorite? (input 'y' or 'yes' if yes) : ");
					String add=input2.nextLine(); 
						
					if(add.equals("yes")||add.equals("y"))
					{
						String sql2="UPDATE music SET ADDNUM=ADDNUM+1 WHERE music.MNUM=?"; //update add number of music 
						statement = con.prepareStatement(sql2);
						statement.setInt(1, num); 
						result2 = statement.executeQuery();
						
						String sql = "INSERT INTO playlist (UNUM,playlist.MNUM,FAVORITE) VALUES (?, ?, ?)";
						statement = con.prepareStatement(sql);
						statement.setInt(1, idNum);
						statement.setInt(2, num);  
						statement.setInt(3, 1);
						int rowsInserted = statement.executeUpdate();
						if (rowsInserted > 0) {
						    System.out.println("\n*** The new music is added to playlist!\n");
						    break;
						}	
					}
					else
					{
						String sql2="UPDATE music SET ADDNUM=ADDNUM+1 WHERE music.MNUM=?"; //update add number of music 
						statement = con.prepareStatement(sql2);
						statement.setInt(1, num); 
						result2 = statement.executeQuery();
						String sql = "INSERT INTO playlist (UNUM,playlist.MNUM,FAVORITE) VALUES (?, ?, ?)";
						statement = con.prepareStatement(sql);
						statement.setInt(1, idNum);
						statement.setInt(2, num); 
						statement.setInt(3, 0);
						int rowsInserted = statement.executeUpdate();
						if (rowsInserted > 0) {
						    System.out.println("\n*** The new music is added to playlist!\n");
						    break;
						}	
					}	
				}
				else if(choose==2)
				{
					System.out.print("\n>> Input Music Number to see information about singer(MUSICNUM) : ");
					int num=input1.nextInt();
					String sql2="SELECT * FROM music,singer WHERE SINGERN=SINGERNUM and MNUM=?";
					statement = con.prepareStatement(sql2);
					statement.setInt(1, num); 
					result2 = statement.executeQuery();
					System.out.println("\nSINGERNUM  SINGERNAME     SINGERTYPE      DEBUTDAY       ENTERTAINMENT");
					System.out.println("------------------------------------------------------------------------");
					while(result2.next())
					{
						String display;
						int aa=result2.getInt("SINGERTYPE");
						if(aa==1)
							display="solo";
						else if(aa==2)
							display="group";
						else if(aa==3)
							display="band";
						else
							display="others";
						System.out.printf("%5d    %12s  %13s  %12s  %15s\n",result2.getInt("SINGERNUM"),result2.getString("SINGERNAME"),
						display,result2.getString("DEBUTDAY"),result2.getString("ENTERTAINMENT"));
					}
					
				}
				else
					break;
				con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public static void purchaseCoupon() // purchase Coupon to buy music
	{
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		PreparedStatement statement;
		ResultSet result1,result2;
		while(true)
		{
			try{
				Connection con=getConnection();
				System.out.println("\n------| Buying Purchase Coupon |------\n");
				System.out.println("1. 5 coupons\n2. 10 coupons\n3. 15 coupons\n4. 20 coupons\n5. Exit");
				System.out.print("\n>> Input number : ");
				int coupon=input1.nextInt(); 
				if(coupon==5)
				{
					System.out.println();
					break;
				}
				System.out.print("\n>> Are you going to pay for it?(input 'y' or 'yes' if yes) : ");
				String pay=input2.nextLine(); 
				
				if(pay.equals("yes")||pay.equals("y"))
				{
					String sql1="SELECT * FROM user WHERE UNUM=?"; //update user's number of coupon
					statement = con.prepareStatement(sql1);
					statement.setInt(1, idNum); 
					result1 = statement.executeQuery();
					result1.last();
					int have=result1.getInt("PURCHASENUM");
					
					//buy coupon - update user's purchaseNum
					String sql2="UPDATE user SET PURCHASENUM=? WHERE UNUM=?"; //update user's number of coupon
					statement = con.prepareStatement(sql2);
					statement.setInt(1, coupon*5+have); 
					statement.setInt(2, idNum); 
					result2 = statement.executeQuery();
					System.out.println("\n*** Coupons are added successfully!");
				}
				else
					System.out.println("\n*** Purchase Failed!");
				con.close();
			}catch(Exception e){
				System.out.println(e);
				break;
			}
		}
	}
	public static void playList()
	{
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		
		PreparedStatement statement;
		ResultSet result1,result2;
		while(true)
		{
			System.out.println("\n------| Playlist |------");
			try{
				Connection con=getConnection();
				String sql2="SELECT * FROM singer,music,playlist WHERE SINGERN=SINGERNUM and playlist.UNUM=? and playlist.MNUM=music.MNUM ";
					statement = con.prepareStatement(sql2);
					statement.setInt(1, idNum);
					result2 = statement.executeQuery();
					System.out.println("\n    MUSICNUM        TITLE          GENRE         ALBUMNAME            SINGER       SELECTDATE");
					System.out.println("------------------------------------------------------------------------------------------------");
					while(result2.next())
					{
						String display;
						int aa=result2.getInt("GENRE");
						
						if(aa==1)
							display="rock";
						else if(aa==2)
							display="pop";
						else if(aa==3)
							display="balad";
						else if(aa==4)
							display="hiphop";
						else if(aa==5)
							display="ost";
						else if(aa==6)
							display="R&B";
						else if(aa==7)
							display="k-pop";
						else
							display="others";
						System.out.printf("%8d  %15s  %13s  %16s  %16s  %15s\n",result2.getInt("MNUM"),result2.getString("TITLE"),
						display,result2.getString("ALBUMNAME"),result2.getString("SINGERNAME"),result2.getDate("SELECTDATE"));
					}
					System.out.print("\n1. Purchase music \n2. Delete music from playlist \n3. show favorite music\n4. Exit\n\n>>Input Number : ");
					int num=input1.nextInt();
					if(num==1)
					{
						sql2="SELECT * FROM user WHERE UNUM=? ";
						statement = con.prepareStatement(sql2);
						statement.setInt(1, idNum);
						result2 = statement.executeQuery();
						
						result2.last();
						int coupon=result2.getInt("PURCHASENUM");
						if(coupon>0)
						{
							System.out.print("\n>> Input Music Number to buy(MNUM) : ");
							int number=input1.nextInt();
							
							sql2="SELECT * FROM purchaselist WHERE USERNUM=? and MUSICNUM=? ";
							statement = con.prepareStatement(sql2);
							statement.setInt(1, idNum);
							statement.setInt(2, number);
							result2 = statement.executeQuery();
							if(resultSetSize(result2)>0)
							{
								System.out.println("*** You've already purchased this music");
							}
							else
							{	
								System.out.print("\n>> Input some comment to this music : ");
								String comment=input2.nextLine();
								String sql = "INSERT INTO purchaselist (MUSICNUM, USERNUM,COMMENT) VALUES (?, ?, ?)";
								statement = con.prepareStatement(sql);
								statement.setInt(1, number);
								statement.setInt(2, idNum); 
								statement.setString(3, comment);
								int rowsInserted = statement.executeUpdate();
								if (rowsInserted > 0) {
								    System.out.println("\n*** You purchased the music! Now you can listen to whole music!\n");
								}
								sql2="UPDATE user SET PURCHASENUM=PURCHASENUM-1 WHERE UNUM=?"; //update add number of music 
								statement = con.prepareStatement(sql2);
								statement.setInt(1, idNum); 
								result2 = statement.executeQuery();
							}
						}
						else
						{
							System.out.print("\n*** You have to buy coupon. Go to 'Buying purchase coupon' page.\n");
							break;
						}
					}
					else if(num==2)
					{
						System.out.print("\n>> Input Music Number to delete(MNUM) : ");
						int number=input1.nextInt();
						String sql1="DELETE FROM playlist WHERE UNUM=? and MNUM=?";
						statement = con.prepareStatement(sql1);
						statement.setInt(1, idNum);
						statement.setInt(2, number);
						result1 = statement.executeQuery();
						System.out.println("\n*** This music is deleted from your playlist successfully!");
					}
					else if(num==3)
					{
						System.out.println("\n---- Show favorite Music ----");
						String sql="SELECT * FROM music,singer,playlist WHERE UNUM=? and SINGERN=SINGERNUM and music.MNUM=playlist.MNUM and FAVORITE=?";
						statement = con.prepareStatement(sql);
						statement.setInt(1, idNum);
						statement.setInt(2,1);
						result2 = statement.executeQuery();
						System.out.println("\n    MUSICNUM        TITLE      ADDNUM        GENRE         ALBUMNAME            SINGER");
						System.out.println("-----------------------------------------------------------------------------------------");
						while(result2.next())
						{
							String display;
							int aa=result2.getInt("GENRE");
							
							if(aa==1)
								display="rock";
							else if(aa==2)
								display="pop";
							else if(aa==3)
								display="balad";
							else if(aa==4)
								display="hiphop";
							else if(aa==5)
								display="ost";
							else if(aa==6)
								display="R&B";
							else if(aa==7)
								display="k-pop";
							else
								display="others";
							System.out.printf("%8d  %15s  %8d  %13s  %16s  %16s\n",result2.getInt("MNUM"),result2.getString("TITLE"),result2.getInt("ADDNUM"),
							display,result2.getString("ALBUMNAME"),result2.getString("SINGERNAME"));
						}
						System.out.println();
						System.out.print("\n>> If you want to exit, press any key : ");
						String exit=input2.nextLine();
					}
					else
						break;
					con.close();
				
			}catch(Exception e){
				System.out.println(e);
				break;
			}
		}
	}
	public static void purchaseList()
	{
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		
		System.out.println("\n------| Purchase List |------");
		PreparedStatement statement;
		ResultSet result1;
		while(true)
		{
			try{
				Connection con=getConnection();
				
				String sql1="SELECT * FROM music,singer,purchaselist WHERE USERNUM=? and SINGERN=SINGERNUM and MUSICNUM=MNUM ";
				statement = con.prepareStatement(sql1);
				statement.setInt(1, idNum);
				result1 = statement.executeQuery();
				System.out.println("\n            SINGERNAME         TITLE       PURCHASEDATE                        COMMENT");
				System.out.println("----------------------------------------------------------------------------------------");
				int total=0;
				while(result1.next())
				{
					total++;
					System.out.printf("%d %20s %13s %18s %30s\n",total,result1.getString("SINGERNAME"),result1.getString("TITLE"),
					result1.getDate("PURCHASEDATE"),result1.getString("COMMENT"));
				}
				if(total==1)
					System.out.printf("\n*** %d song found.\n",total);
				else
					System.out.printf("\n*** %d songs found.\n",total);
				System.out.print("\n>> If you want to exit, press any key : ");
				String exit=input2.nextLine(); 
				System.out.println();
				con.close();
				break;	
			}catch(Exception e){
				System.out.println(e);
				break;
			}
		}
	}
	public static void login()
	{
		System.out.println("\n--------| Login |--------");
		input1=new Scanner(System.in);
		input2=new Scanner(System.in);
		System.out.print("\nID : ");
		String id=input2.nextLine();
		System.out.print("PW : ");
		String pw=input2.nextLine();
		System.out.println();
		int test=0;
		try{
			Connection con=getConnection();
			PreparedStatement statement;
			ResultSet result1, result2;
			
			String sql1="SELECT * FROM admin WHERE ADMINID=?";
			statement = con.prepareStatement(sql1);
			statement.setString(1, id);
			result1 = statement.executeQuery();
			
			String sql2="SELECT * FROM user WHERE UID=?";
			statement = con.prepareStatement(sql2);
			statement.setString(1, id);
			result2=statement.executeQuery();
			
			if(resultSetSize(result1)==1)
			{	
				result1.last();
				if(pw.equals(result1.getString("ADMINPW")))
				{
					test=1;
				}
			}
			else if(resultSetSize(result2)==1)
			{
				result2.last();
				if(pw.equals(result2.getString("UPW")))
				{
					test=2;
					idNum=result2.getInt("UNUM");
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		if(test==1)//login as admin
		{
			System.out.println("\n*** Login success as an admin!");
			while(true)
			{
				System.out.println("\n--------|| Admin Page ||--------\n\n1. Singer Register/Delete Singer\n2. Music Register/Delete Music\n3. Exit");
				System.out.print("\n>> Input number : ");
				int number=input1.nextInt();
				System.out.println();
				if(number==1)
				{
					try{
						System.out.println("\n--------| Singer Register/Delete Singer |--------");

						Connection con=getConnection();
						PreparedStatement statement;
						ResultSet result2;
						String sql2="SELECT * FROM singer";
						statement = con.prepareStatement(sql2);
						result2 = statement.executeQuery();
						System.out.println("\nSINGERNUM  SINGERNAME     SINGERTYPE      DEBUTDAY       ENTERTAINMENT");
						System.out.println("------------------------------------------------------------------------");
						while(result2.next())
						{
							String display;
							int aa=result2.getInt("SINGERTYPE");
							if(aa==1)
								display="solo";
							else if(aa==2)
								display="group";
							else if(aa==3)
								display="band";
							else
								display="others";
							System.out.printf("%5d    %12s  %13s  %12s  %15s\n",result2.getInt("SINGERNUM"),result2.getString("SINGERNAME"),
							display,result2.getString("DEBUTDAY"),result2.getString("ENTERTAINMENT"));
						}
						System.out.println("\n1. Singer Register\n2. Delete Singer\n3. Exit");
						System.out.print("\n>>Input Number : ");
						number=input1.nextInt();
						if(number==1)
							singerRegister();
						else if(number==2)
							DeleteSinger();
						else
							System.out.println("\n*** Exit from this page");
						con.close();	
					}catch(Exception e){
						System.out.println(e);
						break;
					}	
				}
				else if(number==2)
				{
					try{
						System.out.println("\n--------| Music Register/Delete Music |--------");

						Connection con=getConnection();
						PreparedStatement statement;
						ResultSet result2;
						String sql2="SELECT * FROM singer,music WHERE SINGERN=SINGERNUM";
						statement = con.prepareStatement(sql2);
						result2 = statement.executeQuery();
						System.out.println("\n    MUSICNUM        TITLE      ADDNUM        GENRE         ALBUMNAME            SINGER");
						System.out.println("-----------------------------------------------------------------------------------------");
						while(result2.next())
						{
							String display;
							int aa=result2.getInt("GENRE");
							
							if(aa==1)
								display="rock";
							else if(aa==2)
								display="pop";
							else if(aa==3)
								display="balad";
							else if(aa==4)
								display="hiphop";
							else if(aa==5)
								display="ost";
							else if(aa==6)
								display="R&B";
							else if(aa==7)
								display="k-pop";
							else
								display="others";
							System.out.printf("%8d  %15s  %8d  %13s  %16s  %16s\n",result2.getInt("MNUM"),result2.getString("TITLE"),result2.getInt("ADDNUM"),
							display,result2.getString("ALBUMNAME"),result2.getString("SINGERNAME"));
						}
						System.out.println("\n1. Music Register\n2. Delete Music\n3. Exit");
						System.out.print("\n>>Input Number : ");
						number=input1.nextInt();
						if(number==1)
							musicRegister();//if singer isn't registered yet, call singerRegister() function
						else if(number==2)
							DeleteMusic();
						else
							System.out.println("\n*** Exit from this page");
						con.close();
					}catch(Exception e){
						System.out.println(e);
						break;
					}		
				}
				else
					break;
			}
		}
		else if(test==2)//login as user
		{
			System.out.println("\n*** Login success as a user!");
			while(true)
			{
				System.out.println("\n--------| User Page |--------\n\n1. Show music List/Add music to Playlist"
				+ "\n2. Buying Purchase coupon\n3. Show Playlist/Purchase music/Delete music\n4. Show Purchase list\n5. My Page\n6. Logout");
				System.out.print("\n>> Input number : ");
				int number=input1.nextInt();
				System.out.println();
				if(number==1)
				{
					musicList();
				}
				else if(number==2)
				{
					purchaseCoupon();
				}
				else if(number==3)
				{
					playList();
				}
				else if(number==4)
				{
					purchaseList();
				}
				else if(number==5)
				{
					mypage();
				}
				else
					break;
			}
		}
		else
			System.out.println("\n*** Login Fail!\n");
	}
	public static void mypage()
	{
		while(true)
		{
			try
			{
				System.out.println("\n--------| My Page |--------");
				input1=new Scanner(System.in);
				input2=new Scanner(System.in);
				
				System.out.print("\nInput your PW to confirm : ");
				String pw=input2.nextLine();
				Connection con=getConnection();
				PreparedStatement statement;
				ResultSet result1;
				
				String sql1="SELECT * FROM user WHERE UNUM=?";
				statement = con.prepareStatement(sql1);
				statement.setInt(1, idNum);
				result1 = statement.executeQuery();
				result1.last();
				
				if(pw.equals(result1.getString("UPW")))
				{
					System.out.printf("\no User ID : %s\no User password : %s\no User email : %s\no Number of Purchase Coupons : %d\n",
					result1.getString("UID"),result1.getString("UPW"),result1.getString("UEMAIL"),result1.getInt("PURCHASENUM"));
					System.out.print("\n>> If you want to exit, press any key : ");
					String exit=input2.nextLine(); 
					break;
				}
				else
					System.out.println("\n*** Confirm your password!");
				
				con.close();
			}catch(Exception e){
				System.out.println(e);
				break;
			}
		}
	}
	public static void DeleteSinger() 
	{
		try
		{
			System.out.println("\n--------| Delete Singer |--------");
			input1=new Scanner(System.in);
			input2=new Scanner(System.in);
			System.out.print("\n>>Input Singer Number to delete : ");
			int number=input1.nextInt();
			
			Connection con=getConnection();
			PreparedStatement statement;
			ResultSet result1;
			
			String sql1="DELETE FROM singer WHERE SINGERNUM=?";
			statement = con.prepareStatement(sql1);
			statement.setInt(1, number);
			result1 = statement.executeQuery();

			System.out.println("\n*** Singer is deleted Successfully");
			
			con.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void DeleteMusic() // admin can delete music
	{
		try
		{
			System.out.println("\n--------| Delete Music |--------");
			input1=new Scanner(System.in);
			input2=new Scanner(System.in);
			System.out.print("\n>>Input Music Number to delete : ");
			int number=input1.nextInt();
			
			Connection con=getConnection();
			PreparedStatement statement;
			ResultSet result1;
			
			String sql1="DELETE FROM music WHERE MNUM=?";
			statement = con.prepareStatement(sql1);
			statement.setInt(1, number);
			result1 = statement.executeQuery();

			System.out.println("\n*** Music is deleted Successfully!");
			
			con.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static int resultSetSize(ResultSet rs) // get the size of record
	{
		int count=0;
		try {
			while(rs.next())
				count++;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
