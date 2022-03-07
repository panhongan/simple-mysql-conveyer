package com.github.panhongan.mysql.conveyer.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class DbBase implements Serializable {

    private static final String DEFAULT_OPERATOR = "system";

    public static String defaultOperator() {
        return DEFAULT_OPERATOR;
    }

    /**
     * 数据库表记录id
     */
    private Long id;

    private String createdBy;

    private Date createdAt;

    private String updatedBy;

    private Date updatedAt;

    public static void assign(DbBase src, DbBase dst) {
        if (Objects.isNull(src) || Objects.isNull(dst)) {
            return;
        }

        dst.setId(src.getId());
        dst.setCreatedBy(src.getCreatedBy());
        dst.setCreatedAt(src.getCreatedAt());
        dst.setUpdatedBy(src.getUpdatedBy());
        dst.setUpdatedAt(src.getUpdatedAt());
    }
}
