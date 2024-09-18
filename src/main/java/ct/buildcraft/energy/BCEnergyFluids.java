package ct.buildcraft.energy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import ct.buildcraft.lib.fluid.BCFluid;
import com.mojang.blaze3d.shaders.FogShape;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class BCEnergyFluids {
    public static BCFluid[] crudeOil = new BCFluid[3];
    /** All 3 fuels (no residue) */
    public static BCFluid[] oilDistilled = new BCFluid[3];
    /** The 3 heaviest components (fuelLight, fuelDense and oilResidue) */
    public static BCFluid[] oilHeavy = new BCFluid[3];
    /** The 2 lightest fuels (no dense fuel) */
//    public static BCFluid[] fuelMixedLight = new BCFluid[3];
    /** The 2 heaviest fuels (no gaseous fuel) */
//    public static BCFluid[] fuelMixedHeavy = new BCFluid[3];
    /** The 2 heaviest products (fuelDense and oilResidue) */
    public static BCFluid[] oilDense = new BCFluid[3];

    // End products in order from least to most dense
//    public static BCFluid[] fuelGaseous = new BCFluid[3];
//    public static BCFluid[] fuelLight = new BCFluid[3];
//    public static BCFluid[] fuelDense = new BCFluid[3];
    public static BCFluid[] oilResidue = new BCFluid[3];

    public static BCFluid tar;

    public static final List<BCFluid> allFluids = new ArrayList<>();

	private static int i =0;
    private static int[][] data = { //@formatter:off
            // Tabular form of all the fluid values
            // density, viscosity, boil, spread,  tex_light,   tex_dark, sticky, flammable
            {      900,      2000,    3,      6, 0xFF505050, 0x05_05_05,      1,         1 },// Crude Oil
            {     1200,      4000,    3,      4, 0x10_0F_10, 0x42_10_42,      1,         0 },// Residue
            {      850,      1800,    3,      6, 0xA0_8F_1F, 0x42_35_20,      1,         1 },// Heavy Oil
            {      950,      1600,    3,      5, 0x87_6E_77, 0x42_24_24,      1,         1 },// Dense Oil
            {      750,      1400,    2,      8, 0xE4_AF_78, 0xB4_7F_00,      0,         1 },// Distilled Oil
            {      600,       800,    2,      7, 0xFF_AF_3F, 0xE0_7F_00,      0,         1 },// Dense Fuel
            {      700,      1000,    2,      7, 0xF2_A7_00, 0xC4_87_00,      0,         1 },// Mixed Heavy Fuels
            {      400,       600,    1,      8, 0xFF_FF_30, 0xE4_CF_00,      0,         1 },// Light Fuel
            {      650,       900,    1,      9, 0xF6_D7_00, 0xC4_B7_00,      0,         1 },// Mixed Light Fuels
            {      300,       500,    0,     10, 0xFA_F6_30, 0xE0_D9_00,      0,         1 },// Gas Fuel
        };//@formatter:on
    private static final String[] NAME = {"oil","oil_residue","oil_heavy","oil_dense","oil_distilled",
    									  "fuel_dense","fuel_mixed_heavy","fuel_light","fuel_mixed_light","fuel_gaseous"};
    public static final ResourceLocation OilCrudeCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/crude_oil/oil_crude_cool_still");
    public static final ResourceLocation OilCrudeCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/crude_oil/oil_crude_cool_flow");
    public static final ResourceLocation OilCrudeHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/crude_oil/oil_crude_hot_still");
    public static final ResourceLocation OilCrudeHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/crude_oil/oil_crude_hot_flow");
    public static final ResourceLocation OilCrudeSearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/crude_oil/oil_crude_searing_still");
    public static final ResourceLocation OilCrudeSearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/crude_oil/oil_crude_searing_flow");
    
    
    
    public static final ResourceLocation OilResidueCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/residue_oil/oil_residue_cool_still");
    public static final ResourceLocation OilResidueCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/residue_oil/oil_residue_cool_flow");
    public static final ResourceLocation OilResidueHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/residue_oil/oil_residue_hot_still");
    public static final ResourceLocation OilResidueHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/residue_oil/oil_residue_hot_flow");
    public static final ResourceLocation OilResidueSearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/residue_oil/oil_residue_searing_still");
    public static final ResourceLocation OilResidueSearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/residue_oil/oil_residue_searing_flow");
    
    public static final ResourceLocation OilHeavyCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/heavy_oil/oil_heavy_cool_still");
    public static final ResourceLocation OilHeavyCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/heavy_oil/oil_heavy_cool_flow");
    public static final ResourceLocation OilHeavyHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/heavy_oil/oil_heavy_hot_still");
    public static final ResourceLocation OilHeavyHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/heavy_oil/oil_heavy_hot_flow");
    public static final ResourceLocation OilHeavySearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/heavy_oil/oil_heavy_searing_still");
    public static final ResourceLocation OilHeavySearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/heavy_oil/oil_heavy_searing_flow");
    
    public static final ResourceLocation OilDenseCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_oil/oil_dense_cool_still");
    public static final ResourceLocation OilDenseCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_oil/oil_dense_cool_flow");
    public static final ResourceLocation OilDenseHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_oil/oil_dense_hot_still");
    public static final ResourceLocation OilDenseHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_oil/oil_dense_hot_flow");
    public static final ResourceLocation OilDenseSearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_oil/oil_dense_searing_still");
    public static final ResourceLocation OilDenseSearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_oil/oil_dense_searing_flow");
    
    public static final ResourceLocation OilDistilledCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/distilled_oil/oil_distilled_cool_still");
    public static final ResourceLocation OilDistilledCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/distilled_oil/oil_distilled_cool_flow");
    public static final ResourceLocation OilDistilledHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/distilled_oil/oil_distilled_hot_still");
    public static final ResourceLocation OilDistilledHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/distilled_oil/oil_distilled_hot_flow");
    public static final ResourceLocation OilDistilledSearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/distilled_oil/oil_distilled_searing_still");
    public static final ResourceLocation OilDistilledSearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/distilled_oil/oil_distilled_searing_flow");
    public static final Material FLAMMABLELIQUID = new Material(MaterialColor.COLOR_BLACK,true,false,true,false,true,true,PushReaction.DESTROY);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, BCEnergy.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, BCEnergy.MODID);
//    public static final DeferredRegister<ForgeFlowingFluid.Flowing> FLOWING= DeferredRegister.create(ForgeFlowingFluid.Flowing, null)
/*    
    public static final RegistryObject<FluidType> $$$$_COOL = FLUID_TYPES.register(NAME[i]+"_cool", () -> new aFluidType(FluidType.Properties.create().canDrown(true).canSwim(false).density(data[i][0]).viscosity(data[i][1]), %%%%%, %%%, i) );
    public static final RegistryObject<FlowingFluid> $$$$_COOL_SOURCE = FLUIDS.register(NAME[i]+"_cool_source", () -> new BCFluid.Source(BCEnergyFluids.$$$$_COOL_PROPERTY));
    public static final RegistryObject<FlowingFluid> $$$$_COOL_FLOWING = FLUIDS.register(NAME[i]+"_cool_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.$$$$_COOL_PROPERTY));
    public static final RegistryObject<BucketItem> $$$$_COOL_BUCKET = BCEnergy.ITEMS.register(NAME[i]+"_cool_bucket", () -> new BucketItem(BCEnergyFluids.$$$$_COOL_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> $$$$_COOL_BLOCK = BCEnergy.BLOCKS.register(NAME[i]+"_cool", () -> new LiquidBlock($$$$_COOL_SOURCE, BlockBehaviour.Properties.of(FLAMMABLELIQUID).noCollission().strength(100.0F).noLootTable()));
    public static final ForgeFlowingFluid.Properties $$$$_COOL_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.$$$$_COOL,BCEnergyFluids.$$$$_COOL_SOURCE,BCEnergyFluids.$$$$_COOL_FLOWING).block(BCEnergyFluids.$$$$_COOL_BLOCK).bucket(BCEnergyFluids.$$$$_COOL_BUCKET).slopeFindDistance(6);
*/
    
    
    
    
    
    
    
    //curde oil
    public static final RegistryObject<FluidType> OIL_COOL = FLUID_TYPES.register(NAME[i]+"_cool", () -> new aFluidType(FluidType.Properties.create().canDrown(true).canSwim(false).density(data[i][0]).viscosity(data[i][1]), OilCrudeCoolStillTexture, OilCrudeCoolFlowTexture, i) );
    public static final RegistryObject<BCFluid> OIL_COOL_SOURCE = FLUIDS.register(NAME[i]+"_cool_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_COOL_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_COOL_FLOWING = FLUIDS.register(NAME[i]+"_cool_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_COOL_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_COOL_BUCKET = BCEnergy.ITEMS.register("oil/"+NAME[i]+"_cool_bucket", () -> new BucketItem(BCEnergyFluids.OIL_COOL_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_COOL_BLOCK = BCEnergyBlocks.BLOCKS.register("oil/"+NAME[i]+"_cool", () -> new LiquidBlock(OIL_COOL_SOURCE, BlockBehaviour.Properties.of(FLAMMABLELIQUID).noCollission().strength(100.0F).noLootTable()));
    public static final ForgeFlowingFluid.Properties OIL_COOL_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_COOL,BCEnergyFluids.OIL_COOL_SOURCE,BCEnergyFluids.OIL_COOL_FLOWING).block(BCEnergyFluids.OIL_COOL_BLOCK).bucket(BCEnergyFluids.OIL_COOL_BUCKET).slopeFindDistance(6).tickRate(10);
    
    public static final RegistryObject<FluidType> OIL_HOT = FLUID_TYPES.register(NAME[i]+"_hot", () -> new aFluidType(FluidType.Properties.create().canDrown(true).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400), OilCrudeHotStillTexture, OilCrudeHotFlowTexture, i) );
    public static final RegistryObject<BCFluid> OIL_HOT_SOURCE = FLUIDS.register(NAME[i]+"_hot_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_HOT_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_HOT_FLOWING = FLUIDS.register(NAME[i]+"_hot_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_HOT_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_HOT_BUCKET = BCEnergy.ITEMS.register("oil/"+NAME[i]+"_hot_bucket", () -> new BucketItem(BCEnergyFluids.OIL_HOT_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_HOT_BLOCK = BCEnergyBlocks.BLOCKS.register("oil/"+NAME[i]+"_hot", () -> new LiquidBlock(OIL_HOT_SOURCE, BlockBehaviour.Properties.of(FLAMMABLELIQUID).noCollission().strength(100.0F).noLootTable()));
    public static final ForgeFlowingFluid.Properties OIL_HOT_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_HOT,BCEnergyFluids.OIL_HOT_SOURCE,BCEnergyFluids.OIL_HOT_FLOWING).block(BCEnergyFluids.OIL_HOT_BLOCK).bucket(BCEnergyFluids.OIL_HOT_BUCKET).slopeFindDistance(6);
    
    public static final RegistryObject<FluidType> OIL_SEARING = FLUID_TYPES.register(NAME[i]+"_searing", () -> new aFluidType(FluidType.Properties.create().canDrown(true).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(500), OilCrudeSearingStillTexture, OilCrudeSearingFlowTexture, i) );
    public static final RegistryObject<BCFluid> OIL_SEARING_SOURCE = FLUIDS.register(NAME[i]+"_searing_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_SEARING_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_SEARING_FLOWING = FLUIDS.register(NAME[i]+"_searing_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_SEARING_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_SEARING_BUCKET = BCEnergy.ITEMS.register("oil/"+NAME[i]+"_searing_bucket", () -> new BucketItem(BCEnergyFluids.OIL_SEARING_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_SEARING_BLOCK = BCEnergyBlocks.BLOCKS.register("oil/"+NAME[i]+"_searing", () -> new LiquidBlock(OIL_SEARING_SOURCE, BlockBehaviour.Properties.of(FLAMMABLELIQUID).noCollission().strength(100.0F).noLootTable()));
    public static final ForgeFlowingFluid.Properties OIL_SEARING_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_SEARING,BCEnergyFluids.OIL_SEARING_SOURCE,BCEnergyFluids.OIL_SEARING_FLOWING).block(BCEnergyFluids.OIL_SEARING_BLOCK).bucket(BCEnergyFluids.OIL_SEARING_BUCKET).slopeFindDistance(6);
    
    //residue oid
    public static final RegistryObject<FluidType> OIL_RESIDUE_COOL = FLUID_TYPES.register(NAME[++i]+"_cool", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]),OilResidueCoolStillTexture , OilResidueCoolFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_RESIDUE_COOL_SOURCE = FLUIDS.register(NAME[i]+"_cool_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_RESIDUE_COOL_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_RESIDUE_COOL_FLOWING = FLUIDS.register(NAME[i]+"_cool_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_RESIDUE_COOL_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_RESIDUE_COOL_BUCKET = BCEnergy.ITEMS.register("oil_residue/"+NAME[i]+"_cool_bucket", () -> new BucketItem(BCEnergyFluids.OIL_RESIDUE_COOL_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_RESIDUE_COOL_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_residue/"+NAME[i]+"_cool", () -> new LiquidBlock(BCEnergyFluids.OIL_RESIDUE_COOL_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_RESIDUE_COOL_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_RESIDUE_COOL,BCEnergyFluids.OIL_RESIDUE_COOL_SOURCE,BCEnergyFluids.OIL_RESIDUE_COOL_FLOWING).block(BCEnergyFluids.OIL_RESIDUE_COOL_BLOCK).slopeFindDistance(4).levelDecreasePerBlock(2).tickRate(20);
    
    public static final RegistryObject<FluidType> OIL_RESIDUE_HOT = FLUID_TYPES.register(NAME[i]+"_hot", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400),OilResidueHotStillTexture , OilResidueHotFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_RESIDUE_HOT_SOURCE = FLUIDS.register(NAME[i]+"_hot_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_RESIDUE_HOT_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_RESIDUE_HOT_FLOWING = FLUIDS.register(NAME[i]+"_hot_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_RESIDUE_HOT_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_RESIDUE_HOT_BUCKET = BCEnergy.ITEMS.register("oil_residue/"+NAME[i]+"_hot_bucket", () -> new BucketItem(BCEnergyFluids.OIL_RESIDUE_HOT_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_RESIDUE_HOT_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_residue/"+NAME[i]+"_hot", () -> new LiquidBlock(BCEnergyFluids.OIL_RESIDUE_HOT_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_RESIDUE_HOT_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_RESIDUE_HOT,BCEnergyFluids.OIL_RESIDUE_HOT_SOURCE,BCEnergyFluids.OIL_RESIDUE_HOT_FLOWING).block(BCEnergyFluids.OIL_RESIDUE_HOT_BLOCK).slopeFindDistance(4).levelDecreasePerBlock(2).tickRate(15);
    
    public static final RegistryObject<FluidType> OIL_RESIDUE_SEARING = FLUID_TYPES.register(NAME[i]+"_searing", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400),OilResidueSearingStillTexture , OilResidueSearingFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_RESIDUE_SEARING_SOURCE = FLUIDS.register(NAME[i]+"_searing_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_RESIDUE_SEARING_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_RESIDUE_SEARING_FLOWING = FLUIDS.register(NAME[i]+"_searing_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_RESIDUE_SEARING_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_RESIDUE_SEARING_BUCKET = BCEnergy.ITEMS.register("oil_residue/"+NAME[i]+"_searing_bucket", () -> new BucketItem(BCEnergyFluids.OIL_RESIDUE_SEARING_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_RESIDUE_SEARING_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_residue/"+NAME[i]+"_searing", () -> new LiquidBlock(BCEnergyFluids.OIL_RESIDUE_SEARING_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_RESIDUE_SEARING_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_RESIDUE_SEARING,BCEnergyFluids.OIL_RESIDUE_SEARING_SOURCE,BCEnergyFluids.OIL_RESIDUE_SEARING_FLOWING).block(BCEnergyFluids.OIL_RESIDUE_SEARING_BLOCK).slopeFindDistance(4).levelDecreasePerBlock(2).tickRate(10);
    
    //heavy oil
    public static final RegistryObject<FluidType> OIL_HEAVY_COOL = FLUID_TYPES.register(NAME[++i]+"_cool", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]),OilHeavyCoolStillTexture , OilHeavyCoolFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_HEAVY_COOL_SOURCE = FLUIDS.register(NAME[i]+"_cool_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_HEAVY_COOL_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_HEAVY_COOL_FLOWING = FLUIDS.register(NAME[i]+"_cool_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_HEAVY_COOL_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_HEAVY_COOL_BUCKET = BCEnergy.ITEMS.register("oil_heavy/"+NAME[i]+"_cool_bucket", () -> new BucketItem(BCEnergyFluids.OIL_HEAVY_COOL_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_HEAVY_COOL_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_heavy/"+NAME[i]+"_cool", () -> new LiquidBlock(BCEnergyFluids.OIL_HEAVY_COOL_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_HEAVY_COOL_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_HEAVY_COOL,BCEnergyFluids.OIL_HEAVY_COOL_SOURCE,BCEnergyFluids.OIL_HEAVY_COOL_FLOWING).block(BCEnergyFluids.OIL_HEAVY_COOL_BLOCK).slopeFindDistance(6).levelDecreasePerBlock(1).tickRate(20);
    
    public static final RegistryObject<FluidType> OIL_HEAVY_HOT = FLUID_TYPES.register(NAME[i]+"_hot", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400),OilHeavyHotStillTexture , OilHeavyHotFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_HEAVY_HOT_SOURCE = FLUIDS.register(NAME[i]+"_hot_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_HEAVY_HOT_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_HEAVY_HOT_FLOWING = FLUIDS.register(NAME[i]+"_hot_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_HEAVY_HOT_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_HEAVY_HOT_BUCKET = BCEnergy.ITEMS.register("oil_heavy/"+NAME[i]+"_hot_bucket", () -> new BucketItem(BCEnergyFluids.OIL_HEAVY_HOT_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_HEAVY_HOT_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_heavy/"+NAME[i]+"_hot", () -> new LiquidBlock(BCEnergyFluids.OIL_HEAVY_HOT_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_HEAVY_HOT_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_HEAVY_HOT,BCEnergyFluids.OIL_HEAVY_HOT_SOURCE,BCEnergyFluids.OIL_HEAVY_HOT_FLOWING).block(BCEnergyFluids.OIL_HEAVY_HOT_BLOCK).slopeFindDistance(6).levelDecreasePerBlock(1).tickRate(15);
    
    public static final RegistryObject<FluidType> OIL_HEAVY_SEARING = FLUID_TYPES.register(NAME[i]+"_searing", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400),OilHeavySearingStillTexture , OilHeavySearingFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_HEAVY_SEARING_SOURCE = FLUIDS.register(NAME[i]+"_searing_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_HEAVY_SEARING_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_HEAVY_SEARING_FLOWING = FLUIDS.register(NAME[i]+"_searing_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_HEAVY_SEARING_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_HEAVY_SEARING_BUCKET = BCEnergy.ITEMS.register("oil_heavy/"+NAME[i]+"_searing_bucket", () -> new BucketItem(BCEnergyFluids.OIL_HEAVY_SEARING_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_HEAVY_SEARING_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_heavy/"+NAME[i]+"_searing", () -> new LiquidBlock(BCEnergyFluids.OIL_HEAVY_SEARING_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_HEAVY_SEARING_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_HEAVY_SEARING,BCEnergyFluids.OIL_HEAVY_SEARING_SOURCE,BCEnergyFluids.OIL_HEAVY_SEARING_FLOWING).block(BCEnergyFluids.OIL_HEAVY_SEARING_BLOCK).slopeFindDistance(6).levelDecreasePerBlock(1).tickRate(10);
    
    //dense oil
    public static final RegistryObject<FluidType> OIL_DENSE_COOL = FLUID_TYPES.register(NAME[++i]+"_cool", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]),OilDenseCoolStillTexture , OilDenseCoolFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_DENSE_COOL_SOURCE = FLUIDS.register(NAME[i]+"_cool_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_DENSE_COOL_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_DENSE_COOL_FLOWING = FLUIDS.register(NAME[i]+"_cool_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_DENSE_COOL_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_DENSE_COOL_BUCKET = BCEnergy.ITEMS.register("oil_dense/"+NAME[i]+"_cool_bucket", () -> new BucketItem(BCEnergyFluids.OIL_DENSE_COOL_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_DENSE_COOL_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_dense/"+NAME[i]+"_cool", () -> new LiquidBlock(BCEnergyFluids.OIL_DENSE_COOL_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_DENSE_COOL_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_DENSE_COOL,BCEnergyFluids.OIL_DENSE_COOL_SOURCE,BCEnergyFluids.OIL_DENSE_COOL_FLOWING).block(BCEnergyFluids.OIL_DENSE_COOL_BLOCK).slopeFindDistance(5).levelDecreasePerBlock(2).tickRate(20);
    
    public static final RegistryObject<FluidType> OIL_DENSE_HOT = FLUID_TYPES.register(NAME[i]+"_hot", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400),OilDenseHotStillTexture , OilDenseHotFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_DENSE_HOT_SOURCE = FLUIDS.register(NAME[i]+"_hot_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_DENSE_HOT_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_DENSE_HOT_FLOWING = FLUIDS.register(NAME[i]+"_hot_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_DENSE_HOT_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_DENSE_HOT_BUCKET = BCEnergy.ITEMS.register("oil_dense/"+NAME[i]+"_hot_bucket", () -> new BucketItem(BCEnergyFluids.OIL_DENSE_HOT_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_DENSE_HOT_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_dense/"+NAME[i]+"_hot", () -> new LiquidBlock(BCEnergyFluids.OIL_DENSE_HOT_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_DENSE_HOT_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_DENSE_HOT,BCEnergyFluids.OIL_DENSE_HOT_SOURCE,BCEnergyFluids.OIL_DENSE_HOT_FLOWING).block(BCEnergyFluids.OIL_DENSE_HOT_BLOCK).slopeFindDistance(5).levelDecreasePerBlock(2).tickRate(15);
    
    public static final RegistryObject<FluidType> OIL_DENSE_SEARING = FLUID_TYPES.register(NAME[i]+"_searing", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400),OilDenseSearingStillTexture , OilDenseSearingFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_DENSE_SEARING_SOURCE = FLUIDS.register(NAME[i]+"_searing_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_DENSE_SEARING_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_DENSE_SEARING_FLOWING = FLUIDS.register(NAME[i]+"_searing_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_DENSE_SEARING_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_DENSE_SEARING_BUCKET = BCEnergy.ITEMS.register("oil_dense/"+NAME[i]+"_searing_bucket", () -> new BucketItem(BCEnergyFluids.OIL_DENSE_SEARING_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_DENSE_SEARING_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_dense/"+NAME[i]+"_searing", () -> new LiquidBlock(BCEnergyFluids.OIL_DENSE_SEARING_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_DENSE_SEARING_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_DENSE_SEARING,BCEnergyFluids.OIL_DENSE_SEARING_SOURCE,BCEnergyFluids.OIL_DENSE_SEARING_FLOWING).block(BCEnergyFluids.OIL_DENSE_SEARING_BLOCK).slopeFindDistance(5).levelDecreasePerBlock(2).tickRate(10);
    
    //distill oil
    public static final RegistryObject<FluidType> OIL_DISTILLED_COOL = FLUID_TYPES.register(NAME[++i]+"_cool", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]),OilDistilledCoolStillTexture , OilDistilledCoolFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_DISTILLED_COOL_SOURCE = FLUIDS.register(NAME[i]+"_cool_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_DISTILLED_COOL_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_DISTILLED_COOL_FLOWING = FLUIDS.register(NAME[i]+"_cool_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_DISTILLED_COOL_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_DISTILLED_COOL_BUCKET = BCEnergy.ITEMS.register("oil_distilled/"+NAME[i]+"_cool_bucket", () -> new BucketItem(BCEnergyFluids.OIL_DISTILLED_COOL_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_DISTILLED_COOL_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_distilled/"+NAME[i]+"_cool", () -> new LiquidBlock(BCEnergyFluids.OIL_DISTILLED_COOL_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_DISTILLED_COOL_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_DISTILLED_COOL,BCEnergyFluids.OIL_DISTILLED_COOL_SOURCE,BCEnergyFluids.OIL_DISTILLED_COOL_FLOWING).block(BCEnergyFluids.OIL_DISTILLED_COOL_BLOCK).slopeFindDistance(5).levelDecreasePerBlock(1).tickRate(10);
    
    public static final RegistryObject<FluidType> OIL_DISTILLED_HOT = FLUID_TYPES.register(NAME[i]+"_hot", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400),OilDistilledHotStillTexture , OilDistilledHotFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_DISTILLED_HOT_SOURCE = FLUIDS.register(NAME[i]+"_hot_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_DISTILLED_HOT_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_DISTILLED_HOT_FLOWING = FLUIDS.register(NAME[i]+"_hot_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_DISTILLED_HOT_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_DISTILLED_HOT_BUCKET = BCEnergy.ITEMS.register("oil_distilled/"+NAME[i]+"_hot_bucket", () -> new BucketItem(BCEnergyFluids.OIL_DISTILLED_HOT_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_DISTILLED_HOT_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_distilled/"+NAME[i]+"_hot", () -> new LiquidBlock(BCEnergyFluids.OIL_DISTILLED_HOT_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_DISTILLED_HOT_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_DISTILLED_HOT,BCEnergyFluids.OIL_DISTILLED_HOT_SOURCE,BCEnergyFluids.OIL_DISTILLED_HOT_FLOWING).block(BCEnergyFluids.OIL_DISTILLED_HOT_BLOCK).slopeFindDistance(5).levelDecreasePerBlock(1).tickRate(7);
    
    public static final RegistryObject<FluidType> OIL_DISTILLED_SEARING = FLUID_TYPES.register(NAME[i]+"_searing", () -> new aFluidType(FluidType.Properties.create().canDrown(false).canSwim(false).density(data[i][0]).viscosity(data[i][1]).temperature(400),OilDistilledSearingStillTexture , OilDistilledSearingFlowTexture, i));
    public static final RegistryObject<BCFluid> OIL_DISTILLED_SEARING_SOURCE = FLUIDS.register(NAME[i]+"_searing_source", () -> new BCFluid.Source(BCEnergyFluids.OIL_DISTILLED_SEARING_PROPERTY));
    public static final RegistryObject<BCFluid> OIL_DISTILLED_SEARING_FLOWING = FLUIDS.register(NAME[i]+"_searing_flowing", () -> new BCFluid.Flowing(BCEnergyFluids.OIL_DISTILLED_SEARING_PROPERTY));
    public static final RegistryObject<BucketItem> OIL_DISTILLED_SEARING_BUCKET = BCEnergy.ITEMS.register("oil_distilled/"+NAME[i]+"_searing_bucket", () -> new BucketItem(BCEnergyFluids.OIL_DISTILLED_SEARING_SOURCE,new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).craftRemainder(Items.BUCKET)));
    public static final RegistryObject<LiquidBlock> OIL_DISTILLED_SEARING_BLOCK = BCEnergyBlocks.BLOCKS.register("oil_distilled/"+NAME[i]+"_searing", () -> new LiquidBlock(BCEnergyFluids.OIL_DISTILLED_SEARING_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final ForgeFlowingFluid.Properties OIL_DISTILLED_SEARING_PROPERTY = new ForgeFlowingFluid.Properties(BCEnergyFluids.OIL_DISTILLED_SEARING,BCEnergyFluids.OIL_DISTILLED_SEARING_SOURCE,BCEnergyFluids.OIL_DISTILLED_SEARING_FLOWING).block(BCEnergyFluids.OIL_DISTILLED_SEARING_BLOCK).slopeFindDistance(5).levelDecreasePerBlock(1).tickRate(5);
   
    
    public static void registry(IEventBus bus) {
		FLUID_TYPES.register(bus);
		FLUIDS.register(bus);
    }
    
    public static void init() {
    	crudeOil[0] = OIL_COOL_SOURCE.get();
    	crudeOil[1] = OIL_HOT_SOURCE.get();
    	crudeOil[2] = OIL_SEARING_SOURCE.get();
    	oilDistilled[0] = OIL_DENSE_COOL_SOURCE.get();
    	oilDistilled[1] = OIL_DENSE_HOT_SOURCE.get();
    	oilDistilled[2] = OIL_DENSE_SEARING_SOURCE.get();
    	oilHeavy[0] = OIL_HEAVY_COOL_SOURCE.get();
    	oilHeavy[1] = OIL_HEAVY_HOT_SOURCE.get();
    	oilHeavy[2] = OIL_HEAVY_SEARING_SOURCE.get();
    	oilDense[0] = OIL_DENSE_COOL_SOURCE.get();
    	oilDense[1] = OIL_DENSE_HOT_SOURCE.get();
    	oilDense[2] = OIL_DENSE_SEARING_SOURCE.get();
    	oilResidue[0] = OIL_RESIDUE_COOL_SOURCE.get();
    	oilResidue[1] = OIL_RESIDUE_HOT_SOURCE.get();
    	oilResidue[2] = OIL_RESIDUE_SEARING_SOURCE.get();
    }
    
    static class aFluidType extends FluidType{

    	private final ResourceLocation stillTexture;
    	private final ResourceLocation flowTexture;
//    	private final int num;
		public aFluidType(Properties properties,ResourceLocation still, ResourceLocation flowingTexture,int i) {
			super(properties);
			this.stillTexture = still;
			this.flowTexture = flowingTexture;
//			num = i;

			// TODO Auto-generated constructor stub
		}
    	@Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
        {
    		consumer.accept(new IClientFluidTypeExtensions() {
    			@Override
    			public ResourceLocation getStillTexture() {
					return stillTexture;
    			}

				@Override
				public int getTintColor() {
					// TODO Auto-generated method stub
//					return BCEnergyFluids.data[i][4];
					return 0xFFffffFF;
				}
				

				@Override
				public ResourceLocation getFlowingTexture() {
					return flowTexture;

				}
				
				@Override
				public @Nullable ResourceLocation getOverlayTexture() {
					return IClientFluidTypeExtensions.super.getOverlayTexture();
				}

				@Override
				public void modifyFogRender(Camera camera, FogMode mode, float renderDistance, float partialTick,
						float nearDistance, float farDistance, FogShape shape) {
					IClientFluidTypeExtensions.super.modifyFogRender(camera, mode, renderDistance, partialTick, nearDistance, farDistance,
							shape);
				}
				
    			
    		});
        }
    }
}
