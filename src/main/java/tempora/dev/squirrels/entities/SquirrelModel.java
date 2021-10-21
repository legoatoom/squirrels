package tempora.dev.squirrels.entities;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tempora.dev.squirrels.Squirrels;

public class SquirrelModel extends AnimatedGeoModel<SquirrelEntity>
{
    @Override
    public Identifier getModelLocation(SquirrelEntity object)
    {
        return new Identifier(Squirrels.ModID, "geo/squirrel.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SquirrelEntity object)
    {
        switch (object.getColor()) {
            case GREY -> {
                return new Identifier(Squirrels.ModID, "textures/entity/grey_squirrel.png");
            }
            case BLACK -> {
                return new Identifier(Squirrels.ModID, "textures/entity/black_squirrel.png");
            }
            case WHITE -> {
                return new Identifier(Squirrels.ModID, "textures/entity/white_squirrel.png");
            }
            case GOLDEN -> {
                return new Identifier(Squirrels.ModID, "textures/entity/golden_squirrel.png");
            }
            default -> {
                return new Identifier(Squirrels.ModID, "textures/entity/brown_squirrel.png");
            }
        }
    }

    @Override
    public Identifier getAnimationFileLocation(SquirrelEntity object)
    {
        return new Identifier(Squirrels.ModID, "animations/squirrel.animation.json");
    }
}
