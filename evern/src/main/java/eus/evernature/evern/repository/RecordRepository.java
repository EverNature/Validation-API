package eus.evernature.evern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.evernature.evern.models.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    
}
