package eus.evernature.evern.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.evernature.evern.models.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    public Optional<Animal> findByName(String name);
}
