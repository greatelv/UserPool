package com.up;
import java.io.*;
import java.net.*;
import java.util.*;

/*
 * class : PizzaHandler 
 * public class PizzaHandler는 서버로 들어온 클라이언트와의 연결을 
 * 개별적으로 처리하는 일을 맡은 Thread 클래스.  
 * 클라이언트로부터의 메시지를 받는 역할
 */
public class UserPoolHandler implements Runnable {
	protected Socket socket;
	protected DataInputStream dataIn;
	protected DataOutputStream dataOut;
	protected Thread listener;
	private Date pdate;

	/**
	 * 생성자 : PizzaHandler() 
	 * 생성자는 클라이언트로부터 들어온 연결을 맡은 
	 * 소켓인 socket의 참조자를 받아 복사해 둠.
	 */
	public UserPoolHandler(Socket socket) {
		this.socket = socket;
	}


	/**
	 * 메소드 : init() 
	 * init()는 입력/출력 스트림을 열고, 버퍼링 데이터 스트림을 
	 * 사용하여, String을 주고받을 수 있는 기능을 제공 .
	 * 클라이언트 처리용 쓰레드인 listener를 새로 만들어 시작.
	 */
	public synchronized void init() {
		if (listener == null) {
			try{
				dataIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				dataOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

				listener = new Thread(this);
				listener.start();
			}catch (IOException exception) {
				exception.printStackTrace();
			}
		}

	}

	/**
	 * 메소드 : stop() 
	 * listener 쓰레드에 인터럽트를 걸고 
	 * dataOut 스트림을 닫는 역할.
	 */
	public synchronized void stop() {
		if (listener != null) {
			try {
				if (listener != Thread.currentThread())
					listener.interrupt();
				listener = null;
				dataOut.close();
			}
			catch (IOException ignored) {
			}
		}
	}


	public void run() {
		try {
			while (!Thread.interrupted()) {
				String message = dataIn.readUTF();
				try {
					StringTokenizer stk = new StringTokenizer(message, "|");
					// nextToken() 메소드를 이용해 파싱한 토큰을 가져와 name에 설정.
					String name = stk.nextToken();
					// 다음 토큰을 가져와 설정합니다.
					String address = stk.nextToken();
					String phone = stk.nextToken();
					String pkind = stk.nextToken();
					String psize = stk.nextToken();

					
					pdate = new Date();
					System.out.println(pdate.toString());
					System.out.println("주문자 성명 : " + name);
					System.out.println("배달 주소 : " + address);
					System.out.println("연락처 : " + phone);
					System.out.println("주문피자 : " + pkind + " " + psize + "\n");
				} catch (NoSuchElementException e) {
					stop();
				}
			}
		} 
		catch (EOFException ignored) {
			System.out.println( "접속을 종료하셨습니다.");
		}
		catch (IOException ie) {
			if (listener == Thread.currentThread())
				ie.printStackTrace();
		} finally {
			stop();
		}
	}
}