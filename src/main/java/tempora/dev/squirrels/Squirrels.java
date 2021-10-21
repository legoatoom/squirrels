package tempora.dev.squirrels;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;
import tempora.dev.squirrels.entities.SquirrelEntity;
import tempora.dev.squirrels.entities.SquirrelRenderer;

public class Squirrels implements ClientModInitializer, ModInitializer {
    public static final String ModID = "squirrels";

    public static final EntityType<SquirrelEntity> SQUIRREL_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "squirrel"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SquirrelEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    public static final ItemGroup DEFAULT = FabricItemGroupBuilder.create(new Identifier(ModID, "default")).icon(() -> new ItemStack(Items.ACORN)).build();

    @Override
    public void onInitializeClient() {
        GeckoLib.initialize();
        EntityRendererRegistry.INSTANCE.register(SQUIRREL_ENTITY_TYPE, SquirrelRenderer::new);
    }

    @Override
    public void onInitialize() {
        Items.init();
        FabricDefaultAttributeRegistry.register(SQUIRREL_ENTITY_TYPE, SquirrelEntity.createMobAttributes());
    }
}
