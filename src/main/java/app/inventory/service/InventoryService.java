package app.inventory.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.account.entity.Account;
import app.inventory.entity.Inventory;

@Service
public class InventoryService extends DatabaseService{
	
	public InventoryService(JdbcTemplate jdbcTemplate, EntityManagerFactory factory) {
		super(jdbcTemplate, factory);
	}
	
	public List<Inventory> getInventoryByAccount(Account account) {
		String sql = "select * from inventory where account=?";
		List<Inventory> inventoryList = this.gets(Inventory.class, sql,
				new Long[] { account.getId() });
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
}
