package br.com.duxusdesafio.repository;

import br.com.duxusdesafio.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long > {
}
