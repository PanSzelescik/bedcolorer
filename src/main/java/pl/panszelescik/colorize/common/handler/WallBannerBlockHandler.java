package pl.panszelescik.colorize.common.handler;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import pl.panszelescik.colorize.common.api.BaseBlockHandler;

public class WallBannerBlockHandler extends BaseBlockHandler<WallBannerBlock> {

    @Override
    protected @Nullable WallBannerBlock getOldBlock(BlockState state) {
        var block = state.getBlock();
        if (block instanceof WallBannerBlock bannerBlock && WALL_BANNERS.containsValue(bannerBlock)) {
            return bannerBlock;
        }
        return null;
    }

    @Override
    protected @Nullable DyeColor getOldColor(BlockState state, WallBannerBlock block) {
        return block.getColor();
    }

    @Override
    protected WallBannerBlock getNewBlock(DyeColor color) {
        return WALL_BANNERS.get(color);
    }

    @Override
    protected boolean replace(Level level, BlockPos pos, BlockState state, ItemStack stack, DyeColor newColor) {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BannerBlockEntity bannerBlockEntity) {
            var tag = bannerBlockEntity.getUpdateTag();

            level.removeBlock(pos, false);

            var newBlock = this.getNewBlock(newColor);
            level.setBlock(pos, newBlock.withPropertiesOf(state), 0);
            var newEntity = level.getBlockEntity(pos); //TODO save

            return true;
        }

        return false;
    }

    private static final Object2ObjectOpenHashMap<DyeColor, WallBannerBlock> WALL_BANNERS = new Object2ObjectOpenHashMap<>(16) {{
        put(DyeColor.WHITE, (WallBannerBlock) Blocks.WHITE_WALL_BANNER);
        put(DyeColor.ORANGE, (WallBannerBlock) Blocks.ORANGE_WALL_BANNER);
        put(DyeColor.MAGENTA, (WallBannerBlock) Blocks.MAGENTA_WALL_BANNER);
        put(DyeColor.LIGHT_BLUE, (WallBannerBlock) Blocks.LIGHT_BLUE_WALL_BANNER);
        put(DyeColor.YELLOW, (WallBannerBlock) Blocks.YELLOW_WALL_BANNER);
        put(DyeColor.LIME, (WallBannerBlock) Blocks.LIME_WALL_BANNER);
        put(DyeColor.PINK, (WallBannerBlock) Blocks.PINK_WALL_BANNER);
        put(DyeColor.GRAY, (WallBannerBlock) Blocks.GRAY_WALL_BANNER);
        put(DyeColor.LIGHT_GRAY, (WallBannerBlock) Blocks.LIGHT_GRAY_WALL_BANNER);
        put(DyeColor.CYAN, (WallBannerBlock) Blocks.CYAN_WALL_BANNER);
        put(DyeColor.PURPLE, (WallBannerBlock) Blocks.PURPLE_WALL_BANNER);
        put(DyeColor.BLUE, (WallBannerBlock) Blocks.BLUE_WALL_BANNER);
        put(DyeColor.BROWN, (WallBannerBlock) Blocks.BROWN_WALL_BANNER);
        put(DyeColor.GREEN, (WallBannerBlock) Blocks.GREEN_WALL_BANNER);
        put(DyeColor.RED, (WallBannerBlock) Blocks.RED_WALL_BANNER);
        put(DyeColor.BLACK, (WallBannerBlock) Blocks.BLACK_WALL_BANNER);
    }};
}
