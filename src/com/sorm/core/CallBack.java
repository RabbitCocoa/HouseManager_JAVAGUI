package com.sorm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface CallBack {
    Object dock(Connection con, PreparedStatement ps, ResultSet rs);
}
