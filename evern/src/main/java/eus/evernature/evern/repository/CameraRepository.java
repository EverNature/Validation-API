package eus.evernature.evern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.evernature.evern.models.Camera;

public interface CameraRepository extends JpaRepository<Camera, Integer> {
    
}
