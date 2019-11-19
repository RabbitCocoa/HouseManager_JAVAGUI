package edu.fzu.house.core.columnInterface;

import java.util.HashMap;
import java.util.List;

/*该接口用于处理数据  比如讲地址号转化为地址*/
public interface ColumnInterface {
    HashMap<Integer, List<Object>> getMap(List<Object> list);
}
