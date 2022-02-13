package net.pufferteam.puffercore.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.Objects;

public class ItemInitUtils {

    private final DeferredRegister<Item> register;
    private final DeferredRegister<Block> blockRegister;

    public ItemInitUtils(@Nonnull DeferredRegister<Item> register, @Nonnull DeferredRegister<Block> blockRegister) {
        this.register = register;
        this.blockRegister = blockRegister;
    }

    public Ore registerOre(Ore ore) {
        if(ore.ore) {
            ore.oreItem = registerItem("raw_" + ore.registryName, new Item.Properties().tab(ore.tab));
            ore.rawOreBlock = registerBlock("raw_" + ore.registryName + "_block");
        }
        if(ore.ingot) ore.ingotItem = registerItem(ore.registryName + "_ingot", new Item.Properties().tab(ore.tab));
        if(ore.nugget) ore.nuggetItem = registerItem(ore.registryName + "_nugget", new Item.Properties().tab(ore.tab));
        if(ore.block) ore.storageBlock = registerBlock(ore.registryName + "_block");
        if(ore.dust) ore.dustItem = registerItem(ore.registryName + "_dust", new Item.Properties().tab(ore.tab));
        return ore;
    }

    private RegistryObject<Block> registerBlock(String name) {
        return blockRegister.register(name, () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    }

    private RegistryObject<Item> registerItem(String name, Item.Properties properties) {
        return register.register(name, () -> new Item(properties));
    }

    //Pain
    public static class OreBuilder {
        public final String registryName;
        private final int color;

        //Build all the parameters
        private Boolean oreItem, ingot, nugget, block, dust;
        private CreativeModeTab tab = CreativeModeTab.TAB_MISC;

        public OreBuilder(String registryName, int rgbColor) {
            this.registryName = registryName.replaceAll("\\s+", "_").toLowerCase();
            this.color = rgbColor;
        }

        public OreBuilder(String registryName, Color color) {
            this(registryName, color.getRGB());
        }

        public OreBuilder tab(CreativeModeTab tab) {
            this.tab = tab;
            return this;
        }

        public OreBuilder ore() {
            this.oreItem = inverseBool(oreItem);
            return this;
        }

        public OreBuilder ingot() {
            this.ingot = inverseBool(ingot);
            return this;
        }

        public OreBuilder nugget() {
            this.nugget = inverseBool(nugget);
            return this;
        }

        public OreBuilder block() {
            this.block = inverseBool(block);
            return this;
        }

        public OreBuilder dust() {
            this.dust = inverseBool(dust);
            return this;
        }

        private boolean inverseBool(Boolean bool) {
            return bool == null || !bool;
        }

        private boolean getBool(Boolean bool) {
            return bool != null && bool;
        }

        public Ore build() {
            return new Ore(registryName, color, getBool(oreItem), getBool(ingot), getBool(nugget), getBool(block), getBool(dust), tab);
        }
    }

    public static class Ore {
        public final String registryName;
        private final int color;

        //Add new registry parameters here
        private final boolean ore, ingot, nugget, block, dust;
        private final CreativeModeTab tab;

        //Note the list of all the items/slurry/fluid that can be registered with the functions above
        private RegistryObject<Item> oreItem, ingotItem, nuggetItem, dustItem;
        private RegistryObject<Block> storageBlock, rawOreBlock;
        //private FluidInit.FluidObject moltenFluidObject;

        //Reference all the parameters here
        public Ore(String registryName, int color, boolean oreItem, boolean ingot, boolean nugget, boolean block, boolean dust, CreativeModeTab tab) {
            this.registryName = registryName;
            this.color = color;
            this.ore = oreItem;
            this.ingot = ingot;
            this.nugget = nugget;
            this.block = block;
            this.dust = dust;
            this.tab = tab;
        }

        public int getColor() {
            return color;
        }

        public boolean getOre() {
            return ore;
        }

        public boolean getIngot() {
            return ingot;
        }

        public boolean getNugget() {
            return nugget;
        }

        public boolean getBlock() {
            return block;
        }

        public boolean getDust() {
            return dust;
        }

        public RegistryObject<Item> getOreItem() {
            return oreItem;
        }

        public RegistryObject<Item> getIngotItem() {
            return ingotItem;
        }

        public RegistryObject<Item> getNuggetItem() {
            return nuggetItem;
        }

        public RegistryObject<Item> getDustItem() {
            return dustItem;
        }

        public RegistryObject<Block> getStorageBlock() {
            return storageBlock;
        }

        public RegistryObject<Block> getRawOreBlock() {
            return rawOreBlock;
        }

        public CreativeModeTab getTab() {
            return tab;
        }

        //TODO: Add molten fluid
        /*public FluidInit.FluidObject getMoltenFluidObject() {
            return moltenFluidObject;
        }*/
    }

    public static void onRegisterItems(final RegistryEvent.Register<Item> event, DeferredRegister<Block> blockRegistry, CreativeModeTab tab) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        blockRegistry.getEntries().stream()
                .filter(blockRegistryObject -> !(blockRegistryObject.get() instanceof LiquidBlock) && !(blockRegistryObject.get() instanceof TorchBlock))
                .map(RegistryObject::get).forEach(block -> {
                    final Item.Properties properties = new Item.Properties().tab(tab);
                    final BlockItem blockItem = new BlockItem(block, properties.tab(tab));
                    blockItem.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
                    registry.register(blockItem);
                });
    }

}
