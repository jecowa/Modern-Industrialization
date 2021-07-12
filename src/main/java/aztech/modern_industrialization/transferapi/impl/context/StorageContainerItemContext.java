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
package aztech.modern_industrialization.transferapi.impl.context;

import aztech.modern_industrialization.transferapi.api.context.ContainerItemContext;
import aztech.modern_industrialization.transferapi.api.item.ItemKey;
import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class StorageContainerItemContext implements ContainerItemContext {
    private final ItemKey boundKey;
    private final Storage<ItemKey> storage;

    public StorageContainerItemContext(ItemKey boundKey, Storage<ItemKey> storage) {
        this.boundKey = boundKey;
        this.storage = storage;
    }

    @Override
    public long getCount(TransactionContext tx) {
        try (Transaction nested = tx.openNested()) {
            return storage.extract(boundKey, Long.MAX_VALUE, nested);
        }
    }

    @Override
    public boolean transform(long count, ItemKey into, TransactionContext tx) {
        Preconditions.checkArgument(count <= getCount(tx));

        try (Transaction nested = tx.openNested()) {
            if (storage.extract(boundKey, count, nested) != count) {
                throw new AssertionError("Bad implementation.");
            }

            if (storage.insert(into, count, nested) == count) {
                nested.commit();
                return true;
            }
        }

        return false;
    }
}