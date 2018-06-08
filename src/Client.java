import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private Socket mSocket;

	private BufferedReader mIn;
	private PrintWriter mOut;

	public Client(String ip, int port) {
		try {
			mSocket = new Socket(ip, port);
			System.out.println(ip + "connect try...");

			mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			mOut = new PrintWriter(mSocket.getOutputStream());

			Scanner sc = new Scanner(System.in);
			do {
				System.out.print("message : ");
				String s = sc.nextLine();
				mOut.println(s);
				mOut.flush();
				System.out.println(s + "  <- send success");
				System.out.println("server : " + mIn.readLine());
			} while (true);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				mSocket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {

		try {

			InetAddress ip = InetAddress.getLocalHost();
			int port = 6666;

			System.out.println("connecting... + " + ip + ":" + port);

			new Client(ip.getHostAddress(), port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}