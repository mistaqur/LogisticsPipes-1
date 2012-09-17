package logisticspipes.modules;

import logisticspipes.interfaces.ILogisticsModule;
import logisticspipes.interfaces.ISendRoutedItem;
import logisticspipes.interfaces.IWorldProvider;
import logisticspipes.logisticspipes.IInventoryProvider;
import logisticspipes.logisticspipes.modules.SinkReply;
import logisticspipes.main.SimpleServiceLocator;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class ModuleApiaristAnalyser implements ILogisticsModule {
	
	private IInventoryProvider _invProvider;
	private ISendRoutedItem _itemSender;
	private int ticksToAction = 100;
	private int currentTick = 0;
	
	public ModuleApiaristAnalyser() {
		
	}

	@Override
	public void registerHandler(IInventoryProvider invProvider, ISendRoutedItem itemSender, IWorldProvider world) {
		_invProvider = invProvider;
		_itemSender = itemSender;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound, String prefix) {
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound, String prefix) {
		
	}

	@Override
	public int getGuiHandlerID() {
		return -1;
	}

	@Override
	public SinkReply sinksItem(ItemStack item) {
		if(SimpleServiceLocator.forestryProxy.isBee(item)) {
			if(!SimpleServiceLocator.forestryProxy.isAnalysedBee(item)) {
				SinkReply reply = new SinkReply();
				reply.fixedPriority = SinkReply.FixedPriority.APIARIST_Analyser;
				reply.isPassive = true;
				return reply;
			}
		}
		return null;
	}

	@Override
	public ILogisticsModule getSubModule(int slot) {
		return null;
	}

	@Override
	public void tick() {
		if (++currentTick  < ticksToAction) return;
		currentTick = 0;
		
		IInventory inv = _invProvider.getRawInventory();
		if(inv == null) return;
		for(int i=0; i < inv.getSizeInventory(); i++) {
			ItemStack item = inv.getStackInSlot(i);
			if(SimpleServiceLocator.forestryProxy.isBee(item)) {
				if(SimpleServiceLocator.forestryProxy.isAnalysedBee(item)) {
					_itemSender.sendStack(inv.decrStackSize(i,1));
				}
			}
		}
	}
}
