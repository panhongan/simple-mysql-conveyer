package com.github.panhongan.conveyer.service.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.util.Date;

/**
 * @author panhongan
 * @since 2020.9.1
 * @version 1.0
 *
 * @param <B> 业务对象
 */

@Getter
@Setter
@ToString
public class AddReq<B> {

    @NotNull
    private B bizObj;

    @NotBlank
    @NotNull
    private String createdBy;

    /**
     * 数据标识
     */
    @NotBlank
    @NotNull
    private String dataType;

    /**
     * 数据生效时间
     */
    private Date effectTime;
}
