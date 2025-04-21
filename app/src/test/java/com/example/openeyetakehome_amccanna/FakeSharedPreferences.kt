package com.example.openeyetakehome_amccanna

import android.content.SharedPreferences

class FakeSharedPreferences : SharedPreferences {
    private val map = mutableMapOf<String, Any?>()
    private val listeners = mutableSetOf<SharedPreferences.OnSharedPreferenceChangeListener>()

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return map[key] as? Boolean ?: defValue
    }

    override fun getString(key: String?, defValue: String?): String? {
        return map[key] as? String ?: defValue
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return map[key] as? Int ?: defValue
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return map[key] as? Long ?: defValue
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        return map[key] as? Float ?: defValue
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        @Suppress("UNCHECKED_CAST")
        return map[key] as? MutableSet<String> ?: defValues
    }

    override fun contains(key: String): Boolean = map.containsKey(key)

    override fun getAll(): MutableMap<String, *> = map

    override fun edit(): SharedPreferences.Editor = FakeEditor()

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        listener?.let { listeners.add(it) }
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        listener?.let { listeners.remove(it) }
    }

    inner class FakeEditor : SharedPreferences.Editor {
        private val tempMap = mutableMapOf<String, Any?>()
        private val removeKeys = mutableSetOf<String>()
        private var clearAll = false

        override fun putString(key: String?, value: String?): SharedPreferences.Editor {
            key?.let { tempMap[it] = value }
            return this
        }

        override fun putStringSet(
            key: String?,
            values: MutableSet<String>?
        ): SharedPreferences.Editor {
            key?.let { tempMap[it] = values }
            return this
        }

        override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
            key?.let { tempMap[it] = value }
            return this
        }

        override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
            key?.let { tempMap[it] = value }
            return this
        }

        override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
            key?.let { tempMap[it] = value }
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            tempMap[key] = value
            return this
        }

        override fun remove(key: String?): SharedPreferences.Editor {
            key?.let { removeKeys.add(it) }
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            clearAll = true
            return this
        }

        override fun commit(): Boolean {
            apply()
            return true
        }

        override fun apply() {
            if (clearAll) {
                map.clear()
            }

            removeKeys.forEach { map.remove(it) }
            map.putAll(tempMap)

            val changedKeys = tempMap.keys + removeKeys
            for (listener in listeners) {
                changedKeys.forEach {
                    listener.onSharedPreferenceChanged(
                        this@FakeSharedPreferences,
                        it
                    )
                }
            }

            tempMap.clear()
            removeKeys.clear()
            clearAll = false
        }
    }
}
