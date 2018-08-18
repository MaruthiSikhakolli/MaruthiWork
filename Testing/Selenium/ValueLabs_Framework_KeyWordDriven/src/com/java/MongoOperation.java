package com.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBTCPConnector;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class MongoOperation {
	
	//String dbURL1 = "mongo05d-prf-08.portal.webmd.com";
	//String dbURL2 = "mongo15d-prf-08.portal.webmd.com";
	//String dbURL3 = "mongo25d-prf-08.portal.webmd.com";
	String username = "admin";
	String password = "admin";
	String databaseName = "cp";
	String msg = "";
	public String guid = new String();
	Mongo mongo;
	DBTCPConnector connection;
	DB databaseObject;
	boolean flag = true;

	public MongoOperation() {
		flag = true;
		Properties prop=new Properties();	
		try{
			FileInputStream in = new FileInputStream("./TestInputs/properties/dataSource.properties");
			prop.load(in);
			String strServers=prop.getProperty("mongo");
			in.close();	
			String Servers[]=strServers.split(";");
		List<ServerAddress> serverList = new ArrayList<ServerAddress>();
		for(String ser:Servers)
		{
			ServerAddress addr3 = new ServerAddress(ser);
			serverList.add(addr3);
		}
		mongo = new Mongo(serverList);
		mongo.setWriteConcern(WriteConcern.SAFE);
		databaseObject = mongo.getDB(databaseName);
		databaseObject.authenticate(username, password.toCharArray());
	}catch(IOException io){
		io.printStackTrace();
		System.out.println("Unable to read Datasource Properties File.");
	}
	}
	public void close() {
		mongo.close();
	}

	public void updateSegment(String guid, String segmentid) {

		DBCollection targetlist = databaseObject.getCollection("targetlist");
		DBObject tlquery = new BasicDBObject();
		tlquery.put("uid", guid);

		String curr_seg_id;
		if( segmentid.contains("7"))
		curr_seg_id = "10" ;
		else
		curr_seg_id = "7";
		tlquery.put("segmentId", curr_seg_id);


		DBCursor dbc = targetlist.find(tlquery);
		System.out.println(dbc.count());
		for (DBObject db : dbc) {
		DBObject tlquery_upd = new BasicDBObject("$set", new BasicDBObject("segmentId", segmentid));
		targetlist.update(db, tlquery_upd);
		}
		}
	public void VerifyInfo(String guid,String attrName,String attrValue) {
		//System.out.println(Activity);
		System.out.println(guid);
		System.out.println(attrName);
		System.out.println(attrValue);
		try{
		DBCollection targetlist = databaseObject.getCollection("targetlist");
		//ArrayList lst=new ArrayList();
		//lst.add(new BasicDBObject("listMatchId","562"));
		DBObject tlquery = new BasicDBObject();
		tlquery.put("uid", guid);
		tlquery.put(attrName,attrValue);
		DBCursor dbc = targetlist.find(tlquery);
		System.out.println(dbc.count());
		if(dbc.count()>0)
		{
			System.out.println("Contains the required key and value");	
		}
		else
		{
			System.out.println("Table doesn't contain the required key");
			throw new Exception();
		}}catch(Exception DBE){
			DBE.printStackTrace();
			System.out.println("Required parameters doesn't exist in the table");
		}
		}
	
	public void VerifyInfo(String guid,String attrName,int attrValue) {
		try{
		DBCollection targetlist = databaseObject.getCollection("targetlist");
		DBObject tlquery = new BasicDBObject();
		tlquery.put("uid", guid);
		tlquery.put(attrName,attrValue);
		DBCursor dbc = targetlist.find(tlquery);
		System.out.println(dbc.count());
		if(dbc.count()>0)
		{
			System.out.println("Contains the required key and value");	
		}
		else
		{
			System.out.println("Table doesn't contain the required key");
			throw new Exception();
		}}catch(Exception DBE){
			DBE.printStackTrace();
			System.out.println("Required parameters doesn't exist in the table");
		}
		}
	public void updateAct(String guid, boolean status) {
		
		DBCollection targetlist = databaseObject.getCollection("targetlist");

		DBObject tlquery = new BasicDBObject();
		tlquery.put("uid", guid);
		//tlquery.put("segmentId","7");
		DBCursor dbc = targetlist.find(tlquery);
		for (DBObject db : dbc) {
			DBObject tlquery_upd = new BasicDBObject("$set", new BasicDBObject("active", status));
			targetlist.update(db, tlquery_upd);
		}
	}
	
public void updateTac(String guid, boolean status) {
		DBCollection targetlist = databaseObject.getCollection("target");

		DBObject tlquery = new BasicDBObject();
		tlquery.put("uid",guid);
		//tlquery.put("segmentId","7");
		DBCursor dbc = targetlist.find(tlquery);
		System.out.println(dbc.count());
		for (DBObject db : dbc) {
			DBObject tlquery_upd = new BasicDBObject("$set", new BasicDBObject("active", status));
			targetlist.update(db, tlquery_upd);
		}
	}


	@SuppressWarnings("deprecation")
	public String getDate() {
		Date d = new Date();
		String str;
		str = d.getDate() + "" + (d.getMonth() + 1) + "" + (d.getYear() + 1900)
				+ "" + d.getHours() + "";
		str = str + d.getMinutes() + "" + d.getSeconds();
		return str;
	}

	public void insert(String guid) {
		flag = true;

		DBCollection targetlist = databaseObject.getCollection("targetlist");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		DBObject dbo = new BasicDBObject();
		dbo.put("_class", "com.webmd.cp.domain.TargetListUser");
		dbo.put("uid", guid);
		DBObject dbactivityid = new BasicDBObject();
		dbactivityid.put("0",Integer.parseInt("10289"));
		dbo.put("activityId",dbactivityid);
		//dbo.put("activityId.0",10000);
		dbo.put("active", true);
		DBObject dbAttrs = new BasicDBObject();
		dbAttrs.put("attr15","testpr18");
		dbAttrs.put("listMatchId",Integer.parseInt("21012013"));
		dbAttrs.put("cpLastUpdated",dateFormat.format(date).toString());
		dbAttrs.put("lastUpdated",dateFormat.format(date).toString());
		dbAttrs.put("listSourceId",Integer.parseInt("1"));
		dbAttrs.put("isTestFlg",Integer.parseInt("1"));
		dbo.put("attrs",dbAttrs);
		WriteResult cs = targetlist.insert(dbo);
		msg = cs.getError();
		if (msg != null)
			flag = false;
	}

	public void SaveUser(String objectid) {
		flag = true;

		DBCollection targetlist = databaseObject.getCollection("targetlist");
		DBObject dbquery = new BasicDBObject();
		dbquery.put("_id",objectid);
		DBCursor dbc=targetlist.find(dbquery);
		for(DBObject dbobj:dbc)
		{
			System.out.println(dbobj.toString());
			SeleniumDriverTest.map=dbobj.toMap();
		}
	}
	
	public void InsertSavedMap() {
		flag = true;
		Map map=SeleniumDriverTest.map;
		DBCollection targetlist = databaseObject.getCollection("targetlist");
		DBObject dbobj = new BasicDBObject(map);
		WriteResult cs = targetlist.insert(dbobj);
		msg = cs.getError();
		if (msg != null)
			flag = false;
	}

	public void delete(String guid, String collectionName) {
		DBCollection tlc = databaseObject.getCollection(collectionName);
		DBObject tlcquery = new BasicDBObject();
		tlcquery.put("uid", guid);
		tlc.remove(tlcquery);
		System.out.println("Deleted from : " + collectionName);
	}

	public void updateDate(String guid, String days) {
		DBCollection targetlist = databaseObject.getCollection("tlc");
		DBObject dbo = new BasicDBObject("uid", guid.trim());
		DBCursor dbc = targetlist.find(dbo);
		for (DBObject db : dbc) {
			Date ds = (Date) db.get("date");
			Date d1 = DateUtils.addDays(ds, Integer.parseInt(days));
			DBObject tlquery_upd = new BasicDBObject("$set", new BasicDBObject("date", d1));
			targetlist.update(db, tlquery_upd);
		}
	}


	/*public void delete() {
		DBCollection tlc = databaseObject.getCollection("events");

		DBObject query = new BasicDBObject();
		query.put("uid", "17239288");
		query.put("date", "\"$date\" : \"3970-11-13T16:51:26.000Z\"");

		DBObject update = new BasicDBObject();
		update.put("date", "\"$date\" : \"Sat Oct 06 12:54:50 IST 2012\"");

		System.out.println(tlc.findOne());
		DBObject df = tlc.findAndModify(query, update);

		System.out.println(df);
		System.out.println("Deleted from : " + df.get("date"));
	}*/

	public static void main(String arg[]) throws Exception {

		MongoOperation mongo = new MongoOperation();
		mongo.VerifyInfo("19974417", "attrs.attr15", "testpr20");
		/*
		 * mongo.getUID("7", true); System.out.println(mongo.guid);
		 * System.exit(0);
		 */
		/*mongo.insert("19974418");
		//mongo.updateAct("19974400",false);
		//mongo.SaveUser("8348927");
		//System.out.println(SeleniumDriver.map.toString());
		//mongo.delete("14343133", "targetlist");
		//mongo.VerifyInfo("19974398","attrs.attr15", "testpr17");
		//mongo.updateSegment("14325476", "7");
		//mongo.delete("14325476", "tlc");
		mongo.close();*/

		// mongo.updateSegment("14325474", "10");
		// System.out.println(mongo.msg);
		// mongo.delete("14325475", "events");
		// mongo.updateSegment("14325474", "7");

}

}
