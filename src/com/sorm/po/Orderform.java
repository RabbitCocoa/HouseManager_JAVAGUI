package	com.sorm.po;

import java.sql.*;
import java.util.*;

public class	Orderform {
	private String hid;
	private java.sql.Timestamp createtime;
	private Integer hprice;
	private Integer ostate;
	private java.sql.Timestamp endtime;
	private String photo;
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

	public	java.sql.Timestamp getCreatetime(){
		return this.createtime;
	}
	public	void   setCreatetime(java.sql.Timestamp createtime){
		this.createtime=createtime;
	}

	public	Integer getHprice(){
		return this.hprice;
	}
	public	void   setHprice(Integer hprice){
		this.hprice=hprice;
	}

	public	Integer getOstate(){
		return this.ostate;
	}
	public	void   setOstate(Integer ostate){
		this.ostate=ostate;
	}

	public	java.sql.Timestamp getEndtime(){
		return this.endtime;
	}
	public	void   setEndtime(java.sql.Timestamp endtime){
		this.endtime=endtime;
	}

	public	String getPhoto(){
		return this.photo;
	}
	public	void   setPhoto(String photo){
		this.photo=photo;
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
