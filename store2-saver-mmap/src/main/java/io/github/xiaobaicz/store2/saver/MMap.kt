package io.github.xiaobaicz.store2.saver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.xiaobaicz.store2.utils.unzip
import io.github.xiaobaicz.store2.utils.zip
import java.io.File
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption

internal class MMap(
    private val table: String,
    private val dir: String,
    private val delegate: MutableMap<String, String> = HashMap()
) : MutableMap<String, String> by delegate {

    companion object {
        private const val CAP_DEF = 1024 * 8L
        private const val SIZE_HEAD = 32
        private const val KEY_TMP_DIR = "java.io.tmpdir"
        internal val tmpDir = System.getProperty(KEY_TMP_DIR, ".")
        private val gson = Gson()
        private val typeToken = object : TypeToken<HashMap<String, String>>() {}
    }

    private val file = File(dir, table).apply {
        println("mmap: ${this.absolutePath}")
    }

    private var cap = 0L

    private var map: MappedByteBuffer

    private var contentSize = 0

    init {
        open().use {
            cap = it.size()
            if (cap == 0L) {
                cap = CAP_DEF
            }
            map = it.map(FileChannel.MapMode.READ_WRITE, 0, cap)
            contentSize = map.int
            if (contentSize > 0) {
                map.position(SIZE_HEAD)
                val buf = ByteArray(contentSize)
                map.get(buf)
                delegate.putAll(gson.fromJson(unzip(buf), typeToken))
            }
        }
    }

    override fun put(key: String, value: String): String? {
        return delegate.put(key, value).apply {
            sync()
        }
    }

    override fun putAll(from: Map<out String, String>) {
        delegate.putAll(from)
        sync()
    }

    override fun remove(key: String): String? {
        return delegate.remove(key).apply {
            sync()
        }
    }

    override fun remove(key: String, value: String): Boolean {
        return delegate.remove(key, value).apply {
            if (this) sync()
        }
    }

    override fun clear() {
        delegate.clear()
        sync()
    }

    private fun sync() {
        val json = gson.toJson(delegate)
        val content = zip(json)
        val size = content.size + SIZE_HEAD
        if (size > cap) {
            while (size > cap) {
                cap = cap shl 1
            }
            open().use {
                map = it.map(FileChannel.MapMode.READ_WRITE, 0, cap)
            }
        }
        contentSize = content.size
        map.clear()
        map.putInt(contentSize)
        map.position(SIZE_HEAD)
        map.put(content, 0, contentSize)
    }

    private fun open(): FileChannel = FileChannel.open(
        file.toPath(),
        StandardOpenOption.CREATE,
        StandardOpenOption.SYNC,
        StandardOpenOption.READ,
        StandardOpenOption.WRITE
    )

}