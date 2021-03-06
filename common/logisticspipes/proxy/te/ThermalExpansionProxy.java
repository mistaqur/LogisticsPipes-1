package logisticspipes.proxy.te;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cofh.core.RegistryEnderAttuned;
import cofh.lib.transport.EnderRegistry;
import logisticspipes.recipes.CraftingParts;
import logisticspipes.proxy.interfaces.IThermalExpansionProxy;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import cofh.api.transport.IEnderItemHandler;
import cofh.thermalexpansion.block.TEBlocks;
import cofh.thermalexpansion.block.ender.TileTesseract;
import cofh.thermalexpansion.item.TEItems;

public class ThermalExpansionProxy implements IThermalExpansionProxy {

	@Override
	public boolean isTesseract(TileEntity tile) {
		return tile instanceof TileTesseract;
	}

	@Override
	public List<TileEntity> getConnectedTesseracts(TileEntity tile) {
		EnderRegistry registry = RegistryEnderAttuned.getRegistry();
		List<TileEntity> validOutputs = new LinkedList<>();
		if(registry == null) return validOutputs;
		List<IEnderItemHandler> interfaces = registry.getLinkedItemOutputs((TileTesseract) tile);
		if (interfaces == null) {
			return validOutputs;
		}
		validOutputs.addAll(interfaces.stream()
				.filter(object -> object.canReceiveItems() && object.canSendItems() && object instanceof TileEntity)
				.map(object -> (TileEntity) object).collect(Collectors.toList()));
		return validOutputs;
	}

	@Override
	public boolean isTE() {
		return true;
	}

	@Override
	public CraftingParts getRecipeParts() {
		return null;
	}
}
