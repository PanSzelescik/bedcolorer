package pl.panszelescik.colorize.common.handler;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import org.jetbrains.annotations.Nullable;
import pl.panszelescik.colorize.common.api.BaseBlockHandler;
import pl.panszelescik.colorize.common.api.ColorizeConfig;
import pl.panszelescik.colorize.common.api.Colors;

public class BedBlockHandler extends BaseBlockHandler<BedBlock> {

    public BedBlockHandler(ColorizeConfig config) {
        super(config);
    }

    @Override
    protected @Nullable BedBlock getOldBlock(BlockState state) {
        var block = state.getBlock();
        if (block instanceof BedBlock bedBlock && BEDS.containsValue(bedBlock)) {
            return bedBlock;
        }
        return null;
    }

    @Override
    public @Nullable Colors getOldColor(BlockState state, BedBlock block) {
        return Colors.getByDyeColor(block.getColor());
    }

    @Override
    public BedBlock getNewBlock(Colors color) {
        return BEDS.get(color);
    }

    @Override
    protected boolean isEnabled() {
        return this.config.bedHandler();
    }

    @Override
    protected boolean requireSneaking() {
        return this.config.bedSneaking();
    }

    @Override
    public boolean replace(Level level, BlockPos pos, BlockState state, ItemStack stack, Colors newColor) {
        var part = state.getValue(BedBlock.PART);
        var direction = state.getValue(BedBlock.FACING);
        var pos2 = pos.relative(part == BedPart.FOOT ? direction : direction.getOpposite());

        var footPos = part == BedPart.FOOT ? pos : pos2;
        var headPos = part == BedPart.FOOT ? pos2 : pos;

        var footBlockState = level.getBlockState(footPos);
        var headBlockState = level.getBlockState(headPos);

        level.removeBlock(headPos, false);
        level.removeBlock(footPos, false);

        var newBlock = this.getNewBlock(newColor);
        level.setBlock(footPos, newBlock.withPropertiesOf(footBlockState), 0);
        level.setBlock(headPos, newBlock.withPropertiesOf(headBlockState), 0);

        return true;
    }

    private static final Object2ObjectOpenHashMap<Colors, BedBlock> BEDS = new Object2ObjectOpenHashMap<>(16);

    static {
        BEDS.put(Colors.WHITE, (BedBlock) Blocks.WHITE_BED);
        BEDS.put(Colors.ORANGE, (BedBlock) Blocks.ORANGE_BED);
        BEDS.put(Colors.MAGENTA, (BedBlock) Blocks.MAGENTA_BED);
        BEDS.put(Colors.LIGHT_BLUE, (BedBlock) Blocks.LIGHT_BLUE_BED);
        BEDS.put(Colors.YELLOW, (BedBlock) Blocks.YELLOW_BED);
        BEDS.put(Colors.LIME, (BedBlock) Blocks.LIME_BED);
        BEDS.put(Colors.PINK, (BedBlock) Blocks.PINK_BED);
        BEDS.put(Colors.GRAY, (BedBlock) Blocks.GRAY_BED);
        BEDS.put(Colors.LIGHT_GRAY, (BedBlock) Blocks.LIGHT_GRAY_BED);
        BEDS.put(Colors.CYAN, (BedBlock) Blocks.CYAN_BED);
        BEDS.put(Colors.PURPLE, (BedBlock) Blocks.PURPLE_BED);
        BEDS.put(Colors.BLUE, (BedBlock) Blocks.BLUE_BED);
        BEDS.put(Colors.BROWN, (BedBlock) Blocks.BROWN_BED);
        BEDS.put(Colors.GREEN, (BedBlock) Blocks.GREEN_BED);
        BEDS.put(Colors.RED, (BedBlock) Blocks.RED_BED);
        BEDS.put(Colors.BLACK, (BedBlock) Blocks.BLACK_BED);
    }
}
