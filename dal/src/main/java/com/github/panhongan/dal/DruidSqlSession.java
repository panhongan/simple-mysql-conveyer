package com.github.panhongan.dal;

import com.github.panhongan.common.Bean2SqlException;
import com.github.panhongan.common.utils.MysqlUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author panhongan
 * @since 2019.7.14
 * @version 1.0
 */

@Service
public class DruidSqlSession {

    public long getMaxRowId(String sql, Map<Integer, String> values) {
        return 1L;
    }

    public List<Long> insert(String sql, Map<Integer, String> values) {
        return Arrays.asList(1L);
    }

    public int update(String sql, Map<Integer, String> values) {
        return 1;
    }

    public <D> List<D> select(String sql, Map<Integer, String> values, Class<D> c) throws Bean2SqlException {
        return null;
    }

    public int getCount(String countSql, Map<Integer, String> values) throws Bean2SqlException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            int count = 0;

            DataSource dataSource = null; // 从DruidDataSource获取，略
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(countSql);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                ps.setString(entry.getKey(), entry.getValue());
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            throw new Bean2SqlException(e);
        } finally {
            MysqlUtils.closeResultSet(rs);
            MysqlUtils.closeStatement(ps);
            MysqlUtils.closeConnection(conn);
        }
    }
}
