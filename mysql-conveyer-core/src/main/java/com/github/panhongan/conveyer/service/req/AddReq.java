package com.github.panhongan.conveyer.service.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.oval.constraint.NotNull;

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

    private String createdBy;
}
