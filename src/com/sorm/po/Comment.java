package	com.sorm.po;

import java.sql.*;
import java.util.*;

public class	Comment {
	private java.sql.Timestamp ccreatetime;
	private String bcid;
	private Integer cdegree;
	private String ctitle;
	private String cname;
	private String ctext;
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

	public	String getCtitle(){
		return this.ctitle;
	}
	public	void   setCtitle(String ctitle){
		this.ctitle=ctitle;
	}

	public	String getCname(){
		return this.cname;
	}
	public	void   setCname(String cname){
		this.cname=cname;
	}

	public	String getCtext(){
		return this.ctext;
	}
	public	void   setCtext(String ctext){
		this.ctext=ctext;
	}

	public	String getCid(){
		return this.cid;
	}
	public	void   setCid(String cid){
		this.cid=cid;
	}

}
