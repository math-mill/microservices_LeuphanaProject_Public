package de.leuphana.customer_service.connector.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import de.leuphana.customer_service.component.structure.Address;

public interface AddressRepository extends JpaRepository<Address, UUID>{

}
