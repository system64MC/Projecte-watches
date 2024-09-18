package net.system64.projecttimemaster;

import com.mojang.logging.LogUtils;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.system64.projecttimemaster.items.ModCreativeModTabs;
import net.system64.projecttimemaster.items.ModItems;
import org.slf4j.Logger;

import moze_intel.projecte.ClientRegistration;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ProjectTimeMaster.MODID)
public class ProjectTimeMaster
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "projecttimemaster";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public ProjectTimeMaster()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
//        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
//            event.accept(ModItems.TIMEWATCH_MK2);
//        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    private static void addPropertyOverrides(ResourceLocation override, ItemPropertyFunction propertyGetter, ItemLike... itemProviders) {
        for (ItemLike itemProvider : itemProviders) {
            ItemProperties.register(itemProvider.asItem(), override, propertyGetter);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            event.enqueueWork(() -> {
               addPropertyOverrides(ClientRegistration.ACTIVE_OVERRIDE, (stack, level, entity, seed) -> ItemHelper.checkItemNBT(stack, Constants.NBT_KEY_ACTIVE) ? 1F : 0F, ModItems.TIMEWATCH_MK2, ModItems.TIMEWATCH_MK3, ModItems.TIMEWATCH_MK4, ModItems.TIMEWATCH_MK5, ModItems.TIMEWATCH_MK6, ModItems.TIMEWATCH_MK7, ModItems.TIMEWATCH_MK8);
            });
        }
    }
}
