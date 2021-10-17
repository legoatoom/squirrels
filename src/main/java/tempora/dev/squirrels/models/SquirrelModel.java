package tempora.dev.squirrels.models;

import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.Identifier;
import software.bernie.example.item.JackInTheBoxItem;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tempora.dev.squirrels.Squirrels;

public class SquirrelModel extends AnimatedGeoModel<GeoSquirrelEntity>
{
    @Override
    public Identifier getModelLocation(GeoSquirrelEntity object)
    {
        return new Identifier(Squirrels.ModID, "geo/squirrel.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GeoSquirrelEntity object)
    {
        return new Identifier(Squirrels.ModID, "textures/entity/squirrel.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GeoSquirrelEntity object)
    {
        return new Identifier(Squirrels.ModID, "animations/squirrel.animation.json");
    }
}
