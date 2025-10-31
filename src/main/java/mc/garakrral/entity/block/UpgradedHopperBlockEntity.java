package mc.garakrral.entity.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static mc.garakrral.entity.EntityType.UPGREADED_HOPPER;

public class UpgradedHopperBlockEntity extends BlockEntity implements Inventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private int cooldown = 0;

    // Normal huni 8 tickte bir aktarım yapar, bunu aynı yapıyoruz
    private static final int TRANSFER_COOLDOWN = 8;

    public UpgradedHopperBlockEntity(BlockPos pos, BlockState state) {
        super(UPGREADED_HOPPER, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, UpgradedHopperBlockEntity hopper) {
        if (world.isClient) return;

        if (hopper.cooldown > 0) {
            hopper.cooldown--;
            return;
        }

        // Üstteki envanteri al
        BlockPos above = pos.up();
        Inventory aboveInv = net.minecraft.block.entity.HopperBlockEntity.getInventoryAt(world, above);

        // Eğer üstte envanter varsa oradan eşya al
        if (aboveInv != null) {
            for (int i = 0; i < aboveInv.size(); i++) {
                ItemStack stack = aboveInv.getStack(i);
                if (!stack.isEmpty()) {
                    // 1 eşya kopyala
                    ItemStack single = stack.copy();
                    single.setCount(1);

                    // Eşyayı tam ortadan aşağı düşür (sağa sola saçılmadan)
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() - 0.5; // biraz daha aşağıya
                    double z = pos.getZ() + 0.5;

                    ItemEntity drop = new ItemEntity(world, x, y, z, single);
                    drop.setVelocity(0, -0.1, 0); // sadece aşağıya yavaşça düşsün
                    drop.setNoGravity(false); // normal yer çekimiyle düşsün
                    drop.setPickupDelay(10); // hemen alınmasın
                    drop.setYaw(0);
                    drop.setPitch(0);

                    // Dümdüz, yönsüz şekilde doğsun
                    world.spawnEntity(drop);

                    // Üst envanterden 1 eşya eksilt
                    stack.decrement(1);
                    aboveInv.markDirty();

                    // Cooldown başlat (normal huni gibi)
                    hopper.cooldown = TRANSFER_COOLDOWN;
                    break;
                }
            }
        }
    }

    // INVENTORY
    @Override public int size() { return inventory.size(); }
    @Override public boolean isEmpty() { for (ItemStack s : inventory) if (!s.isEmpty()) return false; return true; }
    @Override public ItemStack getStack(int slot) { return inventory.get(slot); }
    @Override public ItemStack removeStack(int slot, int amount) { ItemStack r = Inventories.splitStack(inventory, slot, amount); if (!r.isEmpty()) markDirty(); return r; }
    @Override public ItemStack removeStack(int slot) { ItemStack r = Inventories.removeStack(inventory, slot); if (!r.isEmpty()) markDirty(); return r; }
    @Override public void setStack(int slot, ItemStack stack) { inventory.set(slot, stack); if (stack.getCount() > getMaxCountPerStack()) stack.setCount(getMaxCountPerStack()); markDirty(); }
    @Override public boolean canPlayerUse(net.minecraft.entity.player.PlayerEntity player) { return true; }
    @Override public void clear() { inventory.clear(); }
}
