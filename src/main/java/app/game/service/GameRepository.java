package app.game.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository {
    @Query("select max(ratioEnd) from species")
    int getRandomMax();
    @Query("select id from vlife.species where ratioStart<=:randomValue and ratioEnd>=:randomValue")
    Long getSpeiceByRandomValue(@Param("randomValue") Integer randomValue);
}
