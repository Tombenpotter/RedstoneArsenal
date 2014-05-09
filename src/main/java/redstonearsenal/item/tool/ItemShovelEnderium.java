package redstonearsenal.item.tool;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ItemShovelEnderium extends ItemShovelRF {
	int bonemealNumber = 5;

	public ItemShovelEnderium(ToolMaterial toolMaterial) {
		super(toolMaterial);
		maxEnergy = 320000;
		energyPerUse = 350;
		energyPerUseCharged = 950;
	}

	public ItemShovelEnderium(Item.ToolMaterial toolMaterial, int harvestLevel) {
		this(toolMaterial);
		this.harvestLevel = harvestLevel;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int hitSide, float hitX, float hitY, float hitZ) {
		if (!player.canPlayerEdit(x, y, z, hitSide, stack) || !player.capabilities.isCreativeMode && getEnergyStored(stack) < getEnergyPerUse(stack)) {
			return false;
		}

		Block block = world.getBlock(x, y, z);
		BonemealEvent event = new BonemealEvent(player, world, block, x, y, z);

		if (MinecraftForge.EVENT_BUS.post(event)) {
			return false;
		}

		if (event.getResult() == Result.ALLOW) {
			if (!player.capabilities.isCreativeMode) {
				useEnergy(stack, false);
			}
			return true;
		}

		if (block instanceof IGrowable) {
			IGrowable growable = (IGrowable) block;

			if (growable.func_149851_a(world, x, y, z, world.isRemote)) {
				if (!world.isRemote) {
					if (growable.func_149852_a(world, world.rand, x, y, z)) {
						growable.func_149853_b(world, world.rand, x, y, z);
					}

					if (growable.func_149852_a(world, world.rand, x, y, z) && isEmpowered(stack)) {
						for (int i = 0; i <= 5; i++) {
							growable.func_149853_b(world, world.rand, x, y, z);
						}
					}
				}
			}
			return true;
		}
		return false;
	}
}
