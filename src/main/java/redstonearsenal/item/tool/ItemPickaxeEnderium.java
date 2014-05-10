package redstonearsenal.item.tool;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cofh.util.MathHelper;

public class ItemPickaxeEnderium extends ItemPickaxeRF {
	int range = 4;

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
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {

		if (!(entity instanceof EntityPlayer)) {
			return false;
		}
		EntityPlayer player = (EntityPlayer) entity;

		if ((block == Blocks.cobblestone || block == Blocks.stone || block == Blocks.sandstone || block == Blocks.netherrack) && isEmpowered(stack)) {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int k = z - 1; k <= z + 1; k++) {
					for (int j = y - 2; j <= y + 2; j++) {
						if (world.getBlock(i, j, k) == Blocks.cobblestone || world.getBlock(i, j, k) == Blocks.stone || world.getBlock(i, j, k) == Blocks.sandstone || world.getBlock(i, j, k) == Blocks.netherrack) {
							int facing = MathHelper.floor(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

							if (facing == 0) {
								int coordZ = z - range;
								if (world.isAirBlock(i, j, coordZ)) {
									world.setBlockToAir(i, j, z);
									world.setBlock(i, j, coordZ, block);
									for (int n = 0; n <= 5; n++)
										world.spawnParticle("portal", i, j, z, 1, 1, 1);
								} else
									harvestBlock(world, i, j, z, player);
							} else if (facing == 1) {
								int coordX = x + range;
								if (world.isAirBlock(coordX, j, k)) {
									world.setBlockToAir(x, j, k);
									world.setBlock(coordX, j, k, block);
									for (int n = 0; n <= 5; n++)
										world.spawnParticle("portal", x, j, k, 1, 1, 1);
								} else
									harvestBlock(world, x, j, k, player);
							} else if (facing == 2) {
								int coordZ = z + range;
								if (world.isAirBlock(i, j, coordZ)) {
									world.setBlockToAir(i, j, z);
									world.setBlock(i, j, coordZ, block);
									for (int n = 0; n <= 5; n++)
										world.spawnParticle("portal", i, j, z, 1, 1, 1);
								} else
									harvestBlock(world, i, j, z, player);
							} else if (facing == 3) {
								int coordX = x - range;
								if (world.isAirBlock(coordX, j, k)) {
									world.setBlockToAir(x, j, k);
									world.setBlock(coordX, j, k, block);
									for (int n = 0; n <= 5; n++)
										world.spawnParticle("portal", x, j, k, 1, 1, 1);
								} else
									harvestBlock(world, x, j, k, player);
							}
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
}
