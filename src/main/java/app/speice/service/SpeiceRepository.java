package app.speice.service;

import app.speice.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeiceRepository extends JpaRepository<Species, Long> {

}
