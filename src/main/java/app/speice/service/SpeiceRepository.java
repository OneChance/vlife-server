package app.speice.service;

import app.speice.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpeiceRepository extends JpaRepository<Species, Long> {
    @Query("select max(ratioEnd) from Species")
    int getRandomMax();
    @Query("select id from Species where ratioStart<=:randomValue and ratioEnd>=:randomValue")
    Long getSpeiceByRandomValue(@Param("randomValue") Integer randomValue);
}
