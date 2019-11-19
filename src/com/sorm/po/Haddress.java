package	com.sorm.po;

import java.sql.*;
import java.util.*;

public class	Haddress {
	private String area;
	private String town;
	private String province;
	private String county;
	private Integer addressid;

	public	String getArea(){
		return this.area;
	}
	public	void   setArea(String area){
		this.area=area;
	}

	public	String getTown(){
		return this.town;
	}
	public	void   setTown(String town){
		this.town=town;
	}

	public	String getProvince(){
		return this.province;
	}
	public	void   setProvince(String province){
		this.province=province;
	}

	public	String getCounty(){
		return this.county;
	}
	public	void   setCounty(String county){
		this.county=county;
	}

	public	Integer getAddressid(){
		return this.addressid;
	}
	public	void   setAddressid(Integer addressid){
		this.addressid=addressid;
	}

}
