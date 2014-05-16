package redstonearsenal.item.tool;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import redstonearsenal.util.Utils;

public class ItemWrenchBattleEnderium extends ItemWrenchBattleRF {
	int radius = 2;
	Random random = new Random();
	int spinDamage = 2;
	int resistanceEffect = 2;

	public ItemWrenchBattleEnderium(ToolMaterial toolMaterial) {
		super(toolMaterial);
		maxEnergy = 320000;
		damage = 8;
		damageCharged = 5;
		energyPerUse = 350;
		energyPerUseCharged = 950;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (isEmpowered(stack)) {
			radius = 4;
			spinDamage = 4;
			resistanceEffect = 4;
		}

		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
		Iterator iter = world.getEntitiesWithinAABB(EntityLivingBase.class, bb).iterator();
		if (iter != null) {
			while (iter.hasNext()) {
				EntityLivingBase entity = (EntityLivingBase) iter.next();
				entity.attackEntityFrom(Utils.causePlayerFluxDamage(player), spinDamage);
				player.setAngles(-90, 90);
				world.spawnParticle("largeexplode", player.posX, player.posY, player.posZ, 1, 1, 1);
				if (!player.capabilities.isCreativeMode && random.nextInt(10) == 0)
					useEnergy(stack, false);
			}
		}
		player.addPotionEffect(new PotionEffect(Potion.resistance.id, 20, resistanceEffect, false));
		return stack;
	}
}
