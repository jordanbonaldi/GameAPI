package net.neferett.gameapi.modules.permissions;

public class PermissionsModuleManager {	

	public boolean pvp = false;
	public boolean blockPlace = false;
	public boolean blockBreak = false;
	public boolean damagePlayer = false;
	public boolean damageMob = false;
	public boolean food = false;
	public boolean entityTarget = false;
	public boolean itemPickup = false;
	public boolean deadDrop = false;
	public boolean autoRespawn = false;
	public boolean drop = false;
	public boolean craft = false;
	public boolean move = true;
	public boolean movex = true;
	public boolean movey = true;
	public boolean inventoryClick = false;
	
	public void setInventoryClick(boolean inventoryClick) {
		this.inventoryClick = inventoryClick;
	}
	
	public void setMove(boolean move) {
		this.move = move;
	}
	
	public void setMoveX(boolean movex) {
		this.movex = movex;
	}
	public void setMoveY(boolean moveY) {
		this.movey = moveY;
	}
	
	public void setAutoRespawn(boolean autoRespawn) {
		this.autoRespawn = autoRespawn;
	}
	
	public void setBlockBreak(boolean blockBreak) {
		this.blockBreak = blockBreak;
	}
	
	public void setBlockPlace(boolean blockPlace) {
		this.blockPlace = blockPlace;
	}
	
	public void setDamageMob(boolean damageMob) {
		this.damageMob = damageMob;
	}
	
	public void setDamagePlayer(boolean damagePlayer) {
		this.damagePlayer = damagePlayer;
	}
	
	public void setDeadDrop(boolean deadDrop) {
		this.deadDrop = deadDrop;
	}
	
	public void setDrop(boolean drop) {
		this.drop = drop;
	}
	
	public void setFood(boolean food) {
		this.food = food;
	}
	
	public void setItemPickup(boolean itemPickup) {
		this.itemPickup = itemPickup;
	}
	
	public void setPvp(boolean pvp) {
		this.pvp = pvp;
	}
	
	public void setEntityTarget(boolean entityTarget) {
		this.entityTarget = entityTarget;
	}
	
	public void setCraft(boolean craft) {
		this.craft = craft;
	}

}
