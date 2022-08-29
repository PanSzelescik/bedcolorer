package pl.panszelescik.colorize.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ColorizeForge.MODID)
public class ColorizeForge {

    public static final String MODID = "colorize";
    private final ColorizeForgeHandler handler = new ColorizeForgeHandler(ColorizeForgeConfig.CONFIG);

    public ColorizeForge() {
        MinecraftForge.EVENT_BUS.addListener(this::onRightClickBlock);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ColorizeForgeConfig.SPEC);
    }

    private void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (this.handler.handle(event.getPlayer(), event.getWorld(), event.getHand(), event.getPos())) {
            event.setCanceled(true);
        }
    }
}
