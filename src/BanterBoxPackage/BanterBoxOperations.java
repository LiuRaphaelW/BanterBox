package BanterBoxPackage;

import java.io.*;
import java.net.*;
import java.util.*;

public class BanterBoxOperations {
	public static void readFile(File file) {
		
	}
	public static void transferFile(File fileT, File fileR) {
		
	}
	public static boolean containsUsername(ArrayList<String> usernames, String username) {
		if(usernames.size() ==0) {
			return false;
		}
		for(int i = 0 ; i< usernames.size(); i++) {
			if(usernames.get(i).equals(username)) {
				return true;
			}
		}
		return false;
	}
	 public static void readLastLines(String filePath, int linesToRead, PrintWriter writer) throws IOException {
	        Deque<String> lastLines = new ArrayDeque<>();
	        if(ifFileExists(filePath)) {
	        	try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r")) {
		            long fileLength = randomAccessFile.length();
		            long currentPosition = fileLength - 1;
		            int linesCount = 0;
		            StringBuilder currentLine = new StringBuilder();
		            
		            while (currentPosition >= 0 && linesCount < linesToRead) {
		                randomAccessFile.seek(currentPosition);
		                int readByte = randomAccessFile.read();
		                
		                if (readByte == '\n') {
		                    lastLines.offerFirst(currentLine.reverse().toString());
		                    currentLine.setLength(0);
		                    linesCount++;
		                } else {
		                    currentLine.append((char) readByte);
		                }
		                
		                currentPosition--;
		            }
		            
		            for (String line : lastLines) {
		            	writer.write(1);
		                writer.write(line);
		            }
		            writer.write(0);
		        }
	        }
	        
	    }
	 public static void clearFile(String filePath) throws IOException {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
	            // Writing nothing to the file effectively clears its content
	            // The second parameter "false" indicates not to append, but to start fresh
	        }
	    }
	public static File makeFile(String filePath) throws IOException {
		File file = new File(filePath);
		if(file.exists()) {
			return file;
		}
		else {
			file.createNewFile();
			return file;
		}
		
	}
	public static boolean ifFileExists(String fileName) {
		File file  = new File(fileName);
		if(file.exists()) {
			return true;
		}
		return false;
	}
	public static boolean isOnline(ArrayList<BBServerClient> trackSC, HashSet<String> track, String sender, String reciever) {
		if(!track.contains(reciever)) {
			return false;
		}
		for(int i = 0; i < trackSC.size(); i++) {
			if(trackSC.get(i).getUser().equals(reciever)) {
				if(trackSC.get(i).getPartner().equals(sender)) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	public static void initalSendDelayed(String message, String Filename) throws IOException {
		File buffer = makeFile(Filename);
		FileWriter writer = new FileWriter(Filename+"\n");
	}
	public static void updateMessages(String StoR, String RtoS) {
		if(ifFileExists(StoR)) {
			try {
				makeFile(RtoS);
				Scanner readerStoR = new Scanner (new File(StoR));
				FileWriter writerRtoS = new FileWriter(RtoS);
				while(readerStoR.hasNext()) {
					String buff = readerStoR.nextLine();
					writerRtoS.write(buff);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void removeClient(String userName, ArrayList<BBServerClient> trackSC, HashSet<String> track) {
		track.remove(userName);
		for(int i = 0 ; i< trackSC.size(); i++) {
			if(trackSC.get(i).getUser().equals(userName)) {
				trackSC.remove(i);
				return;
			}
		}
	}
	public static void printNotifications(String filePath, PrintWriter writer) {
		if(ifFileExists(filePath)) {
			File file = new File(filePath);
			
			Scanner fileReader;
			try {
				fileReader = new Scanner(file);
				HashSet<String> names = new HashSet<>();
				while(fileReader.hasNext()) {
					String name = fileReader.nextLine();
					if(!names.contains(name)) {
						writer.write(1);
						writer.write(name);
						names.add(name);
					}
				}
				writer.write(0);;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			writer.write(1);
			writer.write("You have 0 new messages");
			writer.write(0);
		}
	}
	public static void fillContacts(String filePath, HashSet<String> contacts) {
		if(ifFileExists(filePath)) {
			File file = new File(filePath);
			try {
				Scanner fileReader = new Scanner(file);
				while(fileReader.hasNext()) {
					String temp = fileReader.nextLine();
					contacts.add(temp);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
