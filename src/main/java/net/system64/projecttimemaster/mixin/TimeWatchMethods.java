package net.system64.projecttimemaster.mixin;

import moze_intel.projecte.gameObjs.items.rings.TimeWatch;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TimeWatch.class)
public interface TimeWatchMethods {

    @Invoker("speedUpBlockEntities")
    public void invokeSpeedUpBlockEntities(Level level, int bonusTicks, AABB bBox);

    @Invoker("slowMobs")
    public void invokeSlowMobs(Level level, AABB bBox, double mobSlowdown);

    @Invoker("speedUpRandomTicks")
    public void invokeSpeedUpRandomTicks(Level level, int bonusTicks, AABB bBox);

    @Invoker("getTimeBoost")
    byte invokeGetTimeBoost(ItemStack stack);
}
