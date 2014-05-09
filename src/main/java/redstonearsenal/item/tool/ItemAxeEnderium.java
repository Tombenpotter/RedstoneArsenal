package redstonearsenal.item.tool;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAxeEnderium extends ItemAxeRF {

	public ItemAxeEnderium(Item.ToolMaterial toolMaterial) {

		super(toolMaterial);
		damage = 6;
		maxEnergy = 320000;
		energyPerUse = 350;
		energyPerUseCharged = 2200;
	}

	public ItemAxeEnderium(Item.ToolMaterial toolMaterial, int harvestLevel) {
		this(toolMaterial);
		this.harvestLevel = harvestLevel;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {

		if (!(entity instanceof EntityPlayer)) {
			return false;
		}
		if (block.getBlockHardness(world, x, y, z) == 0.0D) {
			return true;
		}
		EntityPlayer player = (EntityPlayer) entity;

		if (block.getMaterial() == Material.wood && isEmpowered(stack)) {
			for (int i = x - 2; i <= x + 2; i++) {
				for (int k = z - 2; k <= z + 2; k++) {
					for (int j = y - 2; j <= y + 2; j++) {
						if (world.getBlock(i, j, k).getMaterial() == Material.wood) {
							harvestBlock(world, i, j, k, player);
						}
					}
				}
			}
		}
		if (!player.capabilities.isCreativeMode) {
			useEnergy(stack, false);
		}
		return true;
	}
}
