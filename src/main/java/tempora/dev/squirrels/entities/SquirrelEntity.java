package tempora.dev.squirrels.entities;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
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

import java.lang.reflect.Array;
import java.util.Random;

public class SquirrelEntity extends AnimalEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(SquirrelEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public SquirrelEntity(EntityType<? extends SquirrelEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

    public void setColor(SquirrelColor color) {
        this.dataTracker.set(COLOR, color.getId());
    }

    public SquirrelColor getColor() {
        return SquirrelColor.getFromId(this.dataTracker.get(COLOR));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (entityData instanceof SquirrelEntityData) {
            ((SquirrelEntityData) entityData).color = getRandom(SquirrelColor.values());
            super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        } else {
            entityData = new SquirrelEntityData(true);
            BlockPos location = this.getBlockPos();
            float temperature = world.toServerWorld().getBiome(location).getTemperature();
            double random = Math.random();
            if(random * 100 <= 5) {
                this.setColor(SquirrelColor.GOLDEN);
            } else if(random * 100 > 10 && random * 100 <= 20) {
                this.setColor(SquirrelColor.BLACK);
            } else if(temperature <= -0.15F) {
                this.setColor(SquirrelColor.WHITE);
            } else if(temperature > 0F && temperature <= 0.5F){
                this.setColor(SquirrelColor.GREY);
            } else if(temperature > 0.5F) {
                this.setColor(SquirrelColor.BROWN);
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static SquirrelColor getRandom(SquirrelColor[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
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

    protected enum SquirrelColor {
        WHITE(0, "white"),
        BROWN(1, "brown"),
        BLACK(2, "black"),
        GREY(3, "grey"),
        GOLDEN(4, "golden");

        private final int id;
        private final String name;

        SquirrelColor(int id, String name) {
            this.id = id;
            this.name = name;
        }

        static SquirrelColor getFromId(int id) {
            return SquirrelColor.values()[id];
        }

        int getId() {
            return this.id;
        }
    }

    protected static class SquirrelEntityData extends PassiveEntity.PassiveData {
        public SquirrelColor color;

        public SquirrelEntityData(boolean babyAllowed) {
            super(babyAllowed);
        }

        public SquirrelEntityData(float chance) {
            super(chance);
        }
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
