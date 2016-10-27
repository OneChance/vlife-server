package app.inventory.service;

import app.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long>{
    List<Inventory> getByAccount(Long account);
}
