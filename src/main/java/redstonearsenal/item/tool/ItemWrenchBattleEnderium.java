package redstonearsenal.item.tool;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import redstonearsenal.util.Utils;


/*
The idea behind the BattleWrench ability is swinging the wrench in a wide arc damaging mobs/players/etc in a 2 (4 if empowered) block radius. In order to "simulate" the
swing, the player will be turned slightly as if the momentum moved them. This is not how we intended (wanted the player to spin in a full circle) but this works, too.
*/
 public class ItemWrenchBattleEnderium extends ItemWrenchBattleRF {
	int radius = 2;
	Random random = new Random();
	int spinDamage = 2;
	int resistanceEffect = 1;

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
    		player.addPotionEffect(new PotionEffect(Potion.resistance.id, 20, resistanceEffect, false));
    		player.swingItem();
			if (iter != null) {
				while (iter.hasNext()) {
					EntityLivingBase entity = (EntityLivingBase) iter.next();
					entity.attackEntityFrom(Utils.causePlayerFluxDamage(player), spinDamage);
					player.setAngles(-180, 10);
					world.spawnParticle("largeexplode", player.posX, player.posY, player.posZ, 1, 1, 1);
					if (!player.capabilities.isCreativeMode && random.nextInt(5) == 0)
						useEnergy(stack, false);
				}
			}
		return stack;
	}
}
