package eus.evernature.evern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.evernature.evern.models.Prediction;

public interface PredictionRepository extends JpaRepository<Prediction, Integer> {
    
}
