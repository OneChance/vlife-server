package app.inventory.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.account.entity.Account;
import app.inventory.entity.Inventory;

@Service
public class InventoryService{

	public List<Inventory> getInventoryByAccount(Account account) {
		List<Inventory> inventoryList = inventoryRepository.getByAccount(account.getId());
		if (inventoryList == null) {
			inventoryList = new ArrayList<Inventory>();
		}
		return inventoryList;
	}

	public void setInventoryDetail(Inventory inventory) {
		if (inventory.getType().equals("material")) {

		} else if (inventory.getType().equals("food")) {

		}
	}

	@Resource
	InventoryRepository inventoryRepository;
}
