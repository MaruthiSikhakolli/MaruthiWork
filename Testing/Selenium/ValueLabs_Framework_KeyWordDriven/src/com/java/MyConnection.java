package com.java;

public class MyConnection {
	
	public boolean flag;
	public String msg = "";
	
	public void updateDate(String guid,String days){
		
		flag = false;
		MongoOperation mongo = new MongoOperation();
		if(!mongo.flag){
		msg = mongo.msg;
		return;
		}
		mongo.updateDate(guid,days);
		
	}
	
	public void insertSavedMap(){
		
		flag = false;
		MongoOperation mongo = new MongoOperation();
		if(!mongo.flag){
		msg = mongo.msg;
		return;
		}
		mongo.InsertSavedMap();
		
	}
	
	public void saveUser(String objectId){
		
		flag = false;
		MongoOperation mongo = new MongoOperation();
		if(!mongo.flag){
		msg = mongo.msg;
		return;
		}
		mongo.SaveUser(objectId);
		
	}
	public void VerifyTable(String guid,String attrName,String attrValue){
		flag  = false;
		MongoOperation mongo  = new MongoOperation();
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		mongo.VerifyInfo(guid, attrName, attrValue);
	}
	
	public void VerifyTable(String guid,String attrName,int attrValue){
		flag  = false;
		MongoOperation mongo  = new MongoOperation();
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		mongo.VerifyInfo(guid, attrName, attrValue);
	}
	
	public void delete(String guid,String collection){
		flag  = false;
		MongoOperation mongo  = new MongoOperation();
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		mongo.delete(guid,collection);
		flag = true;
	}
	
	public void updateSegment(String guid,String segment){
		flag  = false;
		MongoOperation mongo  = new MongoOperation();
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		mongo.updateSegment(guid,segment);
	}

	public void insert(String value,String condition){
		flag  = false;
		MongoOperation mongo  = new MongoOperation();
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		mongo.insert(value);
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		flag = true;
	}

	public void updateAct(String guid, String status) {
		flag  = false;
		MongoOperation mongo  = new MongoOperation();
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		
		mongo.updateAct(guid,Boolean.parseBoolean(status));
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		flag = true;
		
	}
	public void updateTac(String guid, String status) {
		flag  = false;
		MongoOperation mongo  = new MongoOperation();
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		mongo.updateTac(guid,Boolean.parseBoolean(status));
		if(!mongo.flag){
			msg = mongo.msg;
			return;
		}
		flag = true;
}
}