package net.pufferteam.puffercore.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.pufferteam.puffercore.PufferCore;

public class BlockInit {

    private BlockInit() {}

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PufferCore.MOD_ID);

}
