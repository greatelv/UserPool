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

	static String path = "userpooldb.txt";

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
				boolean result = true;
				String userMessage = "";

				try {
					switch(model.getMethod()){
					case "post" :
						result = addUser(message);
						dataOut.writeUTF(String.valueOf(result));
						dataOut.flush();
						break;
					case "getOne" :
						
						userMessage = getOneUser(model.getId());
						
						dataOut.writeUTF(userMessage);
						dataOut.flush();
						
						break;
					case "getList" :
						String userData = getTotalUser();
						System.out.println("userData : "+userData);
						
						dataOut.writeUTF(userData);
						dataOut.flush();
						break;
					case "edit" :
						result = deleteUser(model.getId());
						System.out.println("[delete Message] : "+message);
						result = addUser(message);
						
						dataOut.writeUTF(String.valueOf(result));
						dataOut.flush();
						break;
					case "delete" :
						result = deleteUser(model.getId());
						dataOut.writeUTF(String.valueOf(result));
						dataOut.flush();
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

	private boolean addUser(String userRow){

		boolean result = true;
		String str = userRow + System.getProperty("line.separator");

		FileWriter fw;
		try {
			fw = new FileWriter(new File(path), true);
			fw.write(str);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;

		} finally{

		}

		return result;
	}
	
	private boolean deleteUser(String userId){

		boolean result = true;
		String message = "";
		
		UserPoolModel user_model = new UserPoolModel();
		int lineIdx = 0;

		try {
			File fileDir = new File(path);
			String line = "";
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(fileDir), "UTF8"));
			
			while ((line = in.readLine()) != null){
				System.out.println(lineIdx + " line : "+line);
				user_model = parser.toModel(line);
				
				if(!user_model.getId().equals(userId)){
					if(lineIdx == 0){
						message = message + line;
					}else{
						message = message + System.getProperty("line.separator") + line;
					}
					
					lineIdx ++;
				}
				
				
			}
			
			PrintWriter writer = new PrintWriter(fileDir);
			writer.print("");
			writer.close();
			
			//System.out.println("message : "+message);
			addUser(message);
			
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	private String getTotalUser(){

		String result = "";
		int lineIdx = 0;

		try {
			File fileDir = new File(path);
			String line = "";
			
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(fileDir), "UTF8"));
			

			while ((line = in.readLine()) != null){
			
				if(lineIdx == 0){
					result = result + line;
				}else{
					result = result + System.getProperty("line.separator") + line;
				}
				
				lineIdx ++;
			}

			in.close();
		} 
		catch (UnsupportedEncodingException e){
			System.out.println(e.getMessage());
		} 
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}finally{
			lineIdx = 0;
		}

		return result;
	}
	
	private String getOneUser(String userId){

		String result = "";
		UserPoolModel user_model = new UserPoolModel();
		int lineIdx = 0;

		try {
			File fileDir = new File(path);
			String line = "";
			
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(fileDir), "UTF8"));
			
			while ((line = in.readLine()) != null){
				user_model = parser.toModel(line);
				
				if(user_model.getId().equals(userId)){
					result = line;
					break;
				}
				
				lineIdx ++;
			}

			in.close();
		} 
		catch (UnsupportedEncodingException e){
			System.out.println(e.getMessage());
		} 
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}finally{
			lineIdx = 0;
		}


		return result;
	}
}