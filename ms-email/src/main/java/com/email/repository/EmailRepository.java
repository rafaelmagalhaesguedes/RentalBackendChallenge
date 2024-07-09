package com.email.repository;

import com.email.entity.Email;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Email repository.
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {

}
