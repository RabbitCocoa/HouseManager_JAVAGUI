package com.sorm.bean;

/*
 *管理配置信息
 */
public class Configuration {
    private String Driver; //驱动类
    private String Url; //JDBC URL
    private String User;//数据库用户名
    private String Password;//数据库密码
    private String UsingDb;//使用的数据库 mysql
    private String srcPath;//项目源码路径
    private String poPackage;//自动生成的java类路径
    private String DbName;//数据库名

    public String getDbName() {
        return DbName;
    }

    public void setDbName(String dbName) {
        DbName = dbName;
    }

    public Configuration(String driver, String url, String user, String password, String usingDb, String srcPath, String poPackage, String DbName) {
        Driver = driver;
        Url = url;
        User = user;
        Password = password;
        UsingDb = usingDb;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
        this.DbName=DbName;
    }

    public Configuration() {
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsingDb() {
        return UsingDb;
    }

    public void setUsingDb(String usingDb) {
        UsingDb = usingDb;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }
}
