package BanterBoxPackage;
import java.io.*;
import java.net.*;
import java.util.*;



public class BanterBoxServer {
	public static void main(String[] args) {
        BanterBoxServer server = new BanterBoxServer();
        server.start(55284);
	}
	private ArrayList<BBServerClient> online = new ArrayList<>();
	private HashSet<String> onlineUsernames = new HashSet<>();
	private BufferedReader reader;
	private PrintWriter writer;
	Hashtable<String, String> BBLoginInfo = new Hashtable<>();
	public ArrayList<BBServerClient> getOnline(){
		return online;
	}
	public HashSet<String> getOnlineUsernames(){
		return onlineUsernames;
	}
	public Hashtable <String, String> getBBLoginInfo(){
		return BBLoginInfo;
	}
	public void start (int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is ready");
            Scanner loginInfo = new Scanner("BBLoginInfo.txt");
            FileWriter loginInfoWriter = new FileWriter("BBLoginInfo.txt");
            while(loginInfo.hasNext()) {
    			String tempUser = loginInfo.nextLine();
    			String tempPass = loginInfo.nextLine();
    			BBLoginInfo.put(tempUser, tempPass);
    		}
            while (true) {
                Socket clientSocket = serverSocket.accept();
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                
                	new Thread(() -> {
    	                BBServerClient temp = LogIntoAccount(BBLoginInfo, reader, writer, clientSocket, this, loginInfoWriter);
    	                online.add(temp);
    	                onlineUsernames.add(temp.getUser());
    	                temp.start();
    	                
    	            }).start();
            	
//              System.out.println(name+" connected: " + clientSocket.getInetAddress().getHostAddress());
//              ClientList ClientList = new ClientList(clientSocket, this, name);
//              clients.add(ClientList);
//              ClientList.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

	public BBServerClient LogIntoAccount(Hashtable<String, String> loginInfo, BufferedReader reader, PrintWriter writer, Socket socket, BanterBoxServer server, FileWriter loginInfoWriter) {
		int action = 0;
		try{
			while(action == 0) {
		
			action = reader.read();
			if(action == 1) {
				while(action ==1) {
					String username = reader.readLine();
					if(loginInfo.containsKey(username)) {
						action = 3;
						while(action == 3) {
							writer.write("success");
							String Pass = reader.readLine();
							if(Pass.equals(loginInfo.get(username))) {
								writer.write("success");
								return new BBServerClient(socket, server, username);
							}
							else if(Pass.equals("back")) {
								writer.write("back");
								action = 1;
							}
							else {
								writer.write("unsuccessful");
							}
						}
					}
					else if(username.equals("back")) {
						writer.write("back");
						action = 0;
					}
					else {
						writer.write("unsuccessful");
					}
				}
				}
			else if(action == 2) {
				while(action == 2) {
					String tempName = reader.readLine();
					if(tempName.equals("back")) {
						writer.write("back");
						action = 0;
					}
					else if(!loginInfo.containsKey(tempName)) {
						writer.write("successful");
						String Pass = reader.readLine();
						loginInfoWriter.write(tempName+"\n"+Pass);
						return new BBServerClient(socket, server, tempName);
					}
					else {
						writer.write("unsuccessful");
					}
				}
			}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	
	
	return null;
}

}


class BBServerClient extends Thread{
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private BanterBoxServer server;
    public String user;
    private int page;
    private String Partner;
    private HashSet<String> contacts = new HashSet<>();

    public BBServerClient(Socket socket, BanterBoxServer server, String name) throws IOException {
        clientSocket = socket;
        this.server = server;
        user = name;
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        BanterBoxOperations.fillContacts(user+"contacts.txt", contacts);
    }
    public String getUser() {
    	return user;
    }
    public int getPage() {
    	return page;
    }
    public String getPartner() {
    	return Partner;
    }

	@Override
	public void run() {
		
		   
		int selectionOption = 0;
		try {
			while(selectionOption == 0) {
				selectionOption = reader.read();
				if(selectionOption == 1) {
					BanterBoxOperations.printNotifications(user+"notifications.txt", writer);
				}
				else if(selectionOption == 2) {
					while(selectionOption == 2) {
						String recipient = reader.readLine();
						if(contacts.contains(recipient)) {
							writer.write(1);
							boolean ifFirst = true;
							while(true) {
								int stillGoing = reader.read();
								if(stillGoing == 1) {
									String message = reader.readLine();
									String fileName = user+"to"+recipient+".txt";
									String fileName2 = recipient+"to"+user+".txt";
									if(!BanterBoxOperations.ifFileExists(fileName)) {
										BanterBoxOperations.makeFile(fileName);
									}
									FileWriter fw = new FileWriter(fileName);
									FileWriter fw2 = new FileWriter(fileName2);
									
									if(ifFirst) {
										fw.write(user+ ": "+ message+"\n");
										fw2.write(user+ ": "+ message+"\n");
										
										FileWriter fwtemp = new FileWriter(recipient+"notifications.txt");
										fwtemp.write(user+"\n");
										fwtemp.close();
										ifFirst = false;
									}
									fw.write(message+"\n");
									fw.close();
									fw2.write(message+"\n");
									fw2.close();
								}
								else {
									selectionOption = 0;

									break;
								}
							}

						}
						else if(recipient.equals("back")) {
							writer.write(2);
							selectionOption = 0;
						}
						else {
							writer.write(0);
						}	
					}
				} 
				else if(selectionOption == 3) {
					while(selectionOption == 3) {
						String recipient = reader.readLine();
						if(contacts.contains(recipient)) {
							writer.write(1);
							int lines = reader.read();
							BanterBoxOperations.readLastLines(user+"to"+recipient+".txt",lines,writer);
							selectionOption = 0;
							
						}
						else if(recipient.equals("back")) {
							writer.write(2);
							selectionOption = 0;
						}
						else {
							writer.write(0);
						}	
					}
				}
				else if(selectionOption == 4) {
					while(selectionOption == 4) {
						int contactOption = reader.read();
						if(contactOption ==1) {
							for (String element : contacts) {
								writer.write(1);
								writer.write(element);
							}
							writer.write(0);
						}
						else if( contactOption ==2) {
							String request = reader.readLine();
							if(server.getBBLoginInfo().containsKey(request)) {
								writer.write(1);
								if(BanterBoxOperations.ifFileExists(request+"contactrequests.txt")) {
									FileWriter fileWriter = new FileWriter(request+"contactrequests.txt");
									fileWriter.write(user);
								}
								else {
									BanterBoxOperations.makeFile(request+"contactrequests.txt");
									FileWriter fileWriter = new FileWriter(request+"contactrequests.txt");
									fileWriter.write(user);
								}
							}
							else {
								writer.write(0);
							}
							
						}
						else if(contactOption == 3) {
							if(BanterBoxOperations.ifFileExists(user+"contactrequests.txt")) {
								Scanner contactRequestReader = new Scanner(new File(user+"contactrequests.txt"));
								while(contactRequestReader.hasNext()) {
									writer.write(1);
									String temp = contactRequestReader.nextLine();
									writer.write(temp);
									int AorR = reader.read();
									if(AorR == 1) {
										FileWriter tempWriter = new FileWriter(temp+"contacts.txt");
										tempWriter.write(user);
										tempWriter.close();
									}
								}
								writer.write(0);
								File del = new File(user+"contactrequests.txt");
								del.delete();
							}
							else {
								writer.write(1);
							}
						}
						else {
							selectionOption = 0;
						}
					}
				}
				else {
					break;
				}
			}
			
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(user+": " + message);
               // server.broadcastMessage(user+" : " + message, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
            BanterBoxOperations.removeClient(user, server.getOnline(), server.getOnlineUsernames());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            	}
        	}
		}
	

}