package Reika.DyeTrees;

import java.awt.Color;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Libraries.ReikaAABBHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaParticleHelper;
import Reika.DyeTrees.Blocks.BlockRainbowLeaf;
import Reika.DyeTrees.Registry.DyeBlocks;

public class TileEntityRainbowBeacon extends TileEntityBase {

	@Override
	public int getTileEntityBlockID() {
		return DyeBlocks.RAINBOW.getBlockID();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (rand.nextInt(120) == 0) {
			AxisAlignedBB box = ReikaAABBHelper.getBlockAABB(x, this.getTreeBaseY(), z).expand(48, 48, 48);
			List<EntityPlayer> inbox = world.getEntitiesWithinAABB(EntityPlayer.class, box);
			for (int i = 0; i < inbox.size(); i++) {
				EntityPlayer ep = inbox.get(i);
				for (int k = 0; k < 18; k++) {
					double ex = ReikaRandomHelper.getRandomPlusMinus(ep.posX, 1);
					double ez = ReikaRandomHelper.getRandomPlusMinus(ep.posZ, 1);
					double ey = ep.posY-1+rand.nextDouble();
					int ix = MathHelper.floor_double(ex);
					int iy = MathHelper.floor_double(ey);
					int iz = MathHelper.floor_double(ez);
					BlockRainbowLeaf brl = (BlockRainbowLeaf)DyeBlocks.RAINBOW.getBlockInstance();
					int color = brl.colorMultiplier(world, ix, iy, iz);
					Color c = new Color(color);
					float r = c.getRed()/255F;
					float g = c.getGreen()/255F;
					float b = c.getBlue()/255F;
					ReikaParticleHelper.REDSTONE.spawnAt(world, ex, ey, ez, r, g, b);
				}
			}
		}
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	protected String getTEName() {
		return "Rainbow Tree Beacon";
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 0;
	}

	private int getTreeBaseY() {
		return yCoord-26;
	}

}