package redstonearsenal.item.tool;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import redstonearsenal.util.Utils;

public class ItemWrenchBattleEnderium extends ItemWrenchBattleRF {
	int radius = 2;
	Random random = new Random();

	public ItemWrenchBattleEnderium(ToolMaterial toolMaterial) {
		super(toolMaterial);
		damage = 8;
		damageCharged = 5;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (isEmpowered(stack))
			radius = 4;

		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
		Iterator iter = world.getEntitiesWithinAABB(EntityLivingBase.class, bb).iterator();
		if (iter != null) {
			while (iter.hasNext()) {
				EntityLivingBase entity = (EntityLivingBase) iter.next();
				entity.attackEntityFrom(Utils.causePlayerFluxDamage(player), 2);
				player.setAngles(-90, 90);
				world.spawnParticle("largeexplode", player.posX, player.posY, player.posZ, 1, 1, 1);
				if (random.nextInt(20) == 0 && !player.capabilities.isCreativeMode)
					useEnergy(stack, true);
			}
		}
		return stack;
	}
}
