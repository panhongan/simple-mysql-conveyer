package com.github.panhongan.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author panhongan
 * @since 2019.7.13
 * @version 1.0
 */

@Slf4j
public class MysqlUtils {

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            log.warn("", e);
        }
    }

    public static void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
            log.warn("", e);
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            log.warn("", e);
        }
    }
}
