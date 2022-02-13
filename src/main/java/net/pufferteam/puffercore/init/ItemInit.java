package net.pufferteam.puffercore.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.pufferteam.puffercore.PufferCore;
import net.pufferteam.puffercore.items.ItemInitUtils;

import java.awt.Color;

public class ItemInit {

    private ItemInit() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PufferCore.MOD_ID);

    public static final ItemInitUtils.Ore CACAHUETE = new ItemInitUtils(ITEMS, BlockInit.BLOCKS).registerOre(new ItemInitUtils.OreBuilder("cacahuete", new Color(123, 108, 68)).ore().block().ingot().nugget().tab(PufferCore.ModCreativeTab.INSTANCE).build());
}
