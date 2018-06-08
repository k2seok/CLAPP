import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lib.MatrixToImageConfig;
import lib.MatrixToImageWriter;

public class Server_chatSample {
	private ServerSocket mServerSocket;
	private Socket mSocket;

	private BufferedReader mIn; // Reader for data in to server
	private PrintWriter mOut; // Writer for server data out client

	public Server_chatSample() {
		try {
			final int port = 6666;
			mServerSocket = new ServerSocket(port);
			InetAddress ip = InetAddress.getLocalHost();

			System.out.printf("Server launch! making QRcode");
			String path = "C:\\Users\\Administrator\\Google ����̺�\\clapp\\QRCode";
			String fileName = new SimpleDateFormat("yyMMddhhmmss").format(new Date(System.currentTimeMillis()));
			String url = ip.getHostAddress();
			makeQRCode(path, fileName + ".png", url);

			System.out.printf("Server run!! %n ip : %s , port %d%n", ip.getHostAddress(), port);

			mSocket = mServerSocket.accept();

			System.out.println("server get data! checking data...");
			mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			mOut = new PrintWriter(mSocket.getOutputStream());

			// TODO check input data
			String data = null;
			do {
				//// listen
				
				Thread.sleep(5000);
				data = mIn.readLine();
				System.out.println(data == null ? "isNull" : data);
				
				mOut.println("I'm server, your message received!");
				mOut.flush();

			} while (!data.equals("exit"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				mServerSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void makeQRCode(String path, String fileName, String url) {
		try {
			File file = null;
			// ť���̹����� ������ ���丮 ����
			file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			// �ڵ��νĽ� ��ũ�� URL�ּ�
			String codeurl = new String(url.getBytes("UTF-8"), "ISO-8859-1");
			// ť���ڵ� ���ڵ� ����
			int qrcodeColor = 0xFF2e4e96;
			// ť���ڵ� ������
			int backgroundColor = 0xFFFFFFFF;

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			// 3,4��° parameter�� : width/height�� ����
			BitMatrix bitMatrix = qrCodeWriter.encode(codeurl, BarcodeFormat.QR_CODE, 200, 200);
			//
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			// ImageIO�� ����� ���ڵ� ���Ͼ���
			ImageIO.write(bufferedImage, "png", new File(path + "\\" + fileName));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server_chatSample server = new Server_chatSample();
	}
}