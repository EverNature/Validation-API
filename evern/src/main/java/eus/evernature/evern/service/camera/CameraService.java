package eus.evernature.evern.service.camera;

import java.util.Optional;

import eus.evernature.evern.models.Camera;

public interface CameraService {
    Optional<Camera> getCameraById(int id);
}
