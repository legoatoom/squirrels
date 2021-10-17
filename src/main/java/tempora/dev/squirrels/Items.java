package tempora.dev.squirrels;

import net.fabricmc.fabric.api.loot.v1.FabricLootPool;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.loot.entry.LootPoolEntryTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static tempora.dev.squirrels.Squirrels.ModID;

public class Items {
    public static final Item ACORN = new Item(new Item.Settings().group(Squirrels.DEFAULT));
    public static final Item DARK_ACORN = new Item(new Item.Settings().group(Squirrels.DEFAULT));
    public static final Item BIRCH_CATKIN = new Item(new Item.Settings().group(Squirrels.DEFAULT));
    public static final Item JUNGLE_SEED = new Item(new Item.Settings().group(Squirrels.DEFAULT));
    public static final Item SPRUCE_CONE = new Item(new Item.Settings().group(Squirrels.DEFAULT));
    public static final Item ACACIA_POD = new Item(new Item.Settings().group(Squirrels.DEFAULT));
    public static final SpawnEggItem SQUIRREL_SPAWN_EGG = new SpawnEggItem(Squirrels.SQUIRREL_ENTITY_TYPE, 9729324, 13032330, new Item.Settings().group(Squirrels.DEFAULT));


    public static void init() {
        Registry.register(Registry.ITEM, new Identifier(ModID, "squirrel_spawn_egg"), SQUIRREL_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(ModID, "acorn"), ACORN);
        Registry.register(Registry.ITEM, new Identifier(ModID, "dark_acorn"), DARK_ACORN);
        Registry.register(Registry.ITEM, new Identifier(ModID, "birch_catkin"), BIRCH_CATKIN);
        Registry.register(Registry.ITEM, new Identifier(ModID, "jungle_seed"), JUNGLE_SEED);
        Registry.register(Registry.ITEM, new Identifier(ModID, "spruce_cone"), SPRUCE_CONE);
        Registry.register(Registry.ITEM, new Identifier(ModID, "acacia_pod"), ACACIA_POD);
    }
}
