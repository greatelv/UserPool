package com.up;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class UserPoolParser{
	
	//String Line을 UserPool 데이터 모델로 변경
	public UserPoolModel toModel(String message){
		
		UserPoolModel model = new UserPoolModel();
		String[] mArray;
		
		mArray = message.split("\\|", -1);
		
		/*System.out.println("message : "+message);
		
		for(int i=0; i<mArray.length; i++){
			System.out.println("mArray : "+mArray[i]);	
		}*/
		
		model.setMethod(mArray[0]);
		model.setId(mArray[1]);
		model.setName(mArray[2]);
		model.setGender(mArray[3]);
		model.setEmail(mArray[4]);
		model.setPhone(mArray[5]);
		model.setAddress(mArray[6]);
		
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
	
	public String[] toArray(String textData){
		String[] result = textData.split(System.getProperty("line.separator"), -1);
		return result;
	}

}
