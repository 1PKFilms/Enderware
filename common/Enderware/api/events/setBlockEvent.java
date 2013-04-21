package Enderware.api.events;



public class setBlockEvent extends net.minecraftforge.event.Event{
    
    public final int x;
    public final int y;
    public final int z;
    public final int toid;
    public final int fromid;
    public final int dimension;
    public setBlockEvent(int dimension, int x,int y,int z,int fromid,int frommeta,int toid,int tometa){
        this.setResult(Result.ALLOW);
        this.fromid = fromid;
        this.toid = toid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }
@Override
public boolean hasResult() {
    return true;
}
@Override
public boolean isCancelable() {
    return true;
}


}
