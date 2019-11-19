package	com.sorm.po;

import java.sql.*;
import java.util.*;

public class	Huser {
	private String password;
	private String uname;
	private Integer utype;

	public	String getPassword(){
		return this.password;
	}
	public	void   setPassword(String password){
		this.password=password;
	}

	public	String getUname(){
		return this.uname;
	}
	public	void   setUname(String uname){
		this.uname=uname;
	}

	public	Integer getUtype(){
		return this.utype;
	}
	public	void   setUtype(Integer utype){
		this.utype=utype;
	}

}
