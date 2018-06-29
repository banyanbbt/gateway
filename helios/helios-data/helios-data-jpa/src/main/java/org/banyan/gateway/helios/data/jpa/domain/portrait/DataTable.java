package org.banyan.gateway.helios.data.jpa.domain.portrait;

import org.banyan.gateway.helios.data.jpa.converter.HeliosDataJpaConverters;
import org.banyan.gateway.helios.data.jpa.model.DataTableType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc hbase表存储数据名字
 * @date 2018-01-10 15:46:27
 */
@Entity
@Table(name = "datatable")
public class DataTable implements Serializable {
    private static final long serialVersionUID = -5847818601767674893L;
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "table", nullable = false, unique = true)
    @Convert(converter = HeliosDataJpaConverters.DataTableTypeConverter.class)
    private DataTableType table;
    @Column(name = "value", nullable = false)
    private String value;
    // 创建者
    @Column(name = "created_by")
    private Integer createdBy;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public DataTableType getTable() {
        return table;
    }

    public DataTable setTable(DataTableType table) {
        this.table = table;
        return this;
    }

    public String getValue() {
        return value;
    }

    public DataTable setValue(String value) {
        this.value = value;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public DataTable setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public DataTable setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public DataTable setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
