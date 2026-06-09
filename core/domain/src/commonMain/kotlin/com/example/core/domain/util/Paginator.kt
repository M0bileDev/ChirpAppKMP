package com.example.core.domain.util

class Paginator<Key, Item>(
    // representation of initial generic key
    private val initialKey: Key,
    // function type that notifies when load get started
    private val onLoadUpdated: (Boolean) -> Unit,
    // function type that defines how request looks like
    private val onRequest: suspend (nextKey: Key) -> Result<List<Item>, DataError>,
    // function type that returns next key based on list of items
    private val getNextKey: suspend (List<Item>) -> Key,
    private val onError: suspend (Throwable?) -> Unit,
    private val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) {
}