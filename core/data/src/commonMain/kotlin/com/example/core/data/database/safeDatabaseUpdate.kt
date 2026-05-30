package com.example.core.data.database

import androidx.sqlite.SQLiteException
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result

fun <T> safeDatabaseUpdate(update: () -> T): Result<T, DataError.Local> {
    return try {
        Result.Success(update())
    } catch (_: SQLiteException) {
        Result.Failure(DataError.Local.DISK_FULL)
    }
}