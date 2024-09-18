package ct.buildcraft.transport.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import ct.buildcraft.lib.net.MessageManager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class PipeItemMessageQueue {

    private static final Map<LevelChunk, List<MessageMultiPipeItem>> cachedPlayerPackets = new WeakHashMap<>();

    
    public static void serverTick() {
        for (Entry<LevelChunk, List<MessageMultiPipeItem>> entry : cachedPlayerPackets.entrySet()) {
        	LevelChunk chunk = entry.getKey();
        	for(MessageMultiPipeItem msg : entry.getValue()) {
        		MessageManager.sendToAllWatching(msg, chunk);
        	}
        }
        cachedPlayerPackets.clear();
    }

    public static void appendTravellingItem(Level world, BlockPos pos, int stackId, byte stackCount, boolean toCenter,
        Direction side, @Nullable DyeColor colour, byte timeToDest) {
        ServerLevel server = (ServerLevel) world;
//        PlayerChunkMapEntry playerChunkMap = server.getPlayerChunkMap().getEntry(pos.getX() >> 4, pos.getZ() >> 4);
        //to debug
//        if (/*playerChunkMap == null*/((ServerLevel)world).getChunkSource().chunkMap.getDistanceManager().inBlockTickingRange(pos.asLong())) {
            // No-one was watching this chunk.
//            return;
//        }
        // Slightly ugly hack to iterate through all players watching the chunk
/*        playerChunkMap.hasPlayerMatchingInRange(0, player -> {
            players.add(player);
            // Always return false so that the iteration doesn't stop early
            return false;
        });*/
        
        cachedPlayerPackets.computeIfAbsent(server.getChunkAt(pos), 
        	pl -> {
        		List<MessageMultiPipeItem> l = new ArrayList<>();
        		MessageMultiPipeItem msg = new MessageMultiPipeItem();
        		msg.append(pos, stackId, stackCount, toCenter, side, colour, timeToDest);
        		l.add(msg);
        		return l;
        	});
    }
}
