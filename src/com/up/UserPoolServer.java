package com.up;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * UserPool Socket Server Class
 * 클라이언트의 연결을받아 Handler 쓰레드를 생성하는 클래스
 * @author Administrator
 *
 */
public class UserPoolServer {
	
	static String path = "userpooldb.txt";
	
	public static void main(String args[]) throws IOException {
		
		//기본 포트 9000
		int port = args.length != 1 ? 9000 :Integer.parseInt(args[0]);
		
		// 서버소켓 생성.
		ServerSocket server = new ServerSocket(port);
		System.out.println("## UserPoolServer Started");
		
		//회원 데이터베이스 파일이 존재하지 않을 시 생성
		if(!new File(path).exists()){
			new File(path).createNewFile();
		}
		
		// 다수의 클라이언트의 접속을 받아드리기 위해서 무한반복 수행.
		while (true) {
			System.out.println(getTime() + " 연결요청을 기다립니다.");
            Socket socketClient = server.accept();
            System.out.println(getTime() + socketClient.getInetAddress() + " 로부터 연결요청이 들어왔습니다.");
            
            UserPoolHandler handler = new UserPoolHandler(socketClient);
			handler.init();
		}
		
		

	}
	
	private static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[yyyy-mm-dd hh:mm:ss]");
        return f.format(new Date());
    } // getTime
}