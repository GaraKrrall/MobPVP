package mc.garakrral.event.totem.machine;

import me.shedaniel.autoconfig.AutoConfig;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import mc.garakrral.block.BlockType;
import mc.garakrral.data.OvenData;
import mc.garakrral.event.totem.machine.recipe.IndustrialOvenRecipes;
import mc.garakrral.scheduler.ServerScheduler;
import mc.garakrral.event.totem.machine.recipe.IndustrialOvenRecipes.RecipeData;


public class INDUSTRIAL_OVEN {
    private static final OvenData ovenData = AutoConfig.getConfigHolder(OvenData.class).getConfig();

    public static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.PASS;

        BlockPos clickedPos = hit.getBlockPos();
        BlockState state = world.getBlockState(clickedPos);

        // Only works for the industrial oven block
        if (!state.isOf(BlockType.INDUSTRIAL_OVEN)) {
            return ActionResult.PASS;
        }

        BlockPos ovenKey = clickedPos.toImmutable();

        if (!ovenData.activate(ovenKey.toShortString(), recipeFileNameOrId, recipe.time)) {
            player.sendMessage(Text.literal("This oven is already running."), true);
            return ActionResult.SUCCESS;
        }
        AutoConfig.getConfigHolder(OvenData.class).save();


        ServerWorld serverWorld = (ServerWorld) world;

        // Chest should be behind the player's facing direction
        BlockPos chestPos = clickedPos.offset(player.getHorizontalFacing().getOpposite());
        BlockState chestState = world.getBlockState(chestPos);

        if (!chestState.isOf(Blocks.CHEST)) {
            ovenData.deactivate(ovenKey);
            AutoConfig.getConfigHolder(OvenData.class).save();
            return ActionResult.PASS;
        }

        if (!isStructureValid(serverWorld, clickedPos, chestPos)) {
            ovenData.deactivate(ovenKey);
            AutoConfig.getConfigHolder(OvenData.class).save();
            return ActionResult.PASS;
        }

        ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(chestPos);
        if (chest == null) {
            ovenData.deactivate(ovenKey);
            AutoConfig.getConfigHolder(OvenData.class).save();
            return ActionResult.PASS;
        }

        // Load recipes from resources
        IndustrialOvenRecipes.loadRecipes();

        // Match a recipe from the chest
        var recipe = IndustrialOvenRecipes.matchRecipe(chest);
        if (recipe == null) {
            spawnParticles(serverWorld, chestPos, false, 40, 3);
            world.playSound(null, clickedPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
            player.sendMessage(Text.literal("No valid recipe found."), true);
            ovenData.deactivate(ovenKey);
            AutoConfig.getConfigHolder(OvenData.class).save();
            return ActionResult.SUCCESS;
        }

        // Start the process
        world.playSound(null, clickedPos, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1f, 1f);
        int duration = recipe.time;

// Particle effect during process
        for (int i = 0; i < duration; i += 5) {
            int delay = i;
            ServerScheduler.schedule(delay, () -> {
                spawnParticles(serverWorld, clickedPos, true, 20, 5);
            });
        }

        try {
            // Process completion
            ServerScheduler.schedule(duration, () -> {
                boolean hasSpace = false;
                for (int i = 0; i < chest.size(); i++) {
                    if (chest.getStack(i).isEmpty()) {
                        hasSpace = true;
                        break;
                    }
                }
                if (!hasSpace) {
                    spawnParticles(serverWorld, chestPos, false, 40, 3);
                    world.playSound(null, clickedPos, SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), SoundCategory.BLOCKS, 1f, 0.8f);
                    player.sendMessage(Text.literal("The oven did not run because the chest is full!"), true);
                    return;
                }

                // Consume input items
                for (var input : recipe.inputs) {
                    Item item = net.minecraft.registry.Registries.ITEM.get(Identifier.of(input.item));
                    int remaining = input.count;

                    for (int i = 0; i < chest.size(); i++) {
                        ItemStack stack = chest.getStack(i);
                        if (stack.isOf(item)) {
                            int remove = Math.min(remaining, stack.getCount());
                            stack.decrement(remove);
                            remaining -= remove;
                            if (remaining <= 0) break;
                        }
                    }
                }

                // Add output
                ItemStack result = IndustrialOvenRecipes.getOutput(recipe);
                boolean added = false;

                // Try to merge with existing stack
                for (int i = 0; i < chest.size(); i++) {
                    ItemStack stack = chest.getStack(i);
                    if (canCombine(stack, result)) {
                        int transferable = Math.min(stack.getMaxCount() - stack.getCount(), result.getCount());
                        if (transferable > 0) {
                            stack.increment(transferable);
                            result.decrement(transferable);
                            if (result.isEmpty()) {
                                added = true;
                                break;
                            }
                        }
                    }
                }

                // Put in empty slot if available
                if (!added && !result.isEmpty()) {
                    for (int i = 0; i < chest.size(); i++) {
                        if (chest.getStack(i).isEmpty()) {
                            chest.setStack(i, result.copy());
                            result.decrement(result.getCount());
                            added = true;
                            break;
                        }
                    }
                }

                // Drop on ground if still remaining
                if (!added && !result.isEmpty()) {
                    ItemEntity drop = new ItemEntity(world,
                            chestPos.getX() + 0.5,
                            chestPos.getY() + 1.0,
                            chestPos.getZ() + 0.5,
                            result);
                    world.spawnEntity(drop);
                    player.sendMessage(Text.literal("The chest was full, product dropped on the ground."), true);
                }

                chest.markDirty();
                spawnParticles(serverWorld, clickedPos, false, 50, 6);
                world.playSound(null, clickedPos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1f, 1.2f);
                ovenData.deactivate(ovenKey);
                AutoConfig.getConfigHolder(OvenData.class).save();
            });
        } catch (Exception e) {
            ovenData.deactivate(ovenKey);
            AutoConfig.getConfigHolder(OvenData.class).save();
            e.printStackTrace();
        }

        return ActionResult.SUCCESS;
    }

    private static boolean isStructureValid(ServerWorld world, BlockPos headPos, BlockPos chestPos) {
        for (Direction dir : Direction.values()) {
            BlockPos checkPos = headPos.offset(dir);
            if (checkPos.equals(chestPos)) continue;

            BlockState checkState = world.getBlockState(checkPos);
            if (!checkState.isOf(BlockType.INDUSTRIAL_OVEN_BLOCK)) {
                return false;
            }
        }
        return true;
    }

    private static boolean canCombine(ItemStack a, ItemStack b) {
        return !a.isEmpty() && !b.isEmpty() && a.isOf(b.getItem()) && ItemStack.areEqual(a, b);
    }

    private static void spawnParticles(ServerWorld world, BlockPos pos, boolean fire, int count, double spread) {
        var type = fire ? ParticleTypes.FLAME : ParticleTypes.SMOKE;
        for (int i = 0; i < count; i++) {
            double x = pos.getX() + 0.5 + (Math.random() - 0.5) * spread;
            double y = pos.getY() + 0.5 + (Math.random() - 0.5) * spread;
            double z = pos.getZ() + 0.5 + (Math.random() - 0.5) * spread;
            world.spawnParticles(type, x, y, z, 40, 0.1, 0.1, 0.1, 0.02);
        }
    }
}
