package gr.dit.project1.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import gr.dit.project1.entities.Destination;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

	List<Destination> findAllByDestinationIp(@Param("destination") String destination);

}
