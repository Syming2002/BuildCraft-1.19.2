package ct.buildcraft.energy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import ct.buildcraft.core.BCCore;
import ct.buildcraft.lib.fluid.BCFluid;

import com.mojang.blaze3d.shaders.FogShape;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class BCEnergyFluids {
	public static final int COOL_TEM = 300;
	public static final int HOT_TEM = 400;
	public static final int SEARING_TEM = 500;
	public static final int[] TEMS = {COOL_TEM, HOT_TEM, SEARING_TEM};
	public static final String[] TEM_NAMES = {"_cool", "_hot", "_searing"};
	
    public static BCFluid[] crudeOil = new BCFluid[3];
    /** All 3 fuels (no residue) */
    public static BCFluid[] oilDistilled = new BCFluid[3];
    /** The 3 heaviest components (fuelLight, fuelDense and oilResidue) */
    public static BCFluid[] oilHeavy = new BCFluid[3];
    /** The 2 lightest fuels (no dense fuel) */
    public static BCFluid[] fuelMixedLight = new BCFluid[3];
    /** The 2 heaviest fuels (no gaseous fuel) */
    public static BCFluid[] fuelMixedHeavy = new BCFluid[3];
    /** The 2 heaviest products (fuelDense and oilResidue) */
    public static BCFluid[] oilDense = new BCFluid[3];

    // End products in order from least to most dense
    public static BCFluid[] fuelGaseous = new BCFluid[3];
    public static BCFluid[] fuelLight = new BCFluid[3];
    public static BCFluid[] fuelDense = new BCFluid[3];
    public static BCFluid[] oilResidue = new BCFluid[3];

//    public static BCFluid tar;

    public static final List<BCFluid> allFluids = new ArrayList<>();

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
    
    public static final ResourceLocation FuelDenseCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_fuel/fuel_dense_cool_still");
    public static final ResourceLocation FuelDenseCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_fuel/fuel_dense_cool_flow");
    public static final ResourceLocation FuelDenseHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_fuel/fuel_dense_hot_still");
    public static final ResourceLocation FuelDenseHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_fuel/fuel_dense_hot_flow");
    public static final ResourceLocation FuelDenseSearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_fuel/fuel_dense_searing_still");
    public static final ResourceLocation FuelDenseSearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/dense_fuel/fuel_dense_searing_flow");
    
    public static final ResourceLocation FuelMixedHeavyCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_heavy_fuel/fuel_mixed_heavy_cool_still");
    public static final ResourceLocation FuelMixedHeavyCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_heavy_fuel/fuel_mixed_heavy_cool_flow");
    public static final ResourceLocation FuelMixedHeavyHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_heavy_fuel/fuel_mixed_heavy_hot_still");
    public static final ResourceLocation FuelMixedHeavyHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_heavy_fuel/fuel_mixed_heavy_hot_flow");
    public static final ResourceLocation FuelMixedHeavySearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_heavy_fuel/fuel_mixed_heavy_searing_still");
    public static final ResourceLocation FuelMixedHeavySearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_heavy_fuel/fuel_mixed_heavy_searing_flow");
    
    public static final ResourceLocation FuelLightCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/light_fuel/fuel_light_cool_still");
    public static final ResourceLocation FuelLightCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/light_fuel/fuel_light_cool_flow");
    public static final ResourceLocation FuelLightHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/light_fuel/fuel_light_hot_still");
    public static final ResourceLocation FuelLightHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/light_fuel/fuel_light_hot_flow");
    public static final ResourceLocation FuelLightSearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/light_fuel/fuel_light_searing_still");
    public static final ResourceLocation FuelLightSearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/light_fuel/fuel_light_searing_flow");
    
    public static final ResourceLocation FuelMixedLightCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_light_fuel/fuel_mixed_light_cool_still");
    public static final ResourceLocation FuelMixedLightCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_light_fuel/fuel_mixed_light_cool_flow");
    public static final ResourceLocation FuelMixedLightHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_light_fuel/fuel_mixed_light_hot_still");
    public static final ResourceLocation FuelMixedLightHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_light_fuel/fuel_mixed_light_hot_flow");
    public static final ResourceLocation FuelMixedLightSearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_light_fuel/fuel_mixed_light_searing_still");
    public static final ResourceLocation FuelMixedLightSearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/mixed_light_fuel/fuel_mixed_light_searing_flow");
    
    public static final ResourceLocation FuelGasCoolStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/gas_fuel/fuel_gas_cool_still");
    public static final ResourceLocation FuelGasCoolFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/gas_fuel/fuel_gas_cool_flow");
    public static final ResourceLocation FuelGasHotStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/gas_fuel/fuel_gas_hot_still");
    public static final ResourceLocation FuelGasHotFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/gas_fuel/fuel_gas_hot_flow");
    public static final ResourceLocation FuelGasSearingStillTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/gas_fuel/fuel_gas_searing_still");
    public static final ResourceLocation FuelGasSearingFlowTexture = new ResourceLocation("buildcraftenergy:blocks/fluids/gas_fuel/fuel_gas_searing_flow");
    
    public static final Material FLAMMABLELIQUID = new Material(MaterialColor.COLOR_BLACK,true,false,true,false,true,true,PushReaction.DESTROY);
    
    public static final ResourceLocation[][] OIL_TEXTURE = {
    		new ResourceLocation[]{OilCrudeCoolStillTexture, OilCrudeCoolFlowTexture, OilCrudeHotStillTexture, OilCrudeHotFlowTexture, OilCrudeSearingStillTexture,OilCrudeSearingFlowTexture},
    		new ResourceLocation[]{OilResidueCoolStillTexture, OilResidueCoolFlowTexture, OilResidueHotStillTexture, OilResidueHotFlowTexture, OilResidueSearingStillTexture,OilResidueSearingFlowTexture},
    		new ResourceLocation[]{OilHeavyCoolStillTexture, OilHeavyCoolFlowTexture, OilHeavyHotStillTexture, OilHeavyHotFlowTexture, OilHeavySearingStillTexture,OilHeavySearingFlowTexture},
    		new ResourceLocation[]{OilDenseCoolStillTexture, OilDenseCoolFlowTexture, OilDenseHotStillTexture, OilDenseHotFlowTexture, OilDenseSearingStillTexture,OilDenseSearingFlowTexture},
    		new ResourceLocation[]{OilDistilledCoolStillTexture, OilDistilledCoolFlowTexture, OilDistilledHotStillTexture, OilDistilledHotFlowTexture, OilDistilledSearingStillTexture,OilDistilledSearingFlowTexture},
    		new ResourceLocation[]{FuelDenseCoolStillTexture, FuelDenseCoolFlowTexture, FuelDenseHotStillTexture, FuelDenseHotFlowTexture, FuelDenseSearingStillTexture,FuelDenseSearingFlowTexture},
    		new ResourceLocation[]{FuelMixedHeavyCoolStillTexture, FuelMixedHeavyCoolFlowTexture, FuelMixedHeavyHotStillTexture, FuelMixedHeavyHotFlowTexture, FuelMixedHeavySearingStillTexture,FuelMixedHeavySearingFlowTexture},
    		new ResourceLocation[]{FuelLightCoolStillTexture, FuelLightCoolFlowTexture, FuelLightHotStillTexture, FuelLightHotFlowTexture, FuelLightSearingStillTexture,FuelLightSearingFlowTexture},
    		new ResourceLocation[]{FuelMixedLightCoolStillTexture, FuelMixedLightCoolFlowTexture, FuelMixedLightHotStillTexture, FuelMixedLightHotFlowTexture, FuelMixedLightSearingStillTexture,FuelMixedLightSearingFlowTexture},
    		new ResourceLocation[]{FuelGasCoolStillTexture, FuelGasCoolFlowTexture, FuelGasHotStillTexture, FuelGasHotFlowTexture, FuelGasSearingStillTexture,FuelGasSearingFlowTexture}
    };
    public static final List<RegistryObject<FluidType>> OIL_TYPE = new ArrayList<>();
    public static final List<RegistryObject<BCFluid>> OIL_SOURCE = new ArrayList<>();
    public static final List<RegistryObject<BucketItem>> OIL_BUCKET = new ArrayList<>();
    public static final List<RegistryObject<LiquidBlock>> OIL_BLOCK = new ArrayList<>();
    
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, BCEnergy.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, BCEnergy.MODID);
    
    public static final TagKey<Fluid> IS_OIL = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(BCEnergy.MODID, "is_oil"));
    public static final TagKey<Fluid> IS_FUEL = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(BCEnergy.MODID, "is_fuel"));

    private static final String[] NAME = {"oil","oil_residue","oil_heavy","oil_dense","oil_distilled",
			  "fuel_dense","fuel_mixed_heavy","fuel_light","fuel_mixed_light","fuel_gaseous"};


    
    public static void registry(IEventBus bus) {
    	registryFluid();
		FLUID_TYPES.register(bus);
		FLUIDS.register(bus);
    }
    
    public static void init() {
    	int id = 0;
    	crudeOil[0] = OIL_SOURCE.get(id++).get();
    	crudeOil[1] = OIL_SOURCE.get(id++).get();
    	crudeOil[2] = OIL_SOURCE.get(id++).get();
    	oilResidue[0] = OIL_SOURCE.get(id++).get();
    	oilResidue[1] = OIL_SOURCE.get(id++).get();
    	oilResidue[2] = OIL_SOURCE.get(id++).get();
    	oilHeavy[0] = OIL_SOURCE.get(id++).get();
    	oilHeavy[1] = OIL_SOURCE.get(id++).get();
    	oilHeavy[2] = OIL_SOURCE.get(id++).get();
    	oilDense[0] = OIL_SOURCE.get(id++).get();
    	oilDense[1] = OIL_SOURCE.get(id++).get();
    	oilDense[2] = OIL_SOURCE.get(id++).get();
    	oilDistilled[0] = OIL_SOURCE.get(id++).get();
    	oilDistilled[1] = OIL_SOURCE.get(id++).get();
    	oilDistilled[2] = OIL_SOURCE.get(id++).get();
    	fuelDense[0] = OIL_SOURCE.get(id++).get();
    	fuelDense[1] = OIL_SOURCE.get(id++).get();
    	fuelDense[2] = OIL_SOURCE.get(id++).get();
    	fuelMixedHeavy[0] = OIL_SOURCE.get(id++).get();
    	fuelMixedHeavy[1] = OIL_SOURCE.get(id++).get();
    	fuelMixedHeavy[2] = OIL_SOURCE.get(id++).get();
    	fuelLight[0] = OIL_SOURCE.get(id++).get();
    	fuelLight[1] = OIL_SOURCE.get(id++).get();
    	fuelLight[2] = OIL_SOURCE.get(id++).get();
    	fuelMixedLight[0] = OIL_SOURCE.get(id++).get();
    	fuelMixedLight[1] = OIL_SOURCE.get(id++).get();
    	fuelMixedLight[2] = OIL_SOURCE.get(id++).get();
    	fuelGaseous[0] = OIL_SOURCE.get(id++).get();
    	fuelGaseous[1] = OIL_SOURCE.get(id++).get();
    	fuelGaseous[2] = OIL_SOURCE.get(id++).get();
    }
    
    public static void registryFluid() {
    	for(int id=0;id<NAME.length;id++) 
    		for(int tem = 0;tem <3;tem++) {
    			creatFluid(id,tem);
    	}
    }
    
    private static void creatFluid(int id, int tem) {
        RegistryObject<FluidType> TYPE = FLUID_TYPES.register(NAME[id]+TEM_NAMES[tem], () -> new aFluidType(FluidType.Properties.create().canDrown(true).canSwim(false).density(data[id][0]).viscosity(data[id][1]).temperature(TEMS[tem]), OIL_TEXTURE[id][2*tem], OIL_TEXTURE[id][2*tem+1], id) );
        RegistryObject<BCFluid> SOURCE = RegistryObject.create(new ResourceLocation(BCEnergy.MODID,NAME[id]+TEM_NAMES[tem]+"_source"), ForgeRegistries.Keys.FLUIDS, BCEnergy.MODID);
        RegistryObject<BCFluid> FLOWING = RegistryObject.create(new ResourceLocation(BCEnergy.MODID,NAME[id]+TEM_NAMES[tem]+"_flowing"), ForgeRegistries.Keys.FLUIDS, BCEnergy.MODID);
        RegistryObject<BucketItem> BUCKET = BCEnergy.ITEMS.register(NAME[id]+"/"+NAME[id]+TEM_NAMES[tem]+"_bucket", () -> new BucketItem(SOURCE,new Item.Properties().stacksTo(1).tab(BCCore.tabFluids).craftRemainder(Items.BUCKET)));
        RegistryObject<LiquidBlock> FUEL_GAS_COOL_BLOCK = BCEnergyBlocks.BLOCKS.register(NAME[id]+"/"+NAME[id]+TEM_NAMES[tem], () -> new LiquidBlock(SOURCE, BlockBehaviour.Properties.of(FLAMMABLELIQUID).noCollission().strength(100.0F).noLootTable()));
        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(TYPE, SOURCE, FLOWING).bucket(BUCKET).block(FUEL_GAS_COOL_BLOCK);//.slopeFindDistance(0);
        FLUIDS.register(NAME[id]+TEM_NAMES[tem]+"_source", () -> new BCFluid.Source(properties).setHeat(tem));
        FLUIDS.register(NAME[id]+TEM_NAMES[tem]+"_flowing", () -> new BCFluid.Flowing(properties).setHeat(tem));
        OIL_TYPE.add(TYPE);
        OIL_SOURCE.add(SOURCE);
        OIL_BUCKET.add(BUCKET);
        OIL_BLOCK.add(FUEL_GAS_COOL_BLOCK);
//    	System.out.println("\"item.buildcraftenergy."+(NAME[id]+"/"+NAME[id]+TEM_NAMES[tem]+"_bucket").replace('/', '.')+"\":\"\",");

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
