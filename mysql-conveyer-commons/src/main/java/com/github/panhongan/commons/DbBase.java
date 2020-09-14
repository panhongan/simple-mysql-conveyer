package com.github.panhongan.commons;

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

        dst.setCreatedBy(src.getCreatedBy());
        dst.setCreatedAt(src.getCreatedAt());
        dst.setUpdatedBy(src.getUpdatedBy());
        dst.setUpdatedAt(src.getUpdatedAt());
    }
}
