package redstonearsenal.item.tool;

/*
It's... Umm... The same thing but with more energy... Yaay?
*/

public class ItemSickleEnderium extends ItemSickleRF {

	public ItemSickleEnderium(ToolMaterial toolMaterial) {
		super(toolMaterial);
		
		damage= 6;
		maxEnergy = 320000;
		energyPerUse = 350;
		energyPerUseCharged = 2000;
		radius = 4;
	}
}
