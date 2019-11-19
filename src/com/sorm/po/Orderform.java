package	com.sorm.po;

import java.sql.*;
import java.util.*;

public class	Orderform {
	private String hid;
	private Integer hprice;
	private String oid;
	private String bid;
	private String hname;
	private String sid;

	public	String getHid(){
		return this.hid;
	}
	public	void   setHid(String hid){
		this.hid=hid;
	}

	public	Integer getHprice(){
		return this.hprice;
	}
	public	void   setHprice(Integer hprice){
		this.hprice=hprice;
	}

	public	String getOid(){
		return this.oid;
	}
	public	void   setOid(String oid){
		this.oid=oid;
	}

	public	String getBid(){
		return this.bid;
	}
	public	void   setBid(String bid){
		this.bid=bid;
	}

	public	String getHname(){
		return this.hname;
	}
	public	void   setHname(String hname){
		this.hname=hname;
	}

	public	String getSid(){
		return this.sid;
	}
	public	void   setSid(String sid){
		this.sid=sid;
	}

}
