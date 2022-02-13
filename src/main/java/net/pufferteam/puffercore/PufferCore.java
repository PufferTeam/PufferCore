package net.pufferteam.puffercore;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.pufferteam.puffercore.init.BlockInit;
import net.pufferteam.puffercore.init.ItemInit;
import net.pufferteam.puffercore.items.ItemInitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod("puffercore")
@Mod.EventBusSubscriber(modid = "puffercore", bus = Mod.EventBusSubscriber.Bus.MOD)
public class PufferCore {

    public static final String MOD_ID = "puffercore";
    private static final Logger LOGGER = LogManager.getLogger();

    public PufferCore() {
        MinecraftForge.EVENT_BUS.register(this);
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);

    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        ItemInitUtils.onRegisterItems(event, BlockInit.BLOCKS, ModCreativeTab.INSTANCE);
    }

    public static class ModCreativeTab extends CreativeModeTab {

        public static final ModCreativeTab INSTANCE = new ModCreativeTab();

        private ModCreativeTab() {
            super(CreativeModeTab.TABS.length, MOD_ID);
        }

        @NotNull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.AMETHYST_SHARD);
        }
    }
}
