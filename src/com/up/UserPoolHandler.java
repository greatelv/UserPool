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
	
	UserPoolParser parser = new UserPoolParser();

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
				UserPoolModel model = parser.toModel(message);
				
				try {
					System.out.println("model method : "+model.getMethod());
					
					switch(model.getMethod()){
						case "post" :
							System.out.println("성별 : "+model.getGender());
							System.out.println("회원 등록");
							break;
						case "getOne" :
							dataOut.writeUTF("wjswjs2|One");
							dataOut.flush();
							break;
						case "getList" :
							dataOut.writeUTF("wjswjs|List");
							dataOut.flush();
							break;
						case "edit" :
							System.out.println("회원 수정");
							break;
						case "delete" :
							System.out.println("회원 삭제");
							break;
					}
					
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