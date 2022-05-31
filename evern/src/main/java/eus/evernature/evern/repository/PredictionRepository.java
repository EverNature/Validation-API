package eus.evernature.evern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import eus.evernature.evern.models.Prediction;

public interface PredictionRepository extends JpaRepository<Prediction, Integer>, JpaSpecificationExecutor<Prediction> {
    
}