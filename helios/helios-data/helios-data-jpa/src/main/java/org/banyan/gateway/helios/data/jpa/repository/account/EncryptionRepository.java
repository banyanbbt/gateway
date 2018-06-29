package org.banyan.gateway.helios.data.jpa.repository.account;

import org.banyan.gateway.helios.data.jpa.domain.account.Encryption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * EncryptionRepository
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月02日 14:33:00
 */
@Repository
public interface EncryptionRepository extends CrudRepository<Encryption, Integer> {
}
