package ct.buildcraft.factory;

import java.util.function.Consumer;

import ct.buildcraft.api.enums.EnumEngineType;
import ct.buildcraft.core.BCCoreItems;

import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class BCFactoryRecipesProvider extends RecipeProvider{

	public BCFactoryRecipesProvider(DataGenerator p_125973_) {
		super(p_125973_);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> writer) {                
    	ShapedRecipeBuilder builder6 = ShapedRecipeBuilder.shaped(BCFactoryItems.TANK_BLOCK_ITEM.get(), 1);
        builder6.pattern("ggg");
        builder6.pattern("g g");
        builder6.pattern("ggg");
        builder6.define('g', Items.GLASS);
        builder6.unlockedBy("has_"+Items.GLASS.getDescriptionId(), TriggerInstance.hasItems(Items.GLASS));
        builder6.save(writer);
        
    	ShapedRecipeBuilder builder7 = ShapedRecipeBuilder.shaped(BCFactoryItems.MINING_WELL_BLOCK_ITEM.get(), 1);
        builder7.pattern("iri");
        builder7.pattern("igi");
        builder7.pattern("iai");
        builder7.define('i', Items.IRON_INGOT);
        builder7.define('r', Items.REDSTONE);
        builder7.define('g', BCCoreItems.GEAR_IRON.get());
        builder7.define('a', Items.IRON_PICKAXE);
        builder7.unlockedBy("has_"+BCCoreItems.GEAR_IRON.getId().getPath(), TriggerInstance.hasItems(BCCoreItems.GEAR_IRON.get()));
        builder7.save(writer);
        
    	ShapedRecipeBuilder pump = ShapedRecipeBuilder.shaped(BCFactoryItems.MINING_WELL_BLOCK_ITEM.get(), 1);
        pump.pattern("iri");
        pump.pattern("igi");
        pump.pattern("tbt");
        pump.define('i', Items.IRON_INGOT);
        pump.define('r', Items.REDSTONE);
        pump.define('g', BCCoreItems.GEAR_IRON.get());
        pump.define('b', Items.BUCKET);
        pump.define('t', BCFactoryItems.TANK_BLOCK_ITEM.get());
        pump.unlockedBy("has_"+BCCoreItems.GEAR_IRON.getId().getPath(), TriggerInstance.hasItems(BCCoreItems.GEAR_IRON.get()));
        pump.save(writer);
        
    	ShapedRecipeBuilder flood_gate = ShapedRecipeBuilder.shaped(BCFactoryItems.MINING_WELL_BLOCK_ITEM.get(), 1);
        flood_gate.pattern("igi");
        flood_gate.pattern("ftf");
        flood_gate.pattern("ifi");
        flood_gate.define('i', Items.IRON_INGOT);
        flood_gate.define('f', Items.IRON_BARS);
        flood_gate.define('g', BCCoreItems.GEAR_IRON.get());
        flood_gate.define('t', BCFactoryItems.TANK_BLOCK_ITEM.get());
        flood_gate.unlockedBy("has_"+BCCoreItems.GEAR_IRON.getId().getPath(), TriggerInstance.hasItems(BCCoreItems.GEAR_IRON.get()));
        flood_gate.save(writer);
        
        
        super.buildCraftingRecipes(writer);
	}
	

}
