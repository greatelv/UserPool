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
		
		System.out.println(message);
		
		UserPoolModel model = new UserPoolModel();
		StringTokenizer stk = new StringTokenizer(message, "|");
		// nextToken() 메소드를 이용해 파싱한 토큰을 가져와 name에 설정.
		
		model.setMethod(stk.nextToken());
		model.setId(stk.hasMoreTokens() ? stk.nextToken() : "");
		model.setName(stk.hasMoreTokens() ? stk.nextToken() : "");
		model.setGender(stk.hasMoreTokens() ? stk.nextToken() : "");
		model.setEmail(stk.hasMoreTokens() ? stk.nextToken() : "");
		model.setPhone(stk.hasMoreTokens() ? stk.nextToken() : "");
		model.setAddress(stk.hasMoreTokens() ? stk.nextToken() : "");
		
		return model;
	}
	
	//model을 message로
	public String toMessage(UserPoolModel model){
		
		String message = 
				model.getId()+"|"+
				model.getName()+"|"+
				model.getGender()+"|"+
				model.getEmail()+"|"+
				model.getPhone()+"|"+
				model.getAddress();
		
		return message;
	}
	
	public String joinMessage(String method, String id, String name, String gender, String email, String phone, String addr){
		return method+"|"+id+"|"+name+"|"+gender+"|"+email+"|"+phone+"|"+addr;
	}

}
