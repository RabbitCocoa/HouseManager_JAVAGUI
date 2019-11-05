package	com.sorm.po;

import java.sql.*;
import java.util.*;

public class	House {
	private String hid;
	private Integer htype;
	private Integer harea;
	private String uname;
	private String hinformation;
	private Integer hprice;
	private java.sql.Blob photo;
	private java.sql.Clob hdec;
	private Integer hstate;
	private String hname;
	private Integer addressid;

	public	String getHid(){
		return this.hid;
	}
	public	void   setHid(String hid){
		this.hid=hid;
	}

	public	Integer getHtype(){
		return this.htype;
	}
	public	void   setHtype(Integer htype){
		this.htype=htype;
	}

	public	Integer getHarea(){
		return this.harea;
	}
	public	void   setHarea(Integer harea){
		this.harea=harea;
	}

	public	String getUname(){
		return this.uname;
	}
	public	void   setUname(String uname){
		this.uname=uname;
	}

	public	String getHinformation(){
		return this.hinformation;
	}
	public	void   setHinformation(String hinformation){
		this.hinformation=hinformation;
	}

	public	Integer getHprice(){
		return this.hprice;
	}
	public	void   setHprice(Integer hprice){
		this.hprice=hprice;
	}

	public	java.sql.Blob getPhoto(){
		return this.photo;
	}
	public	void   setPhoto(java.sql.Blob photo){
		this.photo=photo;
	}

	public	java.sql.Clob getHdec(){
		return this.hdec;
	}
	public	void   setHdec(java.sql.Clob hdec){
		this.hdec=hdec;
	}

	public	Integer getHstate(){
		return this.hstate;
	}
	public	void   setHstate(Integer hstate){
		this.hstate=hstate;
	}

	public	String getHname(){
		return this.hname;
	}
	public	void   setHname(String hname){
		this.hname=hname;
	}

	public	Integer getAddressid(){
		return this.addressid;
	}
	public	void   setAddressid(Integer addressid){
		this.addressid=addressid;
	}

}
