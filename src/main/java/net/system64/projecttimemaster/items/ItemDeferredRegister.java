package net.system64.projecttimemaster.items;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import moze_intel.projecte.gameObjs.registration.WrappedDeferredRegister;
import moze_intel.projecte.gameObjs.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemDeferredRegister extends WrappedDeferredRegister<Item> {

    public ItemDeferredRegister(String modid) {
        super(ForgeRegistries.ITEMS, modid);
    }

    public <ITEM extends TimeWatchMK2> ItemRegistryObject<ITEM> registerNoStackFireImmune(String name, Function<Item.Properties, ITEM> sup) {
        return register(name, sup, properties -> properties.stacksTo(1).fireResistant());
    }

    public <ITEM extends TimeWatchMK2> ItemRegistryObject<ITEM> register(String name, Function<Item.Properties, ITEM> sup, UnaryOperator<Item.Properties> propertyModifier) {
        return register(name, () -> sup.apply(propertyModifier.apply(new Item.Properties())));
    }

    public <ITEM extends Item> ItemRegistryObject<ITEM> register(String name, Supplier<? extends ITEM> sup) {
        return register(name, sup, ItemRegistryObject::new);
    }
}