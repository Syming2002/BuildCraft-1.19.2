/* Copyright (c) 2016 SpaceToad and the BuildCraft team
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package ct.buildcraft.lib;

import ct.buildcraft.api.core.BCLog;
import ct.buildcraft.lib.block.VanillaRotationHandlers;
import ct.buildcraft.lib.net.MessageManager;
import ct.buildcraft.lib.net.cache.BuildCraftObjectCaches;
import ct.buildcraft.lib.tile.TileBC_Neptune;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

//@formatter:off
@Mod(BCLib.MODID)
/*    modid = BCLib.MODID,
    name = "BuildCraft Lib",
    version = BCLib.VERSION,
    updateJSON = "https://mod-buildcraft.com/version/versions.json",
    acceptedMinecraftVersions = "(gradle_replace_mcversion,)",
    dependencies = "required-after:forge@(gradle_replace_forgeversion,)"
)*/
//@formatter:on
public class BCLib {
    public static final String MODID = "buildcraftlib";
    public static final String VERSION = "$version";
    public static final String MC_VERSION = "${mcversion}";
    public static final String GIT_BRANCH = "${git_branch}";
    public static final String GIT_COMMIT_HASH = "${git_commit_hash}";
    public static final String GIT_COMMIT_MSG = "${git_commit_msg}";
    public static final String GIT_COMMIT_AUTHOR = "${git_commit_author}";

    public static final boolean DEV = true;// = VERSION.startsWith("$") || Boolean.getBoolean("buildcraft.dev");

    public BCLib() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        try {
            BCLog.logger.info("");
        } catch (NoSuchFieldError e) {
            throw throwBadClass(e, BCLog.class);
        }
        BCLog.logger.info("Starting BuildCraft " + BCLib.VERSION);
        BCLog.logger.info("Copyright (c) the BuildCraft team, 2011-2018");
        BCLog.logger.info("https://www.mod-buildcraft.com");
        if (!GIT_COMMIT_HASH.startsWith("${")) {
            BCLog.logger.info("Detailed Build Information:");
            BCLog.logger.info("  Branch " + GIT_BRANCH);
            BCLog.logger.info("  Commit " + GIT_COMMIT_HASH);
            BCLog.logger.info("    " + GIT_COMMIT_MSG);
            BCLog.logger.info("    committed by " + GIT_COMMIT_AUTHOR);
        }
        BCLog.logger.info("");
        BCLog.logger.info("Loaded Modules:");
/*        for (BCModules module : BCModules.VALUES) {
            if (module.isLoaded()) {
                BCLog.logger.info("  - " + module.lowerCaseName);
            }
        }
        BCLog.logger.info("Missing Modules:");
        for (BCModules module : BCModules.VALUES) {
            if (!module.isLoaded()) {
                BCLog.logger.info("  - " + module.lowerCaseName);
            }
        }*/
        BCLog.logger.info("");

//        ExpressionDebugManager.logger = BCLog.logger::info;
//        ExpressionCompat.setup();

        BCLibRegistries.fmlPreInit();
        BCLibProxy.MessageRegistry();
//        BCLibItems.fmlPreInit();

        BuildCraftObjectCaches.fmlPreInit();
//        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, BCLibProxy.getProxy());

   //     MinecraftForge.EVENT_BUS.register(BCLibEventDist.class);
//        MinecraftForge.EVENT_BUS.register(MigrationManager.INSTANCE);
  //      MinecraftForge.EVENT_BUS.register(FluidManager);

        // Set max chunk limit for quarries: 1 chunk for quarry itself and 5 * 5 chunks square for working area
//        ForgeChunkManager.getConfig().get(MODID, "maximumChunksPerTicket", 26);
 //       ForgeChunkManager.syncConfigDefaults();
 //      ForgeChunkManager.setForcedChunkLoadingCallback(BCLib.MODID, ChunkLoaderManager::rebindTickets);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BCLibEventDist.class);
//        init();postInit();
    }


    public void commonSetup(final FMLCommonSetupEvent event) {
        MessageManager.fmlPostInit();
        BuildCraftObjectCaches.fmlPostInit();
    }


    public static Error throwBadClass(Error e, Class<?> cls) throws Error {
        throw new Error(
            "Bad " + cls + " loaded from " + cls.getClassLoader() + " domain: " + cls.getProtectionDomain(), e
        );
    }

    public static void init(/*FMLInitializationEvent evt*/) {
//        BCLibProxy.getProxy().fmlInit();

        BCLibRegistries.fmlInit();
//        VanillaListHandlers.fmlInit();
//        VanillaPaintHandlers.fmlInit();
        VanillaRotationHandlers.fmlInit();

//        RegistrationHelper.registerOredictEntries();
    }

    public static void postInit() {
//        ReloadableRegistryManager.loadAll();
//        BCLibProxy.getProxy().fmlPostInit();

//        VanillaListHandlers.fmlPostInit();
//        MarkerCache.postInit();
        
    }

    public static void serverStarting(/*FMLServerStartingEvent event*/) {
//        event.registerServerCommand(new CommandBuildCraft());
    }

/*    static {
        startBatch();
        registerTag("item.guide").reg("guide").locale("buildcraft.guide").model("guide").tab("vanilla.misc");
        registerTag("item.guide.note").reg("guide_note").locale("buildcraft.guide_note").model("guide_note")
            .tab("vanilla.misc");
        registerTag("item.debugger").reg("debugger").locale("debugger").model("debugger").tab("vanilla.misc");
        endBatch(TagManager.prependTags("buildcraftlib:", EnumTagType.REGISTRY_NAME, EnumTagType.MODEL_LOCATION));
    }

    private static TagEntry registerTag(String id) {
        return TagManager.registerTag(id);
    }*/

    private static void startBatch() {
//        TagManager.startBatch();
    }

/*    private static void endBatch(Consumer<TagEntry> consumer) {
        TagManager.endBatch(consumer);
    }*/
}
