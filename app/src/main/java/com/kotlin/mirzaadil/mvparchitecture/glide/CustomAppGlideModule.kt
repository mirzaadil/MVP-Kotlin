package  com.kotlin.mirzaadil.mvparchitecture.glide

import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule


import java.io.InputStream

/**
 * @author Mirza Adil
 * created: 2018/12/12
 * desc: Custom
 */

@GlideModule
class CustomAppGlideModule : AppGlideModule() {

    /**
     * GlideBuilder(Engine,BitmapPool ,ArrayPool,MemoryCache).
     *
     * @param context
     * @param builder
     */
    override fun applyOptions(context: Context, builder: GlideBuilder) {


        builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024))

    }


    /**
     * List resolution is turned on
     *
     *
     * Not open here, avoid adding the same modules twice
     *
     * @return
     */
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    /**
     *
     * Register a custom String type BaseGlideUrlLoader for the App
     * @param context
     * @param glide
     * @param registry
     */
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.append(String::class.java, InputStream::class.java, CustomBaseGlideUrlLoader.Factory())
    }
}
