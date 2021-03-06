package gr.dit.project1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gr.dit.project1.entities.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

}
