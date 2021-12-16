package com.tomhurry.transactional.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author taozhi
 * @since 2021-04-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Job extends Model<Job> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String status;

    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
