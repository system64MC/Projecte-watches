package net.system64.examplemod.mixin;

import moze_intel.projecte.gameObjs.items.rings.TimeWatch;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.system64.examplemod.items.TimeWatchMK1;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TimeWatch.class)
public interface TimeWatchMethods {

    @Invoker("speedUpBlockEntities")
    public void invokeSpeedUpBlockEntities(Level level, int bonusTicks, AABB bBox);
}
