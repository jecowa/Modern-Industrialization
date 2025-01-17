/*
 * MIT License
 *
 * Copyright (c) 2020 Azercoco & Technici4n
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package aztech.modern_industrialization.compat.rei.nuclear;

import aztech.modern_industrialization.nuclear.INuclearComponent;
import java.util.Collections;
import java.util.List;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;

public class ThermalInteractionDisplay implements Display {

    final INuclearComponent nuclearComponent;
    final ThermalInteractionDisplay.CategoryType type;

    public ThermalInteractionDisplay(INuclearComponent nuclearComponent, CategoryType type) {
        this.nuclearComponent = nuclearComponent;
        this.type = type;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {

        if (nuclearComponent.getVariant() instanceof ItemVariant itemVariant) {
            return Collections.singletonList(EntryIngredient.of(EntryStacks.of(itemVariant.getItem())));
        } else if (nuclearComponent.getVariant() instanceof FluidVariant fluidVariant) {
            return Collections.singletonList(EntryIngredient.of(EntryStacks.of(fluidVariant.getFluid(), 81000)));
        } else {
            throw new IllegalStateException("Unreachable");
        }
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return NeutronInteractionPlugin.THERMAL_CATEGORY;
    }

    public enum CategoryType {
        NEUTRON_EFFICIENCY,
        THERMAL_PROPERTIES,
    }
}
