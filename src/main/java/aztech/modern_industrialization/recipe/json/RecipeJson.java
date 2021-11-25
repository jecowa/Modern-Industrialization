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
package aztech.modern_industrialization.recipe.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.function.Consumer;

/**
 * Marker interface for objects that can be written to JSON recipes with GSON.
 */
public interface RecipeJson {
    Gson GSON = new Gson();

    default String toJson() {
        return GSON.toJson(this);
    }

    default JsonObject toJsonObject() {
        return GSON.toJsonTree(this).getAsJsonObject();
    }

    default byte[] toBytes() {
        return toJson().getBytes();
    }

    void offerTo(Consumer<RecipeJsonProvider> exporter, String recipeId);
}
