import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lib.MatrixToImageConfig;
import lib.MatrixToImageWriter;

public class DummyTest {

	// @Test
	public void test() {
		Calendar day = Calendar.getInstance(Locale.KOREA);
		System.out.println(new Date(day.getTimeInMillis()));
		// System.out.println(day.get(Calendar.MINUTE));
		System.out.println(day.get(Calendar.YEAR));
		System.out.println(day.get(Calendar.MONTH) + 1); // start 0
		System.out.println(day.get(Calendar.DAY_OF_MONTH));
		System.out.println(day.get(Calendar.HOUR));
		System.out.println(day.get(Calendar.MINUTE));

		// System.out.println(new
		// Calendar.getInstance().getTimeZone().toString());
		// System.out.println(new Date(System.currentTimeMillis()).);
		// System.out.println(new Date(new java.util.Date()));
	}

	private final String ip = "192.168.56.1";

	// @Test
	public void QRTest() {

		String path = "C:\\Users\\Administrator\\Google ����̺�\\clapp\\QRCode";
		String fileName = new SimpleDateFormat("yyMMddhhmmss").format(new Date(System.currentTimeMillis()));
		System.out.println(fileName);
		makeQRCode(path, fileName += "png");

	}

	private static void makeQRCode(String path, String fileName) {
		try {
			File file = null;
			// ť���̹����� ������ ���丮 ����
			file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			// �ڵ��νĽ� ��ũ�� URL�ּ�
			String codeurl = new String(
					"https://drive.google.com/drive/folders/1F0YDIWRVMf5VyH-AhB5PJ_5TxSAtUye0".getBytes("UTF-8"),
					"ISO-8859-1");
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

	// @Test
	public void connectionTest() {
		// InetAddress ip = InetAddress.getLocalHost();
		String ip = "70.12.110.184"; // temp ip
		int port = 6666;

		System.out.println("connecting... + " + ip + ":" + port);

		new Client(ip, port);
		// new Client(ip.getHostAddress(), port);
	}

	@Test
	public void timeTest() {
		// long time = System.currentTimeMillis();
		// String str = new SimpleDateFormat("yyMMddhhmmss").format(new
		// Date(time));
		// System.out.println(str);
		String fileName = new SimpleDateFormat("yyMMddhhmmss").format(new java.util.Date(System.currentTimeMillis()));
		System.out.println(fileName);
	}

}
