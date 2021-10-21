package tempora.dev.squirrels.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.DefaultBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tempora.dev.squirrels.Squirrels;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(method = "addMonsters", at = @At("TAIL"))
    private static void addMonsters(SpawnSettings.Builder builder, int zombieWeight, int zombieVillagerWeight, int skeletonWeight, CallbackInfo ci) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(Squirrels.SQUIRREL_ENTITY_TYPE, 8, 6, 6));
    }
}
