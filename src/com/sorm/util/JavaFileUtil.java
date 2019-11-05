package com.sorm.util;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.JavaFieldGetSet;
import com.sorm.bean.TableInfo;
import com.sorm.core.DBManager;
import com.sorm.core.TypeConvertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaFileUtil {
    /**
     * 根据字段信息创建java属性信息
     * @param column 字段信息
     * @param tc	类型转换器
     * @return	返回java属性和set/get源码
     */
    public static JavaFieldGetSet CreateJavaFieldGetSet(ColumnInfo column, TypeConvertor tc)
    {
        JavaFieldGetSet jfg=new JavaFieldGetSet();
        String type=tc.dataToJava(column.getType());
        String name=column.getName();
        jfg.setFieldInfo("	private "+type+" "+name+";\n");
        jfg.setGetInfo("	public	"+type+" get"+StringUtil.firstUpper(name)+"(){\n		return this."+name+";\n\t}\n");
        jfg.setSetInfo("	public	void   set"+StringUtil.firstUpper(name)+"("+type+" "+name+"){\n\t\tthis."+name+"="+name+";\n\t}\n");
        return jfg;
    }

    /*根据表信息 创造java源码 */
    public static String CreateJavcSrc(TableInfo tableInfo,TypeConvertor tc)
    {
        StringBuilder result=new StringBuilder();
        Map<String,ColumnInfo> column=tableInfo.getColumns();
        List<JavaFieldGetSet> javaFields= new ArrayList<>();
        for(ColumnInfo c:column.values())
        {
            JavaFieldGetSet jg=CreateJavaFieldGetSet(c,tc);
            javaFields.add(jg);
        }
        //生成源码
        //package
        result.append("package	"+ DBManager.getConf().getPoPackage()+";\n\n");
        //import
        result.append("import java.sql.*;\n");
        result.append("import java.util.*;\n\n");
        //类声明
        result.append("public class	"+StringUtil.firstUpper(tableInfo.getTname())+" {\n");
        //属性
        for(JavaFieldGetSet jf:javaFields) {
            result.append(jf.getFieldInfo());
        }
        result.append("\n");
        //方法
        for(JavaFieldGetSet jf:javaFields)
        {
            result.append(jf.getGetInfo());
            result.append(jf.getSetInfo());
            result.append("\n");
        }
        //结束符
        result.append("}\n");

        return result.toString();
    }

    /**
     * 生成类文件
     */
    public static void createJavaFile(TableInfo tableInfo,TypeConvertor tc) {
        String src = CreateJavcSrc(tableInfo, tc);
        String path = DBManager.getConf().getSrcPath() + "/" + DBManager.getConf().getPoPackage().replace('.', '/');

        BufferedWriter bw = null;
        File f = new File(path);
        if (!f.exists()) {//如果不存在 则建立目录
            f.mkdirs();
        }
        path = path + "/" + StringUtil.firstUpper(tableInfo.getTname() + ".java");
        try {

            bw = new BufferedWriter(new FileWriter(path));
            bw.write(src);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bw != null)
                try {
                    bw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
}
