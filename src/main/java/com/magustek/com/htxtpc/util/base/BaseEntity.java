package com.magustek.com.htxtpc.util.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BasePage {
    @TableGenerator(
            name = "ID_GENERATOR",
            table = "jpa_id_generator",
            pkColumnName = "pk_name",
            pkColumnValue = "mro_id",
            valueColumnName = "pk_value",
            initialValue = 1,
            allocationSize = 1)
    @GeneratedValue(generator = "ID_GENERATOR") @Id private Long id;
    @CreatedDate @Column(name = "CRTIME") protected Date createDate; //创建日期
    @CreatedBy @Column(name = "CRNAME") protected String creator; //创建人
    @LastModifiedDate @Column(name = "CHDATE") protected Date updateDate; //最后修改日期
    @LastModifiedBy @Column(name = "CHNAME") protected String updater; //最后修改人

}
