package ct.buildcraft.energy;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;

import ct.buildcraft.api.core.BCLog;
import ct.buildcraft.lib.misc.JsonUtil;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BCEnergyBiomes {
	public static final DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, BCEnergy.MODID);
	
    public static final Biome OIL_DESERT ;
    public static final ResourceKey<Biome> OIL_DESERT_KEY = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(BCEnergy.MODID,"oil_desert"));
    
    public static List<Pair<ParameterPoint, ResourceKey<Biome>>> OIL_BIOME_REPLACEMENT;
    
    static {
    	Biome desert = OverworldBiomes.desert();
    	BiomeGenerationSettings.Builder feature$builder = new BiomeGenerationSettings.Builder();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addFossilDecoration(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultSprings(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultGrass(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertVegetation(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertExtraVegetation(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDesertExtraDecoration(biomegenerationsettings$builder);
        OIL_DESERT = new Biome.BiomeBuilder().precipitation(Precipitation.NONE).temperature(2.0f).downfall(0.0f).generationSettings(biomegenerationsettings$builder.build())
        		.mobSpawnSettings(desert.getMobSettings()).precipitation(desert.getPrecipitation()).specialEffects(desert.getModifiedSpecialEffects())
        		.build();
    	
    }
    
    public static void init(IEventBus modEventBus) {
    	BIOME_REGISTER.register("oil_desert", () -> OIL_DESERT);
    	BIOME_REGISTER.register(modEventBus);
//    	getReplaceMentFromOverworldBiomeBuilder();
//    	saveBiomeReplaceMent();
    	if(!loadBiomeReplaceMent()) {
    		getReplaceMentFromOverworldBiomeBuilder();
    		saveBiomeReplaceMent();
    	}
    }
    
    private static void getReplaceMentFromOverworldBiomeBuilder() {
        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> list = new ArrayList<Pair<Climate.ParameterPoint, ResourceKey<Biome>>>();
        OIL_BIOME_REPLACEMENT = new ArrayList<>();
		Constructor<?> constructor = OverworldBiomeBuilder.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> con = list::add;
		for(Method method : OverworldBiomeBuilder.class.getDeclaredMethods()) {
			method.setAccessible(true);
			if(method.getName().equals("addBiomes")) {//TODO
				try {
					OverworldBiomeBuilder builder = (OverworldBiomeBuilder) constructor.newInstance();
					method.invoke(builder, con);
					BCLog.logger.debug("BCEnergyBiomes:invoke:successed");
					for(var pair : list) {
						if(pair.getSecond().equals(Biomes.DESERT)&&pair.getFirst().weirdness().max()>0) {
							OIL_BIOME_REPLACEMENT.add(pair);
							BCLog.logger.debug("BCEnergyBiomes:methodName:get desert biome for "+pair.getFirst());
						}
					}
				} catch (Exception e) {
					BCLog.logger.debug("BCEnergyBiomes:invoke:fail to get BiomeReplaceMent");
					//e.printStackTrace();
				} 
			} 
		}
    }
    
    private static boolean saveBiomeReplaceMent() {
    	Map<String, ResourceKey<Biome>> map = new HashMap<>();
    	OIL_BIOME_REPLACEMENT.stream().forEach((a) -> map.put(a.getFirst().toString(), a.getSecond()));
    	try {
			FileWriter output = new FileWriter("BiomeReplaceMent.json");
			Gson g = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			g.toJson(JsonUtil.BIOME_MAP_CODEC.encodeStart(JsonOps.INSTANCE, map).getOrThrow(false, BCLog.logger::error), output);
			output.close();
		} catch (Exception e) {
			BCLog.logger.debug("BCEnergyBiomes:save:fail to open BiomeReplaceMent.json");
			e.printStackTrace();
			return false;
		}
    	return true;

    }
    
    private static boolean loadBiomeReplaceMent() {
    	Map<String, ResourceKey<Biome>> map = new HashMap<>();
    	try {
			FileReader input = new FileReader("BiomeReplaceMent.json");
			Gson g = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//			BCLog.logger.debug(JsonUtil.BIOME_MAP_CODEC.encodeStart(JsonOps.INSTANCE, map).getOrThrow(false, BCLog.logger::error).toString());
//			g.toJson(JsonUtil.BIOME_MAP_CODEC.encodeStart(JsonOps.INSTANCE, map).getOrThrow(false, BCLog.logger::error).toString(), output);
			map = JsonUtil.BIOME_MAP_CODEC.parse(JsonOps.INSTANCE, g.fromJson(input, JsonElement.class)).getOrThrow(false, BCLog.logger::error);
//			g.(JsonUtil.BIOME_MAP_CODEC.encodeStart(JsonOps.INSTANCE, map).getOrThrow(false, BCLog.logger::error), output);
			input.close();
			loadMapIntoList(map);
		} catch (Exception e) {
			BCLog.logger.debug("BCEnergyBiomes:save:fail to open BiomeReplaceMent.json");
			e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    private static void loadMapIntoList(Map<String, ResourceKey<Biome>> map) {
    	//ParameterPoint[temperature=[5500-10000], humidity=[-10000--3500], continentalness=[-1900-10000], erosion=[500-4500], depth=0, weirdness=[9333-10000], offset=0]";
    	Pattern sp = Pattern.compile("(?<=\\d-)");
    	Pattern tem = Pattern.compile("temperature\\=\\[(.*?)\\]");
    	Pattern hum = Pattern.compile("humidity\\=\\[(.*?)\\]");
    	Pattern con = Pattern.compile("continentalness\\=\\[(.*?)\\]");
    	Pattern ero = Pattern.compile("erosion\\=\\[(.*?)\\]");
    	Pattern dep = Pattern.compile("depth\\=(\\d+)");
    	Pattern wei = Pattern.compile("weirdness\\=\\[(.*?)\\]");
    	Pattern off = Pattern.compile("offset\\=(\\d+)");
    	
    	OIL_BIOME_REPLACEMENT = (map.entrySet().stream().map((a) -> {
    		String s = a.getKey();
    		Matcher offm = off.matcher(s);
    		offm.find();
    		ParameterPoint p = new ParameterPoint(getPara(s, tem), getPara(s, hum), getPara(s, con), getPara(s, ero),
    				getPara(s, dep), getPara(s, wei), Long.valueOf(offm.group(1)));
    		return Pair.of(p, a.getValue());
    	}).toList());
    }
    
    private static Climate.Parameter getPara(String s, Pattern type){
    	Matcher v = type.matcher(s);
    	if(!v.find()) {
    		BCLog.logger.error("BCEnergyBiome:Cannot match BiomeReplaceMent.json element, please Check you File");
    	}
    	String targe = v.group(1);
    	int index = targe.indexOf("-", 1);
    	if(index == -1) 
    		return Climate.Parameter.point(Long.valueOf(targe)/10000f);
    	var k = Climate.Parameter.span(Long.valueOf(targe.substring(0, index))/10000F, Long.valueOf(targe.substring(index+1))/10000F);
    	BCLog.logger.debug("921:"+k.toString());
    	return k;
    }
    

/*        super(new BiomeProperties("Desert Oil Field").setBaseHeight(0.125F).setHeightVariation(0.05F)
            .setTemperature(2.0F).setRainfall(0.0F).setRainDisabled());
        setRegistryName();
        SurfaceRules*/
    
}
