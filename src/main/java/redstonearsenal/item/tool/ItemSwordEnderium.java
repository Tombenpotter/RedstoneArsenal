package redstonearsenal.item.tool;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import codechicken.lib.vec.Vector3;

public class ItemSwordEnderium extends ItemSwordRF {
	int radius = 5;
	Random random = new Random();

	public ItemSwordEnderium(ToolMaterial toolMaterial) {
		super(toolMaterial);
		maxEnergy = 320000;
		damage = 10;
		damageCharged = 6;
		energyPerUse = 350;
		energyPerUseCharged = 950;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {

		if (isEmpowered(stack))
			radius = 10;

		if (!world.isRemote && entity instanceof EntityPlayer && ((EntityPlayer) entity).isUsingItem()) {
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(entity.posX - radius, entity.posY - radius, entity.posZ - radius, entity.posX + radius, entity.posY + radius, entity.posZ + radius);
			Iterator iter = world.getEntitiesWithinAABB(EntityItem.class, bb).iterator();
			if (iter != null) {
				while (iter.hasNext()) {
					EntityItem item = (EntityItem) iter.next();
					moveEntity(item, Vector3.fromEntityCenter(entity), 0.1);
					if (random.nextInt(10) == 0)
						world.playSoundAtEntity(entity, "mob.endermen.portal", 1.0F, 1.0F);
				}
			}
		}
	}

	public void moveEntity(Entity ent, Vector3 target, double speed) {
		double mx = getBlendDouble(ent.posX, target.x, speed);
		double my = getBlendDouble(ent.posY, target.y, speed);
		double mz = getBlendDouble(ent.posZ, target.z, speed);

		ent.velocityChanged = true;
		ent.isAirBorne = true;
		ent.addVelocity(mx, my, mz);
	}

	public double getBlendDouble(double d1, double d2, double blend) {
		if (d1 > d2)
			return -blend;
		if (d1 < d2)
			return blend;
		return 0;
	}
}
