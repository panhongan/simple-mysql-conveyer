package com.github.panhongan.conveyer.service.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.oval.constraint.Min;
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
public class ModifyReq<B> {

    @NotNull
    @Min(value = 1, message = "id最小值为1")
    private Long oriId;

    @NotNull
    private B newBizObj;

    @NotBlank
    @NotNull
    private String updatedBy;

    @NotBlank
    @NotNull
    private String dataType;

    /**
     * 数据生效时间
     */
    private Date effectTime;
}
