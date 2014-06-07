package com.up;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class UserPoolParser{
	
	//String Line을 UserPool 데이터 모델로 변경
	public UserPoolModel toModel(String message){
		
		UserPoolModel model = new UserPoolModel();
		StringTokenizer stk = new StringTokenizer(message, "|");
		// nextToken() 메소드를 이용해 파싱한 토큰을 가져와 name에 설정.
		
		model.setUser_id(stk.nextToken());
		model.setUser_name(stk.nextToken());
		model.setUser_gender(stk.nextToken());
		model.setUser_email(stk.nextToken());
		model.setUser_phone(stk.nextToken());
		model.setUser_address(stk.nextToken());
		
		return model;
	}
	
	//model을 message로
	public String toMessage(UserPoolModel model){
		
		String message = 
				model.getUser_id()+"|"+
				model.getUser_name()+"|"+
				model.getUser_gender()+"|"+
				model.getUser_email()+"|"+
				model.getUser_phone()+"|"+
				model.getUser_address();
		
		return message;
	}
	
	

}
