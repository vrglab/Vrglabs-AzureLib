package org.Vrglab.AzureLib.Armor;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.api.client.renderer.GeoArmorRenderer;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.client.RenderProvider;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.Vrglab.Modloader.Types.ICallBack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AzureArmor extends ArmorItem implements GeoItem {
    protected final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    protected final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public AzureArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        List<AnimationController> controllerList = (List<AnimationController>)getControllers().accept(controllers);
        for (AnimationController controller: controllerList) {
            controllers.add(controller);
        }
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private Renderer renderer;

            @Override
            public @NotNull HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                if (renderer == null)
                    renderer = new Renderer<>(getModel().get());
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    /* ABSTRACT FUNCTIONS */
    public abstract ICallBack getControllers();
    public abstract Supplier<GeoModel<? extends AzureArmor>> getModel();


    /* SUB CLASSES */

    public class Renderer<T extends AzureArmor> extends GeoArmorRenderer<T> {
        public Renderer(GeoModel<T> model) {
            super(model);
        }
    }
}
