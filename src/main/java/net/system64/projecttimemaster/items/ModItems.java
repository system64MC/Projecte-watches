package net.system64.projecttimemaster.items;

import moze_intel.projecte.gameObjs.registration.impl.ItemRegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import net.system64.projecttimemaster.ProjectTimeMaster;

import moze_intel.projecte.gameObjs.registration.impl.ItemDeferredRegister;

public class ModItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(ProjectTimeMaster.MODID);

    public static final ItemRegistryObject<TimeWatchMK2> TIMEWATCH_MK2 = ITEMS.registerNoStackFireImmune("time_watch_mk2", (props) -> new TimeWatchMK2(props, 1.5, 0.1));
    public static final ItemRegistryObject<TimeWatchMK2> TIMEWATCH_MK3 = ITEMS.registerNoStackFireImmune("time_watch_mk3", (props) -> new TimeWatchMK2(props, 2, 0.01));
    public static final ItemRegistryObject<TimeWatchMK2> TIMEWATCH_MK4 = ITEMS.registerNoStackFireImmune("time_watch_mk4", (props) -> new TimeWatchMK2(props, 2.5, 0.001));
    public static final ItemRegistryObject<TimeWatchMK2> TIMEWATCH_MK5 = ITEMS.registerNoStackFireImmune("time_watch_mk5", (props) -> new TimeWatchMK2(props, 3, 0.0001));
    public static final ItemRegistryObject<TimeWatchMK2> TIMEWATCH_MK6 = ITEMS.registerNoStackFireImmune("time_watch_mk6", (props) -> new TimeWatchMK2(props, 3.5, 0.00001));
    public static final ItemRegistryObject<TimeWatchMK2> TIMEWATCH_MK7 = ITEMS.registerNoStackFireImmune("time_watch_mk7", (props) -> new TimeWatchMK2(props, 4, 0.000001));
    public static final ItemRegistryObject<TimeWatchMK2> TIMEWATCH_MK8 = ITEMS.registerNoStackFireImmune("time_watch_mk8", (props) -> new TimeWatchMK2(props, 4.5, 0.0000001));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
