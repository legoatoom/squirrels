package tempora.dev.squirrels.models;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import tempora.dev.squirrels.Items;
import tempora.dev.squirrels.Squirrels;

import java.util.Random;

public class GeoSquirrelEntity extends AnimalEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public GeoSquirrelEntity(EntityType<? extends GeoSquirrelEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }



    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.squirrel.movement", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.squirrel.dig", false)
                    .addAnimation("animation.squirrel.idle", false)
            );
        }
        return PlayState.CONTINUE;
    }



    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new TemptGoal(this, 0.5D, Ingredient.ofItems(Items.ACACIA_POD, Items.ACORN, Items.DARK_ACORN, Items.BIRCH_CATKIN, Items.JUNGLE_SEED, Items.SPRUCE_CONE), true));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.6D));
        this.goalSelector.add(5, new AnimalMateGoal(this, 0.8D));
        this.goalSelector.add(11, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RABBIT_DEATH;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return Squirrels.SQUIRREL_ENTITY_TYPE.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return Ingredient.ofItems(Items.ACACIA_POD, Items.ACORN, Items.DARK_ACORN, Items.BIRCH_CATKIN, Items.JUNGLE_SEED, Items.SPRUCE_CONE).test(stack);
    }

    @Override
    public boolean cannotBeSilenced() {
        return super.cannotBeSilenced();
    }
}
