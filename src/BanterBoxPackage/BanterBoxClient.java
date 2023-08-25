package BanterBoxPackage;

import java.io.*;
import java.net.*;
import java.util.*;

public class BanterBoxClient {

		public static void main(String[] args) {
			BanterBoxOperations Operations = new BanterBoxOperations();
			String user = null;
			Scanner scanner = new Scanner(System.in);
	        try (Socket socket = new Socket(args[1], 55284);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
	        	int choice = 0;
	        	while(choice == 0) {
	        		System.out.println("Enter 1 to login to an existing account, Enter 2 to sign up and create an account");
	        		choice = reader.read();
	        		writer.write(choice);
	        		if(choice == 1) {
	        			while(choice == 1) {
	        				System.out.println("What is your username?(type back to go back)");
		        			String send = scanner.nextLine();
		        			String feedBack = reader.readLine();
		        			if(feedBack.equals("successful")) {
		        				choice =3;
		        				while(choice == 3) {
		        					System.out.print("(type back to go back) \n password: ");
		        					String Pass = scanner.nextLine();
		        					writer.write(Pass);
		        					feedBack = reader.readLine();
		        					if(feedBack.equals("successful")) {
		        						System.out.println("\n Logged In!");
		        						choice =4;
		        					}
		        					else if(feedBack.equals("back")) {
		        						choice = 1;
		        					}
		        					else {
		        						System.out.println("Incorrect Username or Password");
		        					}
		        				}
		        			}
		        			else if(feedBack.equals("back")) {
		        				choice = 0;
		        				break;
		        			}
		        			else {
		        				System.out.println("Not a Valid Username");
		        			}	
	        			}
	        			
	        		}
	        		else if(choice == 2) {
	        			while(choice == 2) {
	        				System.out.println("Please enter a username (type back to go back)");
	        				String tempName = scanner.nextLine();
	        				writer.write(tempName);
	        				String feedBack = reader.readLine();
	        				if(feedBack.equals("successful")) {
	        					System.out.println("enter password (making password 'back' is not allowed");
	        					while(true) {
	        						String Pass = scanner.nextLine();
	        						if(Pass.equals("back")) {
	        							System.out.println("What did I just say");
	        						}
	        						else {
	        							writer.write(Pass);
	        							System.out.println("Account Made!");
	        							choice =4;
	        						}
	        					}
	        				}
	        				else if(feedBack.equals("back")) {
	        					choice = 0;
	        				}
	        				else {
	        					System.out.println("Sorry that username is already taken");
	        				}
	        			}
	        		}
	        		else {
	        			System.out.println("Please choose one of the following options");
	        		}
	        	}
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	            new Thread(() -> {
	                try {
	                	int selectionOption = 0;
	                	while(selectionOption == 0) {
	                		System.out.println("Press 1 to check to see who you have messages from Press 2 to send a message Press 3 to view the History of a conversation");
	                		System.out.println("Press 4 to view contacts");
	                		selectionOption = scanner.nextInt();
	                		writer.write(selectionOption);
	        				if(selectionOption == 1) {
	        					while(true) {
	        						int going = reader.read();
	        						if(going == 1) {
	        							String person = reader.readLine();
	        							System.out.println(person);
	        						}
	        						else {
	        							break;
	        						}
	        					}
	        				}
	        				else if(selectionOption == 2) {
	        					while(selectionOption ==2) {
	        						System.out.println("Who would you like to message?");
		        					String recipient = scanner.nextLine();
		        					writer.write(recipient);
		        					int feedBack = reader.read();
		        					if(feedBack == 0) {
		        						System.out.println(recipient+" is not someone in your contacts, please try again \n type 'back' to go back");
		        					}
		        					else if(feedBack == 1) {
		        						System.out.println("What would you like to say to them? \n type 'back' to go back when you are finished");
		        						while(true){
		        							String intake = scanner.nextLine();
		        							if(intake.equals("back")) {
		        								writer.write(0);
		        								selectionOption = 0;
		        								break;
		        							}
		        							else {
		        								writer.write(1);
		        								writer.write(intake);
		        							}
		        						}
		        					}
		        					else {
		        						selectionOption = 0;
		        					}
	        					}
	        				
	        				} 
	        				else if(selectionOption == 3) {
	        					while(selectionOption == 3) {
	        						System.out.println("Who would you like to view your history of messgaes with?");
		        					String recipient = scanner.nextLine();
		        					writer.write(recipient);
		        					int feedBack = reader.read();
		        					if(feedBack == 0) {
		        						System.out.println(recipient+" is not someone in your contacts, please try again");
		        					}
		        					else if(feedBack == 1) {
		        						System.out.println("How many lines of text do you want to go back and see");
		        						int lines = scanner.nextInt();
		        						writer.write(lines);
		        						while(true) {
		        							int hasLine = reader.read();
		        							if(hasLine == 1) {
		        								String print = reader.readLine();
		        								System.out.println(print);
		        							}
		        							else {
		        								selectionOption = 0;
		        								break;
		        							}
		        						}
		        					}
		        					else {
		        						selectionOption = 0;
		        					}
	        					}
	        					
	        				}
	        				else if(selectionOption == 4) {
	        					while(selectionOption ==4) {
	        						System.out.println("Press 1 to see contacts, Press 2 to request a contact, Press 3 to see contact invites, Press 4 to go back");
		        					int contactOption = scanner.nextInt();
		        					writer.write(contactOption);
		        					if(contactOption ==1) {
		        						while(true) {
		        							int hasNext = reader.read();
		        							if(hasNext == 1) {
		        								String print = reader.readLine();
		        								System.out.println(print);
		        							}
		        							else {
		        								selectionOption = 0;
		        								break;
		        							}
		        						}
		        					}
		        					else if( contactOption ==2) {
		        						System.out.println("Who would you like to request?");
		        						String request = scanner.nextLine();
		        						writer.write(request);
		        						int feedBack = reader.read();
		        						if(feedBack == 1) {
		        							System.out.println("Requast sent!");
		        						}
		        						else {
		        							System.out.println("Sorry could not find anyone with that username");
		        							selectionOption = 0;
		        						}
		        					}
		        					else if(contactOption == 3) {
		        						int result = reader.read();
		        						if(result == 1) {
		        							int hasMore = reader.read();
		        							if(hasMore == 1) {
		        								String name  = reader.readLine();
		        								System.out.println("Press 1 to accept "+ name+"  and press 0 to reject");
		        								while(true) {
		        									int tempChoice = scanner.nextInt();
			        								if(tempChoice == 1) {
			        									writer.write(1);
			        									break;
			        								}
			        								else if(tempChoice == 0) {
			        									writer.write(0);
			        									break;
			        								}
			        								else {
			        									System.out.println("please choose a viable option");
			        								}
		        								}
		        							}
		        							else {
		        								selectionOption = 0;
		        							}
		        						}
		        						else {
		        							System.out.println("You have no new contact requests!");
		        							selectionOption = 0;
		        						}
		        					}
		        					else if(contactOption == 4) {
		        						selectionOption = 0;
		        					}
		        					else {
		        						System.out.println("please choose a Valid Option!");
		        					}
	        					}
	        					
	        				}
	        				else {
	        					break;
	        				}
	                	}
	                    String message;
	                    while ((message = reader.readLine()) != null) {
	                        System.out.println(message);
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }).start();
	            
	        }
	        catch(Exception e) {
	        	System.out.print(e);
	        }
			
			
			scanner.close();
	}
	
}


