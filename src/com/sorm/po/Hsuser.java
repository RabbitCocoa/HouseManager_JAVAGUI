package	com.sorm.po;

import java.sql.*;
import java.util.*;

public class	Hsuser {
	private java.sql.Timestamp logintime;
	private String sphone;
	private String uname;
	private Integer surplus;
	private String ssex;
	private String scardid;
	private String semail;
	private String sname;
	private byte[] photo;

	public	java.sql.Timestamp getLogintime(){
		return this.logintime;
	}
	public	void   setLogintime(java.sql.Timestamp logintime){
		this.logintime=logintime;
	}

	public	String getSphone(){
		return this.sphone;
	}
	public	void   setSphone(String sphone){
		this.sphone=sphone;
	}

	public	String getUname(){
		return this.uname;
	}
	public	void   setUname(String uname){
		this.uname=uname;
	}

	public	Integer getSurplus(){
		return this.surplus;
	}
	public	void   setSurplus(Integer surplus){
		this.surplus=surplus;
	}

	public	String getSsex(){
		return this.ssex;
	}
	public	void   setSsex(String ssex){
		this.ssex=ssex;
	}

	public	String getScardid(){
		return this.scardid;
	}
	public	void   setScardid(String scardid){
		this.scardid=scardid;
	}

	public	String getSemail(){
		return this.semail;
	}
	public	void   setSemail(String semail){
		this.semail=semail;
	}

	public	String getSname(){
		return this.sname;
	}
	public	void   setSname(String sname){
		this.sname=sname;
	}

	public	byte[] getPhoto(){
		return this.photo;
	}
	public	void   setPhoto(byte[] photo){
		this.photo=photo;
	}

}
