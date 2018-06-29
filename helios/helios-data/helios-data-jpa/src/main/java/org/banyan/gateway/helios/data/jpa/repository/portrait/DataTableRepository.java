package org.banyan.gateway.helios.data.jpa.repository.portrait;

import org.banyan.gateway.helios.data.jpa.domain.portrait.DataTable;
import org.banyan.gateway.helios.data.jpa.model.DataTableType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc data table repository
 * @date 2017-12-27 09:31:48
 */
@Repository
public interface DataTableRepository extends CrudRepository<DataTable, DataTableType> {

}
