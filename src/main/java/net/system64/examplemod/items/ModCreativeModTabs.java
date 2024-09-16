package net.system64.examplemod.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.system64.examplemod.ExampleMod;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID);
    
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> MY_TAB = CREATIVE_MODE_TABS.register("my_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MY_ITEM.get()))
            .title(Component.translatable("creativetab.my_tab"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.MY_ITEM.get());
            })
            .build());
}
