package ct.buildcraft.lib.item;

import java.util.EnumMap;

import javax.annotation.Nullable;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemByEnum<E extends Enum<E> & StringRepresentable> extends Item{

	protected final E type;
	
	public ItemByEnum(Properties p_40566_, E type, @Nullable EnumMap<E, ItemByEnum<E>> map) {
		super(p_40566_);
		this.type = type;
		if(map != null&&type!=null)
			map.put(type, this);
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if(this.allowedIn(tab)) {
			list.add(new ItemStack(this));}
	}

	@Override
	public Component getName(ItemStack p_41458_) {
		return Component.translatable(getDescriptionId()/*+"_" + type.getSerializedName()*/);
	}
	
	public E getType() {
		return type;
	}
	
}