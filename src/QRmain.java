import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

//
import lib.MatrixToImageConfig;
import lib.MatrixToImageWriter;

public class QRmain {

	public static void main(String ar[]) {

		String path = "C:\\1004\\qrtest1";
		String fileName = "qrtest" + ".png";
		// System.out.println(day.get(Calendar.DAY_OF_MONTH));
		// System.out.println(day.get(Calendar.HOUR));
		// System.out.println(day.get(Calendar.MINUTE));
		makeQRCode(path, fileName);

	}

	private static void makeQRCode(String path, String fileName) {
		try {
			File file = null;
			// 큐알이미지를 저장할 디렉토리 지정
			file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 코드인식시 링크걸 URL주소
			String codeurl = new String(
					"https://drive.google.com/drive/folders/1F0YDIWRVMf5VyH-AhB5PJ_5TxSAtUye0".getBytes("UTF-8"),
					"ISO-8859-1");
			// 큐알코드 바코드 생상값
			int qrcodeColor = 0xFF2e4e96;
			// 큐알코드 배경색상값
			int backgroundColor = 0xFFFFFFFF;

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			// 3,4번째 parameter값 : width/height값 지정
			BitMatrix bitMatrix = qrCodeWriter.encode(codeurl, BarcodeFormat.QR_CODE, 200, 200);
			//
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			// ImageIO를 사용한 바코드 파일쓰기
			ImageIO.write(bufferedImage, "png", new File(path + "\\" + fileName));

			System.out.printf("create at %s%n", path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
