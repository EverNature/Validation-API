package eus.evernature.evern.service.camera;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.evernature.evern.models.Camera;
import eus.evernature.evern.repository.CameraRepository;

@Service
public class CameraServiceImpl implements CameraService {
    
    @Autowired
    CameraRepository cameraRepository;
    
    @Override
    public Optional<Camera> getCameraById(int id) {
        return cameraRepository.findById(id);
    }

    
}
