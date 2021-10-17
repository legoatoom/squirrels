package tempora.dev.squirrels.models;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GeoSquirrelRenderer extends GeoEntityRenderer<GeoSquirrelEntity> {
    public GeoSquirrelRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SquirrelModel());
    }
}
