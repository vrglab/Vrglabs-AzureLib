package org.Vrglab.AzureLib.fabric;

import mod.azure.azurelib.common.api.common.enchantments.IncendiaryEnchantment;
import mod.azure.azurelib.common.internal.common.AzureLib;
import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.blocks.TickingLightBlock;
import mod.azure.azurelib.common.internal.common.blocks.TickingLightEntity;
import mod.azure.azurelib.common.internal.common.config.AzureLibConfig;
import mod.azure.azurelib.common.internal.common.config.format.ConfigFormats;
import mod.azure.azurelib.common.internal.common.config.io.ConfigIO;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import org.Vrglab.AzureLib.VlAzureLibMod;
import org.Vrglab.AzureLib.fabriclike.VlAzureLibModFabricLike;
import org.Vrglab.fabriclike.VLModFabricLike;

public final class VlAzureLibModFabric implements ModInitializer {

    public static BlockEntityType<TickingLightEntity> TICKING_LIGHT_ENTITY;
    public static final TickingLightBlock TICKING_LIGHT_BLOCK = new TickingLightBlock(BlockBehaviour.Properties.of().sound(SoundType.CANDLE).lightLevel(TickingLightBlock.litBlockEmission(15)).pushReaction(PushReaction.DESTROY).noOcclusion());
    public static final Enchantment INCENDIARYENCHANTMENT = new IncendiaryEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);

    @Override
    public void onInitialize() {
        ConfigIO.FILE_WATCH_MANAGER.startService();
        VlAzureLibMod.init();
        Registry.register(BuiltInRegistries.BLOCK, AzureLib.modResource("lightblock"), VlAzureLibModFabric.TICKING_LIGHT_BLOCK);
        VlAzureLibModFabric.TICKING_LIGHT_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, AzureLib.MOD_ID + ":lightblock", FabricBlockEntityTypeBuilder.create(TickingLightEntity::new, VlAzureLibModFabric.TICKING_LIGHT_BLOCK).build(null));
        AzureLibMod.config = AzureLibMod.registerConfig(AzureLibConfig.class, ConfigFormats.json()).getConfigInstance();
        ServerLifecycleEvents.SERVER_STOPPING.register((server) -> {
            ConfigIO.FILE_WATCH_MANAGER.stopService();
        });
        Registry.register(BuiltInRegistries.ENCHANTMENT, AzureLib.modResource("incendiaryenchantment"), INCENDIARYENCHANTMENT);

        VLModFabricLike.init(VlAzureLibMod.MOD_ID, ()->{
            VlAzureLibModFabricLike.init();
        });
    }
}
