package net.system64.projecttimemaster.items;

import moze_intel.projecte.api.block_entity.IDMPedestal;
import moze_intel.projecte.api.capabilities.item.IItemCharge;
import moze_intel.projecte.api.capabilities.item.IPedestalItem;
import moze_intel.projecte.config.ProjectEConfig;
import moze_intel.projecte.gameObjs.items.IBarHelper;
import moze_intel.projecte.gameObjs.items.rings.TimeWatch;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.system64.projecttimemaster.mixin.TimeWatchMethods;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TimeWatchMK2 extends TimeWatch implements IPedestalItem, IItemCharge, IBarHelper {

    final private double ticksMult;
    final private double slowdownMult;

    public TimeWatchMK2(Properties props, double ticksMult, double slowdownMult) {
        super(props);
        this.ticksMult = ticksMult;
        this.slowdownMult = slowdownMult;
    }

    @Override
    public <PEDESTAL extends BlockEntity & IDMPedestal> boolean updateInPedestal(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockPos pos,
                                                                                              @NotNull PEDESTAL pedestal) {
        // Change from old EE2 behaviour (universally increased tickrate) for safety and impl reasons.
        if (!level.isClientSide && ProjectEConfig.server.items.enableTimeWatch.get()) {
            AABB bBox = pedestal.getEffectBounds();
            if (ProjectEConfig.server.effects.timePedBonus.get() > 0) {
                // speedUpBlockEntities(level, ProjectEConfig.server.effects.timePedBonus.get() * 10, bBox);

                ((TimeWatchMethods) this).invokeSpeedUpBlockEntities(level, (int) (ProjectEConfig.server.effects.timePedBonus.get() * ticksMult), bBox);
                ((TimeWatchMethods) this).invokeSpeedUpRandomTicks(level, (int) (ProjectEConfig.server.effects.timePedBonus.get() * ticksMult), bBox);
            }
            if (ProjectEConfig.server.effects.timePedMobSlowness.get() < 1.0F) {
                ((TimeWatchMethods) this).invokeSlowMobs(level, bBox, 0);
            }
        }
        return false;
    }

    @NotNull
    @Override
    public List<Component> getPedestalDescription() {
        List<Component> list = new ArrayList<>();
        if (ProjectEConfig.server.effects.timePedBonus.get() > 0) {
            list.add(PELang.PEDESTAL_TIME_WATCH_1.translateColored(ChatFormatting.BLUE, (int) (ProjectEConfig.server.effects.timePedBonus.get() * ticksMult)));
        }
        if (ProjectEConfig.server.effects.timePedMobSlowness.get() < 1.0F) {
            list.add(PELang.PEDESTAL_TIME_WATCH_2.translateColored(ChatFormatting.BLUE, String.format("%.3f", ProjectEConfig.server.effects.timePedMobSlowness.get() * slowdownMult)));
        }
        return list;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int invSlot, boolean isHeld) {
        super.inventoryTick(stack, level, entity, invSlot, isHeld);
        if (entity instanceof Player player) {
            if (invSlot < Inventory.getSelectionSize() && ProjectEConfig.server.items.enableTimeWatch.get()) {
                byte timeControl = ((TimeWatchMethods) this).invokeGetTimeBoost(stack);
                if (!level.isClientSide && level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                    ServerLevel serverWorld = (ServerLevel)level;
                    if (timeControl == 1) {
                        serverWorld.setDayTime(Math.min(level.getDayTime() + (long)((this.getCharge(stack) + 1) * 4.0 * ticksMult), Long.MAX_VALUE));
                    } else if (timeControl == 2) {
                        long charge = (long)(this.getCharge(stack) + 1);
                        if (level.getDayTime() - (long)(charge * 4.0 * ticksMult) < 0L) {
                            serverWorld.setDayTime(0L);
                        } else {
                            serverWorld.setDayTime(level.getDayTime() - (long)(charge * 4.0 * ticksMult));
                        }
                    }
                }

                if (!level.isClientSide && ItemHelper.checkItemNBT(stack, "Active")) {
                    long reqEmc = EMCHelper.removeFractionalEMC(stack, this.getEmcPerTick(this.getCharge(stack)));
                    if (!consumeFuel(player, stack, reqEmc, true)) {
                        return;
                    }

                    int charge = this.getCharge(stack);
                    byte bonusTicks;
                    float mobSlowdown;
                    if (charge == 0) {
                        bonusTicks = (byte)(8 * ticksMult);
                        mobSlowdown = 0.25F * (float) slowdownMult;
                    } else if (charge == 1) {
                        bonusTicks = (byte) (12 * ticksMult);
                        mobSlowdown = 0.16F * (float) slowdownMult;
                    } else {
                        bonusTicks = (byte) (16 * ticksMult);
                        mobSlowdown = 0.12F * (float) slowdownMult;
                    }

                    AABB bBox = player.getBoundingBox().inflate(8.0);
                    ((TimeWatchMethods)this).invokeSpeedUpBlockEntities(level, bonusTicks, bBox);
                    ((TimeWatchMethods)this).invokeSpeedUpRandomTicks(level, bonusTicks, bBox);
                    ((TimeWatchMethods)this).invokeSlowMobs(level, bBox, (double)mobSlowdown);
                    return;
                }

                return;
            }
        }

    }
}