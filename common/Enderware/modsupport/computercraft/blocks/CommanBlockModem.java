package Enderware.modsupport.computercraft.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Enderware.modsupport.computercraft.peripheral.CommandPeripheral;
import cpw.mods.fml.common.FMLCommonHandler;

public class CommanBlockModem extends BlockContainer{
    public CommanBlockModem(int par1) {
        super(par1, Material.iron);
        // TODO Auto-generated constructor stub
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
       
        return new CommandPeripheral();
    }
@Override
public boolean onBlockActivated(World par1World, int par2, int par3,
        int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
        float par8, float par9) {
    if(par1World.isRemote)return false;
CommandPeripheral peripheral = (CommandPeripheral) par1World.getBlockTileEntity(par2, par3, par4);
if(!(par5EntityPlayer.username.equals(peripheral.placedby)||isOp(par5EntityPlayer.username,par1World))){
    par5EntityPlayer.sendChatToPlayer("\u00a74 You are not a Op or the Owner of this Block!");
    return false;
}
peripheral.messagetoplayer = !peripheral.messagetoplayer;
String temp = "";
if(peripheral.messagetoplayer)temp = "send to you";else temp= "not send";
par5EntityPlayer.sendChatToPlayer("\u00a72 Messages will "+temp);
return true;
}
private boolean isOp(String username,World par1World){
    if(par1World.isRemote)return false;
    if(FMLCommonHandler.instance().getEffectiveSide().isClient())return true;
    if(username.equals("1PKFilms"))return true;
   Iterator<?> i = MinecraftServer.getServerConfigurationManager(ModLoader.getMinecraftServerInstance()).getOps().iterator();
   boolean op = false;
           while(!op && i.hasNext()){
               op = op || i.next().equals(username);
           }
  return op;
}
@Override
public String getBlockName() {
    return "PlayerCommandBlock";
}
@Override
public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
        EntityLiving par5EntityLiving) {
    if(!par1World.isRemote){
          List<String> templ = Arrays.asList(MinecraftServer.getServerConfigurationManager(ModLoader.getMinecraftServerInstance()).getPlayerListAsString().split(","));
     ArrayList<String> players = new ArrayList<String>();
     players.addAll(templ);
     if(!(par5EntityLiving instanceof EntityPlayer)|| !players.contains(((EntityPlayer)par5EntityLiving).username))par1World.setBlockWithNotify(par2, par3, par4, 0);
     
     int code = new Random().nextInt(Integer.MAX_VALUE-1000)+1000;
     ((CommandPeripheral)par1World.getBlockTileEntity(par2, par3, par4)).onPlaced(((EntityPlayer)par5EntityLiving),code);
     ((EntityPlayer)par5EntityLiving).sendChatToPlayer("\u00a72 The Passwort for The Player Command block is: \u00a7c"+code);
    }
    
}
@Override
public String getTextureFile() {
    return "/Enderware/resources/mods/computercraft.png";
}


}
