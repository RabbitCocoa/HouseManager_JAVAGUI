package	com.sorm.po;

import java.sql.*;
import java.util.*;

public class	Comment {
	private java.sql.Timestamp ccreatetime;
	private String bcid;
	private Integer cdegree;
	private String cname;
	private java.sql.Clob ctext;
	private String cid;

	public	java.sql.Timestamp getCcreatetime(){
		return this.ccreatetime;
	}
	public	void   setCcreatetime(java.sql.Timestamp ccreatetime){
		this.ccreatetime=ccreatetime;
	}

	public	String getBcid(){
		return this.bcid;
	}
	public	void   setBcid(String bcid){
		this.bcid=bcid;
	}

	public	Integer getCdegree(){
		return this.cdegree;
	}
	public	void   setCdegree(Integer cdegree){
		this.cdegree=cdegree;
	}

	public	String getCname(){
		return this.cname;
	}
	public	void   setCname(String cname){
		this.cname=cname;
	}

	public	java.sql.Clob getCtext(){
		return this.ctext;
	}
	public	void   setCtext(java.sql.Clob ctext){
		this.ctext=ctext;
	}

	public	String getCid(){
		return this.cid;
	}
	public	void   setCid(String cid){
		this.cid=cid;
	}

}
