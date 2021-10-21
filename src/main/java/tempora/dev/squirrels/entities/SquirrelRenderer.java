package tempora.dev.squirrels.entities;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SquirrelRenderer extends GeoEntityRenderer<SquirrelEntity> {
    public SquirrelRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SquirrelModel());
    }
}
