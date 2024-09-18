package net.system64.projecttimemaster.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.system64.projecttimemaster.ProjectTimeMaster;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ProjectTimeMaster.MODID);
    
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> MY_TAB = CREATIVE_MODE_TABS.register("projecttimemaster_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TIMEWATCH_MK2.get()))
            .title(Component.translatable("creativetab.projecttimemaster_tab"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.TIMEWATCH_MK2.get());
                output.accept(ModItems.TIMEWATCH_MK3.get());
                output.accept(ModItems.TIMEWATCH_MK4.get());
                output.accept(ModItems.TIMEWATCH_MK5.get());
                output.accept(ModItems.TIMEWATCH_MK6.get());
                output.accept(ModItems.TIMEWATCH_MK7.get());
                output.accept(ModItems.TIMEWATCH_MK8.get());
            })
            .build());
}
