package com.up;
import java.io.*;
import java.net.*;


/**
 * UserPool Socket Server Class
 * 클라이언트의 연결을받아 Handler 쓰레드를 생성하는 클래스
 * @author Administrator
 *
 */
public class UserPoolServer {
	
	public static void main(String args[]) throws IOException {
		
		//기본 포트 9000
		int port = args.length != 1 ? 9000 :Integer.parseInt(args[0]);
		
		// 서버소켓 생성.
		ServerSocket server = new ServerSocket(port);
		System.out.println("## UserPoolServer Started");
		
		// 다수의 클라이언트의 접속을 받아드리기 위해서 무한반복 수행.
		while (true) {
			Socket client = server.accept();
			System.out.println("Accepted from: " + client.getInetAddress());
			UserPoolHandler handler = new UserPoolHandler(client);
			handler.init();
		}

	}
}