package redstonearsenal.item.tool;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPickaxeEnderium extends ItemPickaxeRF {
	int range = 3;

	public ItemPickaxeEnderium(Item.ToolMaterial toolMaterial) {
		super(toolMaterial);
		maxEnergy = 320000;
		energyPerUse = 350;
		energyPerUseCharged = 950;
	}

	public ItemPickaxeEnderium(Item.ToolMaterial toolMaterial, int harvestLevel) {
		this(toolMaterial);
		this.harvestLevel = harvestLevel;
	}

	@Override
	/** Temporary Empowerement Bonus **/
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		if (player.isSneaking() && isEmpowered(stack)) {
			if (!(player instanceof EntityPlayer)) {
				return false;
			}
			World world = player.worldObj;
			Block block = world.getBlock(x, y, z);

			if ((block.getMaterial() == Material.rock || block.getMaterial() == Material.iron || block.getMaterial() == Material.anvil) && isEmpowered(stack)) {
				for (int i = x - 1; i <= x + 1; i++) {
					for (int k = z - 1; k <= z + 1; k++) {
						for (int j = y - 2; j <= y + 2; j++) {
							if (world.getBlock(i, j, k).getMaterial() == Material.rock || world.getBlock(i, j, k).getMaterial() == Material.iron || world.getBlock(i, j, k).getMaterial() == Material.anvil) {
								harvestBlock(world, i, j, k, player);
							}
						}
					}
				}
				return true;
			}
			if (!player.capabilities.isCreativeMode) {
				useEnergy(stack, false);
			}
			return true;
		}
		return false;
	}
}
