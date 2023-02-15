import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.io.*;

public class ServerMain {


	public static void main(String[] args) {

		HashMap<String, String> map = new HashMap<>(); // HashMap to to store movies

		try {
			FileInputStream fis = new FileInputStream("Movies.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);

			map = (HashMap<String, String>) ois.readObject(); 

			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();

		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			
		}

		try {
			ServerSocket server = new ServerSocket(5555, 50);

			

			while (true) {
				System.out.println("Accepting Connection...");
				System.out.println("Local Address :" + server.getInetAddress() + " Port:" + server.getLocalPort());

				Socket sock = server.accept();

				// streams to read and write objects
				ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
				ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());

				String strin = (String) instream.readObject();

				// If client sends "BEGIN"
				if (strin.equals("BEGIN")) {

					// server sends "LISTENING"
					outstream.writeObject("LISTENING");
					outstream.flush();

					do {
						
						strin = (String) instream.readObject();

						// if client sends "RQ_INSERT"
						if (strin.equals("RQ_INSERT")) {

							Movie movie = (Movie) instream.readObject(); // create Movie Object

							map.put(movie.getTitle(), movie.getInformation()); // add Movie information to HashMap
							System.out.println(map);

							// server sends OK
							outstream.writeObject("OK");
							outstream.flush();

							strin = "";

							// save HashMap to an Object File
							try {

								FileOutputStream fos = new FileOutputStream("Movies.dat");

								ObjectOutputStream oos = new ObjectOutputStream(fos);
								
								
								oos.writeObject(map);
								oos.close();
								fos.close();

							} catch (IOException ioe) {
								ioe.printStackTrace();
							}

						}

						// if client sends "RQ_SEARCH"
						if (strin.equals("RQ_SEARCH")) {
							
							boolean foundInTitle = false;
							boolean foundInDirector = false;

							strin = (String) instream.readObject();

							// search with the title
							for (String title : map.keySet()) {

								// if instream equals the title
								if (strin.equals(title)) {
									outstream.writeObject("Title : "+title + " \nInformation : " + map.get(title)+"\n");
									outstream.flush();
									foundInTitle = true;
								}

							}
							
							if(strin.equals("ALL")) {
								for (String title : map.keySet()) {
									outstream.writeObject("Title : "+title + " \nInformation : " + map.get(title)+"\n");
									outstream.flush();
									foundInTitle = true;
									foundInDirector = true;
								}
							}

							// search with the director
							for (Entry<String, String> entry : map.entrySet()) {
								String[] directors = entry.getValue().split(","); // create an array to store the value
																					// of the entry splitted by ','

								if (directors[0].equals(strin)) {
									outstream.writeObject((String) ("Title : "+entry.getKey() + " \nInformation : " + entry.getValue()+"\n"));
									outstream.flush();
									foundInDirector = true;
								}

							}

							if (!foundInTitle && !foundInDirector) {
								// send "NO_RECORD"
								outstream.writeObject(new String("NO_RECORD"));
								outstream.flush();
							}

							// send "OK"
							outstream.writeObject("OK");
							outstream.flush();
							
							strin = "";

						}

					} while (!strin.equals("END")); // END = terminate the conversation
				}

				else {
					// not following the protocol
					outstream.writeObject("Not welcome");
					outstream.flush();
				}

				instream.close();
				outstream.close();
				sock.close();

				System.out.println("Connection Closing...");
				break;
			}

		} catch (Exception ex) {
			System.out.println("Error during I/O");
			ex.getMessage();
			ex.printStackTrace();
		}

	}

}