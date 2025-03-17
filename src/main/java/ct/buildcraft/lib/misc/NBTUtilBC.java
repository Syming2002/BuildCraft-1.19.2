/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package ct.buildcraft.lib.misc;

import java.util.BitSet;
import java.util.EnumSet;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ct.buildcraft.api.core.BCLog;
import com.google.common.collect.Sets;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public final class NBTUtilBC {
    public static final CompoundTag NBT_NULL = new CompoundTag();

    public static <N extends Tag> Optional<N> toOptional(N value) {
        return value == NBTUtilBC.NBT_NULL ? Optional.empty() : Optional.of(value);
    }

    public static Tag merge(Tag destination, Tag source) {
        if (source == null) {
            return null;
        }
        if (destination == null) {
            return source;
        }
        if (destination.getId() == Tag.TAG_COMPOUND && source.getId() == Tag.TAG_COMPOUND) {
            CompoundTag result = new CompoundTag();
            for (String key : Sets.union(
                ((CompoundTag) destination).getAllKeys(),
                ((CompoundTag) source).getAllKeys()
            )) {
                if (!((CompoundTag) source).contains(key)) {
                    result.put(key, ((CompoundTag) destination).get(key));
                } else if (((CompoundTag) source).get(key) != NBT_NULL) {
                    if (!((CompoundTag) destination).contains(key)) {
                        result.put(key, ((CompoundTag) source).get(key));
                    } else {
                        result.put(
                            key,
                            merge(
                                ((CompoundTag) destination).get(key),
                                ((CompoundTag) source).get(key)
                            )
                        );
                    }
                }
            }
            return result;
        }
        return source;
    }

    public static CompoundTag getItemData(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) {
            return new CompoundTag();
        }
        return stack.getOrCreateTag();
    }

    @Deprecated/**use {@link BlockPos#asLong()} instead*/
    public static int[] writeBlockPos(BlockPos pos) {
        if (pos == null) {
            throw new NullPointerException("Cannot return a null NBTTag -- pos was null!");
        }
        return new int[] { pos.getX(), pos.getY(), pos.getZ() };
    }

    @Deprecated/**use {@link BlockPos#asLong()} instead*/
    public static CompoundTag writeBlockPosAsCompound(BlockPos pos) {
        if (pos == null) {
            throw new NullPointerException("Cannot return a null NBTTag -- pos was null!");
        }
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("x", pos.getX());
        nbt.putInt("y", pos.getY());
        nbt.putInt("z", pos.getZ());
        return nbt;
    }
    
    /**
     * use {@link #readBlockPos(int[])} for performance
     */
    @Deprecated
    @Nullable
    public static BlockPos readBlockPos(Tag base) {
        if (base == null) {
            return null;
        }
        switch (base.getId()) {
            case Tag.TAG_INT_ARRAY: {
                readBlockPos(((IntArrayTag)base).getAsIntArray());
            }
            case Tag.TAG_COMPOUND: {
                CompoundTag nbt = (CompoundTag) base;
                BlockPos pos = null;
                if (nbt.contains("i")) {
                    int i = nbt.getInt("i");
                    int j = nbt.getInt("j");
                    int k = nbt.getInt("k");
                    pos = new BlockPos(i, j, k);
                } else if (nbt.contains("x")) {
                    int x = nbt.getInt("x");
                    int y = nbt.getInt("y");
                    int z = nbt.getInt("z");
                    pos = new BlockPos(x, y, z);
                } else if (nbt.contains("pos")) {
                    return readBlockPos(nbt.get("pos"));
                } else {
                    BCLog.logger.warn("Attempted to read a block positions from a compound tag without the correct sub-tags! (" + base + ")", new Throwable());
                }
                return pos;
            }
        }
        BCLog.logger.warn("Attempted to read a block position from an invalid tag! (" + base + ")", new Throwable());
        return null;
    }
    @Deprecated/**use {@link BlockPos#of()} instead*/
    public static BlockPos readBlockPos(int[] array) {
        if (array.length == 3){
            return new BlockPos(array[0], array[1], array[2]);
        }
        return null;
    }

    public static ListTag writeVec3(Vec3 vec3) {
        ListTag list = new ListTag();
        list.addTag(0, DoubleTag.valueOf(vec3.x));
        list.addTag(1, DoubleTag.valueOf(vec3.y));
        list.addTag(2, DoubleTag.valueOf(vec3.z));
        return list;
    }

    @Nullable
    public static Vec3 readVec3(Tag nbt) {
        if (nbt instanceof ListTag) {
            return readVec3((ListTag) nbt);
        }
        return null;
    }

    public static Vec3 readVec3(ListTag list) {
        return new Vec3(list.getDouble(0), list.getDouble(1), list.getDouble(2));
    }

    private static final String NULL_ENUM_STRING = "_NULL";

    public static <E extends Enum<E>> Tag writeEnum(E value) {
        if (value == null) {
            return StringTag.valueOf(NULL_ENUM_STRING);
        }
        return StringTag.valueOf(value.name());
    }

    public static <E extends Enum<E>> E readEnum(Tag nbt, Class<E> clazz) {
        if (nbt instanceof StringTag) {
            String value = ((StringTag) nbt).getAsString();
            if (NULL_ENUM_STRING.equals(value)) {
                return null;
            }
            try {
                return Enum.valueOf(clazz, value);
            } catch (Throwable t) {
                // In case we didn't find the constant
                BCLog.logger.warn("Tried and failed to read the value(" + value + ") from " + clazz.getSimpleName(), t);
                return null;
            }
        } else if (nbt instanceof ByteTag) {
            byte value = ((ByteTag) nbt).getAsByte();
            if (value < 0 || value >= clazz.getEnumConstants().length) {
                return null;
            } else {
                return clazz.getEnumConstants()[value];
            }
        } else if (nbt == null) {
            return null;
        } else {
            BCLog.logger.warn(new IllegalArgumentException("Tried to read an enum value when it was not a string! This is probably not good!").getLocalizedMessage());
            return null;
        }
    }

    public static Tag writeDoubleArray(double[] data) {
        ListTag list = new ListTag();
        int counter = 0;
        for (double d : data) {
            list.addTag(counter++, DoubleTag.valueOf(d));
        }
        return list;
    }

    public static double[] readDoubleArray(Tag tag, int intendedLength) {
        double[] arr = new double[intendedLength];
        if (tag instanceof ListTag) {
            ListTag list = (ListTag) tag;
            for (int i = 0; i < list.size() && i < intendedLength; i++) {
                arr[i] = list.getDouble(i);
            }
        }
        return arr;
    }

    /** Writes an {@link EnumSet} to an {@link Tag}. The returned type will either be {@link ByteTag} or
     * {@link ByteArrayTag}.
     * 
     * @param clazz The class that the {@link EnumSet} is of. This is required as we have no way of getting the class
     *            from the set. */
    public static <E extends Enum<E>> Tag writeEnumSet(EnumSet<E> set, Class<E> clazz) {
        E[] constants = clazz.getEnumConstants();
        if (constants == null) throw new IllegalArgumentException("Not an enum type " + clazz);
        BitSet bitset = new BitSet();
        for (E e : constants) {
            if (set.contains(e)) {
                bitset.set(e.ordinal());
            }
        }
        byte[] bytes = bitset.toByteArray();
        if (bytes.length == 1) {
            return ByteTag.valueOf(bytes[0]);
        } else {
            return new ByteArrayTag(bytes);
        }
    }

    public static <E extends Enum<E>> EnumSet<E> readEnumSet(Tag tag, Class<E> clazz) {
        E[] constants = clazz.getEnumConstants();
        if (constants == null) throw new IllegalArgumentException("Not an enum type " + clazz);
        byte[] bytes;
        if (tag instanceof ByteTag) {
            bytes = new byte[] { ((ByteTag) tag).getAsByte() };
        } else if (tag instanceof ByteArrayTag) {
            bytes = ((ByteArrayTag) tag).getAsByteArray();
        } else {
            bytes = new byte[] {};
            BCLog.logger.warn("[lib.nbt] Tried to read an enum set from " + tag);
        }
        BitSet bitset = BitSet.valueOf(bytes);
        EnumSet<E> set = EnumSet.noneOf(clazz);
        for (E e : constants) {
            if (bitset.get(e.ordinal())) {
                set.add(e);
            }
        }
        return set;
    }

 /*   public static ListTag writeCompoundList(Stream<CompoundTag> stream) {
        ListTag list = new ListTag();
        int counter = 0;
        for(CompoundTag tag : (CompoundTag[])stream.toArray()) 
        	list.addTag(counter++, tag);
        return list;
    }*/
	public static <T extends Tag> ListTag writeObjectList(Stream<T> stream) {
        ListTag list = new ListTag();
        int counter = 0;
        for(T tag : stream.toList()) 
        	list.addTag(counter++, tag);
        return list;
    }

    public static Stream<CompoundTag> readCompoundList(Tag list) {
        if (list == null) {
            return Stream.empty();
        }
        if (!(list instanceof ListTag)) {
            throw new IllegalArgumentException();
        }
        return IntStream.range(0, ((ListTag) list).size()).mapToObj(((ListTag) list)::getCompound);
    }

    public static ListTag writeStringList(Stream<String> stream) {
    	ListTag list = new ListTag();
    	int counter = 0;
    	for(StringTag tag : (StringTag[])stream.map(StringTag::valueOf).toArray())
    		list.addTag(counter++, tag);
    	return list;
    }


    public static Stream<String> readStringList(Tag list) {
        if (list == null) {
            return Stream.empty();
        }
        if (!(list instanceof ListTag)) {
            throw new IllegalArgumentException();
        }
        return IntStream.range(0, ((ListTag) list).size()).mapToObj(((ListTag) list)::getString);
    }
}
