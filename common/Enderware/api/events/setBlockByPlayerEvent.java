package Enderware.api.events;

import net.minecraft.entity.player.EntityPlayer;


public class setBlockByPlayerEvent extends setBlockEvent{
public EntityPlayer player = null;
    public setBlockByPlayerEvent(int dimension, int x, int y, int z,
            int fromid,int frommeta, int toid,int tometa,EntityPlayer player) {
        super(dimension, x, y, z, fromid,frommeta, toid,tometa);
       this.player = player;
    }
    
}
