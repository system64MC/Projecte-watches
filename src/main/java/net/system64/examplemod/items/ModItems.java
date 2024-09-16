package net.system64.examplemod.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.*;
import net.system64.examplemod.ExampleMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);

    public static final RegistryObject<Item> MY_ITEM = ITEMS.register("ombry_ombry_magica", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIMEWATCH_MK1 = ITEMS.register("timewatch_mk1", () -> new TimeWatchMK1(new Item.Properties().stacksTo(1).durability(1000)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
