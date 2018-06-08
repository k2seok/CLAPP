import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import datalayer.AppUserDAO_jdbc;
import datalayer.AppUserVO;
import datalayer.RegisterDAO_jdbc;
import datalayer.RegisterVO;
import datalayer.ScheduleDAO_jdbc;
import datalayer.ScheduleVO;
import lib.MatrixToImageConfig;
import lib.MatrixToImageWriter;

public class Server {
	private ServerSocket mServerSocket;
	private Socket mSocket;
	private int port;
	private InetAddress ip;

	public Server() {
		try {

			init();

			System.out.printf("making QRcode");
			String path = "C:\\Users\\Administrator\\Google 드라이브\\clapp\\QRCode";
			String fileName = new SimpleDateFormat("yyMMddhhmmss").format(new Date(System.currentTimeMillis()));
			String url = ip.getHostAddress() + ":" + port;
			makeQRCode(path, fileName + ".png", url);

			System.out.printf("Server run!! %n ip : %s , port %d%n", ip.getHostAddress(), port);
			do {
				mSocket = mServerSocket.accept();

				createSocket(new BufferedReader(new InputStreamReader(mSocket.getInputStream())),
						new PrintWriter(mSocket.getOutputStream()));

			} while (true);

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

	private void init() throws IOException {
		port = 6666;
		mServerSocket = new ServerSocket(port);
		ip = InetAddress.getLocalHost();

		System.out.println("Server launch!");
		dbInit();

	}

	private void dbInit() {
		RegisterVO register = new RegisterVO();
		RegisterDAO_jdbc registerdao = new RegisterDAO_jdbc();

		registerdao.DropRegister();

		AppUserVO temp_user = null;
		AppUserVO user = new AppUserVO();
		AppUserDAO_jdbc userdao = new AppUserDAO_jdbc();
		userdao.DropUser();
		userdao.CreatUser();
		userdao.check();

		user.setBirthday("1990-10-08");
		user.setName("yks");
		userdao.userInsert(user);
		user.setBirthday("1991-04-16");
		user.setName("조상훈");
		userdao.userInsert(user);
		user.setBirthday("1991-04-07");
		user.setName("최성수");
		userdao.userInsert(user);
		user.setBirthday("1993-08-13");
		user.setName("이남호");
		userdao.userInsert(user);

		ScheduleVO schedule = new ScheduleVO();
		ScheduleDAO_jdbc schedao = new ScheduleDAO_jdbc();
		schedao.AutoSchedule();

		// 임시 식단
		schedule.setDates("2018-06-08");
		schedao.UpdateSchedule(schedule, "함박스테이크/스프/오렌지주스", "비빔밥/계란후라이/미숫가루", "");
		schedule.setDates("2018-06-09");
		schedao.UpdateSchedule(schedule, "탕수육/짬봉국/포도주스", "라면/김밥/판타", "");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hrmm = new SimpleDateFormat("HH-mm-ss");

		registerdao.CreatRegister();
		registerdao.CheckRegisterTable();
	}

	private void createSocket(BufferedReader bufferedReader, PrintWriter printWriter) throws IOException {

		// socket Thread start
		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("server get data! checking data...");

				AppUserVO temp_user = null;
				AppUserVO user = new AppUserVO();
				String temp_msg = null;
				AppUserDAO_jdbc userdao = new AppUserDAO_jdbc();
				RegisterVO register = new RegisterVO();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				RegisterDAO_jdbc registerdao = new RegisterDAO_jdbc();
				SimpleDateFormat hrmm = new SimpleDateFormat("HH-mm-ss");

				while (true) {
					try {
						temp_msg = bufferedReader.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (temp_msg == null)
						continue;

					System.out.println(temp_msg);
					String[] select = temp_msg.split("/");
					switch (select[0]) {

					case "1":
						user.setBirthday(select[1]);
						user.setName(select[2]);
						temp_user = userdao.userSearch(user);

						// 학생있으면 1보냄.
						if (temp_user == null) {
							printWriter.println(0);
						} else
							printWriter.println(1);
						printWriter.flush();
						break;

					case "2":// 식단
					{
						ScheduleVO schedule = new ScheduleVO();
						ScheduleDAO_jdbc schedao = new ScheduleDAO_jdbc();
						Calendar cal = Calendar.getInstance();

						schedule.setDates(formatter.format(cal.getTime()));
						schedule = schedao.SearchSchedule(schedule);

						StringBuilder sb = new StringBuilder();
						sb.append("2/A/");
						sb.append(schedule.getMenu1());
						sb.append("/");
						sb.append("B/");
						sb.append(schedule.getMenu2());
						printWriter.println(2 + sb.toString());
						printWriter.flush();

						break;
					}
					case "3":// QR코드처리 -- 학생 출첵도 처리해야함.
						if (select[1].equals("asdf1234s")) {
							user.setBirthday(select[2]);
							user.setName(select[3]);
							temp_user = userdao.userSearch(user);

							if (temp_user == null) {
								printWriter.println(0);
							} // 사용자가 없는 경우.
							else {
								register.setDates(formatter.format(new Date()));
								register.setUser_num(temp_user.getUser_num());

								if (registerdao.SearchRegister(register) == null) {
									register.setEnter(hrmm.format(new Date()));
									registerdao.InsertRegister(register);

									// 출근 성공시 3반환
									System.out.println(3);
									printWriter.println(3);
								} else {
									register.setEnter(registerdao.SearchRegister(register).getEnter());
									register.setDeparture(hrmm.format(new Date()));
									if (registerdao.SearchRegister(register).getDeparture() == null) {
										registerdao.InsertRegister(register);

										// 퇴근 성공시 4반환
										System.out.println(4);
										printWriter.println(4);

									} else {
										// 퇴근 다시 찍을 때, 기존 data update 함
										// registerdao.UpdateRegister(register,
										// register);
										// printWriter.println(4);
									}
								}

								printWriter.flush();
							}
						} else {
							System.out.println(" QR코드 잘못 입력!");
							printWriter.println(0);// QR코드 잘못 입력
							printWriter.flush();
						}
						break;
					default:
						StringBuffer sb = new StringBuffer();
						for (String s : select) {
							sb.append(s);
						}
						System.out.print("message : " + sb.toString());

						printWriter.println("I'm server, your message received!");
						printWriter.flush();
						break;
					}
				}
			}
		}).start();

	}

	private void makeQRCode(String path, String fileName, String url) {
		try {
			File file = null;
			// 큐알이미지를 저장할 디렉토리 지정
			file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 코드인식시 링크걸 URL주소
			String codeurl = new String(url.getBytes("UTF-8"), "ISO-8859-1");

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
	}
}